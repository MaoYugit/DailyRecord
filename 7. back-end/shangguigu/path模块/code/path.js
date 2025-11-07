const path = require("path");
const fs = require("fs");

// 不规范的绝对路径拼接
console.log(__dirname + "/index.html");

// 拼接规范的绝对路径
console.log(path.resolve(__dirname, "./index.html"));

// 获取操作系统路径的分隔符
console.log(path.sep);

// 解析路径并返回对象
console.log(__filename); // 文件的绝对路径
let str =
  "C:\\Users\\28745\\Desktop\\DailyRecord\\7. back-end\\shangguigu\\path模块\\code\\path.js";
console.log(path.parse(str));
// {
//  盘符        root: 'C:\\',
//  文件夹位置  dir: 'C:\\Users\\28745\\Desktop\\DailyRecord\\7. back-end\\shangguigu\\path模块\\code',
//  文件名      base: 'path.js',
//  文件扩展名  ext: '.js',
//  文件名      name: 'path'
// }

// 获取路径的基础名称
console.log(path.basename(str));

// 获取路径的目录名
console.log(path.dirname(str));

// 获得路径的扩展名
console.log(path.extname(__filename));
