const path = require("path");

console.log("当前系统的路径分隔符 (path.sep):", path.sep);
const myPath = "home\\user\\project\\index.js";
const pathSegments = myPath.split(path.sep);
console.log("路径被分隔符拆分后:", pathSegments);
