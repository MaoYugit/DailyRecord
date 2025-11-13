const http = require("http");
const PORT = 3000;

const server = http.createServer((req, res) => {
  const url = req.url;
  const method = req.method;

  console.log(`收到请求: ${method} ${url}`);

  // 路由1: 首页 (GET /)
  if (url === "/" && method === "GET") {
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(
      "<h1>Welcome to our API Homepage!</h1><p>Try visiting /api/user</p>"
    );

    // 路由2: 关于页 (GET /about)
  } else if (url === "/about" && method === "GET") {
    res.writeHead(200, { "Content-Type": "text/plain; charset=utf-8" });
    res.end("This is the about page.");

    // 【新增】路由3: 用户数据API (GET /api/user)
  } else if (url === "/api/user" && method === "GET") {
    // 戒律一：设置正确的Content-Type
    res.writeHead(200, { "Content-Type": "application/json" });

    // 准备要发送的JavaScript对象
    const userData = {
      name: "Alice",
      job: "Backend Developer",
      id: 1,
      skills: ["Node.js", "Databases", "API Design"],
    };

    // 戒律二：将JS对象转换为JSON字符串再发送
    res.end(JSON.stringify(userData));

    // 路由4: 处理所有其他未匹配的URL (404 Not Found)
  } else {
    res.writeHead(404, { "Content-Type": "text/html; charset=utf-8" });
    res.end("<h1>404 - Not Found</h1>");
  }
});

server.listen(PORT, () => {
  console.log(`API服务器已启动，正在监听 ${PORT} 端口...`);
  console.log(`请在浏览器中访问: http://localhost:${PORT}/api/user`);
});
