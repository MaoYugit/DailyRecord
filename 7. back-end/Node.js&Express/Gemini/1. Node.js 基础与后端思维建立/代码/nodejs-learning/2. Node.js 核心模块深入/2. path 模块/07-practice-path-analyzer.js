const path = require("path");

/**
 * 分析一个给定的文件路径，并返回一个包含详细信息的对象。
 * @param {string} filePath - 要分析的文件路径 (可以是相对或绝对路径)
 * @returns {object} - 包含路径各部分信息的对象
 */
function analyzePath(filePath) {
  // 1. 解析出绝对路径，确保我们的分析有一个统一的基准
  const absolutePath = path.resolve(filePath);

  // 2. 使用路径解析器“三件套”来拆解路径
  const dirname = path.dirname(absolutePath); // 文件夹目录
  const extname = path.extname(absolutePath); // 扩展名
  const basename = path.basename(absolutePath); // 文件名 + 扩展名

  // 3. 组合使用 basename 和 extname 获取纯文件名
  const filenameWithoutExt = path.basename(absolutePath, extname);

  // 4. 判断原始路径是否为绝对路径
  const isAbsolute = path.isAbsolute(filePath);

  // 5. 将所有分析结果组装成一个对象并返回
  return {
    原始输入_originalPath: filePath,
    绝对路径_fullAbsolutePath: absolutePath,
    文件夹目录_directory: dirname,
    文件名_fileName: basename,
    纯文件名_fileNameOnly: filenameWithoutExt,
    扩展名_extension: extname,
    是否是绝对路径_isAbsolute: isAbsolute,
  };
}

// --- 以下是测试部分 ---

console.log("--- 测试案例 1: 一个相对路径 ---");
// 使用 path.join 来构建一个可靠的、跨平台的相对路径
const relativeTestPath = path.join("src", "components", "Button.jsx");
const result1 = analyzePath(relativeTestPath);
console.log(result1);

console.log("\n--- 测试案例 2: 一个绝对路径 ---");
const absoluteTestPath = "/var/log/nginx/access.log";
const result2 = analyzePath(absoluteTestPath);
console.log(result2);

console.log("\n--- 测试案例 3: 使用 __dirname ---");
// 使用 __dirname 来构建一个相对于当前文件的路径
const scriptRelativePath = path.join(__dirname, "..", "package.json");
const result3 = analyzePath(scriptRelativePath);
console.log(result3);
