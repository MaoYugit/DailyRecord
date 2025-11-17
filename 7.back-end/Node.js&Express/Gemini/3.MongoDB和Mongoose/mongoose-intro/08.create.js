const mongoose = require("mongoose");
// 假设我们的 User 模型已经定义在 './models/userModel'
const User = require("./06.userModel");

async function main() {
  // 1. 连接数据库
  await mongoose.connect("mongodb://localhost:27017/mongoose-crud-db");
  console.log("✅ Database connected!");

  // 2. 清理环境：为保证每次演示结果一致，先删除所有用户
  await User.deleteMany({});
  console.log("🧹 Previous users cleared.");

  // ----------------------------------------------------
  // 👇 我们将在这里编写所有的 CRUD 操作代码
  // ----------------------------------------------------

  // --- 创建 (Create) ---
  console.log("\n--- 🚀 CREATING ---");

  // 方式一: new Model().save()
  const userFromSave = new User({
    username: "Chris_Save",
    email: "chris_save@example.com",
    age: 30,
  });
  // 在调用 .save() 之前，可以对 userFromSave 对象进行任何操作
  // 比如添加一些动态属性或执行一些逻辑
  // .save() 会触发 Schema 验证和 pre/post 'save' 中间件
  const savedDoc = await userFromSave.save();
  console.log("👤 User created via .save():", savedDoc.username);

  // 方式二: Model.create()
  // create() 内部也是调用了 .save()，所以同样会触发验证和中间件
  const createdDoc = await User.create({
    username: "Diana_Create",
    email: "diana_create@example.com",
    age: 28,
  });
  console.log("👤 User created via .create():", createdDoc.username);

  // Model.create() 还可以批量创建
  const createdDocs = await User.create([
    { username: "Eve", email: "eve@example.com", age: 35 },
    { username: "Frank", email: "frank@example.com", age: 42 },
  ]);
  console.log(`👥 Batch created ${createdDocs.length} users.`);

  // 3. 关闭连接
  await mongoose.connection.close();
  console.log("👋 Database connection closed.");
}

main().catch((err) => console.error("An error occurred:", err));
