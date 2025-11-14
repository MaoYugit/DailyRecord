// stream-writer.js
const fs = require("fs");

// 1. 创建一个可写流，连接到 destination.txt 文件
const writeStream = fs.createWriteStream("./destination.txt", {
  encoding: "utf8", // 指定写入的编码
});

console.log("--- 开始向文件写入数据 ---");

// 2. 使用 .write() 方法向流中写入数据，可以调用多次
writeStream.write("这是第一块数据。\n");
writeStream.write("Node.js的流操作非常高效，特别适合处理大文件。\n");
writeStream.write("即使是很大的数据量，内存占用也可以保持很低。\n");
writeStream.write("这是最后一块数据。\n");

// 3. 使用 .end() 方法，表示没有更多数据要写入了
// 这一步是必须的，否则文件将一直处于打开状态
writeStream.end(() => {
  console.log("--- 所有数据已写入缓冲区，正在关闭文件... ---");
});

// 4. 监听 'finish' 事件，当所有数据都成功写入文件后触发
writeStream.on("finish", () => {
  console.log("--- 数据写入操作全部完成 ---");
  // 在这里可以安全地进行后续操作，比如读取刚刚写入的文件
});

// 5. 监听 'error' 事件
writeStream.on("error", (err) => {
  console.error("写入流时发生错误:", err);
});
