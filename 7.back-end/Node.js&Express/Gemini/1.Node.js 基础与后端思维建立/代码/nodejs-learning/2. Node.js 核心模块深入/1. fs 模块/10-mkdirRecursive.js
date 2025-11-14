const fs = require("fs/promises");

async function createProjectStructure() {
  const projectPath = "./my-awesome-project/src/components";
  try {
    console.log(`准备创建项目结构: ${projectPath}`);
    // recursive: true 会自动创建 所有目录
    await fs.mkdir(projectPath, { recursive: true });
    console.log("🎉 项目目录结构创建成功！");
  } catch (err) {
    console.error("创建项目结构时出错:", err);
  }
}
createProjectStructure();
