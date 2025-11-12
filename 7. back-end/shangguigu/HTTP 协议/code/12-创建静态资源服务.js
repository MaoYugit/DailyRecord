const http = require("http");
const fs = require("fs");
const server = http.createServer((request, response) => {
  let { pathname } = new URL(request.url, "http://127.0.0.1");
  if (pathname === "/") {
    let html = fs.readFileSync(__dirname + "/index.html");
    response.end(html);
  } else if (pathname === "/style.css") {
    let css = fs.readFileSync(__dirname + "/style.css");
    response.end(css);
  } else if (pathname === "/script.js") {
    let js = fs.readFileSync(__dirname + "/script.js");
    response.end(js);
  } else {
    response.statusCode = 404;
    response.end("404 NOT FOUND");
  }
});

server.listen("9000", () => {
  console.log("服务已启动");
});
