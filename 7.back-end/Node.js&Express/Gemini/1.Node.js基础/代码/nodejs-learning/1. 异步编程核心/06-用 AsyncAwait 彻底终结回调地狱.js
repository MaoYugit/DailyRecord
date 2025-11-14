const fs = require("fs/promises");

async function main() {
  try {
    console.log("begin...");
    const path = await fs.readFile("../../data/path.txt", "utf8");

    console.log("content...");
    const content = await fs.readFile(path.trim(), "utf8");

    console.log("write");
    await fs.writeFile("../../data/new_content.text", content);

    console.log("finish");
  } catch (err) {
    console.log(fail);
  }
}

main();
