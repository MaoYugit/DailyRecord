const fs = require("fs/promises");

async function listSrcFiles() {
  const srcPath = "./my-awesome-project/src";
  try {
    console.log(`正在读取目录内容: ${srcPath}`);
    const items = await fs.readdir(srcPath);
    console.log("目录内容清单:");
    console.log(items);
  } catch (err) {
    console.error("读取目录时出错:", err);
  }
}

listSrcFiles();
