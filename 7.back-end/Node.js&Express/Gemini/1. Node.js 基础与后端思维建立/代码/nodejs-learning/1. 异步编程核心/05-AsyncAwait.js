async function sayHello() {
  return "Hello, World!";
}

console.log("1", sayHello()); // Promise { 'Hello, World!' }

sayHello().then((value) => {
  console.log("2", value); // 会打印 'Hello, World!'
});

// 2. 如果返回一个Promise，那就返回那个Promise
async function getSomething() {
  return new Promise((resolve) => resolve("Something"));
}

console.log("3", getSomething());

async function throwError() {
  throw new Error("This is an error!");
}

throwError().catch((err) => {
  console.error("4", err.message); // 会打印 'This is an error!'
});
// throwError() 实际上返回的是 Promise.reject(new Error(...))

// Promise 的方式
function logContent() {
  fs.readFile("./content.txt", "utf8").then((content) => {
    console.log(content);
  });
}

// async/await 的方式
async function logContentAsync() {
  // 直接用一个变量接收结果，就像同步代码一样！
  const content = await fs.readFile("./content.txt", "utf8");
  console.log(content);
}
