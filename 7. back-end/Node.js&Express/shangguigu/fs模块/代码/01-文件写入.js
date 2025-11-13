// 1. 导入 fs 模块
const fs = require("fs");

// 2. 异步写入文件
fs.writeFile("./座右铭.txt", "三人行必有我师焉", (err) => {
  // 写入成功 err 是 null
  // 写入失败 err 是 错误对象
  if (err) {
    console.log("写入失败", err);
  } else {
    console.log("写入成功", err);
  }
});

console.log("1");

// 同步写入文件
fs.writeFileSync("./data.txt", "test");
console.log("2");
