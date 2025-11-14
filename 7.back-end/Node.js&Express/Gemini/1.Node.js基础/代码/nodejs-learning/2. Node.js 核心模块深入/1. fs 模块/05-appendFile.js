const fs = require("fs/promises");

async function logActivity(message) {
  const timestamp = new Date().toISOString();
  const timestamp2 = new Date().toLocaleString();
  const logMessage = `${timestamp} - ${timestamp2} - ${message}\n`;

  try {
    await fs.appendFile("../../../data/app.log", logMessage, "utf8");
    console.log("日志已记录:", logMessage.trim());
  } catch (err) {
    console.error("记录日志时出错:", err);
  }
}

// 模拟两次不同的活动
logActivity("用户登录成功");
logActivity("用户上传了文件");
