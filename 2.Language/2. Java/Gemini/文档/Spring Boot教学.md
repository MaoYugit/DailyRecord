# 第一阶段：环境搭建与 Spring Boot 初探 (Part 1)

## 1. 前置准备：构建工具与开发环境

在进入 Spring Boot 之前，我们必须先搞定“地基”。在 Java 开发中，最重要的两个工具是 **Maven** (构建工具) 和 **IDEA** (集成开发环境)。

### 1.1 Maven：Java 世界的“管家”
很多新手只知道照抄配置，不懂 Maven 到底是干嘛的。

*   **它是什么？**
    Maven 是一个项目管理和构建自动化工具。
*   **它解决了什么问题？**
    在没有 Maven 的时代，我们需要手动去网上下载各种 `jar` 包（比如 `mysql-connector.jar`），然后复制到项目里。如果 A 包依赖 B 包，B 包依赖 C 包，你得手动全下载下来，非常痛苦。
    Maven 就像一个**自动采购清单**。你只需要在 `pom.xml` 告诉它你需要什么（比如“我要 MySQL 驱动”），它就会自动去中央仓库下载，并把相关的依赖（A依赖B，B依赖C）全自动搞定。
*   **核心概念：G.A.V (坐标)**
    任何一个 Jar 包在 Maven 仓库中都有唯一的“身份证号”，由三个部分组成：
    *   **GroupId**: 组织ID（通常是域名倒写，如 `com.google`）。
    *   **ArtifactId**: 项目名/模块名（如 `guava`）。
    *   **Version**: 版本号（如 `31.0-jre`）。

### 1.2 IDEA：工欲善其事
IntelliJ IDEA 是目前最强大的 Java IDE。对于 Spring Boot 开发，它提供了极致的智能提示。
*   **关键设置建议**：
    *   **Maven 配置**：确保 IDEA 使用的是你本地安装的 Maven（或者 IDEA 自带的），并且仓库地址设置正确（建议配置阿里云镜像源，否则下载速度极慢）。
    *   **JDK 设置**：确保 Project Structure 中选择了 JDK 1.8 或 JDK 17+（Spring Boot 3.0+ 强制要求 JDK 17，初学建议用 Spring Boot 2.7.x 配合 JDK 8 或 11，或者直接上最新版 JDK 17）。

---

## 2. Spring Boot 简介：为什么我们需要它？

这是面试中非常喜欢问的“开场白”问题。

### 2.1 Spring 的痛点（Spring Boot 诞生之前）
在 Spring Boot 出现之前，开发一个 Spring Web 项目（SSM：Spring + Spring MVC + MyBatis）简直是噩梦：
1.  **配置繁琐（XML Hell）**：你需要写大量的 XML 配置文件。比如配置数据库连接、配置事务管理器、配置视图解析器……写错一个字母项目就跑不起来。
2.  **依赖冲突（Dependency Hell）**：你需要手动管理 Jar 包版本。比如 Spring 5.0 可能不兼容 Jackson 2.8，版本对不上就会报错 `ClassNotFoundException`，调试极难。
3.  **部署麻烦**：你需要安装一个 Tomcat 服务器，把项目打包成 `war` 包，丢进 Tomcat 的 webapps 目录下才能运行。

### 2.2 Spring Boot 的解决方案
Spring Boot 不是一个新的框架，它是一个**“工具集”**，它的核心目的就是**简化 Spring 应用的初始搭建和开发过程**。
1.  **起步依赖（Starters）**：它把常用的依赖打包在一起。比如你想做 Web 开发，只需要引入 `spring-boot-starter-web`，它就会自动把 Spring MVC、Tomcat、Jackson 等几十个包全部引入，且版本通过测试，**绝对兼容**。
2.  **自动配置（Auto Configuration）**：这是核心！它能根据你引入的依赖，自动帮你配置好 Bean。
3.  **内嵌服务器**：Spring Boot 把 Tomcat 塞进了 Jar 包里。你不需要安装 Tomcat，直接运行 `main` 方法，或者执行 `java -jar app.jar` 就能跑起来。

> **🎓 面试金句**：
> Spring Boot 的核心价值在于 **“约定大于配置”**，它通过 **起步依赖** 解决了版本管理问题，通过 **自动配置** 解决了繁琐的 XML 配置问题，通过 **内嵌容器** 简化了部署流程。

---

## 3. 核心概念：约定大于配置 (Convention over Configuration)

这句话是 Spring Boot 的灵魂。

*   **含义**：Spring Boot 预先定义了一套“最佳实践”的规范（约定）。如果你遵守这些规范，你几乎不需要写配置；只有当你想要打破规范时，才需要写配置。
*   **举个栗子**：
    *   **约定**：Spring Boot 默认去 `src/main/resources/application.properties` 读取配置文件。如果你把配置文件放在这，它自动生效。你不需要告诉 Spring “去哪里找配置文件”。
    *   **约定**：你引入了 `spring-boot-starter-web`，Spring Boot 就“猜测”你大概率需要一个 Tomcat 和 Spring MVC，于是它自动帮你配好了 `DispatcherServlet` 和端口 8080。
    *   **配置**：如果你不想用 8080 端口，想用 9090，这时候你才需要在配置文件里写一行 `server.port=9090`（这就是在覆盖约定）。

---

## 4. 项目结构解析

当你使用 Spring Initializr (IDEA内置创建向导) 创建项目后，你会看到如下标准结构：

```text
my-blog-backend
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.blog
│   │   │       ├── BlogApplication.java  <-- [1] 启动类
│   │   │       ├── controller            <-- [2] 我们之后放 Controller 的地方
│   │   │       ├── service               <-- [3] 业务逻辑层
│   │   │       └── entity                <-- [4] 数据库实体
│   │   ├── resources
│   │   │   ├── static                    <-- [5] 存放静态文件(js/css/img)
│   │   │   ├── templates                 <-- [6] 存放模板文件(Thymeleaf等)
│   │   │   └── application.properties    <-- [7] 全局配置文件
│   └── test                              <-- 测试代码目录
└── pom.xml                               <-- [8] Maven 依赖管理文件
```

### 重点解析：
1.  **启动类 (`BlogApplication.java`)**：
    *   必须放在**根包**下（例如 `com.example.blog`）。
    *   原因：`@SpringBootApplication` 注解默认会扫描**当前包及其子包**下的所有组件。如果你把它移到子包里，它就扫描不到你的 Controller 了。
2.  **`resources` 目录**：
    *   `static` 和 `templates`：在前后端分离开发（我们要做的方式）中，这两个目录通常是用不到的，因为前端是独立的项目。但在传统开发中用于放页面。
    *   `application.properties` (或 `.yml`)：这是整个系统的大脑，所有修改默认配置的操作都在这里进行。
3.  **`pom.xml`**：
    *   **Parent**：你会发现有个 `<parent>` 标签指向 `spring-boot-starter-parent`。这就像一个“父类”，它里面定义了上百种常用库的版本号。所以你在下面引入依赖时，通常**不需要写版本号**。

---

## 5. RESTful 接口初体验

终于到了写代码的环节！我们要写一个最简单的 Web 接口。

### 5.1 什么是 RESTful？
目前最主流的前后端交互风格。
*   **核心**：把网络上的所有东西都看作“资源”（Resource）。
*   **动作**：使用 HTTP 动词来操作资源。
    *   `GET` /users：获取用户列表
    *   `POST` /users：创建一个用户
    *   `DELETE` /users/1：删除 ID 为 1 的用户

### 5.2 编写代码
在 `src/main/java/com/example/blog` 下创建一个包叫 `controller`，然后新建一个类 `HelloController.java`。

```java
package com.example.blog.controller; // 1. 包声明

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这里的 @RestController 是一个组合注解。
 * 它等同于 @Controller + @ResponseBody。
 * 意思就是：这个类里的方法返回的数据，直接作为 HTTP 响应体（JSON/String）返回给浏览器，
 * 而不是去跳转一个 HTML 页面。
 */
@RestController
public class HelloController {

    /**
     * @GetMapping("/hello")
     * 这是一个“路由映射”。
     * 当浏览器发送 GET 请求访问 /hello 路径时，Spring Boot 会调用这个方法。
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot! 这是一个个人博客的开始。";
    }
}
```

### 5.3 运行与测试
1.  找到 `BlogApplication.java`，点击绿色的三角形运行按钮。
2.  观察控制台（Console），如果你看到 `Started BlogApplication in x.xxx seconds`，说明启动成功。
3.  打开浏览器，访问 `http://localhost:8080/hello`。
4.  你应该能看到页面上显示：“Hello, Spring Boot! 这是一个个人博客的开始。”

---

## 🚀 第一阶段第一次作业 (请务必动手完成)

光看不练假把式。请你完成以下操作，这是我们下一次教学的基础：

1.  **创建项目**：打开 IDEA，新建 Project -> Spring Initializr。
    *   Group: `com.yourname`
    *   Artifact: `blog-backend`
    *   JDK: 选择你本地的 JDK
    *   Dependencies（依赖选择）：只勾选 **Spring Web** 即可。
2.  **编写代码**：按照上面的教程，写一个 `TestController`。
3.  **自我挑战**：
    *   尝试修改 `application.properties`，加入一行 `server.port=8888`。
    *   重启项目，试着访问 `http://localhost:8888/hello`，验证“约定大于配置”被你打破了。

---

# 第一阶段·实战：搭建博客后端脚手架

**目标**：从零创建一个 Spring Boot 项目，并成功在浏览器上打印出 "Hello, My Blog!"。

## 步骤一：使用 Spring Initializr 创建项目

Spring Initializr 是官方提供的快速生成 Spring Boot 项目的工具，IDEA 已经内置了它。

