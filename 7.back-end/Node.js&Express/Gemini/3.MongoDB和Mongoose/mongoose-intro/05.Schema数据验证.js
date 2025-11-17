const userSchema = new Schema(
  {
    username: {
      type: String,
      required: [true, "用户名是必填项"], // required: 必填
      unique: true, // unique: 唯一索引 (注意：这不是一个真正的 Mongoose 验证器)
      trim: true, // trim: 自动移除前后空格
      minlength: [3, "用户名至少需要3个字符"], // minlength / maxlength: 字符串长度
      maxlength: 20,
    },
    email: {
      type: String,
      required: true,
      unique: true,
      lowercase: true, // lowercase: 自动转为小写
      match: [/\S+@\S+\.\S+/, "无效的邮箱格式"], // match: 正则表达式匹配
    },
    age: {
      type: Number,
      min: [18, "用户必须年满18岁"], // min / max: 数字范围
      max: 120,
    },
    role: {
      type: String,
      enum: {
        // enum: 枚举，值必须是数组中的一个
        values: ["user", "admin", "editor"],
        message: "{VALUE} 不是一个有效的角色",
      },
      default: "user", // default: 默认值
    },
    password: {
      type: String,
      required: true,
      // 自定义验证器 (Custom Validator)
      validate: {
        validator: function (v) {
          // 至少8个字符，包含一个数字和一个字母
          return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(v);
        },
        message: (props) => `${props.value} 不是一个有效的密码！`,
      },
    },
  },
  { timestamps: true }
);
