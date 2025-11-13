const fs = require("fs");

console.log("1. 老板：管理员，去把 welcome.txt 找来。");

try {
  const data = fs.readFileSync("../../../data/welcome.txt", "utf8");
  console.log("2. 管理员递上文件：老板，内容是:", data);
} catch (err) {
  console.error("出错了！", err);
}
console.log("3. 老板：好了，现在可以做下一件事了。");
