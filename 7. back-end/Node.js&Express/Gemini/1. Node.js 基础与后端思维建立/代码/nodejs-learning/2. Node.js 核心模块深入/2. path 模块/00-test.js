const path = require("path");

const projectRoot = "/Users/my-macbook/my-project"; // 在你的Mac上
const imageFolder = "public/images";
const imageName = "avatar.png";

// 【错误示范】手动使用字符串拼接
const imagePath = projectRoot + "/" + imageFolder + "/" + imageName;

console.log("构建出的图片路径是:", imagePath);
// 在Mac或Linux上输出: /Users/my-macbook/my-project/public/images/avatar.png
// 这个路径看起来很完美，程序运行正常！

// 【正确示范】使用 path.join()
const imagePath_right = path.join(projectRoot, imageFolder, imageName);

console.log("由path模块构建的路径是:", imagePath_right);
