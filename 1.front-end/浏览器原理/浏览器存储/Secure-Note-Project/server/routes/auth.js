const express = require("express");
const router = express.Router();

// 模拟数据库里的用户数据
const MOCK_USER = {
  username: "admin",
  password: "123",
};

// 登录接口 POST /auth/login
router.post("/login", (req, res) => {
  const { username, password } = req.body;

  // 简单验证用户名和密码
  if (username === MOCK_USER.username && password === MOCK_USER.password) {
    res.cookie("authToken", "secret-token_xyz_987", {
      httpOnly: true, // 仅允许通过 HTTP 请求访问，禁止前端 JS 访问，防止 XSS 攻击
      secure: false, // 开发环境下设为 false，生产环境下应设为 true，确保通过 HTTPS 传输 Cookie
      maxAge: 24 * 60 * 60 * 1000, // Cookie 有效期为 1 天
      sameSite: "Lax", // 防止 CSRF 攻击，同时允许从前端地址发起的顶层导航请求携带 Cookie
    });

    // 设置一个非 HttpOnly 的 Cookie (用于对比)
    // 比如记录用户的分组，前端可能需要读取用来做 AB 测试
    res.cookie("user_group", "A", {
      httpOnly: false, // 允许 JS 读取
      maxAge: 1000 * 60 * 60,
    });

    return res.json({ success: true, message: "登录成功" });
  } else {
    return res.status(401).json({ success: false, message: "账号或密码错误" });
  }
});

// 登出接口 POST /auth/logout
router.post("/logout", (req, res) => {
  // 清除 Cookie
  res.clearCookie("auth_token");
  res.clearCookie("user_group");
  res.json({ success: true, message: "已退出登录" });
});

module.exports = router;