1.  **打开创建向导**：
    *   打开 IDEA，点击 **New Project** (如果你已经打开了其他项目，点击菜单栏 `File` -> `New` -> `Project...`)。
    *   在左侧菜单选择 **Spring Initializr** (如果没有这个选项，请检查 IDEA 是否是 Ultimate 版，或者安装 "Spring Boot Helper" 插件；社区版需访问 [start.spring.io](https://start.spring.io/) 生成 zip 包下载导入，但建议初学者直接用 IDEA 旗舰版或配置好的环境)。

2.  **填写项目基础信息** (配置页面)：
    *   **Name**: `blog-backend` (项目名称)
    *   **Location**: 选择一个你存放代码的硬盘目录
    *   **Language**: `Java`
    *   **Type**: `Maven` (我们要用 Maven 来管理依赖)
    *   **Group**: `com.example` (或者 `com.你的名字`，这是包名的前缀)
    *   **Artifact**: `blog-backend`
    *   **Package name**: `com.example.blog` (系统会自动生成，保持默认即可)
    *   **JDK**: 建议选择 `17` (如果你的本地只有 JDK 8，选 8 也可以，Spring Boot 版本需对应调整)。
    *   **Java**: 选择对应的版本 (如 17)。
    *   -> 点击 **Next**。

3.  **选择依赖 (Dependencies)** —— **关键步骤！**
    *   Spring Boot Version：选择 **3.x.x** (如果你用 JDK 17) 或 **2.7.x** (如果你用 JDK 8)。只要不是 (SNAPSHOT) 或 (M1) 这种测试版就行。
    *   在搜索框输入 `Web`，在下方列表中勾选：
        *   **Spring Web** (这是核心，它包含了 Tomcat 和 Spring MVC)。
    *   -> 点击 **Create**。

> **等待加载**：项目创建后，右下角会有进度条。IDEA 需要花一点时间下载 Maven 依赖（第一次可能需要几分钟）。等右下角的进度条跑完，且 `pom.xml` 文件里没有红色波浪线报错，就算环境准备好了。

---

## 步骤二：编写 HelloController

现在我们需要写代码来处理浏览器的请求。

1.  **定位目录**：
    在左侧 Project 视图中，找到路径：`src` -> `main` -> `java` -> `com.example.blog`。
    你会看到一个自动生成的 `BlogBackendApplication.java` (启动类)。

2.  **创建 Controller 包**：
    *   右键点击 `com.example.blog` 包 -> **New** -> **Package**。
    *   输入名字：`controller`。
    *   **注意**：一定要确保 `controller` 包在 `com.example.blog` 下面，而不是平行！
        *   ✅ 正确：`com.example.blog.controller`
        *   ❌ 错误：`com.example.controller` (这样启动类扫描不到)

3.  **创建类**：
    *   右键点击 `controller` 包 -> **New** -> **Java Class**。
    *   输入名字：`HelloController`。

4.  **编写代码**：
    将下面的代码复制进去（或者手敲一遍以增加记忆）：

```java
package com.example.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1. 标记这是一个控制器，并且返回 JSON/字符串 数据
@RestController
public class HelloController {

    // 2. 定义映射路径：当浏览器访问 /hello 时触发
    @GetMapping("/hello")
    public String hello() {
        // 3. 返回的内容直接显示在浏览器上
        return "Hello, My Blog!";
    }
}
```

---

## 步骤三：运行与验证

1.  **启动项目**：
    *   回到 `src/main/java/com/example/blog/BlogBackendApplication.java`。
    *   找到 `main` 方法，点击行号左边的 **绿色播放小箭头** -> **Run 'BlogBackendApplication'**。

2.  **观察控制台 (Run 面板)**：
    *   你会看到一堆日志滚动。
    *   核心关注最后几行，如果看到类似：
        `Tomcat started on port(s): 8080 (http)`
        `Started BlogBackendApplication in 2.345 seconds`
    *   这就说明启动成功了！

3.  **浏览器测试**：
    *   打开 Chrome 或 Edge 浏览器。
    *   在地址栏输入：`http://localhost:8080/hello`
    *   按下回车。

4.  **预期结果**：
    *   浏览器页面上白底黑字显示：`Hello, My Blog!`

---

## 常见问题排查 (Debug)

如果在实战中遇到了问题，请对照检查：

*   **问题 A：端口被占用 (Port 8080 was already in use)**
    *   **原因**：你可能开启了另一个 Tomcat，或者运行了两个 Spring Boot 实例。
    *   **解决**：
        1.  **简单粗暴**：关掉所有 Java 程序重启 IDEA。
        2.  **修改端口**：打开 `src/main/resources/application.properties`，添加一行：`server.port=8081`，然后重新启动，访问 `localhost:8081/hello`。
*   **问题 B：访问 404 (Whitelabel Error Page)**
    *   **原因**：路径写错了，或者 Controller 包的位置不对。
    *   **检查**：
        1.  URL 是否拼写正确？是 `/hello`。
        2.  **重点检查包结构**：`HelloController` 必须位于启动类所在包的**子包**中。
            *   启动类：`com.example.blog.BlogApplication`
            *   控制器：`com.example.blog.controller.HelloController` (必须包含 `com.example.blog`)

---

**✅ 任务完成标记**
如果你成功在浏览器看到了 "Hello, My Blog!"，恭喜你！你已经完成了一个最基础的 Web 后端应用。

**请回复“完成”或者“下一步”，我们将进入第二阶段：Web 开发核心，开始设计真正的数据接口！**

太棒了！这是第一阶段的最后一部分：**面试考点**。

这两道题是 Spring Boot 面试中**出现率极高**的基础题。如果面试官问你 Spring Boot 基础，几乎 90% 会问到这两个。

下面是详细的解析，整理好的 Markdown 格式，可以直接存入你的笔记。

---

# 第一阶段·面试考点：Spring Boot 核心原理

## 面试题 1：Spring Boot 和 Spring MVC 的区别是什么？

这个问题主要考察你对 **“框架”** 和 **“工具/脚手架”** 之间关系的理解。千万不要说“Spring Boot 是新版本的 Spring MVC”，这是错误的。

### 🎓 标准回答（背诵版）

1.  **定位不同**：
    *   **Spring MVC** 是一个**Web 开发框架**（Framework）。它是 Spring 框架的一个模块，专门用于处理 Web 请求（基于 Servlet API），实现了 Model-View-Controller 模式。它的核心职责是：接收请求 -> 调用业务 -> 返回视图/数据。
    *   **Spring Boot** 是一个**快速开发脚手架/工具**（Tool/Scaffolding）。它的核心职责是简化 Spring 应用（包括 Spring MVC）的初始搭建和开发过程。

2.  **关系**：
    *   Spring Boot **包含** 了 Spring MVC。
    *   当我们引入 `spring-boot-starter-web` 依赖时，Spring Boot 会自动把 Spring MVC 的相关 jar 包引入进来，并自动配置好。
    *   可以理解为：**Spring Boot 是把 Spring MVC 包装好，让你开箱即用。**

3.  **配置差异**：
    *   使用 **Spring MVC** 时，我们需要手动配置 `DispatcherServlet`、视图解析器、HandlerMapping，甚至需要写大量的 XML 或 Java 配置类。
    *   使用 **Spring Boot** 时，基于“约定大于配置”，它自动帮我们配置好了 Tomcat 和 Spring MVC 的核心组件，我们只需要写 Controller 业务代码即可。

### 💡 通俗理解（帮你记忆）
*   **Spring MVC** 就像是**发动机**和**轮子**。如果你只买回来发动机和轮子，你是没法开车的，你得自己焊接底盘、组装线路（手动配置）。
*   **Spring Boot** 就像是一家**汽车组装厂**。它直接把发动机（Spring MVC）、底盘（Spring Context）、油箱（Database）全部组装好，给你一辆整车。你拿到车（Spring Boot 项目），踩油门（写 Controller）就能跑。

---

## 面试题 2：Spring Boot 的启动类注解 `@SpringBootApplication` 包含了哪三个核心注解？

这是一个**“组合注解”**（Meta-Annotation）。面试官问这个是为了考察你是否看过源码，是否理解 Spring Boot 的自动装配入口。

### 🎓 标准回答（背诵版）

`@SpringBootApplication` 是一个组合注解，它主要由以下三个核心注解组成：

1.  **`@SpringBootConfiguration`**
    *   **作用**：标记当前类是一个配置类。
    *   **解释**：它本质上就是 Spring 的 `@Configuration` 注解。这意味着你可以直接在这个启动类里定义 `@Bean` 方法，Spring 容器会扫描并管理这些 Bean。

2.  **`@EnableAutoConfiguration` (最核心)**
    *   **作用**：开启自动配置功能。
    *   **解释**：这是 Spring Boot 的灵魂。它告诉 Spring Boot：“请根据我在 `pom.xml` 里引入的 jar 包，自动猜测并配置我需要的 Bean”。
    *   *例如*：检测到 classpath 下有 `mysql-connector` 且配置文件里有数据库 url，它就会自动配置一个 `DataSource`（数据源）。

3.  **`@ComponentScan`**
    *   **作用**：开启组件扫描。
    *   **解释**：它告诉 Spring 容器扫描当前包及其子包下的所有类。
    *   *注意*：这也就是为什么我们要求将 Controller、Service 等类放在启动类的同级或子包下，否则无法被扫描到，导致 Bean 注入失败。

### 💡 源码截图（加分项）
如果你能在面试时说：“我看过源码，点进去 `@SpringBootApplication` 就能看到这三个注解”，面试官会觉得你很好学。

```java
// 伪代码结构
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration  // 核心 1
@EnableAutoConfiguration  // 核心 2 (灵魂)
@ComponentScan(...)       // 核心 3
public @interface SpringBootApplication { ... }
```

---

**✅ 第一阶段学习完成！**

你已经掌握了：
1.  如何搭建 Spring Boot 环境。
2.  如何写一个基本的 Web 接口。
3.  理解了 Spring Boot 为什么能简化开发。
4.  掌握了应对这一阶段最常见的两个面试题。

---

没问题！既然你希望直接上 MySQL，那我们直接调整策略，按照**企业级开发标准**来走。这意味着我们在讲 Web 开发时，脑子里要时刻关联着数据库操作。

根据你的要求，第二阶段（Web 开发核心 + MySQL）我们将分为 7 次教学：
1.  **知识点一：HTTP 协议基础（GET/POST/PUT/DELETE）** <--- 当前进度
2.  知识点二：三层架构与 MySQL 环境准备（Entity/Mapper/Service/Controller）
3.  知识点三：Spring Boot 整合 MyBatis-Plus（实现真正的 Insert/Select）
4.  知识点四：参数接收详解（PathVariable/RequestParam/RequestBody）
5.  知识点五：统一响应封装（Result 类设计）与 Lombok
6.  实战任务：开发文章管理的 CRUD 接口
7.  面试考点：Web 开发相关高频题

下面是**第二阶段·第一次教学**的详细内容。

---

# 第二阶段：Web 开发核心 (MySQL 版) - Part 1

## 1. HTTP 协议基础：与数据库的映射关系

在传统的 Web 开发中（非 RESTful），很多新手只用 `GET` 和 `POST`。
*   查数据用 GET。
*   删数据、改数据、增数据全用 POST。

虽然这样也能跑通，但这不符合 **RESTful 风格**。在现代的前后端分离开发中，我们遵循**“看人下菜碟”**的原则：**不同的业务动作，要使用对应的 HTTP 请求方式**。

这不仅是为了规范，更是为了让接口具有**自解释性**（看到 Method 就知道你要干嘛）。

### 1.1 四种核心请求方式

我们将 HTTP 动词与数据库（MySQL）的操作（CRUD）进行一一对应：

#### 1. GET（查询）
*   **含义**：从服务器获取资源。
*   **数据库映射**：对应 SQL 的 **`SELECT`** 语句。
*   **特点**：
    *   **参数位置**：通常跟在 URL 后面（如 `?id=1`）。
    *   **安全性**：它是“安全”的，因为它只读数据，不会修改服务器上的数据。
    *   **幂等性**：是（请求 1 次和请求 100 次，结果应该是一样的）。
*   **博客场景**：
    *   获取文章列表 (`GET /articles`)
    *   获取文章详情 (`GET /articles/1`)

#### 2. POST（新增）
*   **含义**：在服务器上创建一个新资源。
*   **数据库映射**：对应 SQL 的 **`INSERT`** 语句。
*   **特点**：
    *   **参数位置**：放在 **Request Body**（请求体）中，通常是 JSON 格式。
    *   **安全性**：不安全，因为它会改变数据。
    *   **非幂等**：如果你连续发送两次同样的 POST 请求，服务器会创建**两条**内容一样但 ID 不同的数据（除非你有唯一索引限制）。
*   **博客场景**：
    *   发布一篇新文章 (`POST /articles`) -> 数据库多了一行记录。

#### 3. PUT（修改）
*   **含义**：更新服务器上的资源（通常是全量更新）。
*   **数据库映射**：对应 SQL 的 **`UPDATE`** 语句。
*   **特点**：
    *   **参数位置**：放在 **Request Body** 中。
    *   **幂等性**：是。比如你把 ID=1 的文章标题改为 "Hello"，无论你请求多少次，ID=1 的标题始终是 "Hello"，状态不再改变。
*   **博客场景**：
    *   修改文章内容 (`PUT /articles`) -> 数据库里的那行记录被修改了。

#### 4. DELETE（删除）
*   **含义**：从服务器删除资源。
*   **数据库映射**：对应 SQL 的 **`DELETE`** 语句。
*   **特点**：
    *   **参数位置**：通常将 ID 放在 URL 路径中（如 `/articles/1`）。
    *   **幂等性**：是。删除了 ID=1 的文章，第一次成功，第二次可能会报“不存在”，但最终结果都是“服务器上没有 ID=1 的文章了”，状态一致。
*   **博客场景**：
    *   删除一篇文章 (`DELETE /articles/1`) -> 数据库里那行记录没了。

---

## 2. Spring Boot 中的注解映射

在 Spring Boot 的 Controller 中，我们使用特定的注解来接收这四种请求。

| HTTP Method | Spring Boot 注解 | 对应数据库操作 | 典型场景             |
| :---------- | :--------------- | :------------- | :------------------- |
| **GET**     | `@GetMapping`    | SELECT         | 查询列表、详情       |
| **POST**    | `@PostMapping`   | INSERT         | 登录、注册、发布文章 |
| **PUT**     | `@PutMapping`    | UPDATE         | 修改密码、修改文章   |
| **DELETE**  | `@DeleteMapping` | DELETE         | 删除评论、删除文章   |

### 代码示例预览
虽然我们还没连数据库，但你需要先看懂这种**Controller 的写法风格**。

假设我们有一个 `ArticleController`（文章接口管理）：

```java
@RestController
@RequestMapping("/articles") // 统一的前缀，所有接口都是 /articles 开头
public class ArticleController {

    // 1. 查询：GET /articles
    @GetMapping
    public String listArticles() {
        return "执行 SQL: SELECT * FROM tb_article";
    }

    // 2. 新增：POST /articles
    @PostMapping
    public String publishArticle() {
        return "执行 SQL: INSERT INTO tb_article ...";
    }

    // 3. 修改：PUT /articles
    @PutMapping
    public String updateArticle() {
        return "执行 SQL: UPDATE tb_article SET ...";
    }

    // 4. 删除：DELETE /articles/{id}
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable Integer id) {
        return "执行 SQL: DELETE FROM tb_article WHERE id = " + id;
    }
}
```

---

## 3. 为什么要注意区分 PUT 和 POST？

这是初学者最容易混淆的点，也是面试的坑。

*   **场景区分**：
    *   如果你是**新建**一个原本不存在的事物（比如注册新用户），用 **POST**。
    *   如果你是**修改**一个已经存在的事物（比如修改用户昵称），用 **PUT**。
*   **URL 风格上的区别**：
    *   POST 往往针对**集合**：`POST /articles`（在文章集合里增加一个）。
    *   PUT 往往针对**具体个体**：`PUT /articles/1`（更新 ID 为 1 的那个文章）。
    *   *(注：实际开发中，为了方便，有时候修改操作 PUT/POST 都会用到，但遵循 RESTful 规范是最好的习惯)*。

---

## 📝 第一阶段补充练习（可选，脑补即可）

假设我们要设计一个“用户管理”模块，请你根据上面的知识，思考以下接口应该用什么 HTTP 方法？

1.  用户注册（填表单提交）： `____ /users`
2.  查看某个用户的个人主页： `____ /users/{id}`
3.  用户注销（删除账号）： `____ /users/{id}`
4.  用户修改自己的签名： `____ /users`

*(答案：POST, GET, DELETE, PUT)*

---

**下节预告**：
理论只是铺垫。下节课我们将进入核心环节：**知识点二：三层架构与 MySQL 环境准备**。
我们要开始创建 `User` 实体类，配置 MySQL 连接，并搭建 Controller-Service-Mapper 的代码骨架！请确保你的电脑上已经安装好了 **MySQL** 数据库。

你是对的！非常抱歉，我刚才为了急于引入数据库，擅自调整了顺序。

按照我们最初的大纲，确实应该先彻底搞定 **Web 层的数据交互**。因为在连接数据库之前，我们必须先学会**“如何从前端拿到数据”**。如果你连前端传来的 ID、用户名、JSON 数据都接不到，那连接数据库也没东西可存。

现在我们进行**第二阶段·第二次教学**。

---

# 第二阶段：Web 开发核心 - Part 2

## 知识点：参数接收详解

Spring Boot 提供了多种方式来接收前端传来的数据。在实际开发中，最常用的就是以下三种注解。我们会通过代码演示它们的区别和使用场景。

**为了演示方便，请在你的项目中新建一个 `ParamController.java`。**

### 1. @PathVariable：获取路径上的参数

*   **场景**：用于获取 URL 路径中动态变化的部分。通常用于 RESTful 风格的接口，比如获取详情、删除数据。
*   **特征**：参数写在 URL 的 `/` 后面。
*   **例子**：
    *   `http://localhost:8080/articles/1` (获取 ID 为 1 的文章)
    *   `http://localhost:8080/articles/1024` (获取 ID 为 1024 的文章)

#### 代码示例：
```java
@RestController
@RequestMapping("/param")
public class ParamController {

    /**
     * 1. @PathVariable 演示
     * 请求 URL: GET http://localhost:8080/param/articles/1
     * 
     * {id} 是占位符，必须与 @PathVariable("id") 对应。
     * 如果变量名和占位符一致，("id") 可以省略。
     */
    @GetMapping("/articles/{id}")
    public String getArticleDetail(@PathVariable Integer id) {
        return "你请求的文章 ID 是: " + id;
    }
}
```

### 2. @RequestParam：获取查询参数 (Query String)

*   **场景**：用于获取 URL 中 `?` 后面的参数。通常用于筛选、分页、搜索。
*   **特征**：参数以 `key=value` 形式存在，多个参数用 `&` 连接。
*   **例子**：
    *   `http://localhost:8080/articles?page=1&size=10` (获取第1页，每页10条)
    *   `http://localhost:8080/search?keyword=spring` (搜索关键词 spring)

#### 代码示例：
```java
    /**
     * 2. @RequestParam 演示
     * 请求 URL: GET http://localhost:8080/param/search?keyword=Java&page=1
     * 
     * required = false 代表该参数可以不传（不传则为 null，除非设置了 defaultValue）。
     */
    @GetMapping("/search")
    public String search(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
    ) {
        return "你正在搜索关键词: " + keyword + ", 第 " + page + " 页";
    }
```

### 3. @RequestBody：获取 JSON 对象 (重点)

*   **场景**：用于接收复杂的表单数据，通常用于 **POST** (新增) 或 **PUT** (修改) 请求。
*   **特征**：数据不在 URL 里，而在 **Request Body (请求体)** 中，格式通常是 JSON。
*   **注意**：前端发送请求时，Header 必须设置 `Content-Type: application/json`。

为了演示这个，我们需要先定义一个简单的 Java 类（用来承载数据）。你可以把这个类写在 `ParamController.java` 的下面，或者单独建一个文件。

**准备一个简单的实体类：**
```java
// 简单的用户类，用来接收 JSON 数据
class UserParam {
    private String username;
    private String password;
    private Integer age;

    // 必须要有 Getter/Setter，Spring 才能把 JSON 塞进去
    // (如果装了 Lombok，可以直接用 @Data)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    @Override
    public String toString() {
        return "UserParam{username='" + username + "', age=" + age + "}";
    }
}
```

#### 代码示例：
```java
    /**
     * 3. @RequestBody 演示
     * 请求 URL: POST http://localhost:8080/param/users
     * 请求体 (Body): {"username": "admin", "password": "123", "age": 20}
     */
    @PostMapping("/users")
    public String addUser(@RequestBody UserParam user) {
        // Spring Boot 会自动把 JSON 字符串转成 UserParam 对象
        return "接收到的用户数据: " + user.toString();
    }
```

---

## 总结：三种方式对比表 (面试必问)

| 注解              | 数据位置      | 对应 URL 示例     | 适用场景             | HTTP Method |
| :---------------- | :------------ | :---------------- | :------------------- | :---------- |
| **@PathVariable** | URL 路径中    | `/users/1`        | 获取单条详情、删除   | GET, DELETE |
| **@RequestParam** | URL 问号后    | `/users?type=vip` | 搜索、分页、简单筛选 | GET         |
| **@RequestBody**  | 请求体 (Body) | (URL 看不到数据)  | 提交表单、复杂数据   | POST, PUT   |

---

## 🧪 你的实操建议

这里有一个小门槛：**`@RequestBody` 怎么测试？**
因为浏览器地址栏只能发 GET 请求，不能发带有 JSON Body 的 POST 请求。

1.  **对于 `@PathVariable` 和 `@RequestParam`**：
    *   你可以直接用浏览器访问 URL 进行测试。
2.  **对于 `@RequestBody`**：
    *   你需要下载 **Postman** (或者 Apifox)。
    *   或者，如果你不想下载软件，可以使用 IDEA 自带的 **HTTP Client**。
        *   在 IDEA 里右键 -> New -> File -> `test.http`。
        *   输入以下内容点击运行：
            ```http
            POST http://localhost:8080/param/users
            Content-Type: application/json
            
            {
              "username": "test_user",
              "password": "123",
              "age": 18
            }
            ```

没问题！这是**第二阶段·第三次教学**。

这节课非常关键。很多初学者写代码，喜欢把所有逻辑（接收参数、判断逻辑、查数据库）全写在 Controller 里，这是**大忌**。

我们要学习企业级开发中最标准的**三层架构**。学会这个，你的代码结构才算“入门”了。

---

# 第二阶段：Web 开发核心 - Part 3

## 知识点：三层架构 (Controller -> Service -> Dao)

### 1. 什么是三层架构？
为了让代码各司其职，易于维护，我们将后端代码划分为三个层次。

我们可以用**“餐厅”**来做一个生动的比喻：

1.  **Controller (控制层) —— 餐厅服务员**
    *   **职责**：接待客人（浏览器/前端）。
    *   **工作**：
        *   接收客人的菜单（接收 HTTP 请求和参数）。
        *   检查菜单有没有填错（参数校验）。
        *   把菜单交给厨师（调用 Service 层）。
        *   把做好的菜端给客人（返回响应数据）。
    *   **原则**：**Controller 层尽量薄**，不要在这里写复杂的业务逻辑（比如计算、判断）。

2.  **Service (业务层) —— 餐厅厨师**
    *   **职责**：核心业务逻辑处理。
    *   **工作**：
        *   拿到服务员的单子，开始做菜（处理业务）。
        *   如果需要食材，就去仓库拿（调用 Dao/Mapper 层）。
        *   比如：注册用户时，在这里判断“用户名是否已存在”、“密码是否需要加密”。
    *   **原则**：它是整个系统的核心大脑。

3.  **Dao / Mapper (持久层) —— 仓库管理员**
    *   **职责**：只负责和数据库打交道。
    *   **工作**：
        *   厨师要鸡蛋，它就去冷库拿鸡蛋（执行 SQL 查询）。
        *   厨师做好了酱料要存起来，它就放进柜子（执行 SQL 插入/更新）。
    *   **原则**：只做 CRUD（增删改查），不负责业务逻辑判断。

---

### 2. 项目结构搭建

请在你的 `blog-backend` 项目中，按照以下结构新建包（Package）和类。
*(假设根包是 `com.example.blog`)*

```text
src/main/java/com/example/blog
├── controller          // 存放 Controller
│   └── UserController.java
├── service             // 存放 Service 接口
│   ├── UserService.java
│   └── impl            // 存放 Service 实现类
│       └── UserServiceImpl.java
├── dao                 // 存放 Dao/Mapper (先用模拟数据)
│   └── UserMapper.java // 以后连接数据库时，这里通常是接口
└── entity              // 存放实体类
    └── User.java
```

> **注意**：
> 1. `Service` 层通常设计为 **接口 (Interface) + 实现类 (Impl)** 的模式，这是为了解耦和方便扩展（面向接口编程）。
> 2. `Dao` 层在 MyBatis 框架中通常称为 `Mapper`。我们在本节课为了演示流程，先用普通类模拟数据库操作。

---

### 3. 代码实战：实现一个“查询用户”的功能

我们按照 **从下往上** (Entity -> Dao -> Service -> Controller) 的顺序来写代码。

#### 第一步：准备实体类 (Entity)
在 `entity` 包下新建 `User.java`。

```java
package com.example.blog.entity;

// 对应数据库里的 tb_user 表
public class User {
    private Integer id;
    private String username;
    private Integer age;

    // 构造方法
    public User(Integer id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    // 省略 Getter/Setter 方法 (请自行用 IDEA 生成：右键 -> Generate -> Getter and Setter)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}
```

#### 第二步：编写 Dao/Mapper 层 (仓库)
在 `dao` 包下新建 `UserDao.java`。这里我们暂时模拟数据库。

```java
package com.example.blog.dao;

import com.example.blog.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Repository 注解：
 * 告诉 Spring，这是一个“仓库”组件，请把它加入到 IOC 容器中管理。
 */
@Repository
public class UserDao {

    // 模拟根据 ID 查询数据库
    public User getUserById(Integer id) {
        // 假装这是从 MySQL 查出来的数据
        if (id == 1) {
            return new User(1, "Admin", 18);
        }
        return new User(id, "Unknown", 0);
    }
}
```

#### 第三步：编写 Service 层 (厨师)
这里我们要分两步：先写接口，再写实现类。

1.  在 `service` 包下新建接口 `UserService.java`：
    ```java
    package com.example.blog.service;
    import com.example.blog.entity.User;
    
    public interface UserService {
        User findUser(Integer id);
    }
    ```

2.  在 `service/impl` 包下新建实现类 `UserServiceImpl.java`：
    ```java
    package com.example.blog.service.impl;
    
    import com.example.blog.dao.UserDao;
    import com.example.blog.entity.User;
    import com.example.blog.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    
    /**
     * @Service 注解：
     * 告诉 Spring，这是一个“业务”组件。
     */
    @Service
    public class UserServiceImpl implements UserService {
    
        /**
         * @Autowired：
         * 依赖注入。告诉 Spring：“把刚才那个 UserDao 的实例注入进来，我要用”。
         * 厨师需要仓库管理员的配合。
         */
        @Autowired
        private UserDao userDao;
    
        @Override
        public User findUser(Integer id) {
            // 业务逻辑：可以在这里加判断，比如 id < 0 抛出异常等
            System.out.println("Service层：正在处理业务逻辑...");
            return userDao.getUserById(id);
        }
    }
    ```

#### 第四步：编写 Controller 层 (服务员)
在 `controller` 包下新建 `UserController.java`。

```java
package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 注入 Service。
     * 注意：这里推荐注入的是接口（UserService），而不是实现类。
     * 服务员只知道找“厨师”，不关心具体是哪位厨师（多态）。
     */
    @Autowired
    private UserService userService;

    // 接收前端请求：GET /users/1
    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        System.out.println("Controller层：接收到请求，ID=" + id);
        // 调用 Service
        return userService.findUser(id);
    }
}
```

---

### 4. 运行流程图解

当你写完上述代码并运行项目，浏览器访问 `http://localhost:8080/users/1` 时，数据的流向是这样的：

1.  **浏览器** 发送请求 ->
2.  **UserController** (接收 ID) -> 呼叫 Service
3.  **UserService** (处理业务) -> 呼叫 Dao
4.  **UserDao** (查询数据 `new User(...)`) -> 返回数据
5.  **UserService** -> 返回数据
6.  **UserController** -> 序列化成 JSON
7.  **浏览器** 显示 JSON：`{"id":1, "username":"Admin", "age":18}`

---

### 💡 重点知识总结

*   **IOC (控制反转)**：注意看代码里的 `@Service`, `@Repository`, `@RestController`。这些注解就是告诉 Spring：“把这些类交给你管理，生成实例放进容器里”。我们不再需要手动 `new UserServiceImpl()`。
*   **DI (依赖注入)**：注意看 `@Autowired`。这是告诉 Spring：“在这个类里，我需要用到另一个被你管理的类，请帮我自动赋值进来”。

### 🧪 今日作业
1.  按照上面的代码，完整创建 `entity`, `dao`, `service`, `controller` 四个包。
2.  将代码复制进去，确保没有报错。
3.  运行项目，访问 `http://localhost:8080/users/1`，看是否成功返回 JSON。
4.  **思考题**：如果我在 `UserDao` 里把 `if (id == 1)` 改成 `if (id == 88)`，我应该访问哪个 URL 才能得到数据？

这是**第二阶段·第四次教学**。

在上一节课中，我们完成了三层架构的搭建。但你可能发现了一个问题：
现在的接口返回很“随意”。有时候返回一个 User 对象，有时候返回一个 String 报错信息。

前端开发同学会很抓狂：“大哥，我怎么知道这个请求是成功还是失败？我要不要弹窗报错？你的数据格式一会是 `{}` 一会是 `""`，我怎么解析？”

为了体现**专业性**，我们需要制定一套**“统一响应规范”**。无论接口成功还是失败，永远返回固定的 JSON 格式。

---

# 第二阶段：Web 开发核心 - Part 4

## 知识点一：神器 Lombok (偷懒专用)

在写 `Result` 类之前，我们先引入一个在 Java 开发中几乎**必装**的插件：**Lombok**。
它的作用是：**通过注解自动生成 Getter/Setter/构造方法/toString**，让代码极其简洁。

### 1. 引入依赖
打开项目根目录的 `pom.xml`，在 `<dependencies>` 标签内加入：

```xml
<!-- Lombok 插件核心依赖 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

> **💡 提示**：添加完后，记得点击 IDEA 右上角的 **Maven 刷新图标** (像个小循环箭头的那个) 来下载依赖。
> 此外，确保你的 IDEA 已经安装了 **Lombok 插件**（新版 IDEA 通常默认已安装，如果没有，去 Settings -> Plugins 搜索 Lombok 安装并重启）。

### 2. Lombok 常用注解
*   `@Data`：自动生成 Getter, Setter, toString, equals, hashCode。
*   `@NoArgsConstructor`：自动生成无参构造方法。
*   `@AllArgsConstructor`：自动生成全参构造方法。

---

## 知识点二：统一响应结果 Result\<T\>

我们要设计的返回格式通常长这样：

**成功时：**
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "username": "Admin"
  }
}
```

**失败时：**
```json
{
  "code": 500,
  "msg": "用户名已存在",
  "data": null
}
```

### 1. 创建 Result 类
请在 `com.example.blog` 包下创建一个新包 `common` (通用模块)，并在其中新建 `Result.java`。

```java
package com.example.blog.common;

import lombok.Data;

/**
 * 统一响应类
 * <T> 是泛型，代表 data 里的数据类型不确定，可能是 User，可能是 List<Article>，也可能没有。
 */
@Data // Lombok 注解：自动生成 Getter/Setter/toString
public class Result<T> {

    private Integer code; // 状态码：200成功，其他失败
    private String msg;   // 提示信息
    private T data;       // 返回的数据

    // 私有化构造方法，禁止外部直接 new，强制使用静态方法
    private Result() {}

    // 1. 成功的静态方法（没数据，比如删除成功）
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "success";
        return result;
    }

    // 2. 成功的静态方法（有数据，比如查询成功）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "success";
        result.data = data;
        return result;
    }

    // 3. 失败的静态方法
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 500; // 暂时统一定义为 500，后期可以定义枚举
        result.msg = msg;
        return result;
    }
}
```

---

## 知识点三：改造 Controller

现在我们有了 `Result` 类，我们需要把之前的 `UserController` 修改一下，让它不再直接返回 `User`，而是返回 `Result<User>`。

**修改 `UserController.java`：**

```java
package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 改造前：public User getUser(...)
    // 改造后：返回值变成了 Result<User>
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Integer id) {
        User user = userService.findUser(id);
        
        // 模拟业务逻辑：如果查不到用户
        if (user == null || user.getId() == null) {
            return Result.error("用户不存在");
        }
        
        // 查到了，用 Result.success 包裹返回
        return Result.success(user);
    }
}
```

---

## 知识点四：改造 Entity (使用 Lombok)

既然引入了 Lombok，那之前的 `User` 实体类里那一堆 Getter/Setter 代码就可以删掉了。

**修改 `User.java`：**

```java
package com.example.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                 // 自动生成 Getter/Setter
@NoArgsConstructor    // 生成无参构造
@AllArgsConstructor   // 生成全参构造
public class User {
    private Integer id;
    private String username;
    private Integer age;
}
```

---

## 🚀 运行与验证

1.  **启动项目**。
2.  **测试成功情况**：
    *   访问 `http://localhost:8080/users/1` (假设 Service 里 id=1 能查到)。
    *   **预期结果**：
        ```json
        {
          "code": 200,
          "msg": "success",
          "data": {
            "id": 1,
            "username": "Admin",
            "age": 18
          }
        }
        ```
3.  **测试失败情况**：
    *   访问 `http://localhost:8080/users/999` (假设 Service 查不到)。
    *   **预期结果**：
        ```json
        {
          "code": 500,
          "msg": "用户不存在",
          "data": null
        }
        ```

---

## 💡 为什么要用泛型 `<T>`？

初学者可能会问：*为什么不直接把 data 定义成 Object？*

如果定义成 `Object data`，虽然也能存任何数据，但在代码中使用时，你（或者前端工具）就丢失了**类型信息**。
使用 `Result<User>`，编译器和 API 文档工具（如 Swagger）就能明确知道：这个接口返回的数据里包含的是一个 `User` 对象，而不是别的东西。这对于大型项目协作至关重要。

---

**下节预告**：
目前我们的数据还是写死在 `UserDao` 里的假数据。
下节课，我们将进行**重头戏**：**知识点五：MySQL + MyBatis-Plus**。
我们将真正连接数据库，让代码和 MySQL 进行交互！请务必确认你的电脑上已经安装了 MySQL 并记住了 root 密码。

你说得非常对！抱歉我刚才有点“抢跑”了。

按照我们最初的大纲，**数据库连接（MySQL + MyBatis-Plus）确实是第三阶段的内容**。在第二阶段，我们应当专注于 Java 代码层面的**Web 逻辑**和**对象封装**。

虽然上一节为了写 `Result` 类简单提了一嘴 Lombok，但作为 Java 开发的神器，它值得专门讲透，特别是 **`@Builder` 构建者模式**，这在企业级开发中构建复杂对象时非常常用。

这是**第二阶段·第五次教学**。

---

# 第二阶段：Web 开发核心 - Part 5

## 知识点：Lombok 进阶 (`@Data` & `@Builder`)

Lombok 是一个通过注解在**编译时**自动修改字节码的工具。它的核心价值在于：**消灭样板代码**。

### 1. `@Data`：这一生，我再也不想写 Getter/Setter

我们在上一节已经见识过它了，这里做一个完整的总结。

*   **包含功能**：等价于同时加上了以下 5 个注解：
    *   `@Getter` / `@Setter`：所有字段的读写方法。
    *   `@ToString`：自动生成 `toString()`，打印对象时不再是哈希码。
    *   `@EqualsAndHashCode`：重写 `equals` 和 `hashCode`（用于集合去重）。
    *   `@RequiredArgsConstructor`：生成包含 `final` 字段的构造方法。
*   **使用场景**：几乎所有的 **实体类 (Entity)**、**DTO (数据传输对象)**、**VO (视图对象)** 都会加上它。

### 2. `@Builder`：优雅的链式调用

这是本节课的重点。当一个类的字段非常多时（比如 10 个字段），使用 `set` 方法赋值会非常冗长：

**😩 传统方式 (没有 Builder)：**
```java
Article article = new Article();
article.setId(1);
article.setTitle("Spring Boot 教程");
article.setAuthor("老王");
article.setContent("内容...");
article.setCreateTime("2023-01-01");
// 写了5行才赋值完，而且中间断开容易乱
```

**😎 Builder 方式 (加上 @Builder)：**
```java
Article article = Article.builder()
    .id(1)
    .title("Spring Boot 教程")
    .author("老王")
    .content("内容...")
    .createTime("2023-01-01")
    .build();
// 像一条链子一样，一气呵成，代码可读性极高
```

---

## 🛠 避坑指南：Lombok 的构造方法陷阱

这是一个新手（甚至老手）常踩的坑，请务必记在笔记里：

> **⚠️ 警告**：
> 当你使用了 `@Builder` 时，Lombok 会自动生成一个全参构造方法，但**会把无参构造方法弄丢**！
>
> 而大多数框架（Spring, MyBatis, Jackson 序列化工具）在反射生成对象时，**强制需要无参构造方法**。如果缺少无参构造，程序运行时会报错。

**✅ 最佳实践组合拳**：
在实体类上，通常这四个注解是**绑定出现**的：

```java
@Data
@Builder
@NoArgsConstructor // 补回无参构造
@AllArgsConstructor // Builder 需要全参构造，所以这个也要加上
public class User { ... }
```

---

## 💻 博客实战应用

现在我们利用 Lombok 来完善我们博客系统的实体类。我们需要为博客文章创建一个 `Article` 类。

请在 `src/main/java/com/example/blog/entity` 下新建 `Article.java`：

```java
package com.example.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    
    private Long id;             // 文章ID (用 Long 防止文章太多 int 不够)
    private String title;        // 标题
    private String author;       // 作者
    private String content;      // 内容
    private LocalDateTime createTime; // 创建时间 (Java 8 推荐的时间类型)
}
```

### 演示：如何在 Controller 中使用 Builder 创建模拟数据

既然第二阶段我们还没连数据库，我们需要在 Controller 里手动 `new` 一些假数据返回给前端。这时候 `@Builder` 就非常爽了。

我们修改一下 `HelloController` 或者新建一个 `ArticleController` 来体验一下：

```java
package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.entity.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    // 获取文章列表（模拟数据）
    @GetMapping
    public Result<List<Article>> getArticles() {
        List<Article> list = new ArrayList<>();

        // 使用 Builder 快速构建第一个对象
        Article article1 = Article.builder()
                .id(1L)
                .title("Spring Boot 入门")
                .author("程序员小王")
                .content("这是第一篇博客的内容...")
                .createTime(LocalDateTime.now())
                .build();

        // 使用 Builder 快速构建第二个对象
        Article article2 = Article.builder()
                .id(2L)
                .title("Java 基础复习")
                .author("程序员小张")
                .content("面向对象编程是很重要的...")
                .createTime(LocalDateTime.now().minusDays(1)) // 昨天的时间
                .build();

        list.add(article1);
        list.add(article2);

        // 返回统一响应格式
        return Result.success(list);
    }
}
```

---

## 🧪 验证环节

1.  确保你的 `Article` 类加上了那四个 Lombok 注解。
2.  将 `ArticleController` 代码写好。
3.  启动项目。
4.  浏览器访问 `http://localhost:8080/articles`。

**预期结果**：
你应该能看到类似下面的漂亮的 JSON 数据：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "title": "Spring Boot 入门",
      "author": "程序员小王",
      "content": "这是第一篇博客的内容...",
      "createTime": "2023-11-20T10:00:00"
    },
    {
      "id": 2,
      "title": "Java 基础复习",
      ...
    }
  ]
}
```

---

**Web 开发核心（理论篇）完结撒花！🎉**

现在你已经掌握了：
1.  怎么接参数 (`@RequestParam` 等)
2.  怎么分层写代码 (三层架构)
3.  怎么规范返回数据 (`Result`)
4.  怎么偷懒写实体类 (Lombok)

太棒了！这是第二阶段的**最终考核**。

完成这个任务后，你就拥有了一个**虽然数据存放在内存中（重启会丢失），但在运行期间完全可用**的博客后端原型。这对于理解数据如何在 Controller 和 Service 之间流转至关重要。

我们将严格按照**三层架构**来实现。

---

# 第二阶段·实战任务：搭建内存版博客系统

**目标**：
1.  完善 `User` 和 `Article` 实体。
2.  模拟数据库（使用 `Map`），实现文章的**发布**和**查询**。
3.  接口必须返回标准格式 `Result<T>`。

## 第一步：准备实体类 (Entity)

确保你的 `entity` 包下有两个类：`User` 和 `Article`。我们要用到 Lombok。

**1. `User.java` (用户)**
```java
package com.example.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
}
```

**2. `Article.java` (文章)**
```java
package com.example.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Long id;
    private String title;
    private String content;
    private String author; // 为了简化，这里暂时存作者名字
    private LocalDateTime createTime;
}
```

---

## 第二步：编写业务层 (Service) —— 模拟数据库

这一步是关键。因为我们还没连 MySQL，所以我们需要在 Service 里定义一个 `static Map` 来充当“内存数据库”。

在 `src/main/java/com/example/blog/service` 包下（或者 `service/impl` 下，如果定义了接口的话。这里为了演示方便，我们直接写一个类）：

**`ArticleService.java`**
```java
package com.example.blog.service;

import com.example.blog.entity.Article;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArticleService {

    // 1. 模拟数据库：使用 Map 存储文章，Key 是 ID，Value 是文章对象
    // 使用 ConcurrentHashMap 保证线程安全
    private static final Map<Long, Article> articleStore = new ConcurrentHashMap<>();

    // 2. 模拟自增 ID 生成器 (类似 MySQL 的 Auto Increment)
    private static final AtomicLong idGenerator = new AtomicLong(0);

    /**
     * 功能：发布文章 (保存到 Map)
     */
    public Article publishArticle(Article article) {
        // 生成一个新 ID
        long newId = idGenerator.incrementAndGet();
        article.setId(newId);
        
        // 补充创建时间
        article.setCreateTime(LocalDateTime.now());

        // 存入“数据库”
        articleStore.put(newId, article);

        return article;
    }

    /**
     * 功能：根据 ID 获取文章
     */
    public Article getArticleById(Long id) {
        return articleStore.get(id);
    }
}
```

---

## 第三步：编写控制层 (Controller) —— 对外暴露接口

现在我们要把 Service 的功能通过 HTTP 接口暴露出去。

在 `src/main/java/com/example/blog/controller` 下新建 `ArticleController.java`：

```java
package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 1. 发布文章接口
     * URL: POST /articles
     * 参数: JSON 格式的 Article 对象 (不需要传 id 和 createTime)
     */
    @PostMapping
    public Result<Article> publish(@RequestBody Article article) {
        // 简单的参数校验
        if (article.getTitle() == null || article.getContent() == null) {
            return Result.error("标题或内容不能为空");
        }

        // 调用业务层
        Article savedArticle = articleService.publishArticle(article);
        
        return Result.success(savedArticle);
    }

    /**
     * 2. 获取文章详情接口
     * URL: GET /articles/{id}
     */
    @GetMapping("/{id}")
    public Result<Article> getDetail(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);

        if (article == null) {
            return Result.error("文章不存在");
        }

        return Result.success(article);
    }
}
```

---

## 第四步：运行与测试 (见证奇迹的时刻)

启动你的 Spring Boot 项目，然后我们开始测试。

### 测试 1：发布文章 (POST)
由于是 POST 请求，不能直接用浏览器。你需要使用 **Postman**、**Apifox** 或者 IDEA 的 **HTTP Client**。

**请求信息：**
*   **Method**: `POST`
*   **URL**: `http://localhost:8080/articles`
*   **Headers**: `Content-Type: application/json`
*   **Body (JSON)**:
    ```json
    {
        "title": "我的第一篇博客",
        "author": "Java新手",
        "content": "Spring Boot 学习起来好像也没有那么难！"
    }
    ```

**预期响应 (Response)**：
```json
{
    "code": 200,
    "msg": "success",
    "data": {
        "id": 1,
        "title": "我的第一篇博客",
        "content": "Spring Boot 学习起来好像也没有那么难！",
        "author": "Java新手",
        "createTime": "2023-xx-xxTxx:xx:xx"
    }
}
```
*(注意：你会发现返回的数据里有了 ID 和 createTime，说明 Service 层的逻辑生效了！)*

### 测试 2：查询文章 (GET)
现在有了 ID 为 1 的文章，我们可以去查询它了。

**请求信息：**
*   **Method**: `GET` (可以直接用浏览器)
*   **URL**: `http://localhost:8080/articles/1`

**预期响应**：
你应该能看到刚才发布的文章。

### 测试 3：查询不存在的文章
**URL**: `http://localhost:8080/articles/999`

**预期响应**：
```json
{
    "code": 500,
    "msg": "文章不存在",
    "data": null
}
```

---

## 🎉 阶段总结

恭喜你！你已经成功完成了**第二阶段**的所有内容。
虽然数据存在内存里（一旦停止运行项目，刚才发的文章就没了），但你的**代码逻辑结构**（Controller -> Service -> Result封装）已经和阿里巴巴、腾讯等大厂的真实项目结构**完全一致**了。

**请务必亲手跑通上面的流程。**

太棒了！这是第二阶段的最后一步。

这里整理的三个问题是面试中**基础中的基础**，也是考察你是否真正理解 Spring 核心思想的关键。面试官问这些问题，通常是想确认：**你是在照着文档敲代码，还是真的懂它背后的逻辑？**

请把下面的内容复制到笔记中，这是你的**面试军火库**。

---

# 第二阶段·面试考点：Web 开发核心与 Spring 原理

## 面试题 1：`@Controller` 和 `@RestController` 的区别？

这是一道经典送分题，但也容易回答得不完整。

### 🎓 标准回答
1.  **组合关系**：
    *   `@RestController` 本质上是一个**组合注解**。
    *   公式：**`@RestController` = `@Controller` + `@ResponseBody`**。
2.  **用途区别**：
    *   **`@Controller`**：是 Spring MVC 最原生的注解。如果不加 `@ResponseBody`，它默认返回的是**视图名称**（String），Spring 会去寻找对应的 HTML/JSP 页面进行跳转。适用于传统的“前后端不分离”页面开发。
    *   **`@RestController`**：专为 RESTful 接口设计。它类中的所有方法返回的数据，都会直接序列化成 **JSON**（或 XML）格式写入 HTTP 响应体中，而不会去解析成页面跳转。适用于现代的“前后端分离”开发。

### 💡 简单理解
*   **`@Controller`**：我想给浏览器看一个**网页**。
*   **`@RestController`**：我想给前端代码（Vue/React）传一串 **JSON 数据**。

---

## 面试题 2：GET 请求和 POST 请求在 Spring Boot 中如何处理参数？

这个问题考察你对 HTTP 协议和 Spring 注解的对应关系。

### 🎓 标准回答
在 Spring Boot 中，处理方式主要取决于参数的位置：

1.  **GET 请求**：
    *   参数通常拼接在 **URL** 中。
    *   **Query 参数**（`?name=zhangsan`）：使用 **`@RequestParam`** 接收。
    *   **Path 参数**（`/users/1`）：使用 **`@PathVariable`** 接收。
    *   *注意：GET 请求通常不包含 Request Body。*

2.  **POST 请求**：
    *   参数通常放在 **Request Body**（请求体）中，格式多为 JSON。
    *   **JSON 数据**：必须使用 **`@RequestBody`** 注解，将 JSON 映射为 Java 对象（Entity/DTO）。
    *   *特殊情况*：如果 POST 请求提交的是传统表单（Content-Type: application/x-www-form-urlencoded），也可以使用 `@RequestParam` 或直接用对象接收（不加 `@RequestBody`），但这在前后端分离中较少见。

### 💡 总结表（面试手写版）
| 请求类型   | 参数位置       | 对应注解        | 场景                     |
| :--------- | :------------- | :-------------- | :----------------------- |
| GET        | URL (?key=val) | `@RequestParam` | 搜索、分页               |
| GET/DELETE | URL Path (/1)  | `@PathVariable` | 获取详情、删除           |
| POST/PUT   | Body (JSON)    | `@RequestBody`  | 新增、修改、提交复杂表单 |

---

## 面试题 3：什么是 Bean？Spring 容器是如何管理 Bean 的（IoC 概念）？

这是 Java 后端最核心的概念，也是最抽象的。一定要用通俗的语言解释清楚。

