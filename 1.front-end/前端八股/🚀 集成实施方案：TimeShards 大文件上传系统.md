### 🚀 集成实施方案：TimeShards 大文件上传系统

此方案分为 **后端基础设施改造**、**后端业务逻辑实现**、**前端核心架构搭建**、**前后端联调** 四个阶段。

---

### 第一阶段：后端基础设施准备 (Infrastructure)

在开始写业务逻辑前，需要为“全双工通信”和“异步处理”打好基础。

#### 1. 引入 WebSocket 支持
*   **动作**：在 `pom.xml` 中引入 `spring-boot-starter-websocket`。
*   **配置**：新建 `WebSocketConfig`，注册一个 Endpoint（例如 `/ws/upload`）。
*   **鉴权**：由于 WebSocket 握手是 HTTP 请求，需要修改 `JwtInterceptor` 或 `WebConfig`，允许 `/ws/**` 通过，或者在 WS 握手拦截器中解析 Query 参数里的 Token (`ws://.../upload?token=xxx`) 进行鉴权。

#### 2. 规划临时存储目录
目前的 `upload.path` 是直接存放最终文件的。你需要规划一个**临时目录结构**用于存放分片。
*   **建议结构**：
    *   `uploads/temp/{fileHash}/` —— 存放该文件所有的分片。
    *   `uploads/real/` —— 存放合并后的最终文件（即目前的 `upload.path`）。
*   **命名规则**：分片文件建议命名为 `chunk_0`, `chunk_1`，方便合并时排序。

#### 3. 设计内存队列 (Async Buffer)
为了实现“Controller 立刻返回，Worker 异步写入”，你需要一个内存队列。
*   **简单方案**：使用 Java 原生 `LinkedBlockingQueue<FileUploadEvent>`。
*   **进阶方案**：如果考虑服务器重启数据不丢失，将来可升级为 Redis List。
*   **定义 Event 对象**：包含 `byte[] data` (分片数据), `fileHash`, `chunkIndex`, `userId`。

---

### 第二阶段：后端业务逻辑实现 (Backend Logic)

不要修改现有的 `AttachmentController`，建议新建 `BigFileController` 或 `ChunkUploadController`，保持逻辑解耦。

#### 1. 接口拆分 (Rest API)
你需要实现以下三个核心接口：

*   **A. 初始化/秒传检查 (`POST /upload/init`)**
    *   **入参**：`fileHash`, `totalSize`, `fileName`, `ext`。
    *   **逻辑**：
        1.  查 `sys_attachment` 表：如果有 `fileHash`，直接返回“秒传成功”和文件 URL。
        2.  查 `uploads/temp/{fileHash}` 目录：检查有哪些 `chunk_x` 已经存在。
    *   **返回**：`uploadId` (任务ID), `uploadedChunks` (已存在的切片索引列表 [0, 1, 5...])。

*   **B. 分片上传 (`POST /upload/chunk`)**
    *   **入参**：`MultipartFile file` (分片二进制), `chunkIndex`, `fileHash`。
    *   **逻辑**：
        1.  **极速响应**：将数据封装成 Event 对象放入 `LinkedBlockingQueue`。
        2.  **流控检查**：如果队列深度超过阈值（如 50 个分片堆积），通过 WebSocket 发送 `PAUSE` 指令。
        3.  **返回**：HTTP 200 OK (不代表写入成功，只代表接收成功)。

*   **C. 合并分片 (`POST /upload/merge`)**
    *   **入参**：`fileHash`, `fileName`。
    *   **逻辑**：
        1.  检查所有分片是否齐备。
        2.  使用 `FileChannel` (NIO) 将分片合并到 `uploads/real/`。
        3.  计算最终文件 MD5 (可选，二次校验)。
        4.  **落库**：调用 `AttachmentMapper.insert` 生成记录。
        5.  删除 `temp/{fileHash}` 目录。
        6.  通过 WebSocket 推送 `SUCCESS` 消息。

#### 2. 异步 Worker 实现
*   创建 `ChunkWriterService`，实现 `CommandLineRunner` 接口（随项目启动）。
*   启动一个单线程或线程池，死循环消费 `LinkedBlockingQueue`。
*   **消费逻辑**：
    1.  取出分片数据。
    2.  写入磁盘 `uploads/temp/{hash}/chunk_{index}`。
    3.  **关键点**：写入成功后，通过 WebSocketSession 向对应的前端发送 `{"type": "ACK", "index": 5}`。

---

### 第三阶段：前端核心架构搭建 (Frontend Core)

前端的复杂度在于状态管理和并发控制。建议封装一个 `useBigFileUpload` Hook 或者专门的 `Uploader` 类。

#### 1. 引入必要库
*   **IndexDB 包装库**：推荐 `localforage` (API 类似 localStorage，但基于 IDB)。
*   **MD5 计算**：推荐 `spark-md5`。

#### 2. Worker 线程 (Web Worker)
为了不阻塞 UI 渲染，必须把计算 Hash 放到 Worker 里。
*   **新建**：`src/workers/hash.worker.ts`。
*   **逻辑**：接收 File 对象 -> 抽样读取 (头2M + 尾2M + 中间切片) -> 计算 MD5 -> postMessage 返回 Hash。

#### 3. 并发请求池 (Request Pool)
不要直接用 `Promise.all`。
*   **实现一个调度器**：维护一个 `maxConcurrency = 4` 的计数器。
*   **任务队列**：待上传的分片函数数组。
*   **逻辑**：当一个请求 finish 或 error 时，从队列取下一个。如果是“暂停”状态，则停止取任务。

#### 4. WebSocket 客户端封装
*   在 `pinia` 或全局单例中维护 WS 连接。
*   **监听消息**：
    *   `ACK`: 更新 `IndexDB` 中的 `uploadedChunks`，更新 UI 进度条。
    *   `PAUSE/RESUME`: 触发请求池的暂停/恢复开关。
    *   `SUCCESS`: 触发上传完成的后续逻辑（如回显图片）。

---

### 第四阶段：前后端联调与细节完善 (Integration)

