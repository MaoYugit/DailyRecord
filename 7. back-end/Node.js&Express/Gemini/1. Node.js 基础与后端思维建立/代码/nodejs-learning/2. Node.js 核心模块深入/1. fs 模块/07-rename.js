const fs = require("fs/promises");

async function manageFiles() {
  try {
    // 场景1: 重命名文件
    console.log("1. 将 welcome.txt 重命名为 new_welcome.txt");
    await fs.rename(
      "../../../data/welcome.txt",
      "../../../data/new_welcome.txt"
    );

    // 场景2: 移动文件
    console.log("2. 将 app.log 移动到 代码 文件夹");
    await fs.rename("../../../data/app.log", "../../../代码/app.log");

    console.log("🎉 文件管理操作成功！");
  } catch (err) {
    console.error("操作失败:", err);
  }
}

manageFiles();
