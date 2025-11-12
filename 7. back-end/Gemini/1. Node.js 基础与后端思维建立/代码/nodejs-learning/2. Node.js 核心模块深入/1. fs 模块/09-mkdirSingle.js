const fs = require("fs/promises");

async function createSingleDirectory() {
  const dirPath = "./my-new-folder";
  try {
    console.log(`准备创建目录: ${dirPath}`);
    await fs.mkdir(dirPath);
    console.log("🎉 单层目录创建成功！");
  } catch (err) {
    if (err.code === "EEXIST") {
      console.log("目录已存在，无需创建。");
    } else {
      console.error("创建目录时出错:", err);
    }
  }
}

createSingleDirectory();
