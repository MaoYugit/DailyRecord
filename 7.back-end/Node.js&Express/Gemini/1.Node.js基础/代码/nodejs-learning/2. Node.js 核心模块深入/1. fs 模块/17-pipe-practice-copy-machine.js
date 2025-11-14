const fs = require("fs");

const soursePath = "../../../../data/山风.mp4";
const destinationPath = "../../../../data/山风_copy.mp4";

const readStream = fs.createReadStream(soursePath);

const writeStream = fs.createWriteStream(destinationPath);

readStream.on("err", (err) => {
  console.error("读取流发生错误", err);
});

writeStream.on("err", (err) => {
  console.error("写入流发生错误", err);
});

writeStream.on("finish", () => {
  console.log("🎉 文件复制成功！");
});

console.log("begin");

readStream.pipe(writeStream);