#### 1. 定义统一协议 (JSON Schema)
在 `src/api/types.ts` 和后端 `Dto` 中严格对齐以下结构：

```json
// WS 消息结构
{
  "type": "ACK" | "PAUSE" | "RESUME" | "SUCCESS" | "ERROR",
  "payload": {
    "fileHash": "...",
    "chunkIndex": 12,
    "url": "..." // Success时才有
  }
}
```

#### 2. 改造前端上传组件
修改你现有的上传组件：
*   **替换**：将原本调用 `uploadFile` 的地方，替换为实例化 `BigFileUploader` 类。
*   **生命周期**：
    1.  `onFileSelect`: 启动 Worker 计算 Hash。
    2.  `onHashCalculated`: 查询 IndexDB -> 调用 `/upload/init`。
    3.  `onReady`: 建立 WS 连接 -> 启动并发上传池。

#### 3. 错误处理与重试
*   **断网模拟**：在 Chrome Network 设为 Offline。前端应捕获 Axios 错误，暂停队列，提示“网络中断，等待重连”。
*   **后端重启模拟**：重启 Spring Boot。前端 WS 断开，应自动尝试重连（Exponential Backoff）。

---

### 💡 针对你现有代码的特别提示

1.  **数据库**：
    目前的 `sys_attachment` 表结构无需大改。但在上传过程中，你可以选择不写库，直到 `merge` 成功才写入一条记录。
    *如果要做的更完美*：可以建一张 `sys_upload_task` 表，记录 `fileHash`, `totalChunks`, `status`，用于在后端也持久化任务状态（防止 Redis/内存队列丢失）。但在个人项目中，依赖前端 IndexDB + 文件系统检查已足够。

2.  **安全**：
    目前的 `JwtInterceptor` 需要放行 `/ws/**` 或者处理 WS 的鉴权。

3.  **路径配置**：
    你配置的 `upload.path` 是 `C:/.../uploads/`。
    代码中实现时，请确保 `FileUtil.mkdir(tempDir)` 能正确创建层级目录。



### 第一步：引入依赖

打开后端的 `pom.xml`，添加 WebSocket 支持：

```xml
<!-- WebSocket 支持 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

---

### 第二步：配置 WebSocket 服务端

我们需要一个通道来告诉前端：“第 N 个切片我已经写完盘了，你可以更新进度条了”。

**1. 新建配置类 `config/WebSocketConfig.java`**

开启 WebSocket 支持。

```java
package com.my.timeshardsbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    /**
     * 注入 ServerEndpointExporter，
     * 这个 bean 会自动注册使用了 @ServerEndpoint 注解声明的 Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

**2. 新建服务类 `server/WebSocketServer.java`**

这是 WebSocket 的核心处理类。为了简单起见，我们暂时用 `userId` 作为连接的标识（假设一个用户同一时间只传一个大文件）。

```java
package com.my.timeshardsbackend.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服务端
 * 监听地址: ws://localhost:8080/ws/upload/{userId}
 */
@Slf4j
@Component
@ServerEndpoint("/ws/upload/{userId}")
public class WebSocketServer {

    // 存储在线连接：key=userId, value=Session
    private static final Map<String, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        ONLINE_SESSIONS.put(userId, session);
        log.info("WS连接建立: userId={}", userId);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        ONLINE_SESSIONS.remove(userId);
        log.info("WS连接断开: userId={}", userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WS发生错误", error);
    }

