const fs = require("fs");

const rs = fs.createReadStream("./资料/山风.mp4");

rs.on("data", (chunk) => {
  console.log(chunk);
  console.log(chunk.length); // 每次64kb
});

rs.on("end", () => {
  console.log("读取完成");
});
