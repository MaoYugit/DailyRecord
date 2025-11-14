// 1. 引入 express 模块
// 这行代码会从 node_modules 文件夹中加载 Express 框架
const express = require("express");

// 2. 创建一个 Express 应用实例
// app 对象是 Express 功能的核心，我们后续的所有操作都将基于它
const app = express();

// 3. 定义服务器的端口号
// Web 服务器需要监听一个网络端口来接收请求。
const port = 9000;

// 4. 定义一个路由 (Route)
// 当用户访问我们网站的根路径 ('/') 时，这个函数就会被触发
// req 代表收到的 HTTP 请求，res 代表要发出的 HTTP 响应
app.get("/", (req, res) => {
  console.log(`收到一个 ${req.method} 请求，访问的 URL 是 ${req.url}`);
  // 使用 res.send() 方法向客户端发送响应
  res.send("Hello World!");
});

app.get("/api/user", (req, res) => {
  // 发送一个 JSON 对象作为响应
  res.json({
    id: 1,
    name: "小明",
    email: "xiaoming@example.com",
  });
});

// 5. 启动服务器并监听指定端口
// 这行代码让我们的应用开始在 9000 端口上等待请求
// 第二个参数是一个回调函数，当服务器成功启动时会执行
app.listen(port, () => {
  console.log(`应用正在监听http://localhost:${port}`);
});
