const fs = require("fs");

const files = fs.readdirSync("./nodejs-learning");
console.log(files);

files.forEach((item) => {
  let data = item.split("-");
  console.log(data);
  let [num, name] = data;
  console.log(num, name);
  if (Number(num) < 10) {
    num = "0" + num;
  }
  let newName = num + "-" + name;
  console.log(newName);

  fs.renameSync(`./nodejs-learning/${item}`, `./nodejs-learning/${newName}`);
});
