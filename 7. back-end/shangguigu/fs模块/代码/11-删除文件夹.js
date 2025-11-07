const fs = require("fs");

// 只能删除空文件夹
// fs.rmdir("./创建文件夹测试", (err) => {
//   if (err) {
//     console.log("fail");
//     return;
//   }
//   console.log("success");
// });

// fs.rmdir("./a/b/c", { recursive: true }, (err) => {
//   if (err) {
//     console.log("fail", err);
//     return;
//   }
//   console.log("success");
// });

fs.rm("./a", { recursive: true }, (err) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log("success");
});
