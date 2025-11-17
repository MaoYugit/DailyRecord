const mongoose = require("mongoose");
const { Schema } = mongoose; // 解构 Schema

// 创建一个新的 Schema 实例
const userSchema = new Schema({
  // ------------------ 常用数据类型 ------------------

  // 1. 字符串 (String)
  username: String,
  email: String,

  // 2. 数字 (Number)
  age: Number,

  // 3. 布尔值 (Boolean)
  isActive: Boolean,

  // 4. 日期 (Date)
  registeredAt: Date,

  // 5. 对象ID (ObjectId)，用于关联其他模型
  // 'ref' 告诉 Mongoose 这个 ID 引用的是 'Post' 模型
  posts: [{ type: Schema.Types.ObjectId, ref: "Post" }],

  // 6. 数组 (Array)
  tags: [String], // 一个字符串数组
  profiles: [{ name: String, url: String }], // 一个对象数组

  // 7. 混合类型 (Mixed) - 慎用！
  // 允许存储任何类型的数据，放弃了 Mongoose 的类型检查和验证
  // 适用于高度不确定的数据结构
  metadata: Schema.Types.Mixed,

  // 8. Buffer - 用于存储二进制数据
  profilePicture: Buffer,
});
