const express = require("express");
const app = express();
const port = 9000;

// 定义一个包含 userId 参数的路由
app.get("/users/:userId", (req, res) => {
  // 通过 req.params.userId 访问这个动态值
  res.send(`正在获取 ID 为 ${req.params.userId} 的用户信息。`);
});

// 定义一个包含多个参数的路由
app.get("/users/:userId/books/:bookId", (req, res) => {
  const userId = req.params.userId;
  const bookId = req.params.bookId;
  res.send(`正在获取用户 ${userId} 的图书 ${bookId} 的信息。`);
});

app.listen(port, () => {
  console.log(`服务器正在监听 http://localhost:${port}`);
});
