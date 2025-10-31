const fs = require("fs");

fs.appendFile("./座右铭.txt", "\r\n未来的你会感谢现在奋斗的自己", (err) => {
  // 写入成功 err 是 null
  // 写入失败 err 是 错误对象
  if (err) {
    console.log("写入失败", err);
  } else {
    console.log("写入成功", err);
  }
});
