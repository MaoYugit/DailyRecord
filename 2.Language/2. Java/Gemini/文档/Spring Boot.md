### 第一阶段：环境搭建与 Spring Boot 初探
**目标**：跑通第一个程序，理解 Spring Boot 到底是什么。

1.  **知识点**
    *   **前置准备**：Maven/Gradle 构建工具基础，IDEA 使用。
    *   **Spring Boot 简介**：它解决了 Spring 的哪些痛点（繁琐配置、依赖冲突）？
    *   **核心概念**：约定大于配置（Convention over Configuration）。
    *   **项目结构**：`src/main/java`，`resources`，`pom.xml` 解析。
    *   **RESTful 接口初体验**：`@RestController`, `@GetMapping`。

2.  **博客实战任务**
    *   使用 Spring Initializr 创建一个 `blog-backend` 项目。
    *   写一个 `HelloController`，访问浏览器输出 "Hello, My Blog!"。

3.  **面试考点**
    *   Spring Boot 和 Spring MVC 的区别是什么？
    *   Spring Boot 的启动类注解 `@SpringBootApplication` 包含了哪三个核心注解？

---

### 第二阶段：Web 开发核心（MVC 架构）
**目标**：能够接收前端请求，处理逻辑，并返回 JSON 数据。

1.  **知识点**
    *   **HTTP 协议基础**：GET/POST/PUT/DELETE 的区别。
    *   **参数接收**：`@RequestParam` (URL传参), `@PathVariable` (路径参数), `@RequestBody` (JSON对象)。
    *   **三层架构**：Controller (控制层) -> Service (业务层) -> Dao/Mapper。
    *   **统一响应封装**：设计一个 `Result<T>` 类，统一定义 code, message, data。
    *   **Lombok 插件**：使用 `@Data`, `@Builder` 简化 Bean 代码。

2.  **博客实战任务**
    *   创建 `User` 和 `Article` 实体类。
    *   实现“发布文章”、“获取文章详情”的接口（数据暂时存放在 `Map` 或 `List` 内存中）。
    *   规范接口返回格式，例如：`{"code": 200, "msg": "success", "data": {...}}`。

3.  **面试考点**
    *   `@Controller` 和 `@RestController` 的区别？
    *   Get 请求和 Post 请求在 Spring Boot 中如何处理参数？
    *   什么是 Bean？Spring 容器是如何管理 Bean 的（IoC 概念）？

---

### 第三阶段：数据持久化（连接数据库）
**目标**：将博客数据真正存入 MySQL 数据库。

1.  **知识点**
    *   **MySQL 基础**：建库、建表、SQL 语句。
    *   **数据源配置**：在 `application.yml` 中配置 JDBC 连接。
    *   **ORM 框架选择**：
        *   **MyBatis-Plus**。
    *   **CRUD 操作**：增删改查的实现。
    *   **连接池**：HikariCP（Spring Boot 默认）。
2.  **博客实战任务**
    *   安装 MySQL，设计 `tb_user`（用户表）、`tb_article`（文章表）、`tb_category`（分类表）。
    *   集成 MyBatis-Plus。
    *   重构之前的 Service 代码，实现真正的数据库读写。
    *   实现“文章列表分页查询”功能。
3.  **面试考点**
    *   MyBatis 中 `#` 和 `$` 的区别是什么？（防 SQL 注入）。
    *   Spring Boot 自动装配原理是什么？（**核心高频题**）。
    *   数据库事务 `@Transactional` 的使用及失效场景。

---

### 第四阶段：业务进阶与规范化
**目标**：让你的代码更像“企业级”代码，处理异常和校验。

1.  **知识点**
    *   **全局异常处理**：`@ControllerAdvice` + `@ExceptionHandler`。不要让前端看到 500 报错页。
    *   **参数校验**：`Validation` 框架 (`@NotNull`, `@Size`, `@Email`)。
    *   **AOP 切面编程**：理解切面、切点。
    *   **日志管理**：Logback/SLF4J 配置，记录请求日志。
    *   **文件上传**：实现图片上传功能（本地存储或对象存储 OSS）。

