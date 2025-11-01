const fs = require("fs/promises");

const p1 = fs.readFile("file1.txt", "utf8");
const p2 = fs.readFile("file2.txt", "utf8");
const p3 = fs.readFile("file3.txt", "utf8");

Promise.all([p1, p2, p3])
  .then((result) => {
    console.log("all finish: ", result);
  })
  .catch((err) => {
    console.err("fail", err);
  });
