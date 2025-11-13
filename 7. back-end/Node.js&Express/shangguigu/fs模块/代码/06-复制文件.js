const fs = require("fs");
const process = require("process");

// 方法一
// 同步
// const data = fs.readFileSync("./资料/山风.mp4");

// fs.writeFileSync("./资料/山风-01.mp4", data);
// console.log(process.memoryUsage()); // rss  30179328

// 异步
fs.readFile("./资料/山风.mp4", (err, data) => {
  if (err) {
    console.log("读取失败");
    return;
  } else {
    fs.writeFile("./资料/山风-03.mp4", data, (err) => {
      if (err) {
        console.log("写入失败");
        return;
      } else {
        console.log("复制成功");
      }
    });
  }
});

// 方法二
// const rs = fs.createReadStream("./资料/山风.mp4");

// const ws = fs.createWriteStream("./资料/山风-02.mp4");

// rs.on("data", (chunk) => {
//   ws.write(chunk);
// });

// rs.on("end", () => {
//   console.log(process.memoryUsage()); // rss  32071680
// });

// 或者
// rs.pipe(ws)
