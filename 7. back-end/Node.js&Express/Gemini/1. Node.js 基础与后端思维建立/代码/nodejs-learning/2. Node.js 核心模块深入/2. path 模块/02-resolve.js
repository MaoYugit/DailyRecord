const path = require("path");

console.log("\n--- path.resolve 演示 ---");
console.log("从CWD解析:", path.resolve("temp", "uploads", "avatar.png"));

// 假设你在 /my-project 目录下运行，输出: /my-project/temp/uploads/avatar.png

// 遇到绝对路径，左侧全部作废
const resolvedPath = path.resolve("/home/user", "/var/log", "system.log");
console.log("遇到绝对路径:", resolvedPath);
// 输出: /var/log/system.log
// 因为 /var/log 是绝对路径，/home/user 被完全忽略了

// 对比 join 和 resolve
console.log("Join对比:", path.join("/a", "/b", "c")); // 输出: /a/b/c
console.log("Resolve对比:", path.resolve("/a", "/b", "c")); // 输出: /b/c (因为/b是绝对路径)
console.log(
  "Resolve对比:",
  path.resolve("../1. fs 模块/00-readFile-callback-style.js")
);
console.log("Resolve对比:", path.resolve("00-readFile-callback-style.js"));
console.log("Resolve对比:", path.resolve("aaa"));
