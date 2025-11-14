const express = require("express");
const app = express();
const port = 9000;

// 一个简单的日志中间件函数
const requestLogger = (req, res, next) => {
  console.log(
    `[${new Date().toLocaleString()}] 收到一个 ${
      req.method
    } 请求， 访问的路径是 ${req.url}`
  );
  // 非常重要：调用 next() 将请求传递给下一个处理程序
  next();
};

// 权限验证中间件
const adminAuth = (req, res, next) => {
  console.log("正在检查管理员权限...");
  // 这是一个伪代码，实际应用中会有复杂的逻辑
  const isAdmin = true;
  if (isAdmin) {
    console.log("权限通过，继续...");
    next(); // 权限通过，继续
  } else {
    res.status(403).send("禁止访问"); // 权限不通过，直接返回
  }
};

// 应用级中间件
// 在所有路由之前使用这个中间件
app.use(requestLogger);

// 应用级中间件
// 这个中间件只对 /admin 和其子路径（如 /admin/dashboard）生效
app.use("/admin", adminAuth);

app.get("/", (req, res) => {
  res.send("这是首页");
});

app.get("/users", (req, res) => {
  res.send("这是用户列表页");
});

app.get("/admin/dashboard", (req, res) => {
  res.send("欢迎来到管理员面板");
});

app.listen(port, () => {
  console.log(`服务器正在监听 http://localhost:${port}`);
});
