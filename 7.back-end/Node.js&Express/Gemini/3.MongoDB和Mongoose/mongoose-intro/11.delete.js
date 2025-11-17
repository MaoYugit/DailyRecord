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

  // --- 读取 (Read) ---
  console.log("\n--- 🔍 READING ---");
  // 1. Model.find(): 查找所有文档
  const allUsers = await User.find({}); // {} 表示无任何过滤条件
  console.log(`\nFound ${allUsers.length} total users.`);

  // 2. Model.find() with filters: 查找年龄大于 30 的用户
  const seniorUsers = await User.find({ age: { $gt: 30 } });
  console.log(
    `Found ${seniorUsers.length} users older than 30:`,
    seniorUsers.map((u) => u.username)
  );

  // 3. Model.findOne(): 查找第一个匹配的用户
  const eve = await User.findOne({ username: "Eve" });
  console.log("\nFound user Eve:", eve ? eve.email : "Not found");

  // 4. Model.findById(): 通过 ID 查找
  // 假设我们拿到了 Eve 的 ID
  const eveById = await User.findById(eve._id);
  console.log("Found user by ID:", eveById ? eveById.username : "Not found");

  // --- 更新 (Update) ---
  console.log("\n--- ✏️ UPDATING ---");

  // 1. Model.updateOne(): 更新单个文档
  // 将用户 'Frank' 的年龄增加 1
  const updateResult = await User.updateOne(
    { username: "Frank" },
    { $inc: { age: 1 } } // 使用 $inc 原子操作符
  );
  console.log(
    `\nUpdated Frank. Matched: ${updateResult.matchedCount}, Modified: ${updateResult.modifiedCount}`
  );

  // 2. Model.updateMany(): 更新多个文档
  // 给所有用户添加一个 lastSeen 字段
  const updateManyResult = await User.updateMany(
    {}, // 空过滤器，匹配所有文档
    { $set: { lastSeen: new Date() } }
  );
  console.log(`Updated all ${updateManyResult.modifiedCount} users.`);

  // 3. Model.findOneAndUpdate(): 查找并更新
  // 找到 Eve 并将她的角色更新为 'admin'，并返回更新后的文档
  const updatedEve = await User.findOneAndUpdate(
    { username: "Eve" },
    { $set: { role: "admin" } },
    { new: true, runValidators: true } // { new: true } 确保返回的是更新后的文档
    // { runValidators: true } 强制执行 Schema 验证
  );
  console.log("\nFound and updated Eve:", updatedEve);

  // --- 删除 (Delete) ---
  console.log("\n--- 🗑️ DELETING ---");

  // 1. Model.deleteOne(): 删除单个文档
  const deleteResult = await User.deleteOne({ username: "Chris_Save" });
  console.log(`\nDeleted Chris_Save. Count: ${deleteResult.deletedCount}`);

  // 2. Model.deleteMany(): 删除所有年龄小于 30 的用户
  const deleteManyResult = await User.deleteMany({ age: { $lt: 30 } });
  console.log(
    `Deleted users younger than 30. Count: ${deleteManyResult.deletedCount}`
  );

  // 3. Model.findByIdAndDelete(): 查找并删除
  const frankDoc = await User.findOne({ username: "Frank" });
  if (frankDoc) {
    const deletedFrank = await User.findByIdAndDelete(frankDoc._id);
    console.log("\nFound and deleted Frank:", deletedFrank.username);
  }

  // 3. 关闭连接
  await mongoose.connection.close();
  console.log("👋 Database connection closed.");
}

main().catch((err) => console.error("An error occurred:", err));
