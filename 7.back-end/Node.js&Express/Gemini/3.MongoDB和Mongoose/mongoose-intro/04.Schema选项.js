const userSchema = new Schema(
  {
    username: String,
    email: String,
    password: { type: String, required: true }, // 假设有密码字段
    // ... 其他字段
  },
  {
    // ------------------ 常用 Schema 选项 ------------------

    // 1. timestamps: 自动管理创建和更新时间
    // 设置为 true，Mongoose 会自动添加 createdAt 和 updatedAt 两个字段
    timestamps: true,

    // 2. collection: 自定义集合名称
    // Mongoose 默认会将模型名转为复数并作为集合名 (e.g., 'User' -> 'users')
    // 你可以在这里强制指定一个名字
    collection: "system_users",

    // 3. toJSON / toObject: 自定义文档转换逻辑 (面试重点)
    // 当文档被转换为 JSON (如 res.json(user)) 或对象时触发
    // 常用语隐藏敏感信息，如密码
    toJSON: {
      transform: function (doc, ret) {
        delete ret.password; // 删除返回对象中的 password 字段
        delete ret.__v; // Mongoose 内部版本号，也可以去掉
        return ret;
      },
    },
  }
);
