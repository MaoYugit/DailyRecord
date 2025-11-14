const http = require("http");

const server = http.createServer((req, res) => {
  // 这部分代码是“请求处理中心”
  // 每次有用户访问我们的服务器，这里的代码就会被执行一次
  console.log("收到一个来自客户端的请求！");
});
