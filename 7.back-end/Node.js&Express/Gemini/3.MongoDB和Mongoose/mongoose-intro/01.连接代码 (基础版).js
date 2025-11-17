// 引入 mongoose 库
const mongoose = require("mongoose");

// 定义数据库连接的异步函数
async function main() {
  try {
    // 1. 设置数据库 URI
    const dbURI = "mongodb://localhost:27017/mongoose-intro-db";

    // 2. 发起连接
    await mongoose.connect(dbURI);

    console.log("✅ MongoDB connection successful!");

    // 在这里执行数据库操作...
  } catch (error) {
    console.error("❌ MongoDB connection error:", error);
    process.exit(1); // 连接失败时，退出应用进程
  }
}

main();
