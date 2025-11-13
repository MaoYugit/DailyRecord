const http = require("http");

const server = http.createServer((req, res) => {
  // 这部分代码是“请求处理中心”
  // 每次有用户访问我们的服务器，这里的代码就会被执行一次
  console.log("收到一个来自客户端的请求！");
});

// 将端口号定义为常量，便于管理
const PORT = 3000;

server.listen(PORT, () => {
  // 这部分回调函数，只在服务器成功启动时执行一次
  console.log(`服务器已成功启动，正在监听${PORT}端口...`);
  console.log(`请在浏览器中访问：http://localhost:${PORT}`);
});