### 1. 什么是 Bean？
*   **通俗解释**：在 Java 中，我们自己 `new` 出来的对象叫 Object。而在 Spring 中，**被 Spring 容器（Container）管理的对象，就叫做 Bean**。
*   **区别**：
    *   Object：`User user = new User();` —— 生死由你自己控制。
    *   Bean：`@Autowired User user;` —— 生死由 Spring 容器控制（什么时候创建、什么时候销毁、单例还是多例）。

### 2. 什么是 IoC (Inversion of Control, 控制反转)？
*   **核心思想**：**将对象的创建权和管理权，从程序员手中移交给 Spring 容器。**
*   **为什么叫“反转”**：
    *   **正向（传统）**：我要吃法餐（对象），我必须自己买菜、自己做（自己 `new`）。我是主导者。
    *   **反转（IoC）**：我把需求告诉餐厅（Spring 容器）。我要吃的时候，餐厅直接把做好的菜端给我。餐厅变成了主导者，我只是使用者。

### 3. Spring 容器是如何管理的？（实现原理）
*   **扫描**：Spring 启动时，会扫描带有特定注解的类（如 `@Controller`, `@Service`, `@Repository`, `@Component`）。
*   **注册**：将这些类的信息存入 BeanDefinitionMap（一张大表）。
*   **实例化**：Spring 根据这张表，利用 **反射机制 (Reflection)** 帮我们创建对象（`new` 的过程 Spring 替你做了）。
*   **注入 (DI)**：如果这个对象里用到了其他对象（比如 Controller 里有 Service），Spring 会自动把对应的 Bean **注入** 进去（通过 `@Autowired`）。
*   **存储**：做好的 Bean 通常放在一个单例池（Singleton Objects）的 Map 中，随取随用。

---

**✅ 第二阶段完全结束！**

你现在已经具备了 Web 开发的核心思维。
接下来的**第三阶段**，我们将迎来真正的技术分水岭：**数据持久化**。
我们将安装 MySQL，引入 MyBatis-Plus，让你的博客数据真正“落地生根”。

欢迎来到**第三阶段：数据持久化**。这是从“玩具代码”迈向“生产级应用”的关键一步。

在之前的阶段，你的数据存在内存（Map/List）里，项目一重启，数据就没了。现在，我们要把数据存进 **MySQL** 数据库，让它永久保存（持久化）。

这是**第三阶段·第一次教学**。我们将完成博客系统的数据库设计。

---

# 第三阶段：MySQL 基础 - 数据库设计与 SQL

## 1. 核心概念：数据库、表、行、列

如果你熟悉 Excel，那么 MySQL 极其好理解：

*   **数据库 (Database)** = 一个 **Excel 文件** (`blog_db.xlsx`)。
*   **表 (Table)** = Excel 文件里的 **Sheet 工作表** (`tb_user`, `tb_article`)。
*   **列 (Column/Field)** = 表头（`姓名`, `年龄`, `电话`），对应 Java 类中的**属性**。
*   **行 (Row/Record)** = 每一行数据，对应 Java 中的一个**对象**实例。

## 2. 工具准备：IDEA Database 工具

你不需要专门下载 Navicat 或 DBeaver，**IntelliJ IDEA 自带了非常强大的数据库管理工具**。

1.  打开 IDEA，在右侧边栏找到 **Database** 标签（如果没有，点击菜单栏 `View` -> `Tool Windows` -> `Database`）。
2.  点击 **+** 号 -> **Data Source** -> **MySQL**。
3.  填写信息：
    *   **Host**: `localhost`
    *   **User**: `root`
    *   **Password**: 你的 MySQL 密码
4.  **关键一步**：如果是第一次用，底部会有个 `Download missing driver files` 的提示，点击下载驱动。
5.  点击 **Test Connection**，显示绿色勾勾后点击 **OK**。

---

## 3. SQL 实战：搭建博客数据库

我们将使用 SQL 语句（Structured Query Language）来与数据库对话。请在 IDEA 的 Database 控制台（右键连接 -> Open Console）中执行以下代码。

### 3.1 建库 (Create Database)

我们在创建数据库时，必须指定**字符集**，否则存中文可能会变成乱码（`???`）。

```sql
-- 1. 如果不存在 blog_db 数据库，就创建它
-- CHARACTER SET utf8mb4: 支持中文，甚至支持 emoji 表情 🐯
CREATE DATABASE IF NOT EXISTS blog_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 2. 切换到这个数据库
USE blog_db;
```

### 3.2 建表 (Create Table)

我们需要根据之前的 Java 实体类 (`User`, `Article`) 来设计表结构。

> **规范小贴士**：
> 在数据库设计中，表名通常用 `tb_` 开头（如 `tb_user`），字段名通常用 **下划线命名法**（`create_time`），而 Java 里用 **驼峰命名法**（`createTime`）。MyBatis-Plus 会帮我们自动转换。

#### A. 用户表 (`tb_user`)

```sql
DROP TABLE IF EXISTS tb_user; -- 如果表存在，先删掉（防止报错）

CREATE TABLE tb_user (
    -- id: 整数，自动增长(1,2,3...)，主键(唯一标识)
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    
    -- username: 字符串，最长50字符，不能为空
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    
    -- password: 字符串，最长100字符，不能为空
    password VARCHAR(100) NOT NULL COMMENT '密码',
    
    -- create_time: 时间类型，默认值为当前时间
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户表';
```

#### B. 文章表 (`tb_article`)

```sql
DROP TABLE IF EXISTS tb_article;

CREATE TABLE tb_article (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID',
    
    title VARCHAR(100) NOT NULL COMMENT '文章标题',
    
    -- content: 内容通常很长，不要用 VARCHAR，要用 TEXT (支持64KB) 或 LONGTEXT (支持4GB)
    content LONGTEXT NOT NULL COMMENT '文章内容',
    
    author VARCHAR(50) COMMENT '作者',
    
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'
) COMMENT '文章表';
```

---

## 4. 常用 SQL 语句 (CRUD)

建好表后，我们来练习一下基础的 SQL 操作。虽然以后我们用代码（MyBatis）操作，但作为后端，**手写 SQL 是基本功**。

### 4.1 新增 (INSERT)
往用户表里插数据。

```sql
-- 插入一条数据（不需要传 id 和 create_time，数据库会自动生成）
INSERT INTO tb_user (username, password) VALUES ('admin', '123456');
INSERT INTO tb_user (username, password) VALUES ('test_user', '888888');

-- 往文章表插入数据
INSERT INTO tb_article (title, author, content) 
VALUES ('Spring Boot 初体验', 'admin', '今天学会了建表，好开心！');
```

### 4.2 查询 (SELECT)
这是最常用的语句。

```sql
-- 1. 查询所有列
SELECT * FROM tb_user;

-- 2. 查询指定列（推荐，性能更好）
SELECT id, username FROM tb_user;

-- 3. 带条件查询 (WHERE)
SELECT * FROM tb_user WHERE username = 'admin';

-- 4. 模糊查询 (LIKE) -> 找标题里包含 "Spring" 的文章
-- % 代表任意字符
SELECT * FROM tb_article WHERE title LIKE '%Spring%';
```

### 4.3 修改 (UPDATE)
**⚠️ 危险操作**：千万别忘了加 `WHERE` 条件！否则全表都被修改。

```sql
-- 把 id 为 1 的用户密码改成 '654321'
UPDATE tb_user 
SET password = '654321' 
WHERE id = 1;
```

### 4.4 删除 (DELETE)
**⚠️ 危险操作**：同样别忘了 `WHERE`。

```sql
-- 删除 id 为 2 的用户
DELETE FROM tb_user WHERE id = 2;
```

---

## 🧪 课后作业 (实操)

请在你的 MySQL 环境中执行以下操作，为后续的 Java 代码连接做准备：

1.  **执行建库脚本**：创建 `blog_db`。
2.  **执行建表脚本**：创建 `tb_user` 和 `tb_article`。
3.  **初始化数据**：
    *   手动插入 3 个用户。
    *   手动插入 5 篇文章（标题最好带中文，测试有没有乱码）。
4.  **验证**：执行 `SELECT * FROM tb_article;`，确保能看到你刚才插入的数据。

---

这是**第三阶段·第二次教学**。

在上一节课，我们已经在 MySQL 中建好了仓库（`blog_db`）。现在，我们需要在 Spring Boot 项目中修一条路，直通这个仓库。这条路就是 **JDBC 连接**。

同时，为了顺应企业级开发的潮流，我们将把配置文件从 `application.properties` 切换为更易读的 **`application.yml`**。

---

# 第三阶段：MySQL 基础 - 数据源配置 (application.yml)

## 1. 前置作业：引入 MySQL 驱动

配置之前，必须先在 `pom.xml` 中加入 MySQL 的驱动包（也就是开车的司机）。Spring Boot 无法自己凭空连接数据库。

请打开 `pom.xml`，在 `<dependencies>` 中添加：

```xml
<!-- MySQL 驱动 -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- 这里的版本号通常由 Spring Boot 父工程管理，不需要手动写 version -->
```

> **操作**：添加完后，别忘了点击 IDEA 右上角的 **Maven 刷新按钮**。

---

## 2. 认识 YAML 配置文件

目前你的 `src/main/resources` 下应该有一个 `application.properties`。
这种 `key=value` 的格式虽然简单，但在配置项很多时（比如配置数据库、Redis、MyBatis），会显得非常冗长。

**YAML (`.yml`)** 是目前主流的配置格式，它通过**缩进**来表示层级关系，更加清晰。

### 🛠 动手切换
1.  **重命名**：右键 `application.properties` -> Refactor -> Rename。
2.  **改名**：将其修改为 **`application.yml`**。
3.  **清空**：把里面的内容先清空（或者注释掉）。

---

## 3. 编写 JDBC 配置

请将以下内容复制到你的 `application.yml` 中。

**⚠️ 注意：YAML 对缩进要求极高！通常使用 2 个空格，冒号后面必须加一个空格。**

```yaml
spring:
  datasource:
    # 1. 数据库驱动类 (MySQL 8.0+ 使用 cj.jdbc.Driver)
    driver-class-name: com.mysql.cj.jdbc.Driver
    
    # 2. 数据库连接地址 (URL)
    # 格式：jdbc:mysql://IP:端口/数据库名?参数
    # serverTimezone: 设置时区为上海，防止时间差8小时
    # useUnicode & characterEncoding: 强制使用 UTF-8 避免乱码
    # useSSL: 开发环境通常关闭 SSL 安全连接，减少报错
    url: jdbc:mysql://localhost:3306/blog_db?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
    
    # 3. 数据库账号
    username: root
    
    # 4. 数据库密码 (请修改为你自己的密码！！！)
    password: your_password_here
```

### 🔍 配置项深度解析 (面试常问)

1.  **`driver-class-name`**:
    *   旧版 MySQL (5.x) 常用 `com.mysql.jdbc.Driver`。
    *   新版 MySQL (8.x) 必须用 `com.mysql.cj.jdbc.Driver`。
2.  **`url` 中的 `serverTimezone`**:
    *   MySQL 默认时区可能是 UTC（世界标准时间）。如果你存入 `10:00`，取出来可能会变成 `02:00` 或者报错。
    *   必须设置为 `Asia/Shanghai` 才能保证存取时间一致。
3.  **`username/password`**:
    *   这就不用解释了，千万别写错，写错了项目启动会报错 `Access denied`。

---

## 4. 验证连接是否成功

虽然我们还没有写 MyBatis 代码，但我们可以通过**启动项目**来验证配置是否正确。

Spring Boot 启动时，如果检测到引入了数据库驱动，它会尝试配置数据源。如果配置有误，启动会直接失败。

1.  **操作**：运行 `BlogBackendApplication` 的 `main` 方法。
2.  **观察日志**：
    *   **情况 A (成功)**：
        控制台没有出现红色的 `ERROR`，并且看到了 `Tomcat started on port(s): 8080`。这说明 Spring Boot 至少没有因为数据库配置格式错误而崩溃。
        *(注意：Spring Boot 默认是懒加载数据源的，真正报错可能要等到第一次查库时。但在引入 MyBatis 后，启动时就会检查连接)*
    *   **情况 B (失败 - 密码错误)**：
        如果出现 `Access denied for user 'root'@'localhost'`，说明密码错了。
    *   **情况 C (失败 - 库不存在)**：
        如果出现 `Unknown database 'blog_db'`，说明你上一节课的 SQL 没执行，或者数据库名字写错了。

---

## 💡 扩展知识：多环境配置 (了解即可)

在实际开发中，我们会有开发环境 (dev) 和 生产环境 (prod)。
YAML 支持在一个文件里配置多环境（通过 `---` 分隔），或者创建多个文件：
*   `application-dev.yml` (本地开发库)
*   `application-prod.yml` (线上服务器库)

然后在主文件 `application.yml` 中激活：
```yaml
spring:
  profiles:
    active: dev  # 激活开发环境配置
```
*(目前我们只需要一个 `application.yml` 即可)*

---

这是**第三阶段·第三次教学**。

欢迎来到 Java 开发的“快车道”。在过去，使用原始的 MyBatis，我们需要为每张表编写繁琐的 `Mapper.xml` 文件，写一大堆 `<select>`, `<insert>` 标签。

现在，我们选择 **MyBatis-Plus (简称 MP)**。它是国内 Java 界**最主流**的 ORM 框架之一。

它的口号是：**“只做增强不做改变”**。它在 MyBatis 的基础上，帮你把 90% 的单表 CRUD（增删改查）操作自动完成了。

---

# 第三阶段：ORM 框架 - MyBatis-Plus 集成

## 1. 引入依赖 (pom.xml)

MP 也是一个 Starter。
**注意**：引入了 `mybatis-plus-boot-starter` 后，**不要**再引入官方的 `mybatis-spring-boot-starter`，防止版本冲突。

请打开 `pom.xml`，在 `<dependencies>` 中添加：

```xml
<!-- MyBatis-Plus 核心依赖 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version> <!-- 版本号建议用较新的稳定版 -->
</dependency>
```

> **操作**：添加完毕后，记得刷新 Maven。

---

## 2. 实体类改造 (Entity)

MP 需要知道你的 Java 类对应数据库的哪张表。我们需要用到 MP 提供的几个注解。

请修改 `src/main/java/com/example/blog/entity/User.java`：

```java
package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user") // 1. 告诉 MP，这个类对应数据库里的 tb_user 表
public class User {

    /**
     * @TableId: 标记这是主键
     * type = IdType.AUTO: 告诉 MP，主键生成策略是“数据库自增”。
     * (非常重要！因为我们在建表时用了 AUTO_INCREMENT，如果不加这个，MP 默认会生成一个很长的雪花算法 ID)
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;
    
    // 对应数据库的 create_time。
    // MP 会自动把驼峰命名(createTime) 映射为 下划线命名(create_time)，无需额外配置
    private LocalDateTime createTime; 
}
```

> **作业**：请参照上面的写法，自行修改 `Article.java`，加上 `@TableName("tb_article")` 和 `@TableId`。

---

## 3. 编写 Mapper 接口

这是见证奇迹的地方。

在 MyBatis 中，你需要写 SQL。
在 MyBatis-Plus 中，你只需要让你的接口**继承 `BaseMapper<T>`**，你就立刻拥有了 `insert`, `delete`, `update`, `selectById`, `selectList` 等十几个方法！

请新建包 `com.example.blog.mapper`，并创建接口 `UserMapper.java`：

```java
package com.example.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper: 
 * 加上这个注解，Spring Boot 启动时会自动扫描这个接口，
 * 并生成一个代理实现类放入 IOC 容器。
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 此时，这里面虽然是空的，但已经拥有了单表 CRUD 的所有能力！
}
```

> **作业**：同样地，请创建 `ArticleMapper.java`，让它继承 `BaseMapper<Article>` 并加上 `@Mapper` 注解。

---

## 4. 配置 MP (打印 SQL 日志)

MyBatis-Plus 在后台默默帮我们要生成 SQL 语句。为了开发方便（以及排查 bug），我们需要让它把生成的 SQL **打印在控制台**。

打开 `application.yml`，在最下面添加：

```yaml
# MyBatis-Plus 配置
mybatis-plus:
  configuration:
    # 开启控制台 SQL 日志打印
    # 这样当你调用 mapper.selectById(1) 时，控制台会显示：SELECT * FROM tb_user WHERE id = 1
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

---

## 5. 扫描配置 (补充)

虽然我们在接口上加了 `@Mapper`，但为了保险起见（防止以后漏加），通常建议在**启动类**上加一个全局扫描注解。

打开 `BlogBackendApplication.java`：

```java
package com.example.blog;

import org.mybatis.spring.annotation.MapperScan; // 导入包
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 告诉 Spring：去这个包下找 Mapper 接口
@MapperScan("com.example.blog.mapper") 
public class BlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }
}
```

---

## 💡 核心原理解析 (面试考点)

你可能会问：*接口明明是空的，为什么能调用方法？*

这是 **JDK 动态代理** 的功劳。
1.  **BaseMapper** 定义了标准方法（如 `insert`）。
2.  **MyBatis-Plus** 启动时，通过反射读取泛型 `<User>`，结合 `@TableName("tb_user")`，自动拼接出 SQL 语句（`INSERT INTO tb_user ...`）。
3.  **Spring** 创建了一个代理对象来实现这个接口，当你调用方法时，代理对象直接执行拼接好的 SQL。

---

这是**第三阶段·第四次教学**。

这一刻激动人心！我们将正式删掉之前在内存里模拟的“假数据库”代码，换成 **MyBatis-Plus** 的真枪实弹。

你将亲身体验到，原来写几十行 SQL 才能搞定的 CRUD，现在只需要**一行 Java 代码**。

---

# 第三阶段：ORM 框架 - 实现 CRUD 操作

**目标**：重构 `ArticleService`，使用 `ArticleMapper` 对 MySQL 数据库进行增删改查。

请打开你的 `ArticleService.java`，准备进行“大换血”。

## 1. 注入 Mapper (连接层)

首先，我们要把之前的 `static Map` 删掉，改为注入 `ArticleMapper`。

**修改前**：
```java
// private static final Map<Long, Article> articleStore = ... (删除)
```

**修改后**：
```java
@Autowired
private ArticleMapper articleMapper; // 仓库管理员
```

---

## 2. 实现 C (Create - 新增)

**场景**：发布文章。
**MP 方法**：`insert(entity)`

MP 会自动生成 SQL：`INSERT INTO tb_article (title, content...) VALUES (...)`。
最神奇的是：**它会把数据库生成的自增 ID，自动回填到你的对象中**。

**代码实现**：
```java
public Article publishArticle(Article article) {
    article.setCreateTime(LocalDateTime.now()); // 补充时间
    
    // 调用 MP 的 insert 方法
    // 此时 article 里的 id 还是 null
    articleMapper.insert(article); 
    
    // 执行完上面这就话，MP 已经把数据库生成的 ID 赋值给 article.id 了！
    return article;
}
```

---

## 3. 实现 R (Read - 查询)

**场景 A**：根据 ID 查询详情。
**MP 方法**：`selectById(id)`

**代码实现**：
```java
public Article getArticleById(Long id) {
    // 生成 SQL: SELECT * FROM tb_article WHERE id = ?
    return articleMapper.selectById(id);
}
```

**场景 B**：查询所有文章列表。
**MP 方法**：`selectList(wrapper)`

这里的参数 `Wrapper` 是条件构造器（比如 WHERE id > 10）。如果传 `null`，代表**无条件查询所有**。

**代码实现**：
```java
public List<Article> getAllArticles() {
    // 生成 SQL: SELECT * FROM tb_article
    return articleMapper.selectList(null); 
}
```

---

## 4. 实现 U (Update - 修改)

**场景**：修改文章标题或内容。
**MP 方法**：`updateById(entity)`

**MP 的智能之处**：它默认进行**非空更新**。
如果你传的对象里只有 `id=1` 和 `title="新标题"`，其他字段是 null，那么生成的 SQL 只会更新 title 字段，不会把 content 字段误更新成 null。

**代码实现**：
```java
public void updateArticle(Article article) {
    // 生成 SQL: UPDATE tb_article SET title=?, ... WHERE id=?
    // 必须确保 article 对象里有 ID，否则不知道更新哪一行
    articleMapper.updateById(article);
}
```

---

## 5. 实现 D (Delete - 删除)

**场景**：删除违规文章。
**MP 方法**：`deleteById(id)`

**代码实现**：
```java
public void deleteArticle(Long id) {
    // 生成 SQL: DELETE FROM tb_article WHERE id = ?
    articleMapper.deleteById(id);
}
```

---

## 🚀 完整代码：重构后的 ArticleService

为了方便你核对，这里提供重构后的完整代码。请直接覆盖你的 `ArticleService.java`。

```java
package com.example.blog.service;

import com.example.blog.entity.Article;
import com.example.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    // 1. 新增
    public Article publishArticle(Article article) {
        article.setCreateTime(LocalDateTime.now());
        articleMapper.insert(article);
        return article; // 返回的对象里已经有数据库生成的 ID 了
    }

    // 2. 查询详情
    public Article getArticleById(Long id) {
        return articleMapper.selectById(id);
    }

    // 3. 查询列表 (这是新加的功能)
    public List<Article> getArticleList() {
        return articleMapper.selectList(null);
    }

    // 4. 修改 (这是新加的功能)
    public void updateArticle(Article article) {
        // 这里的 article 必须包含 id
        articleMapper.updateById(article);
    }

    // 5. 删除 (这是新加的功能)
    public void deleteArticle(Long id) {
        articleMapper.deleteById(id);
    }
}
```

> **提示**：因为我们在 Service 里加了新方法（update, delete, getList），你需要同步去 **Controller** 里把对应的接口写出来，才能测试。
>
> *为了节省篇幅，Controller 的 CRUD 补全作为**今日作业**。*

---

## 🧪 验证与观察 (关键步骤)

1.  **启动项目**。
2.  使用 Postman 发送 `POST /articles` 创建一篇文章。
3.  **盯着 IDEA 的控制台**！
    *   你会看到类似这样的日志：
        ```text
        ==>  Preparing: INSERT INTO tb_article (title, content, author, create_time) VALUES (?, ?, ?, ?)
        ==>  Parameters: 我的第一篇数据库文章(String), ...(String)
        <==  Updates: 1
        ```
    *   这就是我们在配置文件里开启 `log-impl: stdout` 的效果。看到这个，说明 SQL 真正执行了！
4.  打开 IDEA 右侧的 Database 面板，刷新表 `tb_article`，你会发现数据真的躺在里面了。

---

## ⚠️ 常见报错预警

如果在运行 `selectById` 时报错：
`org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)`

*   **原因**：通常是 Mapper 接口没有被扫描到。
*   **检查**：
    1.  启动类上有没有加 `@MapperScan("com.example.blog.mapper")`？
    2.  或者 `UserMapper` 接口上有没有加 `@Mapper`？
    3.  包名是否写错了？

---

你说得非常对！你的严谨让我印象深刻。

在进行 CRUD 之前，或者说在项目启动的那一刻，**连接池**其实就已经在工作了。弄清楚它是如何工作的，对于理解“高并发”和“性能优化”至关重要。

这是**第三阶段·第五次教学**。

---

# 第三阶段：数据库连接池 - HikariCP

## 1. 什么是数据库连接池？

我们先来打个比方：

*   **没有连接池**：
    这就好比你每次出门都要**临时造一辆车**。
    1.  用户请求来了 -> 2. 此时与 MySQL 建立连接（TCP 三次握手、验证账号密码，**非常耗时**，可能要 100ms）-> 3. 执行 SQL -> 4. 断开连接（销毁车）。
    *   **后果**：如果有 1000 个人同时访问，服务器就要一瞬间建立 1000 次连接，数据库直接崩溃。

*   **有连接池 (Connection Pool)**：
    这就好比**出租车公司**。
    1.  系统启动时，先造好 10 辆车（建立 10 个连接）停在池子里待命。
    2.  用户请求来了 -> 3. 从池子里**借**一辆车（耗时 0ms）-> 4. 执行 SQL -> 5. 把车**还**回池子（不销毁）。
    *   **优势**：连接复用，速度极快，且能限制最大连接数保护数据库。

## 2. 为什么是 HikariCP？

在 Spring Boot 2.0 之前，默认使用的是 Tomcat JDBC Pool。
但在 Spring Boot 2.0 之后，官方默认采用了 **HikariCP**。

*   **名字由来**："Hikari" 是日语“光”的意思。寓意它的速度**快得像光一样**。
*   **地位**：它是目前 Java 界**性能最强、速度最快**的连接池，字节码极其精简。

> **面试官问**：Spring Boot 默认连接池是什么？
> **答**：HikariCP。

## 3. 如何配置 HikariCP？

虽然 Spring Boot 默认已经帮我们配好了（默认最大连接数是 10），但在实际开发中，我们通常需要手动调整参数以适应业务需求。

请打开你的 `application.yml`，在 `datasource` 下面增加 `hikari` 的配置：

```yaml
spring:
  datasource:
    # ... 之前的 driver, url, username, password 保持不变 ...
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/blog_db?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: your_password
    
    # === HikariCP 连接池配置 ===
    type: com.zaxxer.hikari.HikariDataSource # 指定数据源类型（可选，Spring Boot 会自动识别）
    hikari:
      # 1. 最小空闲连接数：系统很闲时，至少保留几个连接干活？
      minimum-idle: 5
      
      # 2. 最大连接数：最忙的时候，最多允许创建多少个连接？
      # (超过这个数，新的请求就会被阻塞等待，防止把数据库打挂)
      maximum-pool-size: 15
      
      # 3. 连接超时时间：如果池子满了，新请求愿意等多久？(毫秒)
      # 这里设置 30秒。如果30秒还要不到连接，就报错。
      connection-timeout: 30000
      
      # 4. 最大生命周期：一个连接最多存活多久？(毫秒)
      # 推荐设置比数据库的 wait_timeout 短一点，防止持有已断开的连接。
      max-lifetime: 1800000 # 30分钟
      
      # 5. 验证连接是否有效的 SQL (新版 JDBC 驱动通常不需要这个，会自动检测)
      # connection-test-query: SELECT 1
```

## 4. 验证连接池是否生效

我们要确认一下 HikariCP 真的在工作。

1.  **启动项目**。
2.  **观察控制台日志**。
    在启动的头几行日志中，你应该能找到类似这样的一句话：
    ```text
    com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
    com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
    ```
    看到 `HikariPool` 字样，就说明你已经成功使用了 HikariCP。

---

## 💡 扩展知识：Druid (德鲁伊)

在国内的互联网公司（特别是阿里系），你可能会经常听到另一个连接池的名字：**Druid**。

*   **来源**：阿里巴巴开源。
*   **特点**：性能比 Hikari 略低一点点（肉眼不可见），但它有**极其强大的监控功能**（能看到哪个 SQL 执行慢，哪个 IP 访问频繁）。
*   **如何选择**：
    *   追求极致性能、轻量级 -> **HikariCP** (Spring Boot 原生推荐)。
    *   需要详细的 SQL 监控、防火墙功能 -> **Druid**。

*(注：本教程作为入门，我们使用默认的 HikariCP 即可，无需额外引入 Druid 依赖，避免增加复杂度。)*

---

## 🧪 面试题预演

**Q: 为什么要设置最大连接数（maximum-pool-size）？设置得越大越好吗？**

**A:**
*   **不是越大越好**。
*   如果设置太大（比如 1000），数据库就需要维护 1000 个连接的上下文，这会消耗大量的内存和 CPU 进行上下文切换，反而导致性能下降。
*   如果设置太小（比如 2），并发请求来了，大量请求会在排队等待连接，导致响应变慢。
*   **经验值**：通常根据 CPU 核心数和磁盘 IO 来计算。对于普通的博客系统，设置为 `10-20` 足够了。

---

**下节预告**：
现在连接池配置好了，基本的 CRUD 也实现了。
我们要回到刚才的话题，处理更复杂的业务需求：
**“如何实现文章列表的分页查询？”**
这是博客系统首页必不可少的功能，否则一万篇文章一次性查出来，浏览器就卡死了。

下节课：**MyBatis-Plus 分页插件与条件构造器**。

好的，我们继续！这是**第三阶段·第六次教学**。

在之前的课程中，我们已经学会了怎么根据 ID 查单条数据（`selectById`）和怎么查所有数据（`selectList(null)`）。

但在实际的博客系统中，用户通常需要：
1.  **搜索**：比如想找标题里包含 "Spring" 的文章。
2.  **分页**：如果数据库有 1000 篇文章，不能一次性全查出来，要分成第 1 页、第 2 页加载。

今天我们就来解锁 MyBatis-Plus 的两个大杀器：**条件构造器 (Wrapper)** 和 **分页插件 (Pagination)**。

---

# 第三阶段：MyBatis-Plus 进阶 - 条件查询与分页

## 1. 条件构造器 (Wrapper)

MyBatis-Plus 最强大的地方就在于，它能让你用 Java 代码写出 SQL 的 `WHERE` 条件，而不用去拼字符串。

我们要使用的是 **`LambdaQueryWrapper`**。它的好处是使用 Lambda 表达式（`Article::getId`）来指定字段，**防止你手抖把列名写错**。

### 常用语法速查
*   `eq("age", 18)` -> `age = 18`
*   `ne("age", 18)` -> `age <> 18`
*   `gt("age", 18)` -> `age > 18`
*   `lt("age", 18)` -> `age < 18`
*   `like("name", "张")` -> `name LIKE '%张%'`
*   `orderByDesc("create_time")` -> `ORDER BY create_time DESC`

### 代码演示
假设我们要给 `ArticleService` 增加一个“搜索”功能。

```java
// 在 ArticleService 中添加
public List<Article> searchArticles(String keyword) {
    // 1. 创建条件构造器
    LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
    
    // 2. 设定条件：title 包含 keyword (类似 LIKE '%keyword%')
    // Article::getTitle 这种写法能确保你引用的是存在的字段
    if (keyword != null) {
        wrapper.like(Article::getTitle, keyword);
    }
    
    // 3. 设定排序：按创建时间倒序（最新的在前面）
    wrapper.orderByDesc(Article::getCreateTime);

    // 4. 查询
    // 生成 SQL: SELECT * FROM tb_article WHERE title LIKE ? ORDER BY create_time DESC
    return articleMapper.selectList(wrapper);
}
```

---

## 2. 分页插件配置 (Pagination)

默认情况下，MyBatis-Plus 的分页功能是**关闭**的。如果你直接调用分页方法，它不会加 `LIMIT`，而是查询所有数据。

我们需要加一个**配置类**来开启这个“插件”。

### 第一步：创建配置类
请在 `com.example.blog` 包下新建一个 `config` 包，然后新建 `MybatisPlusConfig.java`。

```java
package com.example.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 标记这是一个配置类
public class MybatisPlusConfig {

