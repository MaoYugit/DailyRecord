const fs = require("fs");

// fs.unlink("./观书有感.txt", (err) => {
//   if (err) {
//     console.log("fail");
//     return;
//   }
//   console.log("success");
// });

fs.rm("./座右铭.txt", (err) => {
  if (err) {
    console.log("fail");
    return;
  }
  console.log("success");
});
