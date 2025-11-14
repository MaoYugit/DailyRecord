// 1. 使用require加载math模块。'./'表示相对路径
// mathUtils变量现在的值，就是math.js中module.exports的那个对象
const mathUtils = require("./00-math.js");

// 演示模块缓存：再次require
const mathUtilsAgain = require("./00-math.js");

// 比较一下，看看它们是不是同一个对象
console.log("两次require是否是同一个对象:", mathUtils === mathUtilsAgain); // -> true

// 2. 使用导入的模块
const sum = mathUtils.add(5, 3);
const difference = mathUtils.subtract(10, 4);

console.log(`5 + 3 = ${sum}`);
console.log(`10 - 4 = ${difference}`);

// 尝试访问math.js中的私有变量PI
console.log(mathUtils.PI);
