// 1. 定义一个接口
interface User {
  id: number;
  name: string;
  age?: number; // 可选属性
}

// 2. 创建一个函数
function greet(user: User) {
  console.log(`Hello, ${user.name}!`);
  // 3. 测试 strictNullChecks (tsconfig中已开启)
  // 如果直接写 user.age.toFixed()，TS 会报错，因为 age 可能是 undefined
  if (user.age) {
    console.log(`You are ${user.age} years old.`);
  }
}

const me: User = {
  id: 1,
  name: "Leaner",
};

greet(me);

// 4. 测试 noUncheckedIndexedAccess
const scores: number[] = [85, 90, 78, 92];
const firstScore = scores[0];

// 在普通配置下，firstScore 是 number
// 在我们的配置下，firstScore 是 number | undefined，因为 TS 认为数组可能越界
console.log(firstScore?.toFixed());

let myName; // 不给类型，也不给初始值
myName = "Jack";
console.log(myName.toUpperCase());
