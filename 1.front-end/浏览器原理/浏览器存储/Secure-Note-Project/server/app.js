const express = require("express");

// 导入 CORS 中间件
// CORS（跨源资源共享）用于处理跨域请求
// 浏览器默认禁止前端从不同域名/端口访问后端，需要此中间件来允许跨域
const cors = require("cors");

// 导入 Cookie 解析中间件
// 自动解析请求中的 Cookie，使其可通过 req.cookies 访问
// 当需要处理 Cookie 时必用
const cookieParser = require("cookie-parser");

const app = express();

const authRoutes = require("./routes/auth");

// CORS 配置
// 必须设置 origin 为前端地址，不能是 '*'，否则浏览器拒绝接收 Cookie
app.use(
  cors({
    origin: "http://localhost:5173", // 指定允许访问的后端的前端地址
    credentials: true, // 允许跨域请求携带 Cookie
  })
);

// 使用 Cookie 解析中间件
app.use(cookieParser());

// 启用 JSON 请求体解析中间件
app.use(express.json());

app.use("/auth", authRoutes); // 挂载路由，访问路径变成 /auth/login 或 /auth/logout

// 启动服务器
app.listen(3000, () => console.log("Server is running on port 3000"));
