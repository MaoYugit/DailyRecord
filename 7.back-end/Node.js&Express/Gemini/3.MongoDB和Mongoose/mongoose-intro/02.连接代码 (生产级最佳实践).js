const mongoose = require("mongoose");

// 1. 定义数据库 URI
const dbURI = "mongodb://localhost:27017/mongoose-intro-db";

// 2. 发起连接
mongoose.connect(dbURI);

// 3. 获取连接对象
const db = mongoose.connection;

// 4. 监听连接事件
// 当连接出错时
db.on("error", (err) => {
  console.error("❌ Mongoose connection error:", err);
});

// 当连接成功时，这个事件只会触发一次
db.once("open", () => {
  console.log("✅ Mongoose connection successful!");
});

// 当断开连接时
db.on("disconnected", () => {
  console.log("ℹ️ Mongoose disconnected.");
});

// 优雅地关闭连接
// 当 Node.js 进程接收到关闭信号时（例如 Ctrl+C），我们手动关闭数据库连接
process.on("SIGINT", async () => {
  await mongoose.connection.close();
  console.log("👋 Mongoose connection closed due to app termination.");
  process.exit(0);
});
