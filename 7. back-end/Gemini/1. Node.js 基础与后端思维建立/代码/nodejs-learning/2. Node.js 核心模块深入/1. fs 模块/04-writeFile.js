const fs = require("fs/promises");

async function saveUserData() {
  const user = {
    name: "MaoYu",
    age: 24,
    course: ["Node.js", "Vue"],
  };

  const dataToWrite = JSON.stringify(user, null, 2);

  try {
    await fs.writeFile("../../../data/user-data.json", dataToWrite);
    console.log("🎉 用户数据成功保存到 user-data.json!");
  } catch (err) {
    console.error("写入文件时出错:", err);
  }
}
saveUserData();