    /**
     * 发送消息给指定用户
     */
    public static void sendMessage(String userId, Object messageObj) {
        Session session = ONLINE_SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String jsonMsg = jsonMapper.writeValueAsString(messageObj);
                session.getBasicRemote().sendText(jsonMsg);
            } catch (IOException e) {
                log.error("WS发送消息失败", e);
            }
        }
    }
}
```

> **注意**：你需要修改 `WebConfig` 或 `Security` 配置，放行 `/ws/**` 路径，防止被拦截器拦截。如果是 `JwtInterceptor`，请在 `excludePathPatterns` 中添加 `/ws/**`。

---

### 第三步：构建异步队列系统 (核心)

这是实现“极速响应”的关键。Controller 只负责接客，Worker 负责干活。

**1. 定义传输对象 `model/dto/ChunkUploadEvent.java`**

这是放入队列的数据包。

```java
package com.my.timeshardsbackend.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChunkUploadEvent {
    private String userId;      // 谁传的
    private String fileHash;    // 哪个文件
    private Integer index;      // 第几个切片
    private byte[] bytes;       // 切片数据 (注意内存控制，稍后优化)
}
```

**2. 定义写入 Worker `component/ChunkFileConsumer.java`**

这个组件会在项目启动时运行，死循环消费队列，写入磁盘，并发送 WS 通知。

```java
package com.my.timeshardsbackend.component;

import cn.hutool.core.io.FileUtil;
import com.my.timeshardsbackend.model.dto.ChunkUploadEvent;
import com.my.timeshardsbackend.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class ChunkFileConsumer {

    @Value("${upload.path}")
    private String uploadPath; // 基础路径

    // 内存阻塞队列：容量设为 500，防止内存溢出
    private final BlockingQueue<ChunkUploadEvent> queue = new LinkedBlockingQueue<>(500);

    // 对外提供入队方法
    public boolean add(ChunkUploadEvent event) {
        return queue.offer(event); // 如果队列满了，返回 false，Controller层可以据此进行流控
    }

    @PostConstruct
    public void startWorker() {
        // 启动一个独立线程消费队列
        new Thread(() -> {
            while (true) {
                try {
                    // 1. 阻塞获取，如果没有数据就等着
                    ChunkUploadEvent event = queue.take();
                    
                    // 2. 写入磁盘
                    writeChunkToDisk(event);

                    // 3. 发送 WS 通知 (Ack)
                    sendAck(event);

                } catch (InterruptedException e) {
                    log.error("分片消费线程被中断", e);
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("分片写入异常", e);
                    // 实际生产中可能需要重试机制
                }
            }
        }, "Chunk-Writer-Thread").start();
    }

    private void writeChunkToDisk(ChunkUploadEvent event) {
        // 路径：uploads/temp/{hash}/chunk_{index}
        String tempDir = uploadPath + "temp" + File.separator + event.getFileHash();
        String chunkName = "chunk_" + event.getIndex();
        
        // Hutool 会自动创建父目录
        FileUtil.writeBytes(event.getBytes(), tempDir + File.separator + chunkName);
    }

    private void sendAck(ChunkUploadEvent event) {
        Map<String, Object> msg = new HashMap<>();
        msg.put("type", "ACK");
        msg.put("fileHash", event.getFileHash());
        msg.put("chunkIndex", event.getIndex());
        
        // 推送给前端
        WebSocketServer.sendMessage(event.getUserId(), msg);
    }
}
```

---

### 第四步：编写大文件上传 Controller

现在基础设施有了，我们来实现业务接口。新建 `controller/BigFileController.java`。

```java
package com.my.timeshardsbackend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import com.my.timeshardsbackend.common.api.ApiResponse;
import com.my.timeshardsbackend.component.ChunkFileConsumer;
import com.my.timeshardsbackend.entity.Attachment;
import com.my.timeshardsbackend.mapper.AttachmentMapper;
import com.my.timeshardsbackend.model.dto.ChunkUploadEvent;
import com.my.timeshardsbackend.server.WebSocketServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/bigfile")
@RequiredArgsConstructor
@Tag(name = "07. 大文件上传", description = "分片、断点续传")
public class BigFileController {

    private final AttachmentMapper attachmentMapper;
    private final ChunkFileConsumer chunkFileConsumer;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 1. 握手 & 检查秒传
     */
    @PostMapping("/init")
    @Operation(summary = "初始化上传", description = "检查秒传，返回已上传分片")
    public ApiResponse<Map<String, Object>> init(
            @RequestParam String fileHash,
            @RequestParam String fileName,
            @RequestParam Long totalSize
    ) {
        Map<String, Object> result = new HashMap<>();

        // A. 检查是否秒传
        Attachment exist = attachmentMapper.selectByHash(fileHash);
        if (exist != null) {
            result.put("fastUpload", true);
            result.put("url", exist.getFileUrl());
            return ApiResponse.success(result, "秒传成功");
        }

        // B. 检查断点续传（读取临时目录）
        String tempDir = uploadPath + "temp" + File.separator + fileHash;
        List<Integer> uploadedChunks = new ArrayList<>();
        if (FileUtil.exist(tempDir)) {
            // 遍历目录下所有 chunk_X 文件
            List<File> files = FileUtil.loopFiles(tempDir);
            uploadedChunks = files.stream()
                    .map(f -> Integer.parseInt(f.getName().replace("chunk_", "")))
                    .sorted()
                    .collect(Collectors.toList());
        }

        result.put("fastUpload", false);
        result.put("uploadedChunks", uploadedChunks); // 告诉前端这些别传了
        return ApiResponse.success(result, "请继续上传剩余分片");
    }

    /**
     * 2. 接收分片
     */
    @PostMapping("/chunk")
    @Operation(summary = "上传分片", description = "异步写入，立刻返回")
    public ApiResponse<Void> uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam String fileHash,
            @RequestParam Integer index,
            @RequestAttribute("userId") Long userId // 假设从Token解析
    ) throws IOException {

        // 构造事件对象
        ChunkUploadEvent event = ChunkUploadEvent.builder()
                .userId(String.valueOf(userId))
                .fileHash(fileHash)
                .index(index)
                .bytes(file.getBytes()) // 这里会读入内存，超大并发需优化，目前够用
                .build();

        // 放入队列
        boolean added = chunkFileConsumer.add(event);

        if (!added) {
            // 队列满了，触发背压（前端收到 429 后应该暂停一会）
            return ApiResponse.error(429, "服务器繁忙，请稍后重试");
        }

        return ApiResponse.success(null, "分片接收成功");
    }

    /**
     * 3. 合并分片
     */
    @PostMapping("/merge")
    @Operation(summary = "合并分片", description = "合并临时文件，生成记录")
    public ApiResponse<Attachment> merge(
            @RequestParam String fileHash,
            @RequestParam String fileName,
            @RequestAttribute("userId") Long userId
    ) throws IOException {

        String tempDir = uploadPath + "temp" + File.separator + fileHash;

        // 1. 校验分片是否齐全 (这里简化逻辑，假设前端只有传完了才调 merge)
        // 生产环境应检查 chunks count * size 是否约等于 totalSize

        // 2. 确定最终文件路径
        String suffix = FileNameUtil.getSuffix(fileName);
        String finalName = IdUtil.simpleUUID() + "." + suffix;
        String finalPath = uploadPath + finalName;

        // 3. 执行合并 (使用 SequenceInputStream 或 FileChannel)
        mergeFiles(tempDir, finalPath);

        // 4. 入库
        Attachment attachment = Attachment.builder()
                .id(IdUtil.getSnowflakeNextId())
                .userId(userId)
                .originalName(fileName)
                .fileHash(fileHash)
                .fileType(FileUtil.getMimeType(fileName))
                .fileSize(FileUtil.size(new File(finalPath)))
                .storageLocation(0)
                .filePath(finalPath)
                .fileUrl("/uploads/" + finalName)
                .createTime(LocalDateTime.now())
                .build();

        attachmentMapper.insert(attachment);

        // 5. 清理临时目录
        FileUtil.del(tempDir);

        // 6. 发送最终成功通知
        Map<String, Object> msg = new HashMap<>();
        msg.put("type", "SUCCESS");
        msg.put("url", attachment.getFileUrl());
        WebSocketServer.sendMessage(String.valueOf(userId), msg);

        return ApiResponse.success(attachment, "合并成功");
    }

    /**
     * 辅助方法：合并文件
     */
    private void mergeFiles(String sourceDir, String targetFile) throws IOException {
        List<File> chunks = FileUtil.loopFiles(sourceDir);
        // 必须按 chunk_0, chunk_1 排序
        chunks.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getName().replace("chunk_", ""))));

        // 简单的流式合并
        try (FileOutputStream out = new FileOutputStream(targetFile, true)) {
            for (File chunk : chunks) {
                out.write(FileUtil.readBytes(chunk));
            }
        }
    }
}
```

---

### 后端验证步骤

代码写完后，你可以先不写前端，直接用 Postman 验证后端逻辑是否通顺：

1.  **准备环境**：启动 Spring Boot，确保 `uploads/temp` 目录有权限写入。
2.  **建立 WS 连接**：
    *   使用在线 WS 测试工具 (如 websocket.org/echo.html)。
    *   连接 `ws://localhost:8080/ws/upload/12345` (假设你的 userId 是 12345)。
