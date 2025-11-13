const path = require("path");

console.log("\n--- 路径解析器演示 ---");

const fullFilePath =
  "../../../nodejs-learning/1. 异步编程核心/00-FirstNodejs.js";

const fileName = path.basename(fullFilePath);
console.log("文件名 (basename):", fileName);

const directory = path.dirname(fullFilePath);
console.log("目录名 (dirname):", directory);

const extension = path.extname(fullFilePath);
console.log("扩展名 (extname):", extension);

// 组合使用：获取不带扩展名的文件名
const fileNameWithoutExt = path.basename(fullFilePath, extension);
console.log("无扩展名的文件名:", fileNameWithoutExt);
