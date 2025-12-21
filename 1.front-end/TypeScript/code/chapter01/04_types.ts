export {};

let a: object;
a = {};

a = function () {};

// {} 用来指定一个对象的结构
// 在属性名后面加上 ? 表示该属性是可选的
// 语法: { key1: value1, key2?: value2, ... }
let b: { name: string; age?: number };

// b = {}; // 报错 因为没有提供 name 属性
b = { name: "John" }; // 正确
b = { name: "John", age: 30 }; // 正确

// 语法: { [key: type]: value }
// 已经设置了属性，但是之后可以添加任意数量的属性，此时后面必须使用 any 或者和前面已经设置的类型保持一致
let c: { name: string; [key: string]: any };
c = { name: "John", a: 1, b: 2 };