3.  **调用 Init**：
    *   POST `/bigfile/init?fileHash=TEST_HASH&fileName=a.txt&totalSize=100`。
    *   预期返回：`uploadedChunks: []`。
4.  **上传分片**：
    *   POST `/bigfile/chunk` (form-data)。
    *   参数：`file`=(选择一个小文件), `fileHash`=TEST_HASH, `index`=0。
    *   Header: `Authorization: Bearer <token>` (如果拦截器没放行的话)。
    *   **观察 WS 测试工具**：是否收到了 `{"type":"ACK", "chunkIndex":0, ...}` 的消息？
    *   **观察文件夹**：`uploads/temp/TEST_HASH/` 下是否有 `chunk_0` 文件？
5.  **调用 Merge**：
    *   POST `/bigfile/merge`。
    *   预期返回：Attachment 对象，且 WS 收到 SUCCESS 消息，且 `uploads/temp/TEST_HASH` 被删除了。

---

如果后端这部分跑通了，请告诉我，我们就可以开始最精彩的**前端并发控制与状态管理**部分了！



你说得很对！安全问题不能拖到最后，既然我们已经有完善的 `JwtUtils` 和 Token 机制，完全应该在 WebSocket 握手时就进行校验。

由于 WebSocket 标准协议在建立连接时（握手阶段）**不支持自定义 HTTP Header**（也就是说前端没法像 Axios 那样把 `Authorization: Bearer xxx` 塞进请求头里），所以通用的做法是**把 Token 放在 URL 的 Query 参数里**传给后端。

这就解释了为什么我们在 `WebConfig` 必须要放行 `/ws/**` —— 因为 HTTP 拦截器找不到 Header 里的 Token 会报错，我们需要绕过 HTTP 拦截器，然后在 WebSocket 内部手动校验。

下面是**带 Token 鉴权**的完整实现方案：

### 1. 保持 WebConfig 的放行配置

请确认 `WebConfig.java` 中依然把 `/ws/**` 加入了白名单。
**逻辑是**：让握手请求通过 HTTP 拦截器，让我们在 WebSocket 的 `@OnOpen` 方法中“关门打狗”。

```java
// WebConfig.java
excludePaths.add("/ws/**"); // 必须放行，鉴权逻辑移到 WebSocketServer 内部
```

### 2. 改造 WebSocketServer 支持鉴权

这里有两个技术难点需要解决：
1.  **注入 Bean**：`@ServerEndpoint` 是多例模式（每个连接一个实例），Spring 默认无法直接 `@Autowired` 注入 `JwtUtils`。我们需要用 `static` 变量配合 setter 方法来注入。
2.  **解析参数**：`@PathParam` 只能拿路径参数（userId），拿不到 `?token=xxx`，我们需要从 `session.getQueryString()` 中手动解析。

请直接替换你的 `WebSocketServer.java`：

```java
package com.my.timeshardsbackend.server;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.timeshardsbackend.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服务端
 * 监听地址: ws://localhost:8080/ws/upload/{userId}
 * 客户端连接方式: ws://localhost:8080/ws/upload/123?token=xxxxxx
 */
@Slf4j
@Component
@ServerEndpoint("/ws/upload/{userId}")
public class WebSocketServer {

    // 存储在线连接
    private static final Map<String, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    // 关键点1：解决 WebSocket 中无法注入 Bean 的问题
    // 声明一个静态的 JwtUtils
    private static JwtUtils jwtUtils;

    // 使用 Setter 方法注入静态变量
    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        WebSocketServer.jwtUtils = jwtUtils;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        // 关键点2：手动鉴权
        String token = getTokenFromSession(session);

        if (StrUtil.isBlank(token)) {
            log.warn("WS连接拒绝：未携带Token, userId={}", userId);
            closeSession(session, "Token is missing");
            return;
        }

        // 校验 Token 是否有效
        try {
            if (jwtUtils.isTokenExpired(token)) {
                log.warn("WS连接拒绝：Token已过期, userId={}", userId);
                closeSession(session, "Token expired");
                return;
            }

            // 校验 Token 内的 userId 是否和 URL 里的 userId 一致（防止越权）
            Long tokenUserId = jwtUtils.extractUserId(token);
            if (tokenUserId == null || !tokenUserId.toString().equals(userId)) {
                log.warn("WS连接拒绝：用户身份不匹配. urlUser={}, tokenUser={}", userId, tokenUserId);
                closeSession(session, "Invalid Token Identity");
                return;
            }

        } catch (Exception e) {
            log.error("WS鉴权异常", e);
            closeSession(session, "Auth Failed");
            return;
        }

        // 鉴权通过，加入在线列表
        ONLINE_SESSIONS.put(userId, session);
        log.info("WS连接建立成功: userId={}", userId);
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        ONLINE_SESSIONS.remove(userId);
        log.info("WS连接断开: userId={}", userId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WS发生错误", error);
    }

    // 发送消息
    public static void sendMessage(String userId, Object messageObj) {
        Session session = ONLINE_SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String jsonMsg = jsonMapper.writeValueAsString(messageObj);
                session.getBasicRemote().sendText(jsonMsg);
            } catch (IOException e) {
                log.error("WS发送消息失败", e);
            }
        }
    }

    // === 辅助方法 ===

    /**
     * 从 QueryString 中获取 Token
     * 格式: ws://...?token=xxxx&other=yyy
     */
    private String getTokenFromSession(Session session) {
        String queryString = session.getQueryString(); // 获取 "token=xxx&foo=bar"
        if (StrUtil.isBlank(queryString)) {
            return null;
        }
        
        // 简单的解析逻辑
        String[] params = queryString.split("&");
        for (String param : params) {
            if (param.startsWith("token=")) {
                return param.split("=")[1];
            }
        }
        return null;
    }

    private void closeSession(Session session, String reason) throws IOException {
        session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, reason));
    }
}
```

