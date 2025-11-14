const path = require("path");

console.log("--- 1. path.join 演示 ---");

// 基础拼接 (在你的系统上观察分隔符是 / 还是 \)
const fullPath = path.join("users", "jane", "documents", "report.pdf");
console.log("基础拼接:", fullPath);

// 规范化演示
const messyPath = path.join(
  "/public",
  "assets",
  "../",
  "./images",
  "background.jpg/"
);
console.log("规范化后:", messyPath); // '..' 和 'assets' 相互抵消

// 结合 __dirname (核心用法)
// __dirname 提供了绝对可靠的起点
const configPath = path.join(__dirname, "config", "app-settings.json");
console.log("配置文件绝对路径:", configPath);
