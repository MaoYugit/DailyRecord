const fs = require("fs");

fs.readFile("./观书有感.txt", (err, data) => {
  if (err) {
    console.log("写入失败", err);
  } else {
    console.log("写入成功\r\n", data.toString());
  }
});
