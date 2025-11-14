const fs = require("fs");

console.log("1. 老板：管理员，去把 welcome.txt 找来。");

fs.readFile("../../../data/welcome.txt", "utf8", (err, data) => {
  if (err) {
    console.error("出错了！管理员报告：", err);
    return;
  }
  console.log("3. 管理员打来电话：老板，文件内容是:", data);
});

console.log("2. 老板：指令已下达，我先处理一下其他邮件...");
