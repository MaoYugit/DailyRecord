const express = require("express");
const app = express();
const port = 9000;

// 1. 导入用户路由模块
const usersRouter = require("./routes/users");

// 2. 使用 app.use() 来挂载路由模块
// 这行代码告诉 Express：所有以 /users 开头的请求，都应该由 usersRouter 来处理
app.use("/users", usersRouter);

app.get("/", (req, res) => {
  res.send("这是首页");
});

app.listen(port, () => {
  console.log(`服务器正在监听 http://localhost:${port}`);
});
