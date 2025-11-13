const path = require("path");

// 1. __dirname
console.log("当前文件所在目录 (__dirname):", __dirname);

// 核心用法：构建一个指向同级目录下 public/index.html 的绝对路径
const publicHtmlPath = path.join(__dirname, "public", "index.html");
console.log("构建的HTML路径:", publicHtmlPath);
