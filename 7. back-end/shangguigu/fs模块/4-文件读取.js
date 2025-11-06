const fs = require("fs");

fs.readFile("./观书有感.txt", (err, data) => {
  if (err) {
    console.log("读取失败", err);
  } else {
    console.log("读取成功\r\n", data.toString());
  }
});
