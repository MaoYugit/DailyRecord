const fs = require("fs/promises");

async function main() {
  console.log("1. 老板：管理员，去把 welcome.txt 找来。");

  try {
    const data = await fs.readFile("../../../data/welcome.txt", "utf8");
    console.log("3. 智能助理报告：老板，文件内容是:", data);
  } catch (err) {
    console.error("出错了！智能助理报告：", err);
  }
}
main();
console.log("2. 老板：指令已下达，程序继续运行..."); // 这一行会先于 main 函数内部的 await 之后的内容
