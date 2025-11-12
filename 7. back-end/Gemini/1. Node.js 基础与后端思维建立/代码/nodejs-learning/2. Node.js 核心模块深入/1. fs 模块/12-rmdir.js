const fs = require("fs/promises");

async function cleanupProject() {
  const projectPath = "./my-awesome-project";
  try {
    console.log(`准备递归删除目录: ${projectPath}`);
    await fs.rm(projectPath, { recursive: true, force: true });
    console.log("🎉 项目目录已成功清理！");
  } catch (err) {
    console.error("清理项目时出错:", err);
  }
}
cleanupProject();
