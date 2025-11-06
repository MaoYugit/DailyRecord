const fs = require("fs");

fs.readFile("../../data/path.txt", "utf8", (err1, path) => {
  if (err1) {
    return console.error("fail1", err1);
  }
  fs.readFile(path.trim(), "utf8", (err2, content) => {
    if (err2) {
      return console.error("fail2", err2);
    }
    fs.writeFile("../../data/new_content.text", content, (err3) => {
      if (err3) {
        return console.error("fail3", err3);
      }
      console.log("success");
    });
  });
});
