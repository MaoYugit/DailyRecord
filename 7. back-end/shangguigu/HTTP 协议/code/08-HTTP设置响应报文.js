const http = require("http");

const server = http.createServer((request, response) => {
  // 1.设置响应状态码
  //   response.statusCode = 203;
  // 2.响应状态的描述
  //   response.statusMessage = "i love you";
  // 3.响应头
  //   response.setHeader("content-type", "text/html; charset=utf-8");
  //   response.setHeader("Server", "Node.js");
  //   response.setHeader("myHeader", "text text text");
  //   response.setHeader("test", ["a", "a", "b", "c"]);
  // 4.响应体
  response.write("love");
  response.write("love");
  response.write("love");
  response.write("love");
  response.write("love");
  response.end("");
});

server.listen("9000", () => {
  console.log("服务已启动");
});
