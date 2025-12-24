# AWS白嫖一年服务器并搭建个人网站

## 服务器获取

```bash
aaPanel Internet Address: [https://3.16.11.203:27139/64ace854]
username: yq5wubsn
password: 1a92aa69
```

1. 注册AWS账号

2. 搜索Budgets设置零支出预算

3. 搜索EC2并进入，点击启动实例

   **配置参数：**

   - **名称：** 随便起，比如 MyFreeServer。
   - **操作系统 (AMI)：** 默认的 **Amazon Linux 2023** 即可，注意下方必须带蓝字 **"符合免费套餐资格" (Free tier eligible)**。
   - **实例类型：** 确保选择 **t3.micro**（在俄亥俄 us-east-2 区，t3.micro 是免费的；在有些区是 t2.micro）。**一定要看准那行蓝字标记。**
   - **密钥对 (Key pair)：** 点击“创建新密钥对”。
     - 名字随便起。
     - 格式选 .pem。
     - **注意：** 点击创建后会下载一个文件，**千万别删！** 这是你以后登录服务器的唯一凭证。
   - **网络设置：** 勾选“允许来自 0.0.0.0/0 的 SSH 流量”。（建网站还要勾选“允许来自互联网的 HTTP/HTTPS 流量”）。
   - **配置存储：** 默认是 8GB，免费额度最高可以给到 **30GB**（可以改成 30，省得以后不够用）。
   - 点击右侧的 **"启动实例"**。

   

4. 等待实例状态变为“正在运行”。

   点击该实例 ID，找到 **“公网 IPv4 地址”**。

   直接在页面点击右上角的 **“连接”**，选择 **“EC2 Instance Connect”**，直接在浏览器里就能操作。

   

5. 成功进入服务器

   查看电脑配置：`cat /etc/os-release`

   查看硬盘：`df -h`

   更新软件包：`sudo yum update -y`

6. 安装宝塔国际版

   切换到管理员模式：`sudo -i`

   先安装下载工具：`yum install -y wget`

   下载脚本到服务器：`wget -O install.sh http://www.aapanel.com/script/install_6.0_en.sh`

   正式开始安装：`bash install.sh aapanel`

   j记住最终的：

   - **aaPanel Internet Address:** http://3.16.11.203:abcd/xxxxxx
   - **username:** 随机的一串字母
   - **password:** 随机的一串字母

7. 开放 8888 端口

   aaPanel 使用 **8888 端口**

   - 回到AWS EC2 控制台页面。

   - 点击左侧菜单的 **“安全组” (Security Groups)**。

   - 在列表里找到你刚才创建的那个（名称类似 launch-wizard-1），点进去。

   - 点击下方的 **“编辑入站规则” (Edit inbound rules)**。
   - 点击 **“添加规则”**：
     - **类型：** 自定义 TCP。
     - **端口范围：** 输入 8888。
     - **源：** 选择 0.0.0.0/0（代表允许任何地方访问）。
   - 点击 **“保存规则”**。

8. 开放abcd端口，abcd是刚才aaPanel Internet Address中的几个数字，和8888操作一样

9. 登录网站后台，访问aaPanel Internet Address，输入username和password，成功登录宝塔

10. 安装一些服务，点击one-click, 可能需要等待30分钟到几个小时。

    ```bash
    Nginx 1.24
    MySQL mariadb_10.5
    Pure-Ftpd 1.0.49
    PHP 8.0
    phpMyAdmin 5.2
    ```

11. 开启虚拟内存，因为后端是Java+MySQL 在我们这个1G的服务器上跑会很吃力

    回到刚才的黑色命令行窗口（或 aaPanel 的 Terminal）

    我们要关掉旧的，删除错误的，重新做一个大的。

    **关闭所有当前的 Swap：**

    ```bash
    sudo swapoff -a
    ```

    **删除可能存在的旧文件：**

    ```bash
    rm -f /swapfile
    ```

    **重新创建 2GB 的文件：**
    (这里改用 `fallocate` 命令，它比 `dd` 快得多，如果系统不支持会报错，报错了再换 `dd`)

    ```bash
    sudo fallocate -l 2G /swapfile
    ```

    *如果上面报错，再用这个：*
    `sudo dd if=/dev/zero of=/swapfile bs=1M count=2048`

    **设置权限和格式化：**

    ```bash
    sudo chmod 600 /swapfile
    sudo mkswap /swapfile
    ```

    **启用：**

    ```bash
    sudo swapon /swapfile
    ```

    **验证：**

    ```bash
    swapon --show
    free -m
    ```

    看到 SIZE 是 2G 代表成功了。

    

12. 

    

## 域名购买与设置

1. 在spaceship搜索自己喜欢的域名并购买

2. 搜索买好的域名，进入域名管理器中，关闭自动续费

3. 把域名指向你的服务器（DNS 解析）点击域名，点击名称服务器和DNS，点击高级DNS，添加以下两条DNS记录（需要30min左右生效）

   | 主机 | 类型 |    值    | 生存时间 |
   | :--: | :--: | :------: | :------: |
   |  @   |  A   | 服务器ip |  30min   |
   | www  |  A   | 服务器ip |  30min   |

4. 在 aaPanel 绑定这个域名

   登录 **aaPanel** 面板。

   点击左侧的 **Website** (网站)。

   点击create site。

   在框里输入 maoyu.online 和 www.maoyu.online（一行一个）

   php version 选择 static

   confirm就好了

