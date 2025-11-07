const http = require("http");

const server = http.createServer((request, response) => {
  response.setHeader("content-type", "text/html;charset=utf-8");
  response.end("hello http server,你好");
});

server.listen(9000, () => {
  console.log("server is started");
});
