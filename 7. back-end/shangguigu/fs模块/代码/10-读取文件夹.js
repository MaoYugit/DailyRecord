const fs = require("fs");

fs.readdir("./", (err, data) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log(data);
});
