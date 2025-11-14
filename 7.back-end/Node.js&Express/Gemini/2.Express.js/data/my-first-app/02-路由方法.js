const express = require("express");
const app = express();
const port = 9000;

// GET 方法：获取用户列表
app.get("/users", (req, res) => {
  res.send("GET request to the /users endpoint");
});

// POST 方法：创建一个新用户
app.post("/users", (req, res) => {
  res.send("POST request to the /users endpoint");
});

// PUT 方法：更新指定 ID 的用户
app.put("/users/:id", (req, res) => {
  res.send(`PUT request to update user with id: ${req.params.id}`);
});

// DELETE 方法：删除指定 ID 的用户
app.delete("/users/:id", (req, res) => {
  res.send(`DELETE request to delete user with id: ${req.params.id}`);
});

app.listen(port, () => {
  console.log(`服务器正在监听 http://localhost:${port}`);
});
