const http = require("http");
const url = require("url");

const server = http.createServer((request, response) => {
  console.log("常规解析", request.url);

  let res = url.parse(request.url);
  console.log("用url.pares()解析", res);
  console.log("路径", res.pathname);

  let new_res = url.parse(request.url, true);
  console.log("查询字符串1", new_res.query.key);
  console.log("查询字符串2", new_res.query.num);

  response.end("url"); // 响应体
});

// 监听端口，启动服务
server.listen(9000, () => {
  console.log("服务已启动");
});
