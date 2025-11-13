const fs = require("fs");

// 普通创建
// fs.mkdir("./创建文件夹测试", (err) => {
//   if (err) {
//     console.log("fail");
//     return;
//   }
//   console.log("success");
// });

// 递归创建
fs.mkdir("./a/b/c", { recursive: true }, (err) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log("success");
});
