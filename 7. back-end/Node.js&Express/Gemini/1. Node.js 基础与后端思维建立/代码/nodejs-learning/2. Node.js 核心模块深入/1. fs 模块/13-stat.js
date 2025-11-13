const fs = require("fs/promises");

async function inspectFile(filePath) {
  try {
    const stats = await fs.stat(filePath);
    console.log(`--- 属性报告: ${filePath} ---`);
    console.log(`是文件吗? ${stats.isFile()}`);
    console.log(`是目录吗? ${stats.isDirectory()}`);
    console.log(`文件大小: ${stats.size} 字节`);
    console.log(`创建时间: ${stats.birthtime.toLocaleString()}`); // .toLocaleString() 格式化时间
    console.log(`最后修改: ${stats.mtime.toLocaleString()}`);
    console.log("--------------------------\n");
  } catch (err) {
    if (err.code === "ENOENT") {
      console.error(`错误: 路径 ${filePath} 不存在。`);
    } else {
      console.error("获取属性时出错:", err);
    }
  }
}

inspectFile("./00-readFile-callback-style.js");
