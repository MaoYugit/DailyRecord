const express = require("express");

// 1. 创建一个新的路由实例
const router = express.Router();

// 2. 在 router 实例上定义路由
// 注意：这里的 '/' 实际上是相对于它被挂载的路径
router.get("/", (req, res) => {
  res.send("获取所有用户列表");
});

router.get("/:id", (req, res) => {
  res.send(`获取 ID 为 ${req.params.id} 的用户`);
});

module.exports = router;
