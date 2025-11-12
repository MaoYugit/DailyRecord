const fs = require("fs/promises");

async function cleanup() {
  const tempFilePath = "../../app.log";
  console.log(`准备删除临时文件: ${tempFilePath}`);
  try {
    await fs.unlink(tempFilePath);
    console.log("临时文件已成功删除。");
  } catch (err) {
    console.error("删除文件时出错:", err);
  }
}

cleanup();
