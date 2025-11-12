const http = require("http");

const server = http.createServer((request, response) => {
  response.setHeader("content-type", "text/html;charset=utf-8");
  //   获取请求方法
  console.log("获取请求方法", request.method);

  //   获取请求url
  console.log("获取请求url", request.url); // 只包含 url 中的路径与查询字符串

  // 获取HTTP协议版本号
  console.log("获取HTTP协议版本号", request.httpVersion);

  // 获取HTTP请求头
  console.log("获取HTTP请求头", request.headers);

  response.end("hello http server,你好"); // 设置响应体
});

server.listen(9000, () => {
  console.log("server is started");
});
