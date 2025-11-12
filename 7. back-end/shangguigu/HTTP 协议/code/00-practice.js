const http = require("http");

const server = http.createServer((request, response) => {
  response.setHeader("content-type", "text/html;charset=utf-8");

  let body = "";

  request.on("data", (chunk) => {
    body += chunk;
  });

  request.on("end", () => {
    console.log(body);
    response.end("hello0000");
  });
});

server.listen(8000, () => {
  console.log("hahaah");
});