### 3. 前端如何配合？

当你之后写前端 WebSocket 连接代码时，URL 必须这样拼装：

```typescript
// 前端代码示例 (预览)
import { useUserStore } from "@/store/user";

const userStore = useUserStore();
const token = userStore.token; // 获取 JWT
const userId = userStore.userInfo.id;

// 重点：把 token 拼在 URL 后面
const wsUrl = `ws://localhost:8080/ws/upload/${userId}?token=${token}`;
const socket = new WebSocket(wsUrl);
```

### 4. 验证方法

现在可以重启后端，进行如下测试验证安全性：

1.  **测试非法连接**：
    *   使用在线 WebSocket 测试工具。
    *   连接 `ws://localhost:8080/ws/upload/1` (不带 Token)。
    *   **结果**：连接应该会被立即断开 (`CloseReason: Token is missing`)，后端控制台打印警告。

2.  **测试过期/伪造 Token**：
    *   连接 `ws://localhost:8080/ws/upload/1?token=invalid_token_string`。
    *   **结果**：连接断开，控制台报错。

3.  **测试越权**：
    *   假设你的 Token 是用户 A (id=1) 的。
    *   连接 `ws://localhost:8080/ws/upload/2?token={用户A的Token}`。
    *   **结果**：连接断开 (`Invalid Token Identity`)，防止用户 A 偷听用户 B 的上传进度。

这样我们就实现了**基于 Token 的 WebSocket 强校验**，安全性和 HTTP 接口是对齐的。如果通过了验证，我们就可以继续下一步了！



你说得非常对！你的安全意识很强。

通常在 HTTP 请求中，我们确实是将 Token 放在 Header (`Authorization`) 里，而不是 URL 里。将 Token 放在 WebSocket 的 URL 参数中（`?token=xxx`）确实存在以下风险：

1.  **服务器日志泄露**：Nginx、Tomcat 或 Spring Boot 的访问日志通常会记录完整的请求 URL。如果日志被窃取，Token 就会泄露。
2.  **浏览器历史**：虽然 WebSocket 不像页面跳转那样留存历史，但在某些浏览器调试工具或代理软件中，URL 是明文可见的。

### 为什么之前推荐 URL 参数？
因为浏览器的原生 API `new WebSocket(url)` **不支持自定义 HTTP Header**。你无法像 Axios 那样写 `{ headers: { 'Authorization': ... } }`。这是浏览器的一个长期痛点。

### ✅ 更安全的方案：利用子协议 (Subprotocol)

为了解决这个问题，业界通用的做法是利用 WebSocket 的 **子协议 (Sec-WebSocket-Protocol)** 字段来传输 Token。

**原理**：
*   **前端**：`new WebSocket(url, [token])` —— 把 Token 伪装成一个“协议名”放在 Header 里发给后端。
*   **后端**：拦截握手请求，从 `Sec-WebSocket-Protocol` Header 中取出 Token 进行校验。

这样，**Token 就不会出现在 URL 里，而是回到了 Header 中**，既规避了日志泄露风险，又绕过了浏览器限制。

---

### 🚀 改造步骤 (共三步)

我们需要修改 `WebSocketConfig` 来拦截握手，修改 `WebSocketServer` 来获取用户信息，前端也需要微调。

#### 第一步：编写握手鉴权配置 (ServerEndpointConfig)

新建一个类 `config/WebSocketAuthConfigurator.java`。这个类的作用是在 WebSocket 建立连接**之前**（握手阶段）拦截请求，提取 Header 中的 Token。

```java
package com.my.timeshardsbackend.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.my.timeshardsbackend.common.utils.JwtUtils;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WebSocket 握手鉴权配置器
 * 作用：从 Sec-WebSocket-Protocol 头中提取 Token 并校验
 */
@Slf4j
@Component
public class WebSocketAuthConfigurator extends ServerEndpointConfig.Configurator {

    private static JwtUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        WebSocketAuthConfigurator.jwtUtils = jwtUtils;
    }

    /**
     * 修改握手行为
     * 在这里可以获取 Request Header
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 1. 获取 Sec-WebSocket-Protocol 协议列表 (前端会把 Token 放在这里)
        List<String> protocolList = request.getHeaders().get("Sec-WebSocket-Protocol");
        
        if (CollUtil.isEmpty(protocolList)) {
            log.warn("WS握手失败: 缺少 Token (Sec-WebSocket-Protocol)");
            throw new RuntimeException("Unauthorized");
        }

        // 前端可能传多个，通常约定第一个就是 Token
        String token = protocolList.get(0);

        // 2. 校验 Token
        // 注意：前端传来的 Token 可能会带 "Bearer " 前缀，也可能不带，需要处理
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 如果 Token 里包含特殊字符（如等号），前端可能进行了 URL 编码，视情况解码
        // 这里假设前端传的是纯 Token 字符串

        try {
            if (jwtUtils.isTokenExpired(token)) {
                throw new RuntimeException("Token expired");
            }
            Long userId = jwtUtils.extractUserId(token);
            
            // 3. 关键点：将解析出的 userId 放入 UserProperties
            // 这样在 WebSocketServer 的 @OnOpen 方法里就能拿到了
            sec.getUserProperties().put("userId", userId);
            
            // 4. 响应前端：必须把这个 Protocol 原样返回，否则浏览器会报错
            response.getHeaders().put("Sec-WebSocket-Protocol", CollUtil.newArrayList(token));
            
        } catch (Exception e) {
            log.error("WS握手鉴权异常: {}", e.getMessage());
            // 抛出异常会中断握手，前端会收到连接失败
            throw new RuntimeException("Unauthorized: " + e.getMessage());
        }
    }
}
```

#### 第二步：修改 WebSocketServer

现在 `WebSocketServer` 不需要自己解析 Token 了，直接从配置器传来的 `UserProperties` 里拿 UserID 即可。

