const http = require("http");

const PORT = 3001;

const server = http.createServer((req, res) => {
  // --- 开始侦查 ---
  console.log("--- 收到新请求 ---");
  console.log("请求的 URL:", req.url);
  console.log("请求的方法:", req.method);
  console.log("请求头:", req.headers);

  // --- 响应部分 ---
  res.end("侦查报告已记录在服务器控制台！");
});

server.listen(PORT, () => {
  console.log(`侦探服务器已就位，正在监听 ${PORT} 端口...`);
  console.log(`请尝试用浏览器访问不同的URL，例如:`);
  console.log(`- http://localhost:${PORT}/`);
  console.log(`- http://localhost:${PORT}/users/profile`);
  console.log(`- http://localhost:${PORT}/search?q=nodejs`);
});
