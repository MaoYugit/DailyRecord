const http = require("http");

const server = http.createServer((req, res) => {
  console.log("收到一个来自客户端的请求！");
});