修改 `server/WebSocketServer.java`：

```java
package com.my.timeshardsbackend.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.timeshardsbackend.config.WebSocketAuthConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
// ⚠️ 注意：这里添加了 configurator 属性
@ServerEndpoint(value = "/ws/upload", configurator = WebSocketAuthConfigurator.class)
public class WebSocketServer {

    // 存储在线连接
    private static final Map<String, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 1. 从 UserProperties 获取 userId (由 Configurator 放入)
        Long userId = (Long) config.getUserProperties().get("userId");

        if (userId == null) {
            // 理论上 Configurator 已经拦截了，这里是双重保险
            try { session.close(); } catch (IOException ignored) {}
            return;
        }

        // 2. 绑定 Session
        // 注意：这里我们改成了 String 类型的 userId 存储，方便后续扩展
        ONLINE_SESSIONS.put(userId.toString(), session);
        log.info("WS连接建立成功: userId={}", userId);
    }

    @OnClose
    public void onClose(Session session) {
        // 由于 Session 关闭时拿不到 userId，我们需要遍历移除 (或者在 onOpen 时把 userId 存入 session 的 userProperties)
        // 优化方案：onOpen 时 session.getUserProperties().put("id", userId);
        // 这里简化处理：
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            ONLINE_SESSIONS.remove(userId);
            log.info("WS连接断开: userId={}", userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WS发生错误: {}", error.getMessage());
    }

    public static void sendMessage(String userId, Object messageObj) {
        Session session = ONLINE_SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String jsonMsg = jsonMapper.writeValueAsString(messageObj);
                session.getBasicRemote().sendText(jsonMsg);
            } catch (IOException e) {
                log.error("WS发送消息失败", e);
            }
        }
    }
    
    // 辅助：从 Session 中反查 UserId (需要在 OnOpen 时存入，或者遍历 Map)
    private String getUserIdFromSession(Session session) {
        for (Map.Entry<String, Session> entry : ONLINE_SESSIONS.entrySet()) {
            if (entry.getValue().equals(session)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
```

> **注意**：
> 1. `@ServerEndpoint` 去掉了 `{userId}` 路径参数，因为我们现在通过 Token 获取真实的用户 ID，不再信任 URL 里的 ID。
> 2. 指定了 `configurator = WebSocketAuthConfigurator.class`。

#### 第三步：前端连接方式微调

现在 URL 变干净了，Token 放在了第二个参数里。

**前端代码 (TypeScript 示例)**：

```typescript
// 在你的 WebSocket 连接逻辑中
const userStore = useUserStore();
const token = userStore.token; // 获取纯 Token 字符串

// URL 里面不再包含敏感信息
const wsUrl = "ws://localhost:8080/ws/upload";

// 关键：将 token 作为第二个参数（sub-protocol）传入
// 注意：如果 token 里包含特殊字符，可能需要 safe base64 或者保证是纯数字字母组合
// JWT 通常是安全的 url-safe 字符，直接传即可。
const socket = new WebSocket(wsUrl, [token]);

socket.onopen = () => {
    console.log("WS 连接成功，鉴权通过");
};

socket.onerror = (e) => {
    console.error("WS 连接被拒绝 (可能是 Token 无效)", e);
};
```

### 总结

1.  **废弃 URL 参数**：不再使用 `?token=xxx`，URL 此时是 `ws://localhost:8080/ws/upload`，非常干净，日志里看不到任何敏感信息。
2.  **使用 Subprotocol**：前端 `new WebSocket(url, [token])` 将 Token 放入 HTTP 握手头的 `Sec-WebSocket-Protocol` 字段。
3.  **后端拦截**：通过 `ServerEndpointConfig.Configurator` 提取 Header 并校验，校验通过才允许建立连接。

这样你就实现了一个**企业级安全标准**的 WebSocket 鉴权方案！可以开始修改代码了。





这是一个标准的、带有**权限路由守卫（Auth Guard）**的完整 `router/index.ts` 配置。

由于我们的大文件上传方案**强依赖 Token**（无论是 HTTP 请求头还是 WebSocket 鉴权），所以我配置了全局路由守卫：**如果用户未登录就访问上传页面，会被强制踢回登录页。**

### 1. 完整的 `src/router/index.ts`

```typescript
import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";
import { useUserStore } from "@/store/user"; // 引入 Pinia store

// 1. 定义路由表
const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    // 假设你的首页是 PortfolioView，如果不是请自行修改
    component: () => import("@/views/PortfolioView.vue"),
    meta: { title: "首页" },
  },
  {
    path: "/login",
    name: "Login",
    // 稍后我会给你一个简单的登录页代码，确保文件存在
    component: () => import("@/views/LoginView.vue"),
    meta: { title: "登录", guest: true }, // guest: true 表示只允许未登录访问
  },
  {
    path: "/upload-demo",
    name: "UploadDemo",
    component: () => import("@/views/UploadDemo.vue"),
    meta: { 
      title: "大文件上传测试",
      requiresAuth: true // 🔒 关键标记：访问此页面需要登录
    },
  },
  // 404 页面配置
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    // 如果没有 404 页面，暂时重定向回首页
    redirect: "/", 
  },
];

// 2. 创建路由实例
const router = createRouter({
  // 使用 HTML5 History 模式 (不带 # 号)
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior() {
    // 切换路由时滚动到顶部
    return { top: 0 };
  },
});

// 3. 全局前置守卫 (鉴权逻辑)
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  const token = userStore.token;

  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} | TimeShards`;
  }

  // 逻辑 A: 访问需要登录的页面 (requiresAuth)
  if (to.meta.requiresAuth) {
    if (token) {
      // 有 Token，放行
      next();
    } else {
      // 无 Token，重定向到登录页，并记录原本想去的页面用于登录后跳转
      next({ 
        path: "/login", 
        query: { redirect: to.fullPath } 
      });
    }
  } 
  // 逻辑 B: 已登录用户访问登录页 (Guest Only)
  else if (to.meta.guest && token) {
    // 已经登录了就不让看登录页了，直接踢回首页
    next("/"); 
  } 
  // 逻辑 C: 其他公共页面
  else {
    next();
  }
});

