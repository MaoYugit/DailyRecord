const http = require("http");

const PORT = 3002;

const server = http.createServer((req, res) => {
  const url = req.url;
  const method = req.method;
  console.log(`收到请求: ${method} ${url}`);
  // --- 开始我们的路由逻辑 ---
  if (url === "/" && method === "GET") {
    // 设置响应头
    res.writeHead(200, { "content-type": "text/html; charset=utf-8" });
    // 发送HTML响应
    res.end("<h1>Welcome to our Homepage!</h1><p>This is the main page.</p>");
  } // 路由2: 关于页 (GET /about)
  else if (url === "/about" && method === "GET") {
    // 设置响应头
    res.writeHead(200, { "Content-Type": "text/plain; charset=utf-8" });
    // 发送纯文本响应
    res.end("This is the about page. We are a cool company!");

    // 路由3: 处理所有其他未匹配的URL (404 Not Found)
  } else {
    // 设置404状态码和响应头
    res.writeHead(404, { "Content-Type": "text/html; charset=utf-8" });
    // 发送HTML错误信息
    res.end(
      "<h1>404 - Page Not Found</h1><p>Sorry, the page you are looking for does not exist.</p>"
    );
  }
});

server.listen(PORT, () => {
  console.log(`路由服务器已启动，正在监听 ${PORT} 端口...`);
  console.log("请尝试访问:");
  console.log(`- http://localhost:${PORT}/`);
  console.log(`- http://localhost:${PORT}/about`);
  console.log(`- http://localhost:${PORT}/contact`); // 这是一个不存在的路径
});
