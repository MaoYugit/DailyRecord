let a: any;
let b: string;
let c: unknown;

a = 123;
c = "123";
b = a; // 不会报错

// b = c; // 报错 即使 c 也是字符串，但是他的类型是 unknown

// 必须
if (typeof c == "string") {
  b = c; // 有了类型判断 不报错
}

// 或者使用类型断言
b = c as string; // 类型断言
b = <string>c; // 也可以使用尖括号语法进行类型断言