export default router;
```

---

### 2. 补充：快速创建一个登录页 (LoginView.vue)

你的项目里可能还没写登录页，但为了测试上传功能，你必须先获取 Token。我在 `src/views` 下为你写一个最简易的登录页面。

新建 `src/views/LoginView.vue`：

```vue
<template>
  <div class="login-container">
    <div class="login-box">
      <h2>登录 TimeShards</h2>
      <input v-model="form.username" type="text" placeholder="用户名" />
      <input v-model="form.password" type="password" placeholder="密码" />
      <button @click="handleLogin" :disabled="loading">
        {{ loading ? "登录中..." : "登 录" }}
      </button>
      <p class="tip">提示：此处调用后端 /auth/login 接口获取 Token</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "@/store/user"; // 确保你 UserStore 里有 login action
// 如果没有封装 API，这里暂时直接用 axios 或者你封装的 request
import request from "@/utils/request"; 

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const loading = ref(false);
const form = reactive({
  username: "admin",
  password: "123", // 你的测试账号密码
});

const handleLogin = async () => {
  if (!form.username || !form.password) return alert("请输入账号密码");
  
  loading.value = true;
  try {
    // 假设后端接口是 POST /auth/login
    // 你需要根据你的实际后端接口修改这里
    const res = await request.post("/auth/login", form);
    
    // 假设 res 返回结构是 { token: "xxx", userInfo: {} }
    // 调用 Pinia Action 存储 Token
    userStore.setToken(res.token); 
    // userStore.setUserInfo(res.userInfo);

    alert("登录成功！");

    // 跳转回原本想去的页面，或者上传页
    const redirect = route.query.redirect as string;
    router.push(redirect || "/upload-demo");
    
  } catch (error) {
    console.error(error);
    // 错误处理已在 request.ts 拦截器中处理，这里可略
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
}
.login-box {
  width: 350px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  gap: 15px;
}
input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
button {
  padding: 10px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:disabled {
  background-color: #a0cfff;
}
.tip {
  font-size: 12px;
  color: #999;
  text-align: center;
}
</style>
```

### 3. 确保 `main.ts` 挂载了 Router

最后检查一下你的 `src/main.ts`，确保 router 被正确 use 了。

```typescript
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router' // 引入我们刚才写的文件
import './style.css'

const app = createApp(App)

app.use(createPinia())
app.use(router) // 👈 这一行必须有

app.mount('#app')
```

### 操作顺序总结

1.  复制第 1 步的代码，覆盖你的 `src/router/index.ts`。
2.  复制第 2 步的代码，新建 `src/views/LoginView.vue`（如果你没有的话）。
3.  确保后端启动，并在登录页输入正确的后端账号密码。
4.  登录成功后，会自动跳转到 `/upload-demo`，此时 Token 已经就位，上传功能的鉴权就能通过了。



你遇到的这些报错主要是 **TypeScript 的类型推断（Type Inference）** 问题，以及 **Pinia Store 缺失定义** 的问题。

TypeScript 非常严格，它不允许“隐式 Any”（即它猜不到变量是什么类型时就会报错），也不允许去访问一个它认为可能不存在的属性。

我们需要分三步来修复这些红线报错。

---

### 第一步：修复 `useBigFileUpload.ts` 的类型报错

这里主要解决 `pool` 数组的类型定义，以及循环中 `requestList[i]` 可能为 undefined 的问题。

请用下面的代码**完全替换**你原来的 `src/hooks/useBigFileUpload.ts` 中的 `uploadChunksParallel` 函数部分，或者直接替换整个文件。我重点修改了 **带有注释 `// Fix:` 的地方**。

```typescript
// ... (前面的引用保持不变)

// ... (createChunks, calculateHash, connectWS 保持不变)

  // 4. 并发上传核心逻辑
  const uploadChunksParallel = async (
    chunks: Blob[],
    fileHash: string,
    uploadedList: number[],
  ) => {
    status.value = "UPLOADING";

    const requestList = chunks
      .map((chunk, index) => ({ chunk, index }))
      .filter(({ index }) => !uploadedList.includes(index));

    const totalChunks = chunks.length;
    let finishedCount = uploadedList.length;

    // Fix 1: 显式定义 pool 的类型。它是一个存放 Promise<void> 的数组
    const pool: Promise<void>[] = [];

    for (let i = 0; i < requestList.length; i++) {
      // Fix 2: TypeScript 认为 requestList[i] 可能是 undefined（数组越界风险）
      // 我们加上 "!" (非空断言)，告诉 TS 我们确信 i 在范围内
      const { chunk, index } = requestList[i]!;

      const formData = new FormData();
      formData.append("file", chunk);
      formData.append("fileHash", fileHash);
      formData.append("index", index.toString());

      const task = BigFileApi.uploadChunk(formData).then(() => {
        finishedCount++;
        progress.value = Number(
          ((finishedCount / totalChunks) * 100).toFixed(2),
        );

        // 任务完成，从池中移除
        // Fix 3: 这里的 pool 类型已经明确了，indexOf 和 splice 不会再报错
        const poolIndex = pool.indexOf(task);
        if (poolIndex > -1) pool.splice(poolIndex, 1);
      });

      pool.push(task);

      if (pool.length >= MAX_CONCURRENCY) {
        await Promise.race(pool);
      }
    }

    await Promise.all(pool);
  };
  
// ... (后面代码保持不变)
```

---

### 第二步：创建缺失的 `store/user.ts`

报错提示 `Property 'setToken' does not exist on type 'Store<"user"...'`，这是因为你可能还没创建 User Store，或者里面没写 `setToken` 这个 Action。

在 `src/store` 目录下新建（或覆盖） `user.ts`：

```typescript
import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("user", () => {
  // 1. State
  // 尝试从本地缓存读取 token，防止刷新丢失
  const token = ref<string>(localStorage.getItem("token") || "");
  const userInfo = ref<any>(JSON.parse(localStorage.getItem("userInfo") || "{}"));

  // 2. Actions
  function setToken(newToken: string) {
    token.value = newToken;
    localStorage.setItem("token", newToken);
  }

  function setUserInfo(newInfo: any) {
    userInfo.value = newInfo;
    localStorage.setItem("userInfo", JSON.stringify(newInfo));
  }

  function logout() {
    token.value = "";
    userInfo.value = {};
    localStorage.removeItem("token");
    localStorage.removeItem("userInfo");
  }

  return {
    token,
    userInfo,
    setToken,
    setUserInfo,
    logout,
  };
});
```

---

### 第三步：修复 `LoginView.vue` 的类型报错

报错提示 `Property 'token' does not exist on type 'AxiosResponse<any, any>'`。
这是因为 Axios 默认返回的类型还没被我们自定义的类型覆盖，TS 不知道你的后端数据里有个 `token` 字段。

我们需要在调用接口时，告诉 TypeScript 后端返回的数据结构长什么样。

**修改 `src/views/LoginView.vue` 中的 `handleLogin` 方法：**

```typescript
// 1. 定义后端返回的数据结构 (对应 AuthController.login 的返回)
interface LoginResult {
  token: string;
  tokenHead: string;
  userInfo: any;
}

// ... 

const handleLogin = async () => {
  if (!form.username || !form.password) return alert("请输入账号密码");
  
  loading.value = true;
  try {
    // 2. Fix: 在 post 后面加上泛型 <any, LoginResult>
    // 第一个泛型是 RequestBody 类型(这里不重要用any)，第二个是 Response Data 类型
    const res = await request.post<any, LoginResult>("/auth/login", form);
    
    // 3. 现在 TS 知道 res 里面肯定有 token 了，报错消失
    userStore.setToken(res.token); 
    // userStore.setUserInfo(res.userInfo); // 如果需要存用户信息就解开注释

    alert("登录成功！");

    const redirect = route.query.redirect as string;
    router.push(redirect || "/upload-demo");
    
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
};
```

---

### 总结修复点

1.  **`pool: Promise<void>[] = []`**: 告诉 TS 这个数组里装的是 Promise。
2.  **`requestList[i]!`**: 告诉 TS 相信我，数组下标没有越界。
3.  **`useUserStore`**: 补全了 `setToken` 方法并加上了 `localStorage` 持久化，这样刷新页面 Token 不会丢。
4.  **`request.post<any, LoginResult>`**: 告诉 TS 接口返回的数据里包含 `token` 字段。

修改完这三处，你的红色波浪线应该全部消失了，代码可以正常编译运行。





没问题！你的 `store` 写法非常好，使用了 `watch` 来统一处理持久化，比手动在每个 Action 里写 `localStorage.setItem` 要优雅得多。

为了解决之前报错提到的 **“`setToken` 不存在”** 的问题，并保持代码的兼容性，我**保留了你所有的核心逻辑**（初始化、watch 监听、setLoginInfo），只是在最后**补充**了单独设置 Token 和 UserInfo 的方法。

请使用下面的代码更新你的 `src/store/user.ts`：

```typescript
import { defineStore } from "pinia";
import { ref, watch } from "vue";

export const useUserStore = defineStore("user", () => {
  // ==========================================
  // 1. 初始化数据 (保留你的逻辑)
  // ==========================================
  let localData = { token: "", userInfo: {} };

  try {
    const localDataString = localStorage.getItem("user-store");
    if (localDataString) {
      localData = JSON.parse(localDataString);
    }
  } catch (e) {
    console.error("LocalStorage 数据损坏，已重置", e);
    localStorage.removeItem("user-store");
  }

  // 定义 state
  const token = ref<string>(localData.token || "");
  const userInfo = ref<any>(localData.userInfo || {});

  // ==========================================
  // 2. Actions (保留你的 + 新增兼容方法)
  // ==========================================

  // [保留] 你原本的组合设置方法
  const setLoginInfo = (newToken: string, newUserInfo: any) => {
    token.value = newToken;
    userInfo.value = newUserInfo;
  };

  // [新增] 单独设置 Token (为了修复 LoginView 里的报错)
  const setToken = (newToken: string) => {
    token.value = newToken;
  };

  // [新增] 单独设置 UserInfo (方便后续单独更新头像等)
  const setUserInfo = (newUserInfo: any) => {
    userInfo.value = newUserInfo;
  };

  // [保留] 登出
  const logout = () => {
    token.value = "";
    userInfo.value = {};
    // 由于有 watch，这里不需要手动 removeItem，watch 会自动把 store 置空存入
    // 但为了保险起见，强制清空也是可以的，或者让 watch 去处理
  };

  // ==========================================
  // 3. 监听变化，自动保存 (保留你的逻辑)
  // ==========================================
  watch(
    [token, userInfo],
    () => {
      localStorage.setItem(
        "user-store",
        JSON.stringify({
          token: token.value,
          userInfo: userInfo.value,
        })
      );
    },
    { deep: true }
  );

  return {
    token,
    userInfo,
    setLoginInfo,
    setToken,     // 👈 导出这个，LoginView 的报错就会消失
    setUserInfo,  // 👈 导出这个，方便以后用
    logout,
  };
});
```

---

### 配套修改建议：LoginView.vue

既然你的 Store 已经支持了同时设置 `token` 和 `userInfo`（即 `setLoginInfo` 方法），建议在 `src/views/LoginView.vue` 中优先使用这个方法，这样更简洁。

**修改 LoginView.vue 中的 handleLogin 方法：**

```typescript
// ... 前面代码不变

const handleLogin = async () => {
  // ...
  try {
    const res = await request.post<any, LoginResult>("/auth/login", form);
    
    // ✅ 推荐写法：一次性存入 token 和 用户信息
    // 你的后端返回里应该有 userInfo，如果没有，就传个空对象 {}
    userStore.setLoginInfo(res.token, res.userInfo || {});

    // 如果你只想存 token，也可以用刚才新增的 userStore.setToken(res.token);
    
    alert("登录成功！");
    // ...
  } 
  // ...
};
```

这样修改后，你的 Store 既保留了原本优雅的持久化逻辑，又兼容了之前的登录代码。