    /**
     * 注册 MyBatis-Plus 的插件拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 添加分页插件
        // DbType.MYSQL: 指定数据库类型，MP 会根据不同数据库生成不同的分页 SQL (LIMIT vs ROWNUM)
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        
        return interceptor;
    }
}
```

---

## 3. 实现分页查询业务

现在插件配好了，我们可以去 `ArticleService` 里写分页逻辑了。

### 第二步：修改 Service
MyBatis-Plus 提供了一个 `Page<T>` 对象，用来承载分页参数（第几页、每页几条）和结果。

```java
// 导入包时注意选这个：com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

// 在 ArticleService 中添加
public Page<Article> getArticlePage(int pageNum, int pageSize) {
    // 1. 创建分页对象 (当前页, 每页条数)
    Page<Article> pageInfo = new Page<>(pageNum, pageSize);
    
    // 2. 创建查询条件 (比如我们希望按时间倒序排)
    LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
    wrapper.orderByDesc(Article::getCreateTime);

    // 3. 执行查询
    // MP 会自动执行两条 SQL：
    // A. SELECT COUNT(*) ... (查总数)
    // B. SELECT * ... LIMIT 0, 10 (查数据)
    articleMapper.selectPage(pageInfo, wrapper);
    
    // 4. 返回 pageInfo
    // pageInfo 里现在包含了：records(数据列表), total(总数), pages(总页数) 等信息
    return pageInfo;
}
```

---

## 4. 暴露接口 (Controller)

最后，我们在 Controller 里把这个功能对外开放。

### 第三步：修改 Controller
**`ArticleController.java`**

```java
    // ... 之前的代码 ...

    /**
     * 分页查询接口
     * URL: GET /articles/page?pageNum=1&pageSize=10
     */
    @GetMapping("/page")
    public Result<Page<Article>> getPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        Page<Article> pageResult = articleService.getArticlePage(pageNum, pageSize);
        
        return Result.success(pageResult);
    }
```

---

## 🧪 验证与测试

1.  **重启项目**（因为我们加了配置类，必须重启才能生效）。
2.  确保你的数据库里至少有 3-5 条数据。
3.  **测试分页**：
    *   请求 URL: `GET http://localhost:8080/articles/page?pageNum=1&pageSize=2`
    *   **预期结果**：
        ```json
        {
            "code": 200,
            "msg": "success",
            "data": {
                "records": [ ...这里应该只有 2 条文章... ],
                "total": 5,       // 数据库总条数
                "size": 2,        // 每页大小
                "current": 1,     // 当前页
                "pages": 3        // 总页数
            }
        }
        ```
4.  **观察控制台日志**：
    你会发现 MP 真的打印了两条 SQL，最后一条带有 `LIMIT` 关键字。

---

## 💡 阶段总结

到目前为止，你已经掌握了后端开发最核心的 **CRUD + 复杂查询 + 分页** 能力。
现在的 `blog-backend` 已经具备了一个标准后端服务的雏形。

**第三阶段的内容基本全部结束！**
按照大纲，接下来我们有两个环节：
1.  **博客实战任务**（重构之前的代码，确保所有功能都连上了数据库）。
2.  **面试考点**（数据库相关的核心面试题）。

由于我们在教学过程中已经一步步带着你做了实战（Service 的重构），所以**实战任务其实已经完成了**（只要你跟着敲了代码）。

如果你准备好了，请回复**“面试挑战”**，我们来复习 MySQL 和 MyBatis 的高频考点！

没关系，我们重新开始！

刚才讲到，我们在之前的教学中已经搞定了用户表 (`tb_user`) 和文章表 (`tb_article`)，以及核心的分页功能。

为了完美完成**第三阶段的实战任务**，我们需要补全最后一块拼图：**分类表 (`tb_category`)**，并把它们串联起来。

以下是完整的**第三阶段·实战任务验收手册**。

---

# 第三阶段实战：完善数据库与业务逻辑

**任务清单**：
1.  [x] 安装 MySQL，创建数据库 `blog_db`。
2.  [x] 设计 `tb_user` 和 `tb_article`。
3.  [ ] **设计 `tb_category` (分类表) 并关联文章。** (本次重点)
4.  [x] 集成 MyBatis-Plus。
5.  [x] 重构 Service 实现真正读写。
6.  [x] 实现分页查询。

下面我们来执行**第 3 点**，完成整个阶段的闭环。

## 第一步：设计分类表与关联

我们需要在数据库中创建一个存放“技术”、“生活”、“随笔”等分类的表，并在文章表中加一个字段，用来记录这篇文章属于哪个分类。

请在 IDEA 的 Database 控制台执行以下 SQL：

```sql
USE blog_db;

-- 1. 创建分类表
DROP TABLE IF EXISTS tb_category;
CREATE TABLE tb_category (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '分类表';

-- 2. 初始化几个分类数据
INSERT INTO tb_category (name) VALUES ('Java后端'), ('前端技术'), ('生活随笔');

-- 3. 修改文章表，添加 category_id 字段 (建立关联)
-- 以后文章里存的 category_id 就是 tb_category 表里的 id
ALTER TABLE tb_article ADD COLUMN category_id INT COMMENT '关联的分类ID';
```

## 第二步：创建分类实体与 Mapper

回到 Java 代码，我们需要为新表创建对应的类。

1.  **创建实体类 `Category.java`**
    *(路径: `com.example.blog.entity`)*

    ```java
    package com.example.blog.entity;
    
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import lombok.Data;
    import java.time.LocalDateTime;
    
    @Data
    @TableName("tb_category")
    public class Category {
        @TableId(type = IdType.AUTO)
        private Integer id;
        private String name;
        private LocalDateTime createTime;
    }
    ```

2.  **更新文章实体 `Article.java`**
    既然数据库加了字段，Java 类也要加。
    ```java
    // 在 Article 类中增加这个字段
    private Integer categoryId; 
    ```

3.  **创建 Mapper `CategoryMapper.java`**
    *(路径: `com.example.blog.mapper`)*

    ```java
    package com.example.blog.mapper;
    import com.baomidou.mybatisplus.core.mapper.BaseMapper;
    import com.example.blog.entity.Category;
    import org.apache.ibatis.annotations.Mapper;
    
    @Mapper
    public interface CategoryMapper extends BaseMapper<Category> {
    }
    ```

## 第三步：功能验收 (Checklist)

现在，整个后端的骨架已经完整了。请按照以下步骤检查你的代码是否达到了第三阶段的目标：

### 1. 检查数据库连接
*   查看 `application.yml`，确认 `url`, `username`, `password` 正确。
*   确认 `driver-class-name` 是 `com.mysql.cj.jdbc.Driver`。

### 2. 检查 MyBatis-Plus 集成
*   启动类上是否加了 `@MapperScan("com.example.blog.mapper")`？
*   `pom.xml` 里是否有 `mybatis-plus-boot-starter`？

### 3. 检查 Service 重构
*   打开 `ArticleService`，确认里面不再有 `static Map`。
*   确认所有方法（insert, selectById 等）都是调用的 `articleMapper`。

### 4. 检查分页功能
*   确认配置类 `MybatisPlusConfig` 存在且加了 `@Configuration`。
*   确认 Controller 中的 `/articles/page` 接口返回的数据包含 `total`, `pages`, `records` 字段。

---

## 🧪 最终测试：发布一篇带分类的文章

这是对你第三阶段成果的终极测试。

1.  **启动项目**。
2.  **使用 Postman 发送请求**：
    *   **URL**: `POST http://localhost:8080/articles`
    *   **Body**:
        ```json
        {
            "title": "MyBatis-Plus 真好用",
            "content": "这是集成了 MySQL 后的第一篇文章",
            "author": "Java小白",
            "categoryId": 1
        }
        ```
        *(注意：这里传了 categoryId = 1，对应数据库里的 "Java后端")*

3.  **验证**：
    *   请求成功返回 200。
    *   去数据库执行 `SELECT * FROM tb_article;`。
    *   如果能看到新插入的数据，且 `category_id` 是 1，**恭喜你！第三阶段任务圆满完成！** 🚀

---

你已经成功搭建了一个拥有 **数据库存储、ORM 框架、连接池、分页查询** 的后端系统雏形。

接下来，我们要进行**第三阶段的面试挑战**。这些题目是关于数据库和 MyBatis 最容易被问到的。

**准备好了吗？请回复“面试挑战”！**

好样的！这些面试题是筛选初级与中级开发者的**“分水岭”**。

如果你能流畅回答以下三个问题，说明你对数据持久层不仅仅是“会用”，而是“懂原理”。

请把这些内容刻在脑子里，面试前必看！

---

# 第三阶段·面试考点：数据持久化与 Spring 原理

## 面试题 1：MyBatis 中 `#` 和 `$` 的区别是什么？（防 SQL 注入）

这是 **Top 1 高频题**。面试官问这个主要看你有没有安全意识。

### 🎓 标准回答
1.  **处理方式不同**：
    *   `#{}`：是**预编译处理**（PreparedStatement）。MyBatis 会将 SQL 中的 `#{}` 替换为 `?` 号，然后调用 JDBC 的 `set` 方法赋值。
    *   `${}`：是**字符串替换**。MyBatis 会直接把变量的值拼接到 SQL 语句中。
2.  **安全性不同**：
    *   `#{}`：**能防止 SQL 注入**。因为参数会被当成纯字符串处理，不会被数据库当做指令执行。
    *   `${}`：**不能防止 SQL 注入**。如果参数里包含恶意 SQL 命令（如 `' OR '1'='1`），数据库会照单全收。
3.  **使用场景**：
    *   绝大多数情况（如传 ID、传名字）都必须用 `#{}`。
    *   只有在无法使用预编译的地方（如动态传入**表名**、**排序字段名**）才不得不使用 `${}`。

### 💡 通俗理解
*   `#{}` 就像**把参数关进笼子**里再送给数据库，参数再凶也伤不了人。
*   `${}` 就像**把参数直接放出来**，如果参数是只老虎（恶意 SQL），数据库就被咬死了。

---

## 面试题 2：Spring Boot 自动装配原理是什么？

这个问题我们在第一阶段提到过，但在第三阶段有了具体的例子（MyBatis-Plus），理解会更深。

### 🎓 标准回答
Spring Boot 自动装配的核心机制基于 **`@EnableAutoConfiguration`** 注解。

1.  **加载配置**：
    Spring Boot 启动时，会去读取 `META-INF/spring.factories` 文件（Spring Boot 3.x 后是 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`），这里面列出了几百个“候选配置类”。
2.  **按需生效 (`@Conditional`)**：
    这些配置类不会全部加载，而是通过 `@Conditional` 系列注解进行判断。
    *   例如 **MyBatis-Plus 的自动配置类**，上面会有 `@ConditionalOnClass(SqlSessionFactory.class)`。
    *   意思就是：只有当你引入了 MyBatis 的 jar 包（ClassPath 下有这个类），这个配置才会生效，帮你自动配置 DataSource 和 SqlSessionFactory。
3.  **约定配置**：
    生效的配置类会去读取 `application.yml` 中的属性（如 `spring.datasource.url`），并注入到 Bean 中。

### 💡 一句话总结
**“约定大于配置”：Spring Boot 启动时扫描 classpath，发现你引入了什么 jar 包（比如 MySQL 驱动），就根据默认规则自动帮你配置好对应的 Bean。**

---

## 面试题 3：数据库事务 `@Transactional` 的失效场景有哪些？

你肯定知道 `@Transactional` 是用来保证“要么全成功，要么全失败”的，但面试官更喜欢问**什么时候它没用**。

### 🎓 标准回答
`@Transactional` 基于 Spring 的 **AOP（动态代理）** 实现。以下场景会导致它失效：

1.  **应用在非 `public` 方法上**：
    Spring 默认只拦截 public 方法。如果你写在 `private` 或 `protected` 方法上，事务不生效。
2.  **同类内部调用 (Self-Invocation)**：
    *   假设类里有方法 A 和方法 B。方法 A（无事务）调用了方法 B（有事务）。
    *   当你从外部调用方法 A 时，方法 B 的事务**不会生效**。
    *   *原因*：Spring 的事务是靠代理对象拦截的。类内部调用时，是 `this.B()`，绕过了代理对象，直接调用的原对象方法。
3.  **异常被 `try-catch` 吃掉了**：
    *   如果你在代码里写了 `try { ... } catch (Exception e) { e.printStackTrace(); }`。
    *   Spring 事务管理器监测不到异常抛出，就会认为执行成功，于是**提交事务**，导致回滚失败。
    *   *解决*：在 catch 块中手动抛出 `throw new RuntimeException(e)`。
4.  **数据库引擎不支持**：
    *   如果你用的 MySQL 还是古老的 **MyISAM** 引擎，它根本不支持事务。必须用 **InnoDB**。

---

**✅ 第三阶段圆满结束！**

你现在已经掌握了：
1.  MySQL 建表与 SQL。
2.  MyBatis-Plus 的 CRUD 与分页。
3.  数据库连接池与事务的基础概念。

接下来的 **第四阶段：业务进阶与规范化**，我们将让你的代码从“能跑”变成“健壮”。
我们将学习：

*   **全局异常处理**（再也不用 try-catch 满天飞）。
*   **参数校验**（不用手动写 if null）。
*   **AOP 日志**（自动记录谁访问了接口）。
*   **文件上传**（给文章配图）。

**请回复“进入第四阶段”，我们继续升级打怪！**

欢迎来到**第四阶段：业务进阶与规范化**。

在之前的阶段，我们只关注“功能实现”——即代码能跑通就行。但作为一名合格的后端开发，**代码的健壮性**和**用户体验**同样重要。

这是**第四阶段·第一次教学**。我们要解决一个非常尴尬的问题：**“报错页面”**。

---

# 第四阶段：全局异常处理 - 告别 500 报错页

## 1. 为什么要搞全局异常处理？

**现状**：
假设你的代码里有一行 `int i = 1 / 0;`，或者数据库断连了。
如果前端请求这个接口，Spring Boot 默认会返回一个 HTML 页面，上面写着大大的 **"Whitelabel Error Page"** 和一堆看不懂的 Java 堆栈信息（Stack Trace）。

**后果**：
1.  **前端崩溃**：前端代码期望收到 JSON，结果收到一堆 HTML，解析失败，导致页面白屏或卡死。
2.  **体验极差**：用户看到“500 Internal Server Error”会觉得你的系统很烂。
3.  **安全泄露**：报错信息里可能会暴露你的包名、SQL 语句甚至数据库结构，给黑客可乘之机。

**目标**：
无论发生什么错误（除零异常、空指针、数据库挂了），后端永远返回统一的 JSON 格式：
```json
{
  "code": 500,
  "msg": "系统繁忙，请稍后再试", 
  "data": null
}
```

---

## 2. 核心注解：`@RestControllerAdvice`

我们要用到 Spring 的 AOP（面向切面编程）思想。我们不需要在每个 Controller 的每个方法里写 `try-catch`。我们要建立一个**“全局异常捕获网”**。

*   **`@ControllerAdvice`**: 它是 Controller 的增强版，可以拦截所有 Controller 抛出的异常。
*   **`@RestControllerAdvice`**: 等同于 `@ControllerAdvice` + `@ResponseBody`。这意味着异常处理后的返回值也会自动变成 JSON，非常适合前后端分离。
*   **`@ExceptionHandler`**: 放在方法上，声明这个方法专门用来抓哪个类型的异常。

---

## 3. 代码实战：编写全局异常处理器

请在 `com.example.blog` 包下新建一个包叫 `handler` (或者放在 `common` 包里也可以)，然后新建类 `GlobalExceptionHandler.java`。

```java
package com.example.blog.handler;

import com.example.blog.common.Result;
import lombok.extern.slf4j.Slf4j; // 需要 Lombok 支持日志
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 只要 Controller 层抛出了异常，都会被这里拦截到
 */
@Slf4j // 自动注入 log 对象，用于打印日志
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截所有 Exception 类的异常 (兜底方案)
     * 也就是说，只要代码报错，最后都会走到这里
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        // 1. 打印错误日志到控制台 (非常重要！否则出错了你都不知道错哪儿了)
        log.error("系统出现异常: ", e);

        // 2. 返回统一的 JSON 结果
        // 在生产环境中，通常不会直接把 e.getMessage() 返回给前端（太技术化了）
        // 而是返回 "系统繁忙，请联系管理员"
        // 但在开发阶段，为了方便调试，我们暂时返回 e.getMessage()
        return Result.error("系统错误: " + e.getMessage());
    }

    /**
     * 你还可以针对特定异常进行单独处理
     * 比如：拦截算术异常 (1/0)
     */
    @ExceptionHandler(ArithmeticException.class)
    public Result<String> handleArithmeticException(ArithmeticException e) {
        log.error("发生数学运算异常", e);
        return Result.error("数学没学好，不能除以零！");
    }
    
    // 以后还可以加：
    // @ExceptionHandler(NullPointerException.class) 处理空指针...
    // @ExceptionHandler(BusinessException.class) 处理自定义业务异常...
}
```

> **💡 关于日志**：
> 上面的代码用了 `@Slf4j`。如果你的 IDEA 提示找不到 `log` 变量，请确认你有没有在 `pom.xml` 里引入 Lombok，并且 IDEA 安装了 Lombok 插件。
> 或者你可以手动写：`private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);`

---

## 4. 验证：制造一个 Bug

为了测试这个拦截器是否生效，我们需要去 Controller 里故意写一段报错的代码。

打开 `ArticleController`，添加一个测试接口：

```java
    // 故意报错的接口
    @GetMapping("/error-test")
    public Result<String> errorTest() {
        int i = 1 / 0; // 这里会抛出 ArithmeticException
        return Result.success("这就话永远不会执行");
    }
```

---

## 5. 运行与观察

1.  **启动项目**。
2.  **浏览器访问**：`http://localhost:8080/articles/error-test`
3.  **观察结果**：
    *   **没有全局异常处理前**：浏览器会显示一个大白页，上面写着 `Status 500` `java.lang.ArithmeticException: / by zero`。
    *   **现在**：浏览器显示标准的 JSON：
        ```json
        {
            "code": 500,
            "msg": "数学没学好，不能除以零！",
            "data": null
        }
        ```
4.  **观察 IDEA 控制台**：
    你会看到红色的报错日志 `发生数学运算异常...`，这说明后台记录了错误，但前台优雅地展示了提示。

---

## 6. 进阶：自定义业务异常 (BusinessException)

在实际开发中，我们不仅仅要捕获**系统异常**（如空指针、SQL错误），还要抛出**业务异常**。

例如：用户登录时密码错误。
*   **不推荐的做法**：`return Result.error("密码错误");` (在 Service 层没法直接 return Result，因为 Service 的返回值类型通常是 Entity)。
*   **推荐的做法**：在 Service 层 `throw new BusinessException("密码错误");`，然后由全局异常处理器统一捕获。

### 实操步骤 (可选，但强烈推荐)

1.  **创建异常类 `BusinessException.java`** (放在 `common` 包下)
    ```java
    package com.example.blog.common;
    
    // 继承 RuntimeException，这样代码里抛出时不需要写 try-catch
    public class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
    ```

2.  **修改 `GlobalExceptionHandler`**，增加对它的拦截：
    ```java
    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        // 业务异常不需要打印堆栈信息，因为这是预期的错误（如密码不对）
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }
    ```

3.  **模拟使用**：
    以后在 Service 里，如果查不到数据，可以直接：
    ```java
    if (user == null) {
        throw new BusinessException("用户名不存在");
    }
    ```
    前端就会收到 `code: 500, msg: "用户名不存在"` 的 JSON。

---

这是**第四阶段·第二次教学**。

在上一节课，我们学会了怎么优雅地处理报错。但还有一种代码写起来非常烦人，那就是**参数检查**。

**❌ 以前的写法**：
```java
if (user.getUsername() == null || user.getUsername().equals("")) {
    return Result.error("用户名不能为空");
}
if (user.getPassword().length() < 6) {
    return Result.error("密码不能少于6位");
}
if (!user.getEmail().contains("@")) {
    return Result.error("邮箱格式不对");
}
// 如果有20个字段，你的 Controller 里全是这种 if 代码，业务逻辑都被淹没了。
```

**✅ 现在的写法**：
使用 **JSR-303 (Bean Validation)** 标准。只需要在实体类的字段上打几个注解，Spring Boot 就会自动帮你检查！

---

# 第四阶段：参数校验 - Validation 框架

## 1. 引入依赖 (pom.xml)

从 Spring Boot 2.3 版本开始，Web 模块不再默认包含 Validation，必须手动引入。

请打开 `pom.xml`，添加以下依赖：

```xml
<!-- 参数校验依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

> **记得刷新 Maven！**

---

## 2. 常用注解速查表

这些注解都位于 `javax.validation.constraints` 包下：

| 注解                | 作用                                          | 适用类型          |
| :------------------ | :-------------------------------------------- | :---------------- |
| **@NotNull**        | 不能为 null，但可以是空字符串 ""              | 所有              |
| **@NotEmpty**       | 不能为 null，且长度 > 0 (集合/字符串)         | String, List, Map |
| **@NotBlank**       | **最常用**。不能为 null，且去掉空格后长度 > 0 | String            |
| **@Size(min, max)** | 限制字符串或集合的长度范围                    | String, List      |
| **@Min / @Max**     | 限制数字的大小                                | Integer, Long     |
| **@Email**          | 必须符合邮箱格式                              | String            |
| **@Pattern**        | 使用正则表达式校验 (如手机号)                 | String            |

---

## 3. 改造实体类 (Entity)

我们以 `User` 类为例，给它加上约束规则。
请修改 `src/main/java/com/example/blog/entity/User.java`：

```java
package com.example.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

// 导入校验注解
import javax.validation.constraints.*; 
// 注意：如果你用的是 Spring Boot 3.x (JDK 17)，包名可能是 jakarta.validation.constraints.*

@Data
@TableName("tb_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 1. 用户名不能为空，且长度在 2-20 之间
    // message 参数定义了校验失败时的提示语
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须在2-20之间")
    private String username;

    // 2. 密码不能为空，且最少 6 位
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不能少于6位")
    private String password;
    
    // 3. 假设我们加了一个邮箱字段 (即使数据库没这个字段，也可以先写在类里演示)
    @Email(message = "邮箱格式不正确")
    private String email;

    // 4. 年龄必须在 0 到 150 之间
    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;
}
```

---

## 4. 开启校验 (Controller)

只在实体类上加注解是没用的，你必须在 Controller 接收参数的地方告诉 Spring：“请帮我检查这个对象！”。

**关键注解**：**`@Valid`** (或者 `@Validated`)。

打开 `UserController.java`，新增一个“注册/新增用户”的接口：

```java
    // ... 之前的 import ...
    import javax.validation.Valid; // Spring Boot 3.x 是 jakarta.validation.Valid
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;

    // ... 类内部 ...

    /**
     * 新增用户接口
     * @Valid: 告诉 Spring，把 JSON 转成 User 对象后，立马去检查字段上的注解规则。
     * 如果检查不通过，Spring 会抛出一个 MethodArgumentNotValidException 异常。
     */
    @PostMapping
    public Result<User> saveUser(@RequestBody @Valid User user) {
        // 如果能走到这里，说明参数肯定没问题！
        // 此时可以放心调用 Service 插入数据库
        // userService.save(user); // 假设有这个方法
        return Result.success(user);
    }
```

---

## 5. 配合全局异常处理 (完美闭环)

如果校验失败，Spring 会抛出 `MethodArgumentNotValidException`。
如果我们不处理，前端会收到一堆很丑的 400 错误结构。
我们需要在上一节课写的 `GlobalExceptionHandler` 里把这个异常拦截下来，提取出我们写的 `message`。

请修改 `GlobalExceptionHandler.java`，添加以下方法：

```java
    import org.springframework.web.bind.MethodArgumentNotValidException;

    // ... 类内部 ...

    /**
     * 拦截参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验失败", e);
        
        // 从异常对象中拿到第一个错误提示
        // e.getBindingResult().getFieldError() 可能为空，稳健起见可以加判空，这里简化演示
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        
        return Result.error(msg);
    }
```

---

## 🧪 验证环节

1.  **启动项目**。
2.  使用 **Postman** 发送 `POST` 请求到 `http://localhost:8080/users`。
3.  **测试案例 1：用户名为空**
    *   Body: `{"username": "", "password": "123"}`
    *   **预期响应**：
        ```json
        {
            "code": 500,
            "msg": "用户名不能为空",
            "data": null
        }
        ```
4.  **测试案例 2：密码太短**
    *   Body: `{"username": "admin", "password": "1"}`
    *   **预期响应**：
        ```json
        {
            "code": 500,
            "msg": "密码不能少于6位",
            "data": null
        }
        ```
5.  **测试案例 3：邮箱格式不对**
    *   Body: `{"username": "admin", "password": "123456", "email": "not_an_email"}`
    *   **预期响应**：
        ```json
        {
            "code": 500,
            "msg": "邮箱格式不正确",
            "data": null
        }
        ```

---

## 💡 面试考点：`@NotNull`, `@NotEmpty`, `@NotBlank` 的区别

这是面试中关于校验最常问的细节题。

*   **`@NotNull`**:
    *   只校验 **null**。
    *   `""` (空字符串) -> **通过**。
    *   `"   "` (全是空格) -> **通过**。
    *   *适用场景*：Integer, Date 等非字符串对象。
*   **`@NotEmpty`**:
    *   校验 **null** 和 **长度为0**。
    *   `""` -> **不通过**。
    *   `"   "` (全是空格，但长度是3) -> **通过**。
    *   *适用场景*：List, Map, String (允许空格但不能为空)。
*   **`@NotBlank` (最严格)**:
    *   校验 **null**，且 **trim() 去除空格后长度大于0**。
    *   `""` -> **不通过**。
    *   `"   "` -> **不通过**。
    *   *适用场景*：用户名、密码、手机号等绝大多数 **String** 类型。

---

**下节预告**：
现在我们的代码既不会崩（全局异常），也不会乱收脏数据（参数校验）。
接下来我们要增加一个企业级功能：**AOP 日志**。
你想知道是谁、在什么时间、请求了哪个接口、花了多少毫秒吗？不需要在每个方法里写 `System.out.println`，AOP 帮你搞定。

下节课：**AOP 切面编程与日志管理**。

这是**第四阶段·第三次教学**。

AOP（Aspect-Oriented Programming，面向切面编程）是 Spring 框架的两大基石之一（另一个是 IoC）。

很多新手觉得 AOP 概念抽象，听不懂。今天我用**最通俗的“汉堡包理论”**带你理解它，并手写一个**接口请求日志切面**。

---

# 第四阶段：AOP 切面编程 - 给代码加“滤镜”

## 1. 什么是 AOP？（通俗理解）

想象一下，你写了 10 个 Controller 接口（发布文章、删除文章、获取详情...）。
现在老板提了个需求：**“我要监控每一个接口的耗时，并且要把请求参数打印出来。”**

*   **笨办法**：你打开这 10 个方法，在每个方法的第一行写 `long start = System.currentTimeMillis()`，在最后一行写 `System.out.println(end - start)`。如果有 100 个接口，你会累死。
*   **AOP 办法**：
    *   把这 10 个接口看作是 **“肉饼”**（核心业务）。
    *   把“记录日志/统计耗时”看作是 **“两片面包”**。
    *   AOP 就是把这“两片面包”**横向切入**到你的“肉饼”上下，做成一个汉堡。你不需要修改肉饼（业务代码），就自动拥有了面包（日志功能）。

**这种“不侵入原代码，却能统一增加功能”的技术，就是 AOP。**

---

## 2. 核心术语（必背）

为了听懂面试和看懂文档，这几个词必须记住：

1.  **切面 (Aspect)**：
    *   **是什么**：一个 Java 类。
    *   **作用**：在这个类里定义了“我要干什么”（记录日志）以及“我要对谁干”（所有 Controller）。
2.  **切点 (Pointcut)**：
    *   **是什么**：一个表达式。
    *   **作用**：用来筛选目标的。比如 `execution(* com.example.blog.controller..*(..))`，意思就是“拦截 controller 包下的所有方法”。
3.  **通知 (Advice)**：
    *   **是什么**：方法上的注解（如 `@Before`, `@After`, `@Around`）。
    *   **作用**：决定在什么时候切入。
        *   `@Before`: 方法执行前。
        *   `@After`: 方法执行后（无论成功失败）。
        *   **`@Around` (最强)**: 环绕通知。我把你包围了，我可以决定你什么时候执行，甚至把你拦截下来不执行。
4.  **连接点 (JoinPoint)**：
    *   **是什么**：程序运行时的某个点。
    *   **作用**：可以通过它拿到当前执行的方法名、参数值等信息。

---

## 3. 实战：开发“请求日志切面”

我们要实现的效果：无论访问哪个接口，控制台都会自动打印：
`[IP: 127.0.0.1] [POST] /articles - 耗时: 15ms`

### 步骤一：引入依赖
AOP 不是 Spring Boot 核心包自带的，需要引入 starter。

打开 `pom.xml` 添加：
```xml
<!-- AOP 依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
*(记得刷新 Maven)*

### 步骤二：编写切面类 `LogAspect`
请在 `com.example.blog` 下新建 `aspect` 包，然后新建 `LogAspect.java`。

```java
package com.example.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求日志切面
 */
