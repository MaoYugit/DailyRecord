const http = require("http");

const PORT = 3001;

const server = http.createServer((req, res) => {
  // --- 开始侦查 ---
  // 设置状态码和响应头
  res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });

  const htmlContent =
    "<h1>欢迎来到我的第一个HTML页面！</h1><p>这是用Node.js原生http模块发送的。</p>";
  // 发送内容并结束响应
  res.end(htmlContent);
});

server.listen(PORT, () => {
  console.log(`侦探服务器已就位，正在监听 ${PORT} 端口...`);
  console.log(`请尝试用浏览器访问不同的URL，例如:`);
  console.log(`- http://localhost:${PORT}/`);
  console.log(`- http://localhost:${PORT}/users/profile`);
  console.log(`- http://localhost:${PORT}/search?q=nodejs`);
});
