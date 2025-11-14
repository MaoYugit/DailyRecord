const http = require("http");

const PORT = 3002;

// 1. 我们的“模拟数据库”
const posts = [
  {
    id: 1,
    title: "My First Blog Post",
    content: "This is the content of my very first post!",
  },
  {
    id: 2,
    title: "Learning Node.js HTTP Module",
    content: "It is fundamental for building web applications.",
  },
  {
    id: 3,
    title: "Understanding Streams",
    content: "Streams are powerful for handling large data.",
  },
];

// 辅助函数：创建一个标准的JSON响应
function jsonResponse(res, statusCode, data) {
  res.writeHead(statusCode, { "Content-Type": "application/json" });
  res.end(JSON.stringify(data));
}

const server = http.createServer((req, res) => {
  const url = req.url;
  const method = req.method;

  console.log(`Request received: ${method} ${url}`);

  // --- 路由逻辑 ---

  // Endpoint 1: GET / - 欢迎页
  if (url === "/" && method === "GET") {
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(
      "<h1>Welcome to our Blog API</h1><p>Available endpoints: /posts, /posts/:id</p>"
    );

    // Endpoint 2: GET /posts - 获取所有文章
  } else if (url === "/posts" && method === "GET") {
    jsonResponse(res, 200, posts);

    // Endpoint 3 (挑战): GET /posts/:id - 获取单篇文章
  } else if (url.startsWith("/posts/") && method === "GET") {
    const urlParts = url.split("/");
    const postIdStr = urlParts[2];
    const postId = parseInt(postIdStr, 10);

    const post = posts.find((p) => p.id === postId);

    if (post) {
      jsonResponse(res, 200, post);
    } else {
      jsonResponse(res, 404, { error: `Post with ID ${postIdStr} not found` });
    }
  } else {
    jsonResponse(res, 404, { error: "Endpoint not found" });
  }
});

server.listen(PORT, () => {
  console.log(`Blog API server is running on port ${PORT}`);
  console.log(`Try accessing:`);
  console.log(`- http://localhost:${PORT}/posts`);
  console.log(`- http://localhost:${PORT}/posts/2`);
  console.log(`- http://localhost:${PORT}/posts/99`); // 一个不存在的ID
});
