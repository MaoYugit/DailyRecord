const http = require("http");

const server = http.createServer((request, response) => {
  response.setHeader("content-type", "text/html; charset=utf-8");
  // 获取请求的方法
  let { method } = request;
  // 获取请求的url路径
  let { pathname } = new URL(request.url, "http://127.0.0.1");
  if (method === "GET" && pathname === "/login") {
    response.end("登录"); // 响应体
  } else if (method == "GET" && pathname == "/register") {
    response.end("注册"); // 响应体
  } else {
    response.end("what do you want to do?");
  }
});

// 监听端口，启动服务
server.listen(9000, () => {
  console.log("服务已启动");
});
