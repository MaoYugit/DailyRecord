const http = require("http");

const server = http.createServer((request, response) => {
  // let url = new URL("http://127.0.0.1/search?a=100&b=200");
  // let url = new URL("/search?a=100&b=200", "http://127.0.0.1");
  let url = new URL(request.url, "http://www.xxx.com");
  console.log(url);
  // 输出路径
  console.log(url.pathname);
  // 输出查询字符串
  console.log(url.searchParams.get("key"));
  console.log(url.searchParams.get("num"));

  response.end("url"); // 响应体
});

// 监听端口，启动服务
server.listen(9000, () => {
  console.log("服务已启动");
});