@Aspect     // 1. 标记这是一个切面类
@Component  // 2. 把它交给 Spring 管理
@Slf4j      // Lombok 日志注解
public class LogAspect {

    /**
     * 定义切点 (Pointcut)
     * 表达式含义：
     * execution: 执行
     * *: 任意返回值
     * com.example.blog.controller..: 该包及其子包下的所有类
     * *: 所有方法
     * (..): 任意参数
     */
    @Pointcut("execution(* com.example.blog.controller..*(..))")
    public void controllerLog() {}

    /**
     * 环绕通知 (@Around): 最强大的通知，可以控制目标方法的执行
     */
    @Around("controllerLog()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // === 1. 开始计时 ===
        long startTime = System.currentTimeMillis();

        // === 2. 执行目标方法 (比如 UserController.saveUser) ===
        // result 就是 Controller 返回的 Result 对象
        Object result = joinPoint.proceed(); 

        // === 3. 结束计时 & 记录日志 ===
        long endTime = System.currentTimeMillis();
        long cost = endTime - startTime;

        // 获取当前请求的 Request 对象（为了拿 URL 和 IP）
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod(); // GET/POST
        String ip = request.getRemoteAddr();
        String className = joinPoint.getSignature().getDeclaringTypeName(); // 类名
        String methodName = joinPoint.getSignature().getName(); // 方法名

        // 打印日志
        log.info(">>> 请求: [{} {}] 来自: {} | 方法: {}.{} | 耗时: {}ms", 
                 method, url, ip, className, methodName, cost);

        // 必须要把结果返回回去，否则前端收不到数据！
        return result;
    }
}
```

---

## 4. 验证结果

1.  **启动项目**。
2.  **测试**：用 Postman 或浏览器随便访问几个接口。
    *   `GET http://localhost:8080/articles/page`
    *   `GET http://localhost:8080/articles/1`
3.  **观察 IDEA 控制台**：
    你应该能看到整齐划一的绿色日志（info 级别）：
    ```text
    >>> 请求: [GET http://localhost:8080/articles/page] 来自: 0:0:0:0:0:0:0:1 | 方法: com.example.blog.controller.ArticleController.getPage | 耗时: 23ms
    ```

---

## 5. 扩展应用：AOP 还能干嘛？

除了记日志，AOP 在企业开发中还有很多“神操作”：

1.  **权限控制**：
    *   在方法上加一个自定义注解 `@RequireAdmin`。
    *   写一个 AOP 拦截这个注解，判断当前用户是不是管理员。如果是，放行；如果不是，直接抛出异常，Controller 方法根本不会执行。
2.  **全局事务**：
    *   你常用的 `@Transactional` 其实就是 Spring 实现的一个 AOP 切面！它在方法执行前开启事务，执行后提交事务，报错了回滚事务。
3.  **缓存处理**：
    *   `@Cacheable`：方法执行前先查 Redis，有就直接返回（拦截），没有才执行方法查库。这也是 AOP。

---

## 🧪 面试考点：动态代理

面试官问 AOP 原理时，必问：**Spring AOP 是怎么实现的？**

**答**：Spring AOP 基于**动态代理 (Dynamic Proxy)** 技术。
Spring 会在运行期间，自动生成一个**代理对象**（Proxy Object）包裹住你的原始对象。

有两种实现方式：
1.  **JDK 动态代理**：
    *   如果你的类**实现了接口**（比如 UserService 实现了 Service 接口），Spring 默认用这个。
    *   基于反射机制。
2.  **CGLIB 动态代理**：
    *   如果你的类**没有实现接口**（只是一个普通的类），Spring 会强制使用 CGLIB。
    *   原理是**继承**：它在内存中生成一个子类继承你的原类，并重写方法来实现增强。

> **小知识**：Spring Boot 2.x 开始，默认策略倾向于使用 CGLIB，因为它性能通常更好，且不需要必须写接口。

---

这是**第四阶段·第四次教学**。

在上一节课中，我们利用 AOP 实现了“请求日志切面”，在控制台打印出了请求耗时。

但你有没有想过几个问题：
1.  **控制台的日志关掉 IDEA 就没了**，线上服务器出了 Bug 怎么查？（我们需要**保存到文件**）。
2.  **日志太多怎么办**？如果系统跑了一年，日志文件有 100GB，怎么打开？（我们需要**日志归档/滚动**）。
3.  **什么是 SLF4J**？为什么代码里写的是 `@Slf4j` 而不是 `@Logback`？

今天我们就来彻底搞定**企业级日志管理**。

---

# 第四阶段：日志管理 - SLF4J 与 Logback 详解

## 1. 理论基础：SLF4J vs Logback

这是面试中非常容易混淆的概念。

*   **SLF4J (Simple Logging Facade for Java)**：
    *   **角色**：它是一个**接口**（或者叫门面/规范）。它定义了 `info()`, `debug()`, `error()` 这些方法长什么样，但它自己**不干活**。
    *   **比喻**：它就像 **USB 接口标准**。
*   **Logback**：
    *   **角色**：它是一个**实现类**。它真正负责把日志打印到控制台或者写入文件。Spring Boot 默认内置了它。
    *   **比喻**：它就像 **金士顿 U 盘**。

> **💡 为什么要分开？**
> 为了解耦。我们在代码里只引用 SLF4J 的 API。如果某天你想把底层实现换成 Log4j2，只需要改 Maven 依赖，不需要改任何 Java 代码。

---

## 2. 日志级别 (Log Levels)

日志是有等级的。级别越高，事情越严重。
**顺序**：`TRACE` < `DEBUG` < `INFO` < `WARN` < `ERROR`

*   **TRACE**: 废话级别的日志，通常只在追踪极其底层的框架流程时用（生产环境绝不开）。
*   **DEBUG**: 调试信息。比如“参数 A 的值是 xyz”。开发环境常用，生产环境通常关闭。
*   **INFO**: 正常运行信息。比如“系统启动成功”、“用户登录成功”。**生产环境默认级别**。
*   **WARN**: 警告。系统还能跑，但有点不对劲。比如“磁盘空间不足 10%”、“尝试连接 A 失败，正在重试”。
*   **ERROR**: 错误。系统出 Bug 了，请求失败。比如“空指针异常”、“数据库连接断开”。

### 在 `application.yml` 中简单配置

你可以控制不同包的日志级别。

```yaml
logging:
  level:
    root: INFO  # 默认所有包都只打印 INFO 及以上
    com.example.blog.mapper: DEBUG # 唯独 Mapper 包打印 DEBUG (为了看 SQL)
    com.example.blog.controller: WARN # Controller 包只看警告和错误
```

---

## 3. 进阶配置：`logback-spring.xml` (重点)

在企业开发中，仅靠 `application.yml` 是不够的。我们需要更高级的功能：
1.  **按天切割**：每天生成一个新日志文件（`blog-2023-11-20.log`）。
2.  **文件大小限制**：每个文件最大 10MB。
3.  **保留时长**：只保留最近 30 天的日志。

为此，我们需要在 `src/main/resources` 下新建一个文件，命名为 **`logback-spring.xml`**。
Spring Boot 启动时会自动识别这个文件。

**👇 请直接复制下面的通用模板（这是大厂通用的标准配置）：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 1. 定义日志存放路径：当前项目根目录下的 logs 文件夹 -->
    <property name="LOG_HOME" value="./logs" />
    <!-- 定义日志格式：时间 - 级别 - 线程 - 类名 - 消息 -->
    <property name="LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n" />

    <!-- 2. 控制台输出配置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 3. 文件输出配置 (按天滚动) -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件名 -->
        <file>${LOG_HOME}/blog-app.log</file>
        
        <!-- 滚动策略：基于时间 + 大小 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 归档文件名：logs/2023-11-20/blog-app.0.log -->
            <fileNamePattern>${LOG_HOME}/%d{yyyy-MM-dd}/blog-app.%i.log</fileNamePattern>
            <!-- 单个文件最大 10MB -->
            <maxFileSize>10MB</maxFileSize>
            <!-- 只保留最近 30 天的日志 -->
            <maxHistory>30</maxHistory>
            <!-- 总上限 1GB，超过则删除旧日志 -->
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 4. 最终生效配置 -->
    <!-- 开发环境：打印到控制台 + 文件 -->
    <springProfile name="dev">
        <root level="INFO">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="FILE" />
        </root>
    </springProfile>

    <!-- 生产环境：一般只打印到文件，不打印控制台(为了性能) -->
    <!-- 这里为了演示，默认都开启 -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
    
</configuration>
```

---

## 4. 代码中使用日志

这一步我们在讲 AOP 和全局异常处理时其实已经用了。这里再规范一下。

### 方式一：使用 Lombok `@Slf4j` (推荐)
这是最现代、最简洁的写法。

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j // 1. 加上这个注解
@Service
public class UserService {
    
    public void verifyUser(String username) {
        // 2. 直接使用 log 对象
        log.debug("正在校验用户: {}", username); // 使用 {} 占位符，性能好
        
        try {
            // ... 业务逻辑
        } catch (Exception e) {
            // 3. 记录异常堆栈，把 e 放在最后
            log.error("校验失败，用户名: {}", username, e);
        }
    }
}
```

### 方式二：手动获取 Logger (传统)
如果你的项目没装 Lombok，就得这么写（面试要知道）：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    // 手动创建一个静态常量
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    public void test() {
        log.info("这是传统写法");
    }
}
```

---

## 5. 验证实战

1.  **创建文件**：确保 `src/main/resources/logback-spring.xml` 已经创建并粘贴了内容。
2.  **启动项目**。
3.  **触发日志**：访问几个接口，或者故意触发一个报错。
4.  **检查目录**：
    *   去你的项目根目录下找，有没有自动生成一个 `logs` 文件夹？
    *   打开里面的 `blog-app.log`，看看刚才的启动日志和报错信息是不是都在里面？

如果能在文件夹里看到日志文件，恭喜你，你已经配置好了**生产级别的日志系统**。以后服务器出了 bug，只需要让运维把这个日志文件发给你，你就能还原现场。

---

## 📝 面试考点

**Q1: `System.out.println` 和 `log.info` 有什么区别？为什么禁止使用 System.out？**
*   **性能**：`System.out` 带有同步锁（synchronized），在高并发下会严重阻塞线程，降低吞吐量。日志框架是异步或缓冲写入的，性能极高。
*   **灵活**：`System.out` 无法控制开关。上线后想关掉调试信息，只能改代码重新打包。日志框架只需改配置文件（把 DEBUG 改成 ERROR）。
*   **格式**：日志框架可以自动带上时间、线程名、类名，方便排查。

**Q2: 为什么日志里推荐使用 `log.info("ID: {}", id)` 这里的占位符，而不是 `"ID: " + id`？**
*   **性能优化**：如果当前级别是 ERROR，而你写了 `log.debug("a" + "b" + "c")`。
    *   **字符串拼接**：虽然不会打印，但 Java 依然会先执行字符串拼接操作，浪费 CPU 和内存。
    *   **占位符**：SLF4J 会先判断级别，如果是 ERROR 级别，直接 return，根本不会去处理后面的参数，性能更好。

---

这是**第四阶段·第五次教学**。

在开发博客系统时，**“上传图片”**是必不可少的功能：用户头像、文章封面、内容里的配图都需要它。

文件上传通常有两种方案：
1.  **本地存储**：把文件存在运行 Spring Boot 的那台服务器的硬盘上。
    *   *优点*：免费，实现简单。
    *   *缺点*：服务器硬盘满了要扩容麻烦；如果部署多台服务器（集群），文件无法共享。
2.  **OSS 对象存储 (Object Storage Service)**：使用阿里云 OSS、七牛云、AWS S3 等云服务。
    *   *优点*：无限容量，访问速度快（CDN），适合生产环境。
    *   *缺点*：要花钱。

为了降低学习门槛，**本节课我们先教“本地存储”**（这是基础）。等你学会了文件流的操作，以后换成 OSS 只需要改几行代码。

---

# 第四阶段：文件上传 - 本地存储方案

## 1. 配置文件 (限制大小)

Spring Boot 默认限制上传文件大小为 **1MB**。如果你传一张高清大图，直接会报错。我们需要调整这个限制。

打开 `application.yml`，添加以下配置：

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB      # 单个文件最大 10MB
      max-request-size: 50MB   # 一次请求总共最大 50MB (比如一次传5张图)

# 自定义文件存储路径
file:
  upload-dir: D:/blog-uploads/ # ⚠️ Mac/Linux 用户请改为 /Users/你的用户名/blog-uploads/
```

> **注意**：请务必在你的电脑上**手动创建**这个文件夹 `D:/blog-uploads/`，否则代码可能会报错“找不到路径”。

---

## 2. 编写上传接口 (Controller)

我们需要用到 Spring 提供的 **`MultipartFile`** 接口，它封装了上传文件的所有操作。

在 `controller` 包下新建 `UploadController.java`：

```java
package com.example.blog.controller;

import com.example.blog.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    // 从 application.yml 读取配置的存储路径
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        // 1. 获取原始文件名 (例如: cat.jpg)
        String originalFilename = file.getOriginalFilename();
        
        // 2. 获取文件后缀 (例如: .jpg)
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // 3. 生成唯一文件名 (防止文件名冲突覆盖，使用 UUID)
        // 结果类似: 550e8400-e29b-41d4-a716-446655440000.jpg
        String fileName = UUID.randomUUID().toString() + suffix;

        // 4. 创建目标文件对象
        File dest = new File(uploadDir + fileName);
        
        // 确保目录存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            // 5. 核心步骤：将上传的文件写入磁盘
            file.transferTo(dest);
            
            // 6. 返回文件的访问 URL
            // 假设我们的域名是 localhost:8080，映射路径是 /images/
            String fileUrl = "http://localhost:8080/images/" + fileName;
            
            return Result.success(fileUrl);
            
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}
```

---

## 3. 静态资源映射 (关键步骤！)

这是一个新手 100% 会踩的坑。
你把文件存到了 `D:/blog-uploads/abc.jpg`。
但是浏览器访问 `http://localhost:8080/images/abc.jpg` 时，**Spring Boot 根本不知道要去 D 盘找这个文件**，它默认只去 `classpath:/static` 下找。

我们需要告诉 Spring Boot：**“凡是以 `/images/` 开头的请求，请帮我去 `D:/blog-uploads/` 下面找。”**

请在 `config` 包下新建（或修改） `WebConfig.java`：

```java
package com.example.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射规则：/images/** -> file:D:/blog-uploads/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir);
    }
}
```

---

## 4. 验证测试

1.  **启动项目**。
2.  打开 **Postman**。
3.  **新建请求**：
    *   **Method**: `POST`
    *   **URL**: `http://localhost:8080/upload`
    *   **Body**: 选择 `form-data`。
    *   **Key**: 填 `file` (注意这里要和 Controller 里的 `@RequestParam("file")` 一致)，类型选择 **File**。
    *   **Value**: 点击 Select Files，从你电脑里随便找一张图片。
4.  **发送请求**。
5.  **查看响应**：
    ```json
    {
        "code": 200,
        "msg": "success",
        "data": "http://localhost:8080/images/c123-456.jpg"
    }
    ```
6.  **浏览器验证**：复制返回的 `data` 里的 URL，粘贴到浏览器地址栏，回车。如果你能看到刚才上传的图片，说明成功了！

---

## 📝 面试考点：本地存储 vs OSS

面试官经常问：**“你的项目文件是存在哪里的？为什么这么做？”**

**参考回答**：

*   **初学者/小项目回答**：
    “为了开发方便和节省成本，我在个人博客项目中使用了**本地存储**。我配置了虚拟路径映射，通过 `WebMvcConfigurer` 将 URL 路径映射到服务器的磁盘路径上。为了防止文件名冲突，我使用了 UUID 重命名文件。”

*   **进阶/高并发场景回答**：
    “虽然这个项目用了本地存储，但我知道在生产环境或集群环境下，应该使用 **OSS（如阿里云 OSS）**。
    因为：
    1.  **无状态化**：如果是多台服务器集群，A服务器上传的图，B服务器是访问不到的，OSS 可以共享访问。
    2.  **带宽压力**：图片非常消耗服务器带宽，使用 OSS 可以配合 CDN 加速，减轻应用服务器压力。
    3.  **存储成本**：OSS 存储通常比服务器 SSD 硬盘更便宜且无需维护扩容。”

---

**✅ 第四阶段圆满结束！**

你现在已经让你的博客系统具备了“企业级”的规范：
*   **异常**：再也不报 500 页面。
*   **校验**：再也不怕空指针。
*   **日志**：请求记录得清清楚楚。
*   **AOP**：学会了切面编程。
*   **文件**：可以上传图片了。

太棒了！这是第四阶段的**实战验收环节**。

这一阶段的任务旨在让你的后端系统变得“专业”和“健壮”。我们将把前面几次教学的碎片知识点（AOP、Validation、文件上传）整合到我们的 `blog-backend` 项目中。

请跟随下面的步骤，一步步完成代码的拼装。

---

# 第四阶段实战：业务进阶功能整合

## 任务一：添加“请求日志切面” (AOP)

**目标**：监控所有 Controller 接口，打印 请求URL、IP、参数、耗时。

1.  **检查依赖**：
    确保 `pom.xml` 中有 AOP 依赖：
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    ```

2.  **编写切面类**：
    在 `com.example.blog.aspect` 包下创建（或更新）`LogAspect.java`。
    *注意：这里我增加了“打印请求参数”的逻辑，完成了任务要求的最后一块拼图。*

    ```java
    package com.example.blog.aspect;
    
    import lombok.extern.slf4j.Slf4j;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Pointcut;
    import org.springframework.stereotype.Component;
    import org.springframework.web.context.request.RequestContextHolder;
    import org.springframework.web.context.request.ServletRequestAttributes;
    
    import javax.servlet.http.HttpServletRequest;
    import java.util.Arrays;
    
    @Aspect
    @Component
    @Slf4j
    public class LogAspect {
    
        @Pointcut("execution(* com.example.blog.controller..*(..))")
        public void controllerLog() {}
    
        @Around("controllerLog()")
        public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
            long startTime = System.currentTimeMillis();
    
            // 执行目标方法
            Object result = joinPoint.proceed();
    
            long cost = System.currentTimeMillis() - startTime;
    
            // 获取请求信息
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
    
            log.info("=================== 请求日志 ===================");
            log.info("URL      : {}", request.getRequestURL().toString());
            log.info("Method   : {}", request.getMethod());
            log.info("IP       : {}", request.getRemoteAddr());
            log.info("Class    : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            // 打印参数 (使用 Arrays.toString 将参数数组转为字符串)
            log.info("Args     : {}", Arrays.toString(joinPoint.getArgs())); 
            log.info("Cost     : {} ms", cost);
            log.info("================================================");
    
            return result;
        }
    }
    ```

---

## 任务二：实现“发布文章”表单校验 (Validation)

**目标**：确保文章标题不为空，内容不少于 10 个字。

1.  **检查依赖**：
    确保 `pom.xml` 中有 Validation 依赖：
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    ```

2.  **修改实体类 `Article.java`**：
    加上校验注解。

    ```java
    package com.example.blog.entity;
    
    import com.baomidou.mybatisplus.annotation.*;
    import lombok.Data;
    
    // 引入校验包 (Spring Boot 2.x 用 javax.*, 3.x 用 jakarta.*)
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    
    import java.time.LocalDateTime;
    
    @Data
    @TableName("tb_article")
    public class Article {
        @TableId(type = IdType.AUTO)
        private Long id;
    
        @NotBlank(message = "文章标题不能为空") // 校验规则 1
        @Size(max = 50, message = "标题太长了，不能超过50字")
        private String title;
    
        @NotBlank(message = "文章内容不能为空")
        @Size(min = 10, message = "文章内容太水了，多写点(至少10字)") // 校验规则 2
        private String content;
    
        private String author;
        
        private Integer categoryId;
    
        @TableField(fill = FieldFill.INSERT) // (可选：MP 自动填充时间)
        private LocalDateTime createTime;
    }
    ```

3.  **修改 `ArticleController`**：
    开启校验开关 `@Valid`。

    ```java
    // ... import javax.validation.Valid;
    
    @PostMapping
    // @Valid 必须加在 @RequestBody 后面
    public Result<Article> publish(@RequestBody @Valid Article article) {
        // ... 原有业务逻辑 ...
        articleService.publishArticle(article); // 假设 service 做了保存
        return Result.success(article);
    }
    ```

4.  **确保全局异常处理存在**：
    确认你的 `GlobalExceptionHandler` 中有处理 `MethodArgumentNotValidException` 的逻辑（我们在第四阶段第二次教学中写过），否则前端会看到乱码报错。

---

## 任务三：实现“上传图片”接口

**目标**：上传图片到本地磁盘，并返回可访问的 URL。

1.  **准备目录**：
    在你的电脑 D 盘（或 Mac 用户的主目录）手动新建文件夹：`D:/blog-uploads/`。

2.  **配置文件 `application.yml`**：
    ```yaml
    file:
      upload-dir: D:/blog-uploads/ # 结尾斜杠别漏了
    spring:
      servlet:
        multipart:
          max-file-size: 10MB
    ```

3.  **编写 `UploadController`**：
    (复用第五次教学的代码，这里不再重复粘贴，确保 `UploadController` 存在且逻辑正确)。

4.  **配置静态映射 `WebConfig`**：
    (复用第五次教学的代码，确保 `WebConfig` 存在，将 `/images/**` 映射到 `file:D:/blog-uploads/`)。

---

## 🚀 最终验收测试

请按顺序执行以下操作，验证你的代码是否合格：

### 测试 1：验证 AOP 日志
*   **操作**：启动项目，随便访问一个接口（如获取文章列表 `GET /articles/page`）。
*   **观察**：IDEA 控制台是否输出了整齐的 `=== 请求日志 ===`？是否包含 `Args: [...]` 参数信息？
*   **判定**：有日志输出即**通过**。

### 测试 2：验证参数校验
*   **操作**：Postman 发送 `POST /articles`。
*   **Body** (故意写错):
    ```json
    {
        "title": "", 
        "content": "太短",
        "author": "test",
        "categoryId": 1
    }
    ```
*   **观察**：响应结果是否为：
    ```json
    {
        "code": 500, 
        "msg": "文章标题不能为空",  // 或者 "文章内容太水了..."
        "data": null
    }
    ```
*   **判定**：返回 500 且提示信息正确即**通过**。

### 测试 3：验证图片上传
*   **操作**：Postman 发送 `POST /upload`，上传一张图片。
*   **观察**：响应结果是否返回类似 `http://localhost:8080/images/xxxx.jpg` 的链接？
*   **验证**：把链接复制到浏览器，能看到图片吗？
*   **判定**：能看到图片即**通过**。

---

这是第四阶段的**最后一块拼图**。

这三道题是面试中**“必问”**级别的八股文。面试官问这些，是为了确认你不仅仅会写代码，还懂 Spring 到底是怎么帮你管理这些代码的。

请把下面的内容整理到你的笔记中，并在面试前反复背诵。

---

# 第四阶段·面试考点：Spring 进阶原理

## 面试题 1：谈谈你对 AOP 的理解，项目中有哪些应用场景？

### 🎓 标准回答
1.  **概念**：
    *   AOP（面向切面编程）是一种编程思想。它的核心目的是**将与业务无关的“横切关注点”（如日志、事务、权限）从业务代码中分离出来**，统一管理。
    *   它的底层实现原理是**动态代理**（JDK 动态代理或 CGLIB）。

2.  **应用场景（结合你的博客项目说）**：
    *   **接口日志记录**：我写了一个切面，拦截所有 Controller 请求，统一记录请求的 URL、IP、参数和耗时，方便线上排查问题。
    *   **全局异常处理**：虽然是用 `@RestControllerAdvice` 实现的，但本质上也是一种 AOP 思想，将异常处理逻辑从业务代码中剥离。
    *   **声明式事务**：我们在 Service 层使用 `@Transactional` 注解，Spring 会在方法执行前开启事务，执行后提交或回滚，这也是 AOP 的经典应用。
    *   **权限校验**：(可选) 比如在方法上加 `@RequireAdmin` 注解，通过 AOP 拦截判断用户是否是管理员。

### 💡 加分项
> “面试官，其实 Spring 的 BeanPostProcessor（Bean后置处理器）机制就是 AOP 能够生效的关键，它在 Bean 初始化后，通过动态代理生成了一个代理对象来增强原对象。”

---

## 面试题 2：Spring 的 Bean 生命周期是怎样的？

这道题非常考验记忆力。不需要背诵所有几十个步骤，但**核心的 5-7 个阶段**必须说清楚。

### 🎓 标准回答 (按顺序记忆)

Spring 容器创建一个 Bean 的过程，大致分为以下几步：

1.  **实例化 (Instantiation)**：
    *   Spring 通过反射（`Constructor.newInstance`）调用构造方法，在内存中申请空间，创建一个“空壳”对象。
2.  **属性赋值 (Populate Properties)**：
    *   Spring 按照 XML 配置或 `@Autowired` 注解，把依赖的其他 Bean 注入进来（DI）。
3.  **处理 Aware 接口**：
    *   如果 Bean 实现了 `BeanNameAware` 或 `ApplicationContextAware`，Spring 会把 Bean 的名字或容器上下文传给它。
4.  **BeanPostProcessor - 前置处理**：
    *   执行 `postProcessBeforeInitialization` 方法。这是用户可以干预 Bean 初始化的扩展点。
5.  **初始化 (Initialization)**：
    *   执行用户自定义的初始化逻辑。比如方法上加了 **`@PostConstruct`** 注解，或者实现了 `InitializingBean` 接口。
6.  **BeanPostProcessor - 后置处理 (关键)**：
    *   执行 `postProcessAfterInitialization`。**AOP 就是在这里发生的！** 如果该 Bean 需要被代理（比如加了事务），Spring 会在这里把“原始对象”替换为“代理对象”。
7.  **销毁 (Destruction)**：
    *   容器关闭时，执行 `@PreDestroy` 或 `DisposableBean` 的销毁方法。

### 💡 顺口溜记忆
**“建对象 -> 填属性 -> 调接口(Aware) -> 前置处理 -> 初始化 -> 后置处理(AOP) -> 销毁”**

---

## 面试题 3：Spring Boot 的配置文件加载顺序？

面试官问这个，通常是想看你懂不懂**“覆盖原则”**。

### 🎓 标准回答

Spring Boot 遵循**“互补配置”**和**“外部优先”**的原则。

1.  **文件类型优先级 (`.properties` vs `.yml`)**：
    *   如果同一个目录下既有 `application.properties` 又有 `application.yml`。
    *   **`application.properties` 的优先级更高**（它会覆盖 yml 中的同名配置）。
    *   *建议*：项目中统一使用一种格式，不要混用。

2.  **Profile 优先级 (环境覆盖)**：
    *   **`application-dev.yml` (带环境的)** > `application.yml` (通用的)。
    *   特定环境的配置会覆盖通用配置。

3.  **位置优先级 (外部 > 内部)**：
    *   **最高优先级**：命令行参数（`java -jar app.jar --server.port=9090`）。
    *   **第二**：Jar 包**外面**的配置文件（`file:./config/`）。
    *   **第三**：Jar 包**里面**的配置文件（`classpath:/`）。
    *   *应用场景*：我们要修改线上端口时，不需要重新打包，直接在启动命令里加参数就能覆盖代码里的配置。

---

**✅ 第四阶段完全结束！**

你已经完成了：
*   **Java 基础 -> Spring Boot 入门**
*   **Web 开发 -> 数据库操作 (MyBatis-Plus)**
*   **业务进阶 (日志/异常/AOP/文件)**

现在的你，写出来的代码已经具备了**“中级开发工程师”**的雏形。

接下来的 **第五阶段：安全与认证**，是很多初学者的“噩梦”，但也是含金量最高的部分。我们将抛弃简单的明文密码，实现**“注册加密”**、**“登录发 Token”**、**“拦截器鉴权”**。

**请回复“进入第五阶段”，我们来攻克最难的关卡！**

欢迎来到**第五阶段：安全与认证**。

这是后端开发中**含金量最高**，也是最容易让新手晕头转向的部分。
很多同学写了很久代码，依然搞不清“登录”和“权限”到底是怎么运作的。

