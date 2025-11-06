const fs = require("fs");

// 重命名

// fs.rename("./data.txt", "./data-01.txt", (err) => {
//   if (err) {
//     console.log("fail");
//     return;
//   }
//   console.log("success");
// });

// 移动
fs.rename("./data-01.txt", "./资料/data-01.txt", (err) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log("success");
});