2.  **博客实战任务**
    *   添加“请求日志切面”，记录每个接口的耗时、IP、参数。
    *   实现“发布文章”时的表单校验（标题不能为空，内容不能少于10字）。
    *   实现“上传头像”或“文章配图”接口。

3.  **面试考点**
    *   谈谈你对 AOP 的理解，项目中有哪些应用场景？（日志、权限、事务）。
    *   Spring 的 Bean 生命周期是怎样的？
    *   Spring Boot 的配置文件加载顺序（application.properties vs yml）。

---

### 第五阶段：安全与认证（难点）
**目标**：实现博客的登录、注册，保护后台接口。

1.  **知识点**
    *   **认证 vs 授权**：Authentication（你是谁） vs Authorization（你能干啥）。
    *   **密码加密**：BCryptPasswordEncoder（不要存明文密码！）。
    *   **JWT (JSON Web Token)**：无状态登录的主流方案。
    *   **Spring Security**：**（必学，但在博客中可以先用拦截器实现简易版，之后再升级）**。
        *   *建议路径*：先学 `HandlerInterceptor` 实现登录拦截 -> 再学集成 JWT -> 最后了解 Spring Security 原理。

2.  **博客实战任务**
    *   设计登录接口，验证通过后生成 JWT Token 返回给前端。
    *   定义“登录拦截器”，拦截非公开接口（如发布文章），解析 Token 里的用户 ID。
    *   ThreadLocal 的使用：在同一个线程中传递用户信息。

3.  **面试考点**
    *   Cookie、Session 和 Token 的区别？
    *   JWT 的结构是怎样的？如何防止 Token 被篡改？
    *   过滤器（Filter）和拦截器（Interceptor）的区别？

---

### 第六阶段：性能优化与中间件
**目标**：引入 Redis 缓存，提升系统逼格和性能。

1.  **知识点**
    *   **NoSQL 简介**：Redis 安装与基础命令。
    *   **Spring Data Redis**：`RedisTemplate` 和 `StringRedisTemplate` 的使用。
    *   **缓存策略**：什么数据适合存缓存？（热点文章、配置信息）。
    *   **定时任务**：`@Scheduled`。

2.  **博客实战任务**
    *   实现“文章浏览量”功能：每次访问先存 Redis，定时同步回 MySQL（防止频繁写库）。
    *   缓存“文章分类”和“标签”列表。

3.  **面试考点**
    *   Redis 有哪些数据类型？
    *   Redis 缓存穿透、缓存击穿、缓存雪崩是什么？如何解决？
    *   MySQL 和 Redis 如何保证数据一致性？

---

### 第七阶段：部署与运维
**目标**：将你的博客后端跑到服务器上。

1.  **知识点**
    *   **打包**：Maven package (Jar 包)。
    *   **Linux 基础**：常用命令 (cd, ls, ps, kill, nohup)。
    *   **Docker 基础**：编写 Dockerfile，制作镜像，容器化部署（加分项）。
    *   **跨域问题**：CORS 配置（前后端分离必遇问题）。

2.  **博客实战任务**
    *   购买一台云服务器（或者用虚拟机）。
    *   安装 JDK, MySQL, Redis。
    *   将博客打包成 jar，使用 `java -jar blog.jar` 运行。
    *   （进阶）使用 Docker Compose 一键编排 MySQL+Redis+App。

3.  **面试考点**
    *   如何排查线上 CPU 飙升或内存溢出（OOM）的问题？
    *   常用的 Linux 命令有哪些？

---

### 总结与建议

**给新手的实操建议：**

1.  **不要复制粘贴**：初学时，配置代码尽量手敲，报错了去查，这是成长的最快方式。
2.  **接口测试工具**：下载 **Postman** 或 **Apifox**，后端开发必须学会用工具测试自己的接口，不要依赖前端页面。
3.  **看文档**：遇到问题优先看 Spring Boot 官方文档或 StackOverflow，培养解决问题的能力。
4.  **关于前端**：如果你只做后端，可以使用 Vue 的现成管理后台模板（如 Vue-Element-Admin）来对接你的接口，或者只写接口文档（Swagger/Knife4j）。