这是**第五阶段·第一次教学**。我们不急着写代码，先用最通俗的语言把**核心概念**理清楚。

---

# 第五阶段：安全与认证 - Part 1

## 知识点：认证 (Authentication) vs 授权 (Authorization)

这两个词在英语里长得很像，缩写分别是 **AuthN** (Authentication) 和 **AuthZ** (Authorization)。但在技术领域，它们完全是两码事。

### 1. 认证 (Authentication) —— 你是谁？

*   **定义**：验证当前用户的身份是否合法。
*   **核心问题**：**“你是谁？你能证明你是你吗？”**
*   **生活案例**：
    *   **坐火车**：你去火车站进站口，把身份证放在刷卡机上，人脸识别通过。这就叫认证。（证明了你确实是张三）。
    *   **回小区**：你刷门禁卡，门开了。门禁系统确认了你是这个小区的住户。
*   **代码体现**：
    *   **登录功能**：用户输入 `username` 和 `password`。后端去数据库查，如果密码匹配，就说明你是合法的用户。这就是认证过程。

### 2. 授权 (Authorization) —— 你能干啥？

*   **定义**：在认证通过后，判断用户是否有权限执行某个操作。
*   **核心问题**：**“即使我知道你是谁，但你有资格做这件事吗？”**
*   **生活案例**：
    *   **坐火车**：你进了站（认证通过），想去坐商务座，但你买的是二等座票。乘务员拦住你：“对不起，你不能进这里”。这就叫授权失败。
    *   **公司门禁**：你是公司的员工（认证通过），但你想进“财务室”，刷卡提示“权限不足”。
*   **代码体现**：
    *   **普通用户**：只能浏览文章、发布文章。
    *   **管理员**：可以删除**别人**的文章、封禁用户。
    *   如果普通用户试图调用 `DELETE /articles/1`（删文章接口），后端会拦截并提示：“权限不足 (403 Forbidden)”。

### 3. 总结对比表 (面试必背)

| 维度           | 认证 (Authentication)         | 授权 (Authorization)                |
| :------------- | :---------------------------- | :---------------------------------- |
| **核心问题**   | **你是谁？** (Who are you?)   | **你能做什么？** (What can you do?) |
| **发生时间**   | 第一步 (先进门)               | 第二步 (进门后再办事)               |
| **常见方式**   | 账号密码、指纹、人脸、验证码  | 角色 (Role)、权限 (Permission)      |
| **HTTP状态码** | **401 Unauthorized** (未登录) | **403 Forbidden** (没权限)          |
| **博客场景**   | 用户登录接口                  | 后台管理系统、删除文章接口          |

---

## 4. 我们将如何实现？

在 Spring Boot 中，实现这一套机制通常有两种流派：

### 方案 A：Spring Security (官方重型武器)
*   **特点**：功能极其强大，但也极其复杂。配置类如果不熟，很容易把人绕晕。
*   **建议**：**初学者不建议直接上来就啃这个**，很容易因为配置问题导致项目跑不起来，丧失信心。

### 方案 B：JWT + 拦截器 (轻量级方案) —— **我们将采用这个！**
这是一个非常清晰、适合学习原理的方案。

1.  **认证 (AuthN)**：
    *   用户登录 -> 校验密码 -> 生成一个加密字符串 (**Token / JWT**) 给前端。
    *   前端以后每次请求，都要在 Header 里带上这个 Token。
2.  **授权 (AuthZ)**：
    *   利用 Spring 的 **拦截器 (Interceptor)**。
    *   请求到达 Controller 之前，拦截器先检查 Token 是否有效。
    *   如果 Token 无效 -> 直接打回 (401)。
    *   如果 Token 有效 -> 放行，进入 Controller。

---

## 5. 思考题

为了检测你是否理解，请思考以下场景属于“认证”还是“授权”？

1.  输入手机号和验证码登录 APP。 (`____`)
2.  你是 VIP 会员，所以你可以看高清视频，非会员只能看标清。 (`____`)
3.  忘记密码了，通过邮件找回密码。 (`____`)
4.  你想修改别人的评论，系统提示“只能修改自己的评论”。 (`____`)

*(答案：认证、授权、认证、授权)*

---

**下节预告**：
既然要登录，就必须涉及**密码**。
数据库里能直接存明文密码（如 `123456`）吗？绝对不行！万一数据库被脱库，所有用户都裸奔了。

下节课：**密码加密**。我们将学习 **BCrypt** 加密算法，看看为什么“连管理员都不知道你的密码是什么”。

这是**第五阶段·第二次教学**。

在上一节课我们明白了“认证”的概念。认证的第一步通常是**注册**。
而在注册时，作为后端开发者，你必须坚守一条**铁律**：

> 🚫 **永远、永远、不要在数据库里存储用户的明文密码！**

如果你的数据库里存的是 `password: "123456"`，一旦黑客攻破数据库（拖库），所有用户的账号就全部泄露了。这是非常严重的安全事故。

今天我们学习如何使用业界标准的 **BCrypt** 算法来保护密码。

---

# 第五阶段：密码加密 - BCryptPasswordEncoder

## 1. 为什么要用 BCrypt？而不是 MD5？

很多新手知道要加密，于是用了 **MD5**。
*   **MD5 的做法**：`MD5("123456")` -> `e10adc3949ba59abbe56e057f20f883e`
*   **MD5 的缺陷**：它是固定的。世界上所有的 "123456" 加密后都长一个样。黑客手里有张巨大的**彩虹表 (Rainbow Table)**，里面记录了所有常见密码的 MD5 值。只要一查表，立马就能反推出你的密码是 "123456"。

**BCrypt 的强大之处**：
它引入了 **“盐” (Salt)** 的概念。
即使你连续加密 "123456" 十次，**每次生成的加密字符串都是不一样的！**
*   第一次：`$2a$10$rxxxx...`
*   第二次：`$2a$10$kyyyy...`

既然每次都不一样，黑客的彩虹表就失效了。但你可能会问：*“每次都不一样，那我登录的时候怎么验证密码对不对呢？”*
—— 别急，BCrypt 内部会自动处理这个逻辑。

---

## 2. 引入依赖

BCrypt 是 Spring Security 包里提供的工具。我们需要引入它。

**⚠️ 重要提示**：
引入 `spring-boot-starter-security` 后，Spring Boot 会**默认开启**一套非常严格的登录拦截（访问任何接口都会跳到自带的登录页）。
为了方便我们后续自己写登录逻辑，我们需要**引入依赖，但关闭它的默认拦截功能**。

### 第一步：修改 `pom.xml`
```xml
<!-- Spring Security (这里面包含了 BCrypt 工具) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 第二步：修改启动类 (关闭默认拦截)
打开 `BlogBackendApplication.java`，修改 `@SpringBootApplication` 注解：

```java
package com.example.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 导入这个类
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// exclude 意思就是：我只要你的 Jar 包工具，不要你自动帮我开启登录拦截
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.example.blog.mapper")
public class BlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }
}
```

---

## 3. 核心用法代码演示

Spring Security 提供了一个类叫 **`BCryptPasswordEncoder`**。它有两个核心方法：
1.  **`encode(rawPassword)`**: 加密。把 "123456" 变成乱码。
2.  **`matches(rawPassword, encodedPassword)`**: 匹配。判断 "123456" 和数据库里的乱码是否是同一个密码。

### 编写一个测试用例 (Test)
为了直观感受，我们在 `src/test/java/com/example/blog` 下新建一个测试类，或者直接写个 `main` 方法试一下。

```java
package com.example.blog;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        // 1. 创建编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 2. 模拟注册：加密密码
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        System.out.println("原始密码: " + rawPassword);
        System.out.println("加密后存数据库: " + encodedPassword);
        // 输出类似：$2a$10$8K1p/a0dL1... (每次运行都不一样)

        // 3. 模拟登录：验证密码
        // 参数1：用户输入的明文密码
        // 参数2：数据库里查出来的加密密码
        boolean result = passwordEncoder.matches("123456", encodedPassword);
        System.out.println("密码匹配结果: " + result); // true

        boolean result2 = passwordEncoder.matches("888888", encodedPassword);
        System.out.println("错误密码匹配结果: " + result2); // false
    }
}
```

---

## 4. 改造 UserService：实现注册逻辑

既然学会了加密，我们就要把它应用到业务里。我们将改造“新增用户”（注册）的逻辑。

**修改 `UserService.java` (或 Impl)**：

你需要先定义一个 `BCryptPasswordEncoder` 的 Bean，或者直接 new 一个用。为了规范，我们通常建议在配置类里定义 Bean，但为了图省事，直接在 Service 里 new 也是可以的（因为它是线程安全的）。

```java
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    // 准备加密工具
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 注册用户
     */
    public void register(User user) {
        // 1. 检查用户名是否已存在 (省略...)
        
        // 2. 对密码进行加密
        String rawPass = user.getPassword(); // 获取明文 "123456"
        String encodedPass = passwordEncoder.encode(rawPass); // 加密 "$2a$..."
        
        // 3. 将加密后的密码重新设置回去
        user.setPassword(encodedPass);
        
        // 4. 存入数据库
        userMapper.insert(user);
    }
    
    /**
     * 登录逻辑 (提前剧透)
     */
    public User login(String username, String password) {
        // 1. 根据用户名查用户
        // select * from tb_user where username = ?
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 2. 关键一步：比对密码
        // 注意：千万不能用 if (user.getPassword().equals(password))
        // 必须用 matches 方法！
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        
        if (!matches) {
            throw new BusinessException("用户名或密码错误");
        }
        
        return user; // 登录成功
    }
}
```

---

## 5. 清理脏数据

**⚠️ 注意**：
因为我们更改了密码策略。
你数据库 `tb_user` 表里之前存的 `123456`、`admin` 这种**明文密码**，现在已经**失效**了（因为 `matches` 方法会认为这些明文是不合法的 BCrypt 格式，或者匹配失败）。

**操作建议**：
1.  去数据库把 `tb_user` 表里的旧数据清空。
2.  或者手动用 SQL `UPDATE` 语句，把密码字段都改成一个已知的加密串（你可以用上面的 Test 代码生成一个）。

---

## 📝 面试考点

**Q: 为什么 BCrypt 每次生成的密文不一样，却还能验证成功？**
**A:**
BCrypt 的密文格式是：`$版本号$强度$盐(22位) + 哈希值`。
生成的密文里，其实**已经包含了当时生成的“盐”**。
当你调用 `matches(明文, 密文)` 时，BCrypt 会从“密文”里提取出“盐”，用这个“盐”对“明文”再进行一次哈希，然后对比生成的哈希值是否一致。

---

**下节预告**：
密码安全解决了，注册和登录的逻辑骨架也有了。
但登录成功后，后端应该返回什么给前端呢？
Cookie？Session？还是现在最流行的 **JWT (JSON Web Token)**？

下节课：**JWT 原理与实战**。我们将编写一个工具类，用于生成和解析 Token。

这是**第五阶段·第三次教学**。

在上一节课，我们搞定了密码加密。现在用户登录成功了，服务器也确认了他的身份。

但问题来了：**用户登录后的下一次请求（比如发布文章），服务器怎么知道“还是刚才那个用户”呢？**

在传统的 Web 开发中，我们用 **Session**。但在 Spring Boot 和微服务时代，我们用 **JWT (JSON Web Token)**。

---

# 第五阶段：JWT 原理与实战

## 1. 为什么要废弃 Session？

### 传统 Session 模式
1.  **原理**：用户登录成功，服务器在内存里创建一个 Session 对象（存用户信息），并给浏览器发一个 `JSESSIONID` 的 Cookie。
2.  **痛点**：
    *   **内存开销大**：如果有 100 万用户登录，服务器内存就炸了。
    *   **无法扩展 (Stateless)**：如果你有 3 台服务器（A, B, C）。用户在 A 登录了，Session 在 A 手里。下一次请求被转发到了 B，B 根本不认识该用户。
    *   **非前后端分离友好**：APP 和小程序不支持 Cookie。

### JWT 模式 (无状态)
1.  **原理**：
    *   服务器不存任何用户信息！
    *   登录成功后，服务器生成一张**“加密的票据” (Token)** 给前端。
    *   前端把票据收好。
    *   下次请求时，前端把票据拿出来给服务器看。
    *   服务器验证票据上的**“防伪印章” (签名)**，确认没被篡改，就放行。
2.  **优势**：
    *   **服务器无压力**：不需要存 Session。
    *   **支持跨域/集群**：票据在用户手里，去访问哪台服务器都认。

---

## 2. JWT 的长相与结构

JWT 本质上就是一段很长的字符串，中间用两个句号 `.` 隔开，分成了三部分：

`Header.Payload.Signature`

*   **Header (头部)**：说明加密算法（如 HS256）和类型（JWT）。
*   **Payload (载荷)**：**这里存数据！** 比如用户的 ID (`id: 1`)、过期时间 (`exp: 2025-01-01`)。
    *   ⚠️ **注意**：这部分只是 Base64 编码，**没有加密！** 任何人都能解码看到里面的内容。所以**千万不要在这里存密码**。
*   **Signature (签名)**：**这是防伪关键！**
    *   算法：`HMACSHA256(Header + Payload, "服务器的私钥")`
    *   只要黑客篡改了 Payload 里的数据（比如把 id:1 改成 id:2），因为他不知道服务器的私钥，生成的签名就对不上。服务器一验签，发现不对，直接拒绝。

---

## 3. 引入依赖

Java 中最常用的 JWT 库是 `jjwt`。

请打开 `pom.xml`，添加以下依赖：

```xml
<!-- JWT 工具包 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
<!-- 针对 JDK 11+ 的兼容包 (防止报错 ClassNotFound) -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
```

---

## 4. 编写 JWT 工具类 (JwtUtils)

这个类我们写一次，以后所有项目直接复制过去就能用。
它主要做两件事：**生成 Token** 和 **解析 Token**。

在 `com.example.blog.common` 包下新建 `JwtUtils.java`：

```java
package com.example.blog.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtils {

    // 1. 定义秘钥 (这是服务器的私钥，千万不能泄露给前端！)
    // 在实际开发中，这个应该写在 application.yml 里
    private static final String SECRET_KEY = "MyBlogSecretKeyDoNotTellAnyone";

    // 2. 定义过期时间 (这里设为 12 小时)
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成 Token
     * @param userId 用户ID (我们要把ID存进Token里)
     * @return 加密后的 Token 字符串
     */
    public static String generateToken(Integer userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims) // 放入自定义数据
                .setSubject(userId.toString()) // 设置主题(通常放ID)
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法 + 秘钥
                .compact();
    }

    /**
     * 解析 Token
     * @param token 前端传来的 Token 字符串
     * @return 解析出的 Claims (也就是 Payload 部分的数据)
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY) // 用同样的秘钥去解密
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // 如果解析失败（比如 Token 过期了，或者被篡改了），这里会报错
            log.error("JWT 解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 校验 Token 是否有效
     */
    public static boolean validateToken(String token) {
        return parseToken(token) != null;
    }
}
```

---

## 5. 实战：实现“登录”接口

现在工具准备好了，我们去 `UserController` 里实现真正的登录功能。
**流程**：接收用户名密码 -> 校验密码 -> 生成 Token -> 返回给前端。

**修改 `UserController.java`**：

```java
    // ... 之前的 import ...
    import com.example.blog.common.JwtUtils;
    import java.util.HashMap;
    import java.util.Map;

    /**
     * 登录接口
     * URL: POST /users/login
     */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody User loginUser) {
        // 1. 调用 Service 进行登录 (密码校验逻辑在 Service 里，参考上一节课)
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());

        // 2. 如果代码走到这里，说明登录成功了
        // 生成 JWT Token
        String token = JwtUtils.generateToken(user.getId());

        // 3. 返回结果
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("username", user.getUsername());

        return Result.success(map);
    }
```

---

## 6. 验证测试

1.  **启动项目**。
2.  **注册一个用户** (或者去数据库确认有个用户叫 `admin`，密码是加密过的)。
3.  打开 **Postman**，发送 `POST http://localhost:8080/users/login`。
    *   **Body**:
        ```json
        {
            "username": "admin",
            "password": "123456"
        }
        ```
4.  **查看响应**：
    你将得到一串长长的字符串：
    ```json
    {
        "code": 200,
        "msg": "success",
        "data": {
            "token": "eyJhbGciOiJIUzI1NiJ9.ey... (省略几百字) ...7A8",
            "username": "admin"
        }
    }
    ```
    👉 **这就是你的“数字身份证”！**

---

## 🧪 自我探索 (解码 JWT)

