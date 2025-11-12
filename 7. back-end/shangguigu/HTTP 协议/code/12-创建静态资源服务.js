const http = require("http");
const fs = require("fs");
const server = http.createServer((request, response) => {
  let { pathname } = new URL(request.url, "http://127.0.0.1");
  let fileName = __dirname + pathname;
  fs.readFile(fileName, (err, data) => {
    if (err) {
      response.statusCode = 500;
      response.end("fail");
      return;
    }
    response.end(data);
  });
});

server.listen("9000", () => {
  console.log("服务已启动");
});
