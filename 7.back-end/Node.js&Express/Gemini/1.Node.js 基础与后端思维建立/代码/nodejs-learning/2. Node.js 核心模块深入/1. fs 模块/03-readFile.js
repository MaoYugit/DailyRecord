const fs = require("fs/promises");

async function readMyFile() {
  try {
    const data = await fs.readFile("../../../data/message.txt", "utf8");
    const data2 = await fs.readFile("../../../data/message.txt");
    console.log("文件内容 (字符串):");
    console.log(data);
    console.log("文件内容 (Buffer):");
    console.log(data2);
    console.log("Buffer 转换成字符串:");
    console.log(data2.toString("utf8"));
  } catch (err) {
    console.error("读取文件出错:", err);
  }
}
readMyFile();