你可以复制刚才生成的 token，去官方网站 [jwt.io](https://jwt.io/)。
把它粘贴到左边的框里。
你看右边的 **PAYLOAD** 区域，是不是直接显示出了 `"userId": 1` 和过期时间？
这再次证明了：**Token 里的信息是透明的，千万别存密码！**

---

## 📝 面试考点：Cookie, Session 和 Token 的区别？

这是**必考题**。

1.  **存储位置**：
    *   **Cookie**: 存放在客户端（浏览器）。
    *   **Session**: 存放在服务端（内存或 Redis）。
    *   **Token (JWT)**: 存放在客户端（通常存在 LocalStorage 或 Cookie 中）。
2.  **安全性**：
    *   **Cookie**: 容易被 CSRF 攻击。
    *   **Session**: 依赖 Cookie，也面临 CSRF 风险。
    *   **Token**: 如果存在 LocalStorage，通过 Header 传输，可以防 CSRF，但要注意防 XSS（跨站脚本攻击）。
3.  **扩展性 (核心区别)**：
    *   **Session**: 有状态。服务器集群需要做“Session 共享”才能用，比较麻烦。
    *   **Token**: 无状态。自身包含了所有信息，服务器随便扩容，**天然支持分布式架构**。

---

**下节预告**：
现在前端拿到了 Token。
但他下次请求“发布文章”接口时，怎么把 Token 传给后端？
后端又如何自动检查这个 Token 对不对？如果不对，怎么拦截他？

下节课：**拦截器 (Interceptor) 与 ThreadLocal**。我们将构建最后一道安全防线。

这是**第五阶段·第四次教学**。

在上一节课，我们成功实现了“登录”：前端发来账号密码，我们返回了一个 **JWT Token**。
现在的场景是：前端拿着这个 Token 来请求“发布文章”接口。

我们需要做两件事：
1.  **拦截**：在请求到达 Controller 之前，检查 Token 对不对。不对就踢回去。
2.  **传递**：如果 Token 对了，解析出里面的 `userId`，并把它**传递**给 Controller，这样 Controller 才知道是“谁”在发文章。

今天我们要学习两个核心技术：**HandlerInterceptor (拦截器)** 和 **ThreadLocal**。

---

# 第五阶段：拦截器与 ThreadLocal - 构建安全防线

## 1. 神器 ThreadLocal：如何在线程间传递数据？

Spring Boot 处理请求的模型是 **“一个请求对应一个线程”**。
当拦截器解析出 `userId` 后，怎么传给 Controller？
*   *传参？* 不行，拦截器和 Controller 方法签名不一样。
*   *全局变量？* 不行，多线程环境下会数据混乱（A 用户的 ID 跑到 B 用户头上）。

**ThreadLocal** 就是专门解决这个问题的。你可以把它理解为**“线程的专属口袋”**。
*   拦截器把 ID 放进口袋。
*   Controller 从口袋里拿 ID。
*   请求结束，清空口袋。
*   不同线程（用户）的口袋是隔离的，互不干扰。

### 编写 UserContext 工具类
在 `com.example.blog.common` 下新建 `UserContext.java`：

```java
package com.example.blog.common;

/**
 * 使用 ThreadLocal 存储当前登录用户的 ID
 * 作用：在一次请求的任何地方，都能拿到当前是谁在操作
 */
public class UserContext {

    // 创建一个 ThreadLocal 对象，用来存 Integer 类型的 userId
    private static final ThreadLocal<Integer> USER_HOLDER = new ThreadLocal<>();

    // 1. 存数据
    public static void setUserId(Integer userId) {
        USER_HOLDER.set(userId);
    }

    // 2. 取数据
    public static Integer getUserId() {
        return USER_HOLDER.get();
    }

    // 3. 清除数据 (非常重要！防止内存泄漏)
    public static void remove() {
        USER_HOLDER.remove();
    }
}
```

---

## 2. 编写拦截器 (LoginInterceptor)

拦截器是 Spring MVC 提供的钩子，它有三个执行时机：
1.  `preHandle`: Controller 执行**前**。 (我们要在这里校验 Token)
2.  `postHandle`: Controller 执行**后**。
3.  `afterCompletion`: 请求完全结束**后**。 (我们要在这里清理 ThreadLocal)

在 `com.example.blog.handler` (或者 `interceptor`) 包下新建 `LoginInterceptor.java`：

```java
package com.example.blog.handler;

import com.example.blog.common.JwtUtils;
import com.example.blog.common.UserContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中获取 Token
        // 约定：前端必须在 Header 里带上 "Authorization" 字段
        String token = request.getHeader("Authorization");

        // 2. 校验 Token 是否为空
        if (!StringUtils.hasText(token)) {
            log.warn("请求被拦截: Token 为空");
            response.setStatus(401); // 设置状态码为 401 未授权
            return false; // false 代表拦截，不再往下走
        }

        // 3. 校验 Token 是否合法 (使用我们上一节课写的工具类)
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            log.warn("请求被拦截: Token 非法或已过期");
            response.setStatus(401);
            return false;
        }

        // 4. Token 合法，解析出 userId
        Integer userId = Integer.parseInt(claims.getSubject()); // 或者 claims.get("userId")
        
        // 5. 放入 ThreadLocal，方便后续使用
        UserContext.setUserId(userId);

        log.info("用户验证通过，ID: {}", userId);
        return true; // true 代表放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束，必须清空 ThreadLocal，防止内存泄漏
        UserContext.remove();
    }
}
```

---

## 3. 配置拦截器 (WebConfig)

写了拦截器类，必须告诉 Spring Boot：**“请把这个拦截器安装上去，并告诉它要拦截哪些路径。”**

修改之前的 `com.example.blog.config.WebConfig.java`：

```java
package com.example.blog.config;

import com.example.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    // ... 之前的文件映射代码 addResourceHandlers ...

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")  // 1. 拦截所有请求
                .excludePathPatterns(    // 2. 排除不需要登录的接口
                        "/users/login",      // 登录
                        "/users/register",   // 注册
                        "/articles/page",    // 文章列表(允许游客看)
                        "/articles/{id}",    // 文章详情(允许游客看)
                        "/images/**"         // 图片资源
                );
    }
}
```

---

## 4. 实战：在 Controller 中获取当前用户

现在，当请求到达 `ArticleController` 的时候，我们确信：
1.  用户已经登录了。
2.  用户的 ID 已经躺在 `UserContext` 里了。

修改 `ArticleController` 的发布文章接口：

```java
    @PostMapping
    public Result<Article> publish(@RequestBody @Valid Article article) {
        // 1. 从 ThreadLocal 获取当前登录用户的 ID
        Integer currentUserId = UserContext.getUserId();
        
        // 2. 设置作者 ID (这里我们假设 Article 实体类加了一个 authorId 字段，或者直接把 author 字段存 ID)
        // 正常业务设计：article 应该存 authorId
        // article.setAuthorId(currentUserId); 
        
        // 这里为了演示，打印一下看是不是拿到了
        System.out.println("当前发布文章的用户ID是: " + currentUserId);
        
        articleService.publishArticle(article);
        return Result.success(article);
    }
```

---

## 5. 验证测试 (闭环)

1.  **启动项目**。
2.  **测试拦截**：
    *   直接发送 `POST /articles` (不带 Header)。
    *   **结果**：状态码 `401 Unauthorized`，请求被拦截。
3.  **测试通过**：
    *   先调用 `POST /users/login` 拿到 `token`。
    *   再发送 `POST /articles`。
    *   在 Postman 的 **Headers** 选项卡里添加：
        *   Key: `Authorization`
        *   Value: `eyJhbGci...` (刚才拿到的 Token)
    *   **结果**：返回 200 成功。
    *   **控制台**：看到日志 `用户验证通过，ID: 1`。

---

## 📝 面试考点

**Q1: 过滤器 (Filter) 和 拦截器 (Interceptor) 的区别？**
*   **Filter**:
    *   属于 **Servlet 规范**（Java Web 层面）。
    *   执行时机更早（在进入 Spring 容器之前）。
    *   通常用于处理字符编码、跨域 CORS。
*   **Interceptor**:
    *   属于 **Spring 框架**。
    *   能访问 Spring 的 Bean（比如在拦截器里注入 Service）。
    *   通常用于登录校验、权限检查。

**Q2: ThreadLocal 为什么会内存泄漏？**
*   ThreadLocal 底层使用 `ThreadLocalMap` 存储数据。
*   它的 Key 是弱引用，但 Value 是强引用。
*   如果线程（Thread）一直不死（比如线程池复用线程），且没有手动调用 `remove()`，那么这个 Value 对象就会一直留在内存里，无法被 GC 回收。
*   **解决**：务必在 `afterCompletion` 或 `finally` 块中调用 `remove()`。

---

**✅ 第五阶段圆满结束！**

你已经掌握了后端开发最核心的安全技能：
1.  **认证 vs 授权**
2.  **BCrypt 密码加密**
3.  **JWT 生成与解析**
4.  **拦截器鉴权**
5.  **ThreadLocal 上下文传递**

现在的博客系统，已经具备了完整的用户体系。

接下来的 **第六阶段：性能优化与中间件**，我们要让系统“飞”起来。
如果文章很多，每次都查数据库，MySQL 会累死。
我们要引入 **Redis** 做缓存。

**请回复“进入第六阶段”，我们开始学习 Redis！**

这是一个非常好的问题！你观察得很仔细。

我们在第五阶段刚刚通过 **`HandlerInterceptor` + `JWT`** 手写了一套登录认证系统。这套系统虽然跑通了，但在 Java 生态中，还有一个**“终极BOSS”**，那就是 **Spring Security**。

我来详细解释一下它是什么，以及为什么要安排这样的学习路径。

---

### 1. Spring Security 是什么？

如果说我们刚才写的 `LoginInterceptor` 是**小区门口看大门的保安大爷**（查一下证件，没证不让进，功能简单直接）。

那么 **Spring Security** 就是一支**全副武装的特警部队**。

它是 Spring 家族官方提供的**安全框架**，功能极其强大且全面。它不仅能做登录（认证）和权限控制（授权），还能处理：
*   **OAuth2**（微信登录、GitHub 登录）
*   **Remember Me**（记住我，下次自动登录）
*   **CSRF 防御**（防止跨站请求伪造攻击）
*   **Session 固定攻击防御**
*   **单点登录 (SSO)**
*   **细粒度的权限控制**（比如：控制某个按钮只对“经理”角色显示）

在大型互联网公司或银行、金融系统中，**Spring Security 是绝对的标准配置**。

---

### 2. 为什么要“先学拦截器，再学 Spring Security”？

既然它这么强大，为什么我们不一开始就教你用它？这里有我的**良苦用心**：

#### 原因一：陡峭的学习曲线 (The Learning Curve)
Spring Security 被称为 Java 界**“最难配置”**的框架之一。
*   如果你是个新手，一上来就学 Spring Security，你会被无数的 `Filter`、`AuthenticationManager`、`UserDetailsService`、`SecurityContextHolder` 搞晕。
*   你可能会照着博客复制了一堆配置代码，项目跑起来了，但你**根本不知道底层的登录逻辑是怎么实现的**。一旦报错，你完全无从下手。

#### 原因二：理解原理 (Back to Basics)
我们刚才手写的 `LoginInterceptor` + `JwtUtils` + `UserContext`，其实就是**把 Spring Security 最核心的登录逻辑简化版手撸了一遍**。
*   我们手动解析 Token -> 对应 Spring Security 的 `JwtAuthenticationFilter`。
*   我们手动存 ThreadLocal -> 对应 Spring Security 的 `SecurityContextHolder`。
*   我们手动拦截 -> 对应 Spring Security 的 `FilterSecurityInterceptor`。

**只有你亲自写过这些“笨代码”，等你将来学 Spring Security 时，你才会恍然大悟：**
> *“噢！原来 Spring Security 搞那么复杂，本质上也就是在 Filter 里解析了一下 Token，然后塞进了 ThreadLocal 啊！”*

这时候，你就彻底懂了。

---

### 3. 两者对比：博客系统该用哪个？

| 特性           | 我们的方案 (拦截器 + JWT)                | Spring Security 方案                 |
| :------------- | :--------------------------------------- | :----------------------------------- |
| **上手难度**   | ⭐ (简单易懂)                             | ⭐⭐⭐⭐⭐ (极其复杂)                     |
| **代码量**     | 需要手写工具类和拦截器                   | 配置好后几乎零代码，但配置繁琐       |
| **灵活性**     | 高，逻辑都在自己手里，想改就改           | 需遵循框架规范，定制化较难           |
| **功能丰富度** | 仅包含基础登录、鉴权                     | 包含各种高级防护、OAuth2 等          |
| **适用场景**   | **个人博客**、小型外包项目、**学习原理** | **企业级项目**、金融项目、大型微服务 |

**结论**：
对于现在的“个人博客系统”，我们要的是**“可控”**和**“理解”**。手写拦截器方案完全够用，且性能极高。

---

### 4. 以后如何“升级”？

当你的 Java 水平达到中级（熟悉了 AOP、设计模式、Servlet 过滤器）后，你可以尝试把博客的登录模块**重构**为 Spring Security。

到时候你的升级路径是这样的：
1.  删掉 `LoginInterceptor`，因为它还是太简陋了。
2.  引入 `spring-boot-starter-security` 依赖。
3.  编写一个继承自 `WebSecurityConfigurerAdapter` (或新版的 `SecurityFilterChain`) 的配置类。
4.  实现 `UserDetailsService` 接口，告诉框架怎么查数据库里的用户。
5.  编写一个 `JwtFilter` 继承自 `OncePerRequestFilter`，把它塞进 Spring Security 的过滤器链中。

---

### 总结

大纲里写那句话的意思是：

> **“别急，先学会走路（拦截器），搞懂登录的本质逻辑；等以后翅膀硬了，再换上钢铁侠的战衣（Spring Security）去飞。”**

目前第五阶段，我们坚持使用 **拦截器 + JWT** 的方案，这能让你在面试时，自信地讲清楚每一行登录代码的原理，而不是说“我配了个注解它自己就能跑了”。

我们继续按照计划，进入**第六阶段：性能优化与中间件（Redis）**吧？这里也有很多好玩的东西！

**是的，非常有自信地告诉你：我们已经全部完成了！** 🎉

在**第五阶段的第三次和第四次教学**中，我们一步步地把这三块拼图都拼好了。

为了让你更安心，我帮你把代码和任务做一个**一一对应的复盘**，你可以对照一下你的 IDEA 项目：

### ✅ 任务 1：设计登录接口，生成 JWT

*   **对应代码**：`UserController.java` 中的 `login` 方法。
*   **实现逻辑**：
    1.  接收用户名密码。
    2.  调用 `userService.login()` 校验密码（BCrypt `matches`）。
    3.  校验通过后，调用 `JwtUtils.generateToken(userId)` 生成字符串。
    4.  返回 `Result.success(map)` 给前端。
*   **状态**：**已完成**。

### ✅ 任务 2：定义“登录拦截器”，解析 Token

*   **对应代码**：`LoginInterceptor.java`。
*   **实现逻辑**：
    1.  实现了 `HandlerInterceptor` 接口。
    2.  在 `preHandle` 方法中获取 Header 里的 `Authorization`。
    3.  调用 `JwtUtils.parseToken(token)` 检查是否合法。
    4.  如果不合法，`response.setStatus(401)` 并返回 false（拦截）。
    5.  在 `WebConfig.java` 中通过 `addInterceptors` 注册了它，并排除了 `/login` 接口。
*   **状态**：**已完成**。

### ✅ 任务 3：ThreadLocal 的使用

*   **对应代码**：`UserContext.java` 和 `ArticleController.java`。
*   **实现逻辑**：
    1.  创建了工具类 `UserContext`，内部包含 `private static final ThreadLocal<Integer> USER_HOLDER`。
    2.  **存**：在拦截器 (`LoginInterceptor`) 放行前，执行了 `UserContext.setUserId(userId)`。
    3.  **取**：在控制器 (`ArticleController`) 发布文章时，执行了 `UserContext.getUserId()` 拿到当前用户。
    4.  **删**：在拦截器 `afterCompletion` 中执行了 `UserContext.remove()`（防止内存泄漏）。
*   **状态**：**已完成**。

---

### 🧪 最终验收测试 (The Final Check)

如果你想 100% 确认这套机制在你的电脑上是跑得通的，请做这个**连贯性测试**：

1.  **第一步：登录 (拿票)**
    *   Postman `POST /users/login` -> 拿到 `token` (假设是 `eyJ...`)。
2.  **第二步：无票闯关 (测试拦截)**
    *   Postman `POST /articles` (不带 Header) -> **预期结果：401 Unauthorized**。
3.  **第三步：有票通关 (测试 ThreadLocal)**
    *   Postman `POST /articles` (Header 加上 `Authorization: eyJ...`)。
    *   **预期结果**：
        *   Postman 返回 200 Success。
        *   **关键点**：看你的 IDEA 控制台日志，应该有一行：`当前发布文章的用户ID是: 1` (或者你登录的那个用户的 ID)。

**只要你能看到控制台打印出 UserID，就说明 Token 解析、拦截器放行、ThreadLocal 传递这一条龙服务全部工作正常！**

---

既然这一关已经完美通关，那我们就无需停留。
我们要面对下一个挑战了：当用户量变大，文章变多，怎么保证查询速度？

**请回复“进入第六阶段”，我们开始学习 Redis 缓存！**

我们其实**已经全部覆盖了**！

为了让你心里更有底，我帮你回顾一下它们分别出现在哪里，并为你做一个**“第五阶段·面试考点总整理”**。你可以直接复制这一段，作为本阶段的**最终复习资料**。

---

### 🕵️‍♂️ 考点覆盖情况回顾

1.  **Cookie、Session 和 Token 的区别？**
    *   👉 **已学过**：在 **第五阶段·第三次教学 (JWT 原理)** 的最后部分。
2.  **JWT 的结构是怎样的？如何防止 Token 被篡改？**
    *   👉 **已学过**：在 **第五阶段·第三次教学 (JWT 原理)** 的正文中详细讲解了 `Header.Payload.Signature`。
3.  **过滤器（Filter）和拦截器（Interceptor）的区别？**
    *   👉 **已学过**：在 **第五阶段·第四次教学 (拦截器)** 的最后部分。

---

为了方便你整理笔记，我把这三道题的标准答案在这里**统一汇总**一遍。请查收：

# 第五阶段：安全与认证 - 高频面试题汇总

## 面试题 1：Cookie、Session 和 Token 的区别？

### 🎓 标准回答
1.  **存储位置**：
    *   **Cookie**：存放在客户端（浏览器）。
    *   **Session**：存放在服务端（内存或 Redis）。
    *   **Token (JWT)**：存放在客户端（通常在 LocalStorage 或 Cookie 中）。
2.  **安全性**：
    *   **Cookie**：容易被 CSRF（跨站请求伪造）攻击。
    *   **Session**：依赖 Cookie 里的 SessionID，所以也面临 CSRF 风险。
    *   **Token**：如果不存 Cookie 而存 LocalStorage，并在 Header 中传输，天然免疫 CSRF，但要注意防 XSS（跨站脚本攻击）。
3.  **扩展性 (核心区别)**：
    *   **Session**：**有状态**。服务器集群需要做“Session 共享”（如存入 Redis）才能用，否则 A 服务器无法识别 B 服务器生成的 Session。
    *   **Token**：**无状态**。Token 自身包含了用户信息和签名，服务器不需要存储任何状态。随便加服务器，天然支持分布式/微服务架构。

---

## 面试题 2：JWT 的结构是怎样的？如何防止 Token 被篡改？

### 🎓 标准回答
1.  **结构**：JWT 由三部分组成，中间用 `.` 分隔（`Header.Payload.Signature`）。
    *   **Header (头部)**：声明加密算法（如 HS256）和类型。
    *   **Payload (载荷)**：存放实际数据（如 UserID、过期时间）。**注意：这部分只是 Base64 编码，没有加密，任何人都能解码看到数据。**
    *   **Signature (签名)**：这是防伪的关键。
2.  **防篡改原理**：
    *   签名的生成公式是：`HMACSHA256(Header + Payload, 服务器私钥)`。
    *   服务器手里捏着一把**私钥**（Secret Key），绝对不告诉别人。
    *   如果黑客修改了 Payload 里的数据（比如把 `userId:1` 改成 `userId:2`），但他不知道私钥，所以他造不出对应的签名。
    *   服务器收到 Token 后，用同样的私钥再算一遍签名，发现和 Token 里带的签名不一致，直接拒绝请求。

---

## 面试题 3：过滤器 (Filter) 和 拦截器 (Interceptor) 的区别？

### 🎓 标准回答
1.  **归属不同**：
    *   **Filter**：依赖于 **Servlet 容器**（Tomcat），属于 Java Web 的标准。
    *   **Interceptor**：依赖于 **Spring 框架**，不依赖 Servlet 容器。
2.  **执行时机**：
    *   **Filter**：执行更早。请求进入容器后，进入 Spring 之前执行。
    *   **Interceptor**：执行较晚。请求进入 Spring DispatcherServlet 后，Controller 执行前执行。
    *   *顺序*：Filter -> Servlet -> Interceptor -> Controller。
3.  **功能强弱**：
    *   **Filter**：只能处理 Request/Response，无法直接访问 Spring 的 Bean（虽然可以通过工具类拿，但比较麻烦）。通常用于字符编码、跨域处理。
    *   **Interceptor**：可以访问 Spring 容器中的 Bean（如直接注入 Service）。通常用于**登录验证**、**权限检查**、**日志记录**。

---

**确认完毕！第五阶段没有任何遗漏。**

现在的你，底子已经打得非常牢固了。
接下来我们要进入**第六阶段**，给你的博客系统加上**“涡轮增压”**（Redis）。

**请回复“进入第六阶段”，我们继续！**

欢迎来到**第六阶段：性能优化与中间件**。

在之前的课程中，我们的数据完全依赖 **MySQL**。
MySQL 是存放在**硬盘**上的，虽然我们用了连接池，但硬盘的读写速度终究有限。如果你的博客突然火了，一秒钟有 1 万人访问首页，MySQL 可能会瞬间卡死甚至宕机。

为了解决这个问题，我们需要引入 **Redis**。

这是**第六阶段·第一次教学**。

---

# 第六阶段：NoSQL 与 Redis 基础

## 1. 什么是 NoSQL？为什么需要 Redis？

*   **RDBMS (关系型数据库)**：如 MySQL、Oracle。
    *   **特点**：表结构严谨（行、列）、支持事务、数据存在**硬盘**。
    *   **缺点**：慢。
*   **NoSQL (非关系型数据库)**：如 Redis、MongoDB。
    *   **特点**：没有固定的表结构（Key-Value）、数据存在**内存**。
    *   **Redis 核心**：**快！**（读写速度每秒 10万+ 次）。

**形象比喻**：
*   **MySQL** 是仓库。东西多，安全，但取东西要走很远的路。
*   **Redis** 是收银台旁边的货架（缓存）。东西少，不那么安全（断电可能丢数据），但伸手就能拿到，速度极快。

在博客系统中，我们通常把**“经常被访问，但不经常修改”**的数据（如文章详情、首页列表）放在 Redis 里。

---

## 2. Redis 安装 (Windows 版)

Redis 官方原生只支持 Linux。但在开发阶段，为了方便，我们可以用 Windows 的移植版本。

> **方案 A：直接下载压缩包（最简单，推荐新手）**

1.  **下载**：访问微软归档的 GitHub 地址：[Redis-x64-3.2.100.zip](https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.zip) (虽然版本老，但学基础命令足够了)。
    *   *进阶推荐*：如果你想用新版，可以使用 [Memurai](https://www.memurai.com/) (开发者版免费) 或者使用 Docker (最佳实践)。
2.  **解压**：解压到一个没有中文、没有空格的路径，例如 `D:/tools/redis`。
3.  **启动服务端**：
    *   双击 **`redis-server.exe`**。
    *   你会看到一个黑窗口，显示一个“方盒子”图标，里面写着端口 **6379**。
    *   **注意**：这个窗口**千万别关**！关了 Redis 就停了。
4.  **启动客户端**：
    *   双击 **`redis-cli.exe`**。
    *   你会看到一个黑窗口，光标闪烁 `127.0.0.1:6379>`。这就是我们可以输入命令的地方。

---

## 3. 推荐工具：Another Redis Desktop Manager

整天对着黑窗口敲命令很痛苦。推荐下载这个免费开源的可视化工具。
*   **下载地址**：[Gitee 下载 (速度快)](https://gitee.com/qishibo/AnotherRedisDesktopManager/releases)
*   **安装后**：
    *   Host: `127.0.0.1`
    *   Port: `6379`
    *   Password: (留空，默认没密码)
    *   点击 Connect，连接成功。

---

## 4. Redis 五大核心数据类型与命令

Redis 是 **Key-Value** 结构的数据库。Key 永远是字符串，但 Value 可以是多种类型。
请在 `redis-cli` 黑窗口中动手敲以下命令：

### 4.1 String (字符串) - 最常用
通常用于存储：用户 Session、Token、序列化后的 JSON 对象（文章详情）。

```bash
# 1. 存数据 (SET key value)
set name zhangsan

# 2. 取数据 (GET key)
get name
# 输出: "zhangsan"

# 3. 存数据并设置过期时间 (SETEX key seconds value) -> 重点！做缓存必用
setex code 60 123456  # 验证码，60秒后自动消失

# 4. 查看剩余存活时间 (TTL key)
ttl code
# 输出: 56 (代表还剩56秒)
# 输出: -2 (代表已过期/不存在)
# 输出: -1 (代表永不过期)

# 5. 只有不存在时才设置 (SETNX key value) -> 分布式锁的核心
setnx lock true
```

### 4.2 Hash (哈希)
类似于 Java 的 `Map<String, Map<String, String>>`。适合存储对象。

```bash
# 1. 存对象属性 (HSET key field value)
hset user:1 username admin
hset user:1 age 18

# 2. 取对象属性 (HGET key field)
hget user:1 username

# 3. 取所有属性
hgetall user:1
```

### 4.3 List (列表)
类似于 Java 的 `LinkedList`。适合做**消息队列**或**最新文章列表**。

```bash
# 1. 左侧推入 (LPUSH)
lpush history article:1 article:2

# 2. 右侧弹出 (RPOP)
rpop history

# 3. 查看列表 (LRANGE start end)
lrange history 0 -1
```

### 4.4 Set (集合)
无序，不重复。适合做**点赞用户**、**共同好友**、**标签**。

```bash
# 1. 添加 (SADD)
sadd tags java spring mysql spring

# 2. 查看所有 (SMEMBERS)
smembers tags
# 输出: java, spring, mysql (自动去重了)
```

### 4.5 ZSet (有序集合) - 排行榜神器
每个元素带一个分数 (Score)。适合做**文章热度排行榜**。

```bash
# 1. 添加 (ZADD key score member)
zadd ranking 100 article:A
zadd ranking 80 article:B
zadd ranking 200 article:C

# 2. 查前三名 (ZREVRANGE) -> 也就是按分数倒序
zrevrange ranking 0 2 withscores
# 输出:
# 1) "article:C" (200)
# 2) "article:A" (100)
# 3) "article:B" (80)
```

---

## 5. 通用命令 (Keys)

```bash
# 1. 查看所有的 key (⚠️ 生产环境禁止使用！会卡死服务器)
keys *

# 2. 删除 key
del name

# 3. 检查 key 是否存在
exists name

# 4. 清空当前数据库 (⚠️ 删库跑路专用)
flushdb
```

---

## 🧪 你的任务

1.  下载并运行 Redis Windows 版（或者 Docker 版）。
2.  打开 `redis-cli`。
3.  **模拟缓存场景**：
    *   设置一个 key 叫 `article:1:view`，值为 `0`。
    *   每次有人访问，让它自增 1：`INCR article:1:view`。
    *   查一下现在的阅读量：`GET article:1:view`。
4.  **模拟验证码场景**：
    *   设置 key `login:code:13800000000`，值为 `8888`，有效期 30 秒。
    *   不断执行 `TTL` 观察倒计时。
    *   等 30 秒后，再次 `GET`，确认它消失了。

---

这是**第六阶段·第二次教学**。

上一节课你在黑窗口（CLI）里敲命令操作 Redis，感觉很爽。但在实际开发中，我们肯定是用 Java 代码来自动操作的。

Spring Boot 依然发扬了“开箱即用”的优良传统，为我们封装了 **Spring Data Redis**。它提供了两个核心工具类：
1.  **`StringRedisTemplate`**：专用于处理字符串。
2.  **`RedisTemplate`**：通用的，可以处理对象。

今天我们就来学习如何把它们集成到博客项目中。

---

# 第六阶段：Spring Data Redis 实战

## 1. 引入依赖

请打开 `pom.xml`，添加 Redis 的启动器依赖：

```xml
<!-- Spring Data Redis 依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- 连接池依赖 (可选，但生产环境通常都需要，建议加上) -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

> **刷新 Maven**：别忘了点那个小刷新图标。

---

## 2. 配置连接信息

打开 `application.yml`，配置 Redis 的地址。

```yaml
spring:
  # ... datasource 配置 ...
  
  # Redis 配置
  redis:
    host: localhost
    port: 6379
    password:  # 如果你没有设置密码，这里留空
    database: 0 # Redis 默认有16个库(0-15)，通常用第0个
    timeout: 3000ms # 连接超时时间
    lettuce:
      pool:
        max-active: 8  # 最大连接数
        max-wait: -1ms # 最大等待时间
        max-idle: 8    # 最大空闲连接
        min-idle: 0    # 最小空闲连接
```

---

## 3. 核心概念：两个 Template 的区别 (面试必问)

Spring Boot 自动帮我们注入了两个 Bean，你可以直接 `@Autowired` 使用。

### A. `StringRedisTemplate`
*   **Key 类型**: String
*   **Value 类型**: String
*   **特点**: 它存进去的数据，你在 Redis 客户端（黑窗口或可视化工具）里看，**是人类能看懂的字符串**。
*   **场景**: 比如存验证码 `set code:123 "8888"`。

### B. `RedisTemplate<Object, Object>` (默认版)
*   **Key 类型**: Object
*   **Value 类型**: Object
*   **特点**: 它默认使用 **JDK 序列化**。
    *   你存一个 `User` 对象，它会把它变成一串乱码（二进制流），类似 `\xac\xed\x00\x05t\x00...`。
    *   **缺点**: 这种数据在 Redis 客户端里**完全看不懂**，调试极其痛苦，且跨语言（如 PHP/Python 读取）不兼容。
*   **解决**: 我们通常不直接用默认的，而是**自己配置一个使用 JSON 序列化的 RedisTemplate**。

---

## 4. 进阶：配置 JSON 序列化的 RedisTemplate (推荐)

为了让存进去的对象变成漂亮的 JSON 格式，我们需要写一个配置类。

在 `config` 包下新建 `RedisConfig.java`：

```java
package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 自定义 RedisTemplate
     * 目标：Key 使用 String 序列化，Value 使用 JSON 序列化
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 1. 既然 Key 都是字符串，就用 String 序列化器
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // 2. Value 我们希望是 JSON，使用 Jackson 序列化器
        // GenericJackson2JsonRedisSerializer 可以自动处理对象的泛型
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
```

---

## 5. 实战演示：在 Test 中使用

我们不急着写业务代码，先在单元测试里感受一下用法。
请在 `src/test/java/com/example/blog` 下新建 `RedisTest.java`。

```java
package com.example.blog;

import com.example.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // 注入我们刚才自定义的

    /**
     * 测试 1: 操作简单的字符串
     * 场景: 验证码、计数器
     */
    @Test
    public void testString() {
        // 1. 存数据 (Key, Value)
        stringRedisTemplate.opsForValue().set("name", "张三");

        // 2. 取数据
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("从 Redis 取出的名字: " + name);

        // 3. 设置过期时间 (10秒后过期)
        stringRedisTemplate.opsForValue().set("verify_code", "123456", 10, TimeUnit.SECONDS);
    }

    /**
     * 测试 2: 操作对象 (存 User)
     * 场景: 缓存用户信息、文章详情
     */
    @Test
    public void testObject() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setAge(18);

        // 1. 存对象 (自动转 JSON)
        redisTemplate.opsForValue().set("user:1", user);

        // 2. 取对象 (自动转回 Java 对象)
        // 注意：这里取出来的是 Object，需要强转，或者使用泛型工具
        User cacheUser = (User) redisTemplate.opsForValue().get("user:1");
        System.out.println("从 Redis 取出的用户: " + cacheUser);
    }
}
```

### 常用 API 速查表 (opsForX)

`RedisTemplate` 使用**工厂模式**，针对不同的数据类型提供了不同的操作类：

*   **String**: `opsForValue()` -> `.set()`, `.get()`, `.increment()`
*   **List**: `opsForList()` -> `.leftPush()`, `.rightPop()`
*   **Hash**: `opsForHash()` -> `.put()`, `.get()`, `.entries()`
*   **Set**: `opsForSet()` -> `.add()`, `.members()`
*   **ZSet**: `opsForZSet()` -> `.add()`, `.range()`

---

## 🧪 验证

1.  **运行测试方法** `testObject()`。
2.  **打开 Redis 可视化工具 (Another Redis Desktop Manager)**。
3.  刷新一下，你应该能看到一个 Key 叫 `user:1`。
4.  **查看 Value**：
    *   如果你没配 `RedisConfig`，你会看到一堆乱码。
    *   如果你配了，你会看到清晰的 JSON：
        ```json
        {
          "@class": "com.example.blog.entity.User",
          "id": 1,
          "username": "admin",
          "age": 18
        }
        ```

---

**下节预告**：
工具学会了，现在要应用到博客里了。
我们会面临一个经典业务场景：**文章详情的缓存策略**。
用户第一次访问文章 -> 查 MySQL -> 存 Redis。
用户第二次访问文章 -> 查 Redis (不走 MySQL)。
这能让你的文章加载速度快 10 倍！

下节课：**缓存策略与 @Cacheable 注解**（或者是手动实现缓存逻辑）。为了让你理解原理，我们先**手动实现**。



这是**第六阶段·第三次教学**。

在上一节课，我们已经配好了 `RedisTemplate`。今天我们要解决博客系统中两个非常经典的性能场景：

1.  **缓存策略**：什么数据该存 Redis？怎么存？
2.  **文章浏览量**：每访问一次文章就要改一次数据库吗？绝对不行！我们要利用 Redis 实现**“写缓冲”**。

---

# 第六阶段：Redis 实战 - 缓存策略与浏览量防爆

## 1. 缓存策略：Cache Aside Pattern (旁路缓存)

这是最通用的缓存模式，主要用于**“读多写少”**的数据（如文章详情）。

### 逻辑流程
1.  **查**：先查 Redis。
    *   如果有（Hit）：直接返回。
    *   如果没有（Miss）：查 MySQL -> 写入 Redis -> 返回。
2.  **改**：先更新 MySQL，**然后直接删除 Redis**。
    *   *为什么是删除而不是更新 Redis？* （面试高频题：防止并发导致的数据不一致，删除是最保险的，下次查询时会自动重新加载最新数据）。

### 代码演示 (伪代码逻辑)
```java
// ArticleService.java

public Article getArticle(Long id) {
    String key = "article:" + id;
    
    // 1. 查缓存
    Article article = (Article) redisTemplate.opsForValue().get(key);
    if (article != null) {
        return article; // 缓存命中
    }
    
    // 2. 查数据库
    article = articleMapper.selectById(id);
    
    // 3. 写缓存 (设置 1 小时过期，防止缓存雪崩)
    if (article != null) {
        redisTemplate.opsForValue().set(key, article, 1, TimeUnit.HOURS);
    }
    
    return article;
}
```

---

## 2. 实战任务：实现“文章浏览量” (Write-Behind)

**场景**：如果你的文章很火，1 秒钟有 1 万人点击。
*   **MySQL 做法**：执行 1 万次 `UPDATE tb_article SET view_count = view_count + 1 WHERE id = 1`。数据库磁盘 IO 会直接炸裂。
*   **Redis 做法**：在内存里执行 1 万次 `INCR`（自增），耗时几乎为 0。每隔 10 分钟，把 Redis 里的数字同步回 MySQL 一次。

### 第一步：准备工作
我们需要在数据库和实体类中添加 `view_count` 字段。

1.  **SQL**: `ALTER TABLE tb_article ADD COLUMN view_count INT DEFAULT 0 COMMENT '浏览量';`
2.  **Entity**: 在 `Article.java` 中添加 `private Integer viewCount;`。

### 第二步：编写 Service 逻辑
我们要使用 Redis 的 **Hash 结构** 来存浏览量。
*   **Key**: `view_counts`
*   **Field**: 文章ID (e.g., "1")
*   **Value**: 浏览次数 (e.g., "100")

修改 `ArticleService.java`：

```java
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取文章详情，并增加浏览量
     */
    public Article getArticleAndAddView(Long id) {
        // 1. 先查询文章详情 (这里为了简化，暂时直接查库，你可以加上面的缓存逻辑)
        Article article = articleMapper.selectById(id);
        if (article == null) return null;

        // 2. 浏览量 +1 (Redis INCR 操作)
        // Key: "view_counts", HashKey: id, Delta: 1
        redisTemplate.opsForHash().increment("view_counts", String.valueOf(id), 1);

        // 3. (可选) 读取最新浏览量设置给对象，返回给前端看
        // 为什么不直接用 article.viewCount？因为数据库里的可能是旧的
        Integer viewCount = (Integer) redisTemplate.opsForHash().get("view_counts", String.valueOf(id));
        article.setViewCount(viewCount);

        return article;
    }
```

---

## 3. 核心技术：定时任务 (@Scheduled)

浏览量一直在 Redis 里狂涨，数据库里还是 0。我们需要一个**定时任务**，把 Redis 的数据同步回 MySQL。

### 第一步：开启定时任务支持
在启动类 `BlogBackendApplication` 上添加注解：

```java
@EnableScheduling // 开启定时任务
@SpringBootApplication
public class BlogBackendApplication { ... }
```

### 第二步：编写同步任务
在 `com.example.blog.task` 包下新建 `ViewCountSyncTask.java`：

```java
package com.example.blog.task;

import com.example.blog.entity.Article;
import com.example.blog.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class ViewCountSyncTask {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 定时将 Redis 中的浏览量同步到 MySQL
     * cron 表达式: "0 0/10 * * * ?" 代表每 10 分钟执行一次
     * 为了测试方便，我们改成 "0/30 * * * * ?" (每 30 秒执行一次)
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void syncViewCount() {
        log.info("开始同步文章浏览量...");

        // 1. 获取 Redis 中所有的浏览量数据
        // Key: 文章ID, Value: 浏览量
        Map<Object, Object> viewCountMap = redisTemplate.opsForHash().entries("view_counts");

        if (viewCountMap.isEmpty()) {
            return;
        }

        // 2. 遍历并更新数据库
        for (Map.Entry<Object, Object> entry : viewCountMap.entrySet()) {
            String articleIdStr = (String) entry.getKey();
            Integer viewCount = (Integer) entry.getValue();
            long id = Long.parseLong(articleIdStr);

            // 3. 更新 MySQL
            // 注意：这里不能直接 updateById，因为可能只更新 view_count 字段
            // 最好的方式是写 XML 自定义 SQL，但为了偷懒，我们用 MP 的 UpdateWrapper
            // 或者简单点：先查出来，改了再存 (效率较低但逻辑简单)
            Article article = articleMapper.selectById(id);
            if (article != null) {
                article.setViewCount(viewCount);
                articleMapper.updateById(article);
            }
        }
        
        log.info("同步完成，共更新 {} 篇文章", viewCountMap.size());
    }
}
```

---

## 4. 验证测试

1.  **启动项目**。
2.  **修改 Controller**：把 `ArticleController` 的 `getDetail` 方法调用的 Service 改成 `getArticleAndAddView`。
3.  **疯狂点击**：
    *   用浏览器访问 `http://localhost:8080/articles/1`。
    *   狂按 F5 刷新 10 次。
4.  **观察 Redis**：
    *   打开可视化工具，看到 `view_counts` 这个 Hash 里面，`1` 的值变成了 10。
    *   此时看数据库，`view_count` 还是 0。
5.  **等待同步**：
    *   等待 30 秒（看控制台日志 `开始同步...`）。
    *   刷新数据库，你会发现 `view_count` 变成了 10！

---

## 📝 面试考点：Redis 缓存三大坑

做缓存必问的三个问题，一定要理解。

1.  **缓存穿透 (Penetration)**
    *   **现象**：黑客故意请求一个**不存在的 ID** (如 -1)。Redis 查不到，请求打到数据库；数据库也查不到。黑客发 1000 万次这种请求，数据库就挂了。
    *   **解决**：
        *   如果数据库也没查到，就往 Redis 存一个 `null` (过期时间设短点，如 30秒)。
        *   使用布隆过滤器 (Bloom Filter)。

2.  **缓存击穿 (Breakdown)**
    *   **现象**：一个**热点 Key** (比如微博热搜) 突然过期了。此时一瞬间有 10 万个请求过来，发现 Redis 没数据，全部涌向数据库。
    *   **解决**：
        *   热点数据永不过期。
        *   加互斥锁 (Mutex Lock)：第一个人去查数据库时锁住，其他人等着。

3.  **缓存雪崩 (Avalanche)**
    *   **现象**：Redis 里大量缓存**在同一时间集体过期**（比如都设了 1 小时）。
    *   **解决**：
        *   存数据时，过期时间设为 `随机值` (比如 1 小时 + 随机几分钟)，让它们分散过期。

---

**✅ 第六阶段圆满结束！**

现在你的博客系统已经具备了：
1.  **NoSQL 能力**：使用 Redis 存储数据。
2.  **高并发基础**：利用 Redis 抗住浏览量统计的压力。
3.  **自动化运维**：使用 `@Scheduled` 同步数据。

至此，开发层面的知识我们已经学完了 90%。
最后一个阶段，我们要把这个项目**打包**，并**部署**到真正的服务器（Linux）上运行起来。

**请回复“进入第七阶段”，我们去征服 Linux 服务器！**

你的检查非常细致！👍

我们要**实事求是**地复盘一下：

1.  ✅ **“文章浏览量”功能**：已在**第六阶段·第三次教学**中完成（使用了 `INCR` + `@Scheduled`）。
2.  ✅ **Redis 数据类型**：已在**第六阶段·第一次教学**中完成（String, Hash, List, Set, ZSet）。
3.  ✅ **缓存穿透/击穿/雪崩**：已在**第六阶段·第三次教学**的最后部分完成。

**但是！以下两点确实还需要补全**（或深入讲解）：

1.  ❌ **缓存“文章分类”和“标签”列表**：这个代码我们还没写。
2.  ⚠️ **MySQL 和 Redis 如何保证数据一致性？**：虽然在讲“旁路缓存”时提了一嘴“先更库再删缓存”，但作为一个**超高频**面试题，它值得单独拿出来详细讲透。

下面我们来补全这两块内容，完成第六阶段的完美收官。

---

# 第六阶段·补全篇：列表缓存与数据一致性

## 任务补全：缓存“文章分类”列表

**场景**：首页通常会显示“文章分类”列表。这个列表几乎不怎么变，但每次刷新首页都要查，非常适合缓存。

**实现逻辑**：
1.  **Key**: `cache:category:list`
2.  **读**：先查 Redis。有则返回；无则查 MySQL 并存入 Redis（设个较长的过期时间，如 1 天）。
3.  **写**（后台新增/修改分类）：**删除** Redis 里的 Key，下次查询自动重新加载。

### 代码实现：`CategoryService`

请修改或新建 `CategoryService.java`：

```java
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_KEY = "cache:category:list";

    /**
     * 获取分类列表 (带缓存)
     */
    public List<Category> getAllCategories() {
        // 1. 先查 Redis
        // 这里的 list 需要强转，或者用 ObjectMapper 转换，简单起见直接强转
        List<Category> cacheList = (List<Category>) redisTemplate.opsForValue().get(CACHE_KEY);
        
        if (cacheList != null && !cacheList.isEmpty()) {
            System.out.println("走缓存了！");
            return cacheList;
        }

        // 2. 查数据库
        System.out.println("查数据库了...");
        List<Category> dbList = categoryMapper.selectList(null);

        // 3. 写入 Redis (过期时间 24 小时)
        if (dbList != null && !dbList.isEmpty()) {
            redisTemplate.opsForValue().set(CACHE_KEY, dbList, 24, TimeUnit.HOURS);
        }

        return dbList;
    }

    /**
     * 新增/修改分类 (后台功能)
     */
    public void saveCategory(Category category) {
        // 1. 写数据库
        if (category.getId() == null) {
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateById(category);
        }

        // 2. 删除缓存 (Cache Aside Pattern)
        // 只要数据变了，就把旧缓存扔掉。下次有人查，自然会去库里拿新的。
        redisTemplate.delete(CACHE_KEY);
    }
}
```

---

## 面试题补全：MySQL 和 Redis 如何保证数据一致性？

这个问题是**面试必死题**。如果回答不好，面试官会觉得你没有处理高并发数据的经验。

### 🎓 标准回答 (Cache Aside Pattern)

最主流的方案是 **Cache Aside Pattern (旁路缓存模式)**。

#### 1. 读数据逻辑
先读缓存 -> 缓存没有，读数据库 -> 将数据写入缓存。

#### 2. 写数据逻辑 (争议点)
这里有四种组合，**只有第 4 种是正确的**：

*   ❌ **先写缓存，再写数据库**：如果缓存写成功，数据库写失败。结果：缓存是新的，数据库是旧的。GG。
*   ❌ **先写数据库，再写缓存**：并发场景下，A改了库为1，B改了库为2。B先更新缓存为2，A后更新缓存为1。结果：数据库是2，缓存是1。脏数据。
*   ❌ **先删缓存，再写数据库**：
    *   A 删缓存。
    *   A 还没来得及写库。
    *   B 来查了，发现缓存空，去读库（旧数据），写入缓存。
    *   A 终于写完库了（新数据）。
    *   **结果**：缓存里永远是旧数据。
*   ✅ **先写数据库，再删缓存** (标准答案)：
    *   A 改数据库。
    *   A 删除缓存。
    *   B 来查，发现缓存空，去读库（新数据），写入缓存。
    *   **结果**：数据一致。

### 🗣️ 进阶追问：那如果“删缓存”这一步失败了怎么办？

面试官如果追问这个，说明他是高手。
**答**：
1.  **重试机制**：如果不要求强一致性，可以捕获异常，把要删除的 Key 丢到消息队列（MQ）里，让消费者不断重试删除，直到成功。
2.  **延时双删** (针对“先删缓存”场景的补救)：
    *   先删缓存 -> 写数据库 -> 休眠 1秒 -> 再次删除缓存。
    *   休眠是为了确保读请求结束，把读请求可能产生的脏缓存再次干掉。

---

**✅ 第六阶段彻底完成！**

现在你的博客系统后端：
1.  **功能完备**：用户、文章、分类、上传。
2.  **安全可靠**：JWT 认证、AOP 日志、全局异常。
3.  **性能强悍**：Redis 缓存、浏览量防爆。

接下来的 **第七阶段：部署与运维**，我们要走出舒适区（Windows + IDEA），把你的代码搬运到真实的 **Linux 服务器** 上，并学习使用 **Docker** 进行容器化部署。这是全栈/运维能力的体现！

**请回复“进入第七阶段”，让我们点亮最后技能树！**

欢迎来到**第七阶段：部署与运维**。

这是我们从“开发环境”迈向“生产环境”的第一步。

在 IDEA 里点击“运行”按钮虽然方便，但你不可能把 IDEA 装到服务器上。我们需要把写好的所有代码、依赖的 Jar 包（Spring、MyBatis、Redis 等）、配置文件，全部压缩成一个**可执行的 Jar 包**。

这是**第七阶段·第一次教学**。

---

# 第七阶段：打包与运行 (Maven Package)

## 1. 核心原理：什么是 Fat Jar？

普通 Java 项目打包后的 Jar 包只包含你写的 `.class` 文件，不包含依赖库。如果你想运行它，需要在 classpath 里手动指定几百个依赖包，非常痛苦。

**Spring Boot 的魔法**在于它打出来的是 **Fat Jar (胖包)**：
*   它把所有依赖（Tomcat、Spring、MyBatis...）全部塞进了一个 Jar 包里。
*   它内置了一个特殊的加载器。
*   **结果**：你只需要拥有 JDK，一行命令 `java -jar app.jar` 就能跑起来，无需配置 Tomcat。

---

## 2. 检查 `pom.xml` (至关重要！)

在打包之前，必须检查 `pom.xml` 文件底部是否有 **Spring Boot Maven 插件**。
如果没有它，打出来的包是无法运行的，会报错 `没有主清单属性 (no main manifest attribute)`。

请确认你的 `pom.xml` 包含以下内容：

```xml
<build>
    <plugins>
        <!-- 核心插件：负责把依赖打入 Jar 包，并生成启动脚本 -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

---

## 3. 执行打包 (两种方式)

### 方式 A：使用 IDEA 图形界面 (推荐新手)

1.  在 IDEA 右侧边栏找到 **Maven** 选项卡。
2.  展开 `blog-backend` -> `Lifecycle`。
3.  **双击 `clean`**：
    *   作用：清理 `target` 目录，删除上次打包的残留文件。
    *   结果：控制台显示 `BUILD SUCCESS`。
4.  **双击 `package`**：
    *   作用：编译代码 -> 运行单元测试 -> 打包。
    *   结果：等待几十秒，看到 `BUILD SUCCESS`。

### 方式 B：使用命令行 (装逼/服务器常用)

打开 IDEA底部的 **Terminal** (终端)，输入：

```bash
mvn clean package -DskipTests
```

> **💡 小技巧**：为什么要加 `-DskipTests`？
> 默认情况下，Maven 打包时会运行所有单元测试 (`src/test/java`)。如果你的测试代码里有 bug（比如连不上 Redis），打包就会失败。
> 在赶时间或者确定代码没问题时，**跳过测试**能大大加快打包速度。

---

## 4. 验证产物

打包成功后，去左侧的项目目录树查看：

1.  找到 **`target`** 文件夹（橙色的）。
2.  里面应该有一个 **`blog-backend-0.0.1-SNAPSHOT.jar`** (名字取决于你的 artifactId 和 version)。
3.  这个文件通常有 20MB - 50MB 左右（因为它包含了 Tomcat 和所有依赖）。

---

## 5. 本地模拟运行 (Moment of Truth)

现在我们要假装自己是一台服务器，脱离 IDEA 运行这个 Jar 包。

**准备工作**：
*   🛑 **先停止 IDEA 里正在运行的项目！** (点击红色的方块停止按钮)。
*   如果不停止，端口 8080 会被占用，Jar 包启动会报错 `Port 8080 was already in use`。

**启动步骤**：
1.  在 IDEA 的 **Terminal** (或者 CMD / PowerShell) 中。
2.  进入 target 目录：
    ```bash
    cd target
    ```
3.  执行启动命令：
    ```bash
    java -jar blog-backend-0.0.1-SNAPSHOT.jar
    ```
    *(提示：输入 blog 后按 `Tab` 键可以自动补全文件名)*

**观察结果**：
如果你看到熟悉的 Spring Boot Banner 和日志滚动，最后显示：
`Started BlogBackendApplication in x.xxx seconds`

恭喜你！打包成功！
你可以去浏览器访问 `http://localhost:8080/articles/page`，依然能正常看到数据。

---

## 🧪 常见错误排查

1.  **报错：`xx.jar中没有主清单属性`**
    *   **原因**：`pom.xml` 漏了 `spring-boot-maven-plugin`。
    *   **解决**：补上插件，重新 Maven Reload，重新 `clean package`。

2.  **报错：`Connection refused` (连不上数据库)**
    *   **原因**：如果你在本地运行，确保你的 MySQL 和 Redis 是开着的。
    *   **注意**：如果你把这个包丢到云服务器上，配置文件的 `localhost` 指的是云服务器自己。如果云服务器没装 MySQL，就会报错。

3.  **打包过程报错：`Tests failed`**
    *   **原因**：单元测试没过。
    *   **解决**：使用 `mvn clean package -DskipTests` 跳过测试。

---

**下节预告**：
Jar 包在手，天下我有。
但我们不能总在 Windows 上跑。真正的服务器大多是 **Linux (CentOS/Ubuntu)** 系统。
下节课，我们将学习 **Linux 基础命令**，为把 Jar 包上传到服务器做准备。
(如果你没有云服务器，我会教你用虚拟机或者直接理解流程)。

下节课：**Linux 基础与服务器环境准备**。

这是**第七阶段·第二次教学**。

在 Windows 上，我们习惯了用鼠标双击图标打开程序，用任务管理器关闭程序。
但在 **Linux 服务器**（通常没有图形界面，全黑屏）上，一切都要靠**键盘敲命令**。

对于 Java 后端开发来说，你不需要成为 Linux 运维大师，但**这 5 个命令是生存必备技能**。没有它们，你连项目都发布不了。

---

# 第七阶段：Linux 基础 - 部署五件套

## 1. 导航与查看：`ls` 和 `cd`

这俩是走路的两条腿。

### 📂 `ls` (List)：看看这里有什么
等同于你在 Windows 打开一个文件夹，看里面有哪些文件。

*   **`ls`**: 列出当前目录下的文件（简略版）。
*   **`ls -l`** (常用): **列出详细信息**（权限、拥有者、文件大小、修改时间）。通常简写为 **`ll`** (在 CentOS/Ubuntu 上通用)。
*   **`ls -a`**: 列出所有文件（包含隐藏文件，即以 `.` 开头的文件）。

### 📂 `cd` (Change Directory)：去哪里
等同于你在 Windows 双击文件夹进去，或者点“返回上一级”。

*   **`cd /`**: 去根目录（Linux 的最高级目录，类似 Windows 的 "此电脑"）。
*   **`cd /opt`**: 进入 `/opt` 目录。
*   **`cd ..`**: **返回上一级**目录。
*   **`cd ~`**: 回家（进入当前用户的家目录，比如 `/root`）。

---

## 2. 进程管理：`ps` 和 `kill`

这俩组合起来，就是 Linux 版的**“任务管理器”**。

### 🕵️‍♂️ `ps` (Process Status)：查进程
主要用来检查：**我的 Java 项目启动了吗？PID 是多少？**

*   **核心命令**：
    ```bash
    ps -ef | grep java
    ```
*   **命令拆解**：
    *   `ps -ef`: 列出系统内所有的进程。
    *   `|`: **管道符**。意思是把左边命令的结果，传给右边的命令处理。
    *   `grep java`: **搜索过滤**。在成千上万个进程里，只显示包含 "java" 关键字的行。

*   **输出示例**：
    ```text
    root     12345     1  0 10:00 ?        00:00:10 java -jar blog.jar
    ```
    *   **`12345`**: 这就是 **PID (进程 ID)**，想杀掉这个进程全靠它。

### 🔫 `kill`：杀进程
主要用来：**停止旧的项目，准备发新版**。

*   **`kill 12345`**: 温柔地杀死 PID 为 12345 的进程（发送 SIGTERM 信号，让程序自己做收尾工作）。
*   **`kill -9 12345`**: **强制杀死**（核武器）。不管程序同不同意，直接从内存里抹去。
    *   *场景*：项目卡死了，或者关不掉时使用。

---

## 3. 后台运行：`nohup` (核心重点)

很多新手在服务器上运行 jar 包时会用 `java -jar blog.jar`。
**问题**：当你把连接服务器的终端窗口（SSH 窗口）关闭时，**你的 Java 项目也会跟着断开、停止运行**。

我们需要让项目在**后台默默运行**，即使你关了电脑睡觉，它也不受影响。

*   **标准部署命令 (背诵)**：
    ```bash
    nohup java -jar blog-backend.jar > log.file 2>&1 &
    ```

*   **命令拆解**：
    1.  **`nohup`** (No Hang Up)：不挂断。意思是你关掉终端，程序继续跑。
    2.  **`java -jar ...`**: 启动命令。
    3.  **`> log.file`**: 把项目控制台输出的日志，**重定向**写入到 `log.file` 文件里（因为后台运行你看不到控制台了）。
    4.  **`2>&1`**: 把错误日志（2）也输出到标准日志（1）里，防止漏掉报错。
    5.  **`&`**: **后台运行**。让命令瞬间返回，不占用你的输入界面。

---

## 🚀 实战：一次完整的部署流程 (脚本流)

假设你已经把 `blog-backend.jar` 上传到了服务器的 `/opt/blog` 目录。
现在你要发布一个新版本，你需要按顺序敲下以下命令：

**1. 检查旧应用是否在跑**
```bash
ps -ef | grep java
# 假设看到 PID 是 8888
```

**2. 杀掉旧应用**
```bash
kill -9 8888
```

**3. 再次确认 (防止没杀掉)**
```bash
ps -ef | grep java
# 应该看不到原来的 jar 包进程了
```

**4. 启动新应用**
```bash
nohup java -jar blog-backend.jar > temp.log 2>&1 &
```

**5. 实时查看启动日志 (额外赠送一个命令)**
`nohup` 启动后看不到界面，怎么知道启动成功没？
```bash
tail -f temp.log
```
*   **`tail -f`**: 实时滚动查看文件的最后几行。你会看到熟悉的 Spring Boot 启动日志刷刷刷地跑。
*   按 `Ctrl + C` 退出查看（不会停止项目）。

---

## 🧪 课后作业 (本地模拟)

如果你有 Git Bash (安装 Git 时自带的) 或者 Mac/Linux 环境，可以直接试。
如果是 Windows CMD，`ps` 和 `grep` 是不能用的（Windows 对应的是 `tasklist` 和 `findstr`）。

但你可以在心里默写一遍这个流程。

**下节预告**：
命令学会了，但我们没有服务器怎么办？
1.  如果你有钱，去阿里云/腾讯云买个 **ECS 服务器** (最推荐，真实环境)。
2.  如果你想省钱，我们将学习 **Docker**。
下节课，我将教你编写 **Dockerfile**，把我们的 Java 环境、Jar 包全部封装成一个**镜像 (Image)**，这是现代云原生部署的标准姿势。

下节课：**Docker 基础与镜像制作**。

这是**第七阶段·第三次教学**。

在上一节课，我们学会了在 Linux 上用命令跑 Jar 包。
但这有一个大问题：**环境差异**。
*   你的电脑装的是 JDK 17，服务器上装的是 JDK 8，跑不起来。
*   你的电脑 MySQL 密码是 `root`，服务器上是 `123456`，连不上。
*   你说：“在我电脑上明明能跑啊！”（运维：🙄）

**Docker** 的出现就是为了解决这个问题。
它把你的 **代码 + JDK 环境 + 配置文件** 全部打包进一个**“集装箱”（Image 镜像）**。
扔到任何一台装有 Docker 的服务器上，都能一模一样地运行。

---

# 第七阶段：Docker 基础 - 容器化部署

## 1. 核心概念 (30秒入门)

*   **Dockerfile**: **说明书**。告诉 Docker 怎么制造镜像（比如：先拿一个 JDK，再把 Jar 包放进去，再运行）。
*   **镜像 (Image)**: **模具/光盘**。根据说明书造出来的只读文件包。
*   **容器 (Container)**: **实体/运行中的程序**。基于镜像启动的一个个独立的进程。

> **比喻**：
> *   Dockerfile = 菜谱
> *   Image = 做好的速冻饺子
> *   Container = 煮好的一碗饺子（可以煮好几碗）

---

## 2. 编写 Dockerfile

我们需要在项目根目录下（和 `pom.xml` 同级）创建一个名为 `Dockerfile` 的文件（**注意：没有后缀名！**）。

**内容如下：**

```dockerfile
# 1. 基础镜像：我们要在这个镜像的基础上安装我们的 App
# 推荐使用 openjdk 官方镜像，版本要和你本地开发的一致 (比如 17)
FROM openjdk:17-jdk-alpine

# 2. 作者信息 (可选)
LABEL maintainer="yourname"

# 3. 挂载目录 (可选，用于存放临时文件)
VOLUME /tmp

# 4. 复制文件
# 参数1: 也就是我们 maven package 打出来的 jar 包路径
# 参数2: 放到容器里的名字，我们统一改名叫 app.jar
COPY target/blog-backend-0.0.1-SNAPSHOT.jar app.jar

# 5. 暴露端口 (只是声明，告诉别人我用 8080)
EXPOSE 8080

# 6. 启动命令
# 等价于在终端执行 java -jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

> **⚠️ 注意**：
> 请确认你的 `COPY` 路径是对的。如果你的 Jar 包名字不一样，请修改 `target/xxxx.jar` 这一行。

---

## 3. 构建镜像 (Build)

假设你已经安装了 **Docker Desktop** (Windows) 或 Docker (Linux)。

打开终端（Terminal / CMD），进入项目根目录（`Dockerfile` 所在的目录），执行：

```bash
# 格式: docker build -t 镜像名:版本号 .
# 注意最后那个点 (.) 代表“使用当前目录下的 Dockerfile”
docker build -t blog-backend:1.0 .
```

执行后，你会看到 Docker 正在一层层下载 JDK、复制文件。当看到 `FINISHED` 时，说明镜像制作完成。

**验证**：
```bash
docker images
# 你应该能看到 blog-backend 这个镜像
```

---

## 4. 运行容器 (Run)

现在我们用这个镜像启动一个容器。

```bash
# 格式: docker run -d -p 宿主机端口:容器端口 --name 容器名 镜像名
docker run -d -p 8080:8080 --name my-blog blog-backend:1.0
```

*   **`-d`**: 后台运行 (Detached)。
*   **`-p 8080:8080`**: **端口映射**。
    *   左边的 8080 是**你电脑的端口**。
    *   右边的 8080 是**容器内部的端口**。
    *   意思是：访问我电脑的 8080，就转发给容器里的 8080。
*   **`--name my-blog`**: 给这个运行的容器起个名字，方便管理。

**验证**：
```bash
docker ps
# 应该能看到一个正在运行的容器
```

---

## 5. 致命坑：Localhost 连接失败问题

如果你现在直接启动，**大概率会报错**，连不上 MySQL 或 Redis。

**原因**：
*   你的 Spring Boot 配置文件写的是 `localhost`。
*   在 Docker 容器里，`localhost` 指的是**容器自己**！而容器里并没有装 MySQL，MySQL 在你的宿主机（电脑）上。

**解决方案**：

**方案 A：修改配置文件 (快速测试用)**
如果你是 Windows/Mac 的 Docker Desktop：
将 `application.yml` 里的 `localhost` 改为 **`host.docker.internal`**。这是 Docker 提供的特殊域名，指向宿主机。

**方案 B：修改配置文件 (真实服务器用)**
将 `localhost` 改为**服务器的真实内网 IP**（如 `192.168.1.5`）。

**方案 C：Docker Compose (终极方案)**
让 MySQL、Redis、Java App 全部运行在 Docker 里，通过服务名直接连接。（这是我们下一节课的内容）。

---

## 6. 常用 Docker 命令速查

*   **查看运行中的容器**：`docker ps`
*   **查看所有容器(含已停止)**：`docker ps -a`
*   **查看容器日志**：`docker logs -f my-blog` (排错神器，等同于 `tail -f`)
*   **停止容器**：`docker stop my-blog`
*   **删除容器**：`docker rm my-blog` (删除前必须先停止)
*   **删除镜像**：`docker rmi blog-backend:1.0`

---

## 📝 面试考点：Docker 实现原理

**Q: 虚拟机 (VM) 和 Docker (容器) 有什么区别？**

*   **虚拟机**：
    *   笨重。它虚拟了一套**完整的硬件**，上面跑了一个**完整的操作系统** (Guest OS)，再跑你的 App。启动慢，占用资源多。
*   **Docker**：
    *   轻量。它**共享宿主机的操作系统内核**。容器只是一个**被隔离的进程**，不仅启动是秒级的，而且几乎不额外消耗 CPU/内存。

---

**下节预告**：
现在我们只是把 Java App 容器化了，但 MySQL 和 Redis 还在外面，连接还得改 IP，很麻烦。
能不能写一个脚本，**一键启动 MySQL + Redis + Blog**，并且它们之间自动连通？

能！这就是 **Docker Compose**。
下节课：**Docker Compose 容器编排**（第七阶段的最后一次实操）。

这是**第七阶段·第四次教学**，也是实操环节的**终极一课**。

在上一节课，我们学会了用 Docker 跑 Java 应用。但你发现了一个大麻烦：
你需要分别启动 MySQL 容器、Redis 容器、Java 容器，还得搞定它们之间的 IP 连接问题（改配置文件的 `localhost`）。

**Docker Compose** 就是为了解决这个痛点而生的。
它可以让你在一个 YAML 文件里定义所有的服务（Java+DB+Redis），然后**一条命令全部启动**。并且，它们之间可以通过**服务名**直接通信，不再需要关心 IP。

---

# 第七阶段：Docker Compose 容器编排

## 1. 编写 `docker-compose.yml`

请在项目根目录下（和 `pom.xml`、`Dockerfile` 同级）创建一个名为 **`docker-compose.yml`** 的文件。

**可以直接复制下面的内容：**

```yaml
version: '3.8'

services:
  # --- 服务 1: MySQL ---
  mysql-server:
    image: mysql:8.0
    container_name: blog-mysql
    # 总是重启 (比如服务器重启后，它会自动启动)
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root  # 数据库密码
      MYSQL_DATABASE: blog_db    # 自动创建数据库
    ports:
      - "3306:3306"
    volumes:
      # 数据卷挂载：把容器里的数据存到宿主机，防止删除容器丢失数据
      - ./mysql-data:/var/lib/mysql
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

  # --- 服务 2: Redis ---
  redis-server:
    image: redis:latest
    container_name: blog-redis
    restart: always
    ports:
      - "6379:6379"

  # --- 服务 3: 我们的 Java 应用 ---
  blog-app:
    build: .                      # 使用当前目录的 Dockerfile 构建镜像
    container_name: blog-backend
    restart: always
    ports:
      - "8080:8080"
    depends_on:                   # 依赖顺序：等 MySQL 和 Redis 启动了再启动我
      - mysql-server
      - redis-server
    environment:
      # 🌟 重点：覆盖 application.yml 里的配置
      # 在 Docker 网络里，不需要写 IP，直接写服务名 (mysql-server) 即可
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-server:3306/blog_db?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_REDIS_HOST: redis-server
```

---

## 2. 一键启动 (One Command Rule)

打开终端，进入 `docker-compose.yml` 所在的目录。

1.  **启动所有服务**：
    ```bash
    docker-compose up -d
    ```
    *   `-d`: 后台运行。
    *   Docker 会自动下载 MySQL/Redis 镜像，构建你的 Java 镜像，并按顺序启动。

2.  **查看运行状态**：
    ```bash
    docker-compose ps
    ```
    *   你应该能看到 3 个都在 `Up` 状态的服务。

3.  **查看日志**：
    ```bash
    docker-compose logs -f blog-app
    ```
    *   如果看到 Spring Boot 启动成功的日志，说明大功告成！

---

## 3. 常见问题与维护

*   **Q: 我修改了 Java 代码，怎么更新？**
    *   先执行 Maven 打包：`mvn clean package -DskipTests`
    *   再重新构建并启动：`docker-compose up -d --build`

*   **Q: 怎么停止并删除所有容器？**
    *   `docker-compose down`

*   **Q: 数据库连不上？**
    *   有时候 MySQL 启动比较慢，Java 启动快，导致 Java 连的时候 MySQL 还没准备好。
    *   Docker Compose 会自动重启 Java 应用（因为我们写了 `restart: always`），稍等十几秒通常就好了。

---

## 4. 跨域问题 (CORS) - 部署前的最后补丁

在大纲的最后提到过 **跨域 (Cross-Origin)**。
当你的后端跑在 `8080`，而前端跑在 `80` 或 `3000` 端口时，浏览器会默认拦截前端的请求。
我们需要在后端配置允许跨域。

**请在 `config` 包下的 `WebConfig.java` 中添加：**

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")             // 允许所有路径
            .allowedOriginPatterns("*")    // 允许所有来源
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
            .allowCredentials(true)        // 允许携带 Cookie/Token
            .maxAge(3600);                 // 预检请求缓存时间
}
```

---

## 5. 第七阶段·面试考点：线上排查

部署上线后，面试官通常会问你运维相关的经验。

### 面试题 1：如何排查线上 CPU 飙升的问题？

这是**高频实战题**。不要只说“重启”，要说出排查步骤。

**🎓 标准回答**：
1.  **`top` 命令**：先在服务器上执行 `top`，找到 CPU 占用率最高的那个进程 PID（假设是 Java 进程 12345）。
2.  **`top -H -p 12345`**：查看这个进程下，是哪个**线程** (Thread) 占用了 CPU。记下线程 ID (比如 9999)。
3.  **转换 16 进制**：将线程 ID 9999 转换为 16 进制（0x270f），因为 Java 堆栈里用的是 16 进制。
4.  **`jstack 12345 | grep 0x270f -A 20`**：使用 Java 自带的 `jstack` 工具打印堆栈信息，并过滤出刚才那个线程。
5.  **分析代码**：查看堆栈信息，通常能定位到是哪一行业务代码写了死循环，或者频繁 GC。

### 面试题 2：如何排查内存溢出 (OOM)？

**🎓 标准回答**：
1.  **导出 Dump 文件**：使用命令 `jmap -dump:format=b,file=heap.hprof <PID>` 导出当前的内存快照。
2.  **分析工具**：将文件下载到本地，使用 **JProfiler** 或 **Eclipse Memory Analyzer (MAT)** 打开。
3.  **看大对象**：查看 Histogram（直方图），看哪个对象占用的内存最大，通常能发现是某个 List 或 Map 塞满了数据没清理。

---

# 🎉 全课程总结 (Graduation)

恭喜你！你已经完成了从 **Java 新手** 到 **具备独立开发、部署 Spring Boot 项目能力** 的蜕变。

让我们回顾一下你现在的技能树：

1.  **基础**：搭建 Spring Boot，理解约定大于配置。
2.  **Web**：精通 RESTful 接口，三层架构，参数接收。
3.  **DB**：熟练使用 MySQL + MyBatis-Plus 实现 CRUD 和分页。
4.  **进阶**：掌握 AOP 日志、全局异常、Validation 校验、文件上传。
5.  **安全**：手写了一套基于 JWT + ThreadLocal 的认证鉴权系统。
6.  **性能**：使用 Redis 缓存热点数据，解决击穿/穿透问题。
7.  **部署**：掌握 Linux 基础命令，能使用 Docker Compose 一键上线。

### 👨‍🏫 导师寄语

你现在拥有的这个 `blog-backend` 项目，虽然代码量可能只有几千行，但**麻雀虽小，五脏俱全**。它包含了后端开发 80% 的核心知识点。

**接下来的建议**：
1.  **不要停**：试着给这个博客加点新功能，比如“评论功能”、“点赞功能”。
2.  **写简历**：把这个项目写进你的简历里。不要写“简单的博客系统”，要写**“基于 SpringBoot + Vue + Redis 的高并发个人博客平台，实现了 JWT 无状态登录、Redis 缓存防击穿、Docker 容器化部署”**。

祝你在 Java 后端开发的道路上越走越远，Offer 拿到手软！加油！🚀