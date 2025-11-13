// 1. 引入 http 模块
const http = require("http");

// 2. 定义端口号
const PORT = 3001;

// 3. 创建服务器
const server = http.createServer((req, res) => {
  // 这部分代码是“请求处理中心”
  // 每次有用户访问我们的服务器，这里的代码就会被执行一次
  console.log("收到一个请求！正在准备响应...");
  // 我们将在这里构建并发送响应
  // 4. 发送响应并结束对话
  res.end("Hello from your first Node.js server!");
});

// 5. 启动服务器并监听端口
server.listen(PORT, () => {
  // 这个回调只在启动时执行一次
  console.log(`服务器已成功启动，正在监听 ${PORT} 端口...`);
  console.log(`请在浏览器中访问: http://localhost:${PORT}`);
});
