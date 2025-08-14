### RuoYi-Vue-Plus 初始化项目操作指南

#### 一、启动前的准备工作 (环境搭建)

确保电脑上安装了以下软件和工具，并满足其版本要求：

1. **Git**: 必须安装，用于克隆项目代码。
2. **JDK**: **JDK 17 或 JDK 21 版本**。建议使用 JDK 17。
3. **MySQL**: **MySQL 5.7 或 MySQL 8.0 版本**。mysql -u root -p 密码：123456
4. **Redis**: **Redis 6.x 或 Redis 7.x 版本**。redis-cli 密码：123456
5. **Maven**: **Maven 3.6.1+ 版本**。
6. **Node.js**: **Node.js 16+ 版本** (包含了 npm，用于前端依赖管理)。
7. **IDE (集成开发环境)**: 强烈推荐使用 **IntelliJ IDEA Ultimate Edition**。
8. **数据库工具**: 建议安装一个数据库管理工具，如 Navicat Premium、DataGrip、SQLyog 等，方便管理数据库。
9. **Git Bash (Windows 用户)**: 如果您是 Windows 系统，推荐使用 Git Bash 命令行工具，体验更好。

#### 二、后端项目启动

后端项目是整个系统的核心，负责数据处理和业务逻辑。

1. **克隆项目代码**：
   
   * 打开您的终端 (Terminal) 或 Git Bash。
   * 执行命令克隆 `RuoYi-Vue-Plus` 项目到本地：
     
     ```bash
     git clone https://gitee.com/dromara/RuoYi-Vue-Plus.git
     cd RuoYi-Vue-Plus
     ```

2. **创建数据库**：
   
   * 使用您的数据库工具（如 Navicat）连接到 MySQL。
   * 创建一个新的数据库，数据库名为：`ry_plus`。

3. **导入 SQL 文件**：
   
   * 在克隆下来的 `RuoYi-Vue-Plus` 项目目录中，找到 `doc/sql/ry_plus.sql` 文件。
   * 将 `ry_plus.sql` 文件导入到您刚刚创建的 `ry_plus` 数据库中。

4. **修改后端配置**：
   
   * 使用 IntelliJ IDEA 打开 `RuoYi-Vue-Plus` 项目。
   * 在项目结构中，找到 `ruoyi-admin` 模块。
   * 进入 `ruoyi-admin/src/main/resources` 目录。
   * 找到并打开 `application-druid.yml` 文件。
   * **修改数据库连接信息**：更新 `url`、`username`、`password` 为您本地 MySQL 的配置。
   * **修改 Redis 连接信息**：更新 `host`、`port`、`password` 为您本地 Redis 的配置。如果您的 Redis 没有密码，请将 `password` 一行删除或注释掉。
   * **重要提示**：文件中还有关于 ElasticSearch、Minio、Flowable、RabbitMQ、OSS 等其他组件的配置。**如果您当前不需要使用这些功能，强烈建议您将对应的配置项注释掉或删除**，这可以避免因为这些组件未安装或配置错误导致后端启动失败或卡顿。

5. **启动后端服务**：
   
   * 在 IntelliJ IDEA 中，找到 `ruoyi-admin` 模块下的 `src/main/java` 目录。
   * 找到并打开 `RuoYiApplication.java` 这个类文件。
   * 右键点击 `RuoYiApplication.java` 文件，选择 `Run 'RuoYiApplication'` (或点击类名旁边的绿色运行箭头)。
   * **观察 IDEA 控制台输出**：如果没有红色的错误信息，并且最后看到类似 "Tomcat started on port(s): 8080" 的日志，表示后端服务已成功启动。

#### 三、前端项目启动

后端启动成功后，接下来运行前端界面。文档中提供了多种前端选择，这里我们以 **官方前端项目** 为例进行说明。

1. **克隆官方前端项目**：
   
   * 打开一个新的终端 (Terminal) 或 Git Bash 窗口。
   * **注意：** 克隆到 `RuoYi-Vue-Plus` 项目目录之外的另一个目录，避免混淆。
   * 执行命令克隆 `ruoyi-ui` (官方前端) 项目：
     
     ```bash
     git clone https://gitee.com/dromara/ruoyi-ui.git
     cd ruoyi-ui
     ```

2. **安装前端依赖**：
   
   * 在 `ruoyi-ui` 项目目录下，执行命令安装所有依赖：
     
     ```bash
     npm install
     ```
     
     这个过程可能需要几分钟，取决于您的网络速度。

3. **启动前端服务**：
   
   * 在 `ruoyi-ui` 项目目录下，执行命令启动开发服务器：
     
     ```bash
     npm run dev
     ```
   * 控制台会打印出前端服务的访问地址，通常是 `http://localhost:80` 或 `http://localhost:8080` (如果后端占用了 8080，前端会使用其他端口)。

#### 四、访问系统

1. **打开浏览器**：
   * 复制 `npm run dev` 命令输出的访问地址。
   * 在浏览器中打开该地址。
2. **登录**：
   * 您将看到 `RuoYi-Vue-Plus` 的登录页面。
   * 默认的登录账号和密码通常是：`admin / 123456`。

---

**遇到问题怎么办？**

* **后端启动卡住/报错**：
  * 检查 MySQL 和 Redis 服务是否已启动。
  * 仔细检查 `application-druid.yml` 中数据库和 Redis 的连接配置是否正确。
  * 检查是否按照建议注释或删除了不需要的第三方组件配置。
  * 查看控制台报错信息，通常会提示缺少什么依赖或配置错误。
* **前端启动报错**：
  * 检查 Node.js 和 npm 是否安装正确。
  * 确保 `npm install` 成功完成，没有出现大量错误。
  * 检查端口是否被占用。

通过以上详细步骤，您应该能够成功地将 `RuoYi-Vue-Plus` 项目跑起来。祝您学习顺利！
