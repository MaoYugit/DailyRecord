const fs = require("fs");

fs.stat("./资料/山风.mp4", (err, data) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log(data);
  console.log(data.isFile());
  console.log(data.isDirectory());
});
