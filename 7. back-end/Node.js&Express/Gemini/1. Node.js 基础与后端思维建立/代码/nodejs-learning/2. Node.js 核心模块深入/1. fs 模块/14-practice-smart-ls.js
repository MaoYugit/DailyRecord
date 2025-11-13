const fs = require("fs/promises");
const path = require("path");

async function listDirectoryDetails(dirPath) {
  try {
    console.log(`🔍 正在扫描目录: ${dirPath}\n`);
    // 1. 先读取目录，获取所有条目的名称列表
    const items = await fs.readdir(dirPath);
    // console.log(items);

    for (const item of items) {
      const fullPath = path.join(dirPath, item);
      //   console.log(fullPath);

      // 4. 获取该条目的 stat 对象
      const stats = await fs.stat(fullPath);
      //   console.log(stats);
      // 5. 判断类型并格式化输出
      if (stats.isDirectory()) {
        console.log(`[目录]${item}`);
      } else if (stats.isFile()) {
        console.log(`[文件] ${item} (${stats.size} 字节)`);
      }
    }
  } catch (err) {
    console.error(`处理目录时出错: ${err}`);
  }
}

listDirectoryDetails("./my-new-folder");
