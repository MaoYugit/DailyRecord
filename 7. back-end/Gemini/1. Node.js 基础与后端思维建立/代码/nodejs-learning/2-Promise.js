const fs = require("fs/promises");

console.log("begin ...");

fs.readFile("../../data/path.txt", "utf8")
  .then((path) => {
    console.log("success1", path);
    return fs.readFile(path.trim(), "utf8");
  })
  .then((content) => {
    console.log("success2", content);
    return fs.writeFile("../../data/new_content.text", content);
  })
  .then(() => {
    console.log("success!");
  })
  .catch((err) => {
    console.error("error", err);
  });

console.log("end ...");