5. 获取ssl证书 

   - 点击右侧的 **“Not set”**（在 SSL 那一列）或者点击 **“Conf”**。
   - 在左侧菜单选 **“SSL”**。
   - 选择 **“Let's Encrypt”**。
   - 勾选两个域名，点击 **“Apply”**。
   - 申请成功后，**打开右上角的 “Force HTTPS”**。

   



## 网站搭建

**准备数据库 (MySQL)**

1.  登录 **aaPanel** 面板。
2.  点击左侧 **Database** -> **Add database**。
    *   **Database name:** `myblog_db` (必须和代码一致)
    *   **Username:** `myblog_db`
    *   **Password:** `123456` (为了匹配你提供的配置文件)
    *   **Access Permission:** 本地 (Localhost)
3.  创建成功后，点击右侧的 **Import** -> **Upload local file**。
4.  将你提供的 **SQL 代码** 保存为 `install.sql` 文件，上传并点击 **Import**。
    *   *这样你的表结构（用户表、文章表等）就全部创建好了。*

---

**部署 Java 后端**

由于你使用的是 **JDK 21**，aaPanel 默认可能没有，我们需要手动处理。

1. 安装 JDK 21

回到你的黑色命令行窗口（SSH），执行以下命令：
```bash
sudo yum install fontconfig java-21-amazon-corretto-devel -y
# 验证安装
java -version
```

2. 打包并上传 Jar 包

1.  在你的本地电脑（IntelliJ IDEA）里，点击 Maven 的 **clean** 然后 **package**。
2.  在 `target` 文件夹下找到 `time-shards-backend-0.0.1-SNAPSHOT.jar`。
3.  回到 aaPanel，点击 **Files**，进入目录 `/www/wwwroot/maoyu.online/`。
4.  建议新建一个文件夹叫 `backend`，把 `.jar` 文件上传进去。

3. 运行后端

   彻底杀掉可能残留的 Java 进程

   ```bash
   sudo -i
   sudo pkill -f java
   ```

   手动启动 Java 后端（带内存限制）

   进入jar 包目录并启动它。我们给它设置 256MB 的最大内存限制，防止服务器崩溃：

   ```
   cd /www/wwwroot/maoyu.online/java_server/
   nohup java -Xms128m -Xmx256m -jar app.jar > output.log 2>&1 &
   ```

   执行完后，可以看到一行类似 `[1] 12345` 的数字，代表已经在后台跑起来了。

   检查是否真的启动成功

   ```bash
   tail -f output.log
   ```

​	如果看到 `Started TimeShardsBackendApplication in ... seconds`，说明成功了！按 `Ctrl + C` 退出日志查看（程序还会继续跑）。

---

**让域名访问后端 (Nginx 反向代理)**

1. 在 **Website** -> **Conf** 窗口中，点击左侧菜单的 **Config** (这是 Nginx 的原始配置文件)。

2. 在弹出的代码框里，向下滚动，找到类似 include enable-php-00.conf; 或者原本 location / 的位置。

3. 在这些代码**上方**，直接粘贴下面这段代码：

   ```bash
   # 转发所有以 /api 开头的请求到 Java 后端
       location /api/ {
           proxy_pass http://127.0.0.1:8080/;  # 注意末尾的斜杠，它会自动去掉请求中的 /api
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
           
           # 解决上传大文件限制
           client_max_body_size 50m;
       }
   ```

5. 为什么要这么做？

- **Proxy dir (/api):** 因为你的 Vue 前端通常会统一给接口加上 /api 前缀（例如 maoyu.online/api/login）。
- **Target URL:** 后端 Java 跑在 127.0.0.1:8080，但它的代码里通常没有 /api（例如后端路径是 /login）。
- **注意 proxy_pass 末尾的 /：** 这是一个 Nginx 小技巧，它会把前端发来的 /api/login 变成 /login 再传给 Java，这样前后端就完美匹配了。

---

**前端 Vue 静态资源**

现在的域名访问的是 `/www/wwwroot/maoyu.online/` 目录下的内容。

1.  把本地 Vue 项目 `npm run build` 生成的 `dist` 文件夹里的**所有内容**，上传到服务器的 `/www/wwwroot/maoyu.online/` 目录下。
2.  **注意：** 确保 `index.html` 就在 `/www/wwwroot/maoyu.online/` 这一层，而不是在子文件夹里。

---

**解决 Vue 路由刷新 404 问题**

如果 Vue 使用的是 `History` 模式，刷新页面会 404。

1.  在刚才的 **Website -> Conf** 窗口里，点击左侧的 **URL rewrite** (伪静态)。
2.  选择 `vuejs` 或者直接把下面的代码贴进去：
    ```nginx
    location / {
      try_files $uri $uri/ /index.html;
    }
    ```
3.  点击 **Save**。

---

**创建上传文件夹权限**

 Java 配置里写了上传路径是 `/www/wwwroot/maoyu.online/uploads/`。

1.  在 aaPanel **Files** 中，在 `/www/wwwroot/maoyu.online/` 下新建文件夹 `uploads`。
2.  右键该文件夹 -> **Permission**。
3.  给 **www** 用户 `755` 权限，确保 Java 程序有权写入图片。

---

