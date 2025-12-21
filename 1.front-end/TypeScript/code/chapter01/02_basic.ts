// 以后 a 的值只能是 number
let a: number;

// a = "hello"; // 报错 不能将类型“string”分配给类型“number”。

let b: string;

b = "hello";

let c: boolean = false;

let s = false; // 定义并赋值有自动类型判断，不用继续 : boolean

function sum(a: number, b: number): number {
  return a + b;
}
sum(1, 2); // 正确
// sum(1, "2"); // 报错 不能将类型“string”分配给类型“number”。
export {};
