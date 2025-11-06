const fs = require("fs/promises");

const p1 = fs.readFile("../../data/file1.txt", "utf8");
const p2 = fs.readFile("../../data/file2.txt", "utf8");
const p3 = fs.readFile("../../data/file3.txt", "utf8");

Promise.all([p1, p2, p3])
  .then((result) => {
    console.log("all finish: ", result);
  })
  .catch((err) => {
    console.error("fail", err);
  });
