const mongoose = require("mongoose");
const User = require("./06.userModel");

async function run() {
  // 连接数据库
  await mongoose.connect("mongodb://localhost:27017/mongoose-intro-db");
  console.log("✅ Database connected!");

  // --- 场景1: 创建一个符合验证规则的用户 ---
  try {
    const newUser = new User({
      // new User() 创建一个文档实例
      username: "  Alice123  ", // 包含空格，会被 trim
      email: "ALICE@example.COM", // 大写，会被 lowercase
      age: 25,
      role: "admin",
    });
    const savedUser = await newUser.save(); // .save() 触发验证并存入数据库
    console.log("✅ 用户创建成功:", savedUser);
  } catch (error) {
    console.error("❌ 创建失败:", error.message);
  }

  // --- 场景2: 创建一个不符合验证规则的用户 ---
  try {
    const invalidUser = new User({
      username: "Bo", // 太短了
      email: "bob-at-example.com", // 格式错误
      age: 16, // 年龄太小
    });
    await invalidUser.save();
  } catch (error) {
    // Mongoose 会抛出一个 ValidationError
    console.error("\n❌ 验证失败! 错误详情:");
    // error.errors 是一个包含所有验证失败字段信息的对象
    for (let field in error.errors) {
      console.log(`  - ${error.errors[field].message}`);
    }
  }

  await mongoose.connection.close();
}

run();
