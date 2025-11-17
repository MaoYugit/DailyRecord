const mongoose = require("mongoose");

// 1. 定义 Schema (从上面复制过来)
const userSchema = new mongoose.Schema(
  {
    username: {
      type: String,
      required: [true, "用户名是必填项"],
      unique: true,
      trim: true,
      minlength: [3, "用户名至少需要3个字符"],
    },
    email: {
      type: String,
      required: true,
      unique: true,
      lowercase: true,
      match: [/\S+@\S+\.\S+/, "无效的邮箱格式"],
    },
    age: {
      type: Number,
      min: [18, "用户必须年满18岁"],
    },
    role: {
      type: String,
      enum: ["user", "admin"],
      default: "user",
    },
  },
  { timestamps: true }
);

// 2. 基于 Schema 创建 Model
// mongoose.model('ModelName', schema)
// 第一个参数 'User' 是模型的单数名称。Mongoose 会自动查找名为 'users' 的集合。
const User = mongoose.model("User", userSchema);

// 3. 导出 Model
module.exports = User;
