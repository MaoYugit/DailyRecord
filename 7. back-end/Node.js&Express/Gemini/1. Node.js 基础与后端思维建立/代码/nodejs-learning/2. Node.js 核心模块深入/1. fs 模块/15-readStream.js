const fs = require("fs");

// 1. 创建一个可读流，连接到文件
const readStream = fs.createReadStream("../../../../文档/0. 第一阶段规划.md", {
  encoding: "utf-8", // 指定编码，chunk将是字符串而不是Buffer
  highWaterMark: 1 * 1024, // 每次读取1KB，默认是64KB
});

let chunkCount = 0;

// 2. 监听 'data' 事件
readStream.on("data", (chunk) => {
  chunkCount++;
  console.log(`--- 接收到第 ${chunkCount} 块数据 ---`);
  // console.log(chunk); // 如果文件大，这里会打印很多次
});
// 3. 监听 'end' 事件
readStream.on("end", () => {
  console.log("--- 数据全部读取完毕 ---");
  console.log(`总共接收了 ${chunkCount} 块数据。`);
});

// 4. 监听 'error' 事件
readStream.on("error", (err) => {
  console.error("读取流时发生错误:", err);
});
