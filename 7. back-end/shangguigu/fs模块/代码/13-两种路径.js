const fs = require("fs");

// 相对路径
// fs.writeFileSync("./index.html", "love");
// fs.writeFileSync("index.html", "you");

// 绝对路径
// fs.writeFileSync(
//   "C:/Users/28745/Desktop/DailyRecord/7. back-end/shangguigu/index111.html",
//   "love"
// );
fs.writeFileSync("/index11.html", "you"); // 当前盘符根目录 我们直接在C盘 无权限
