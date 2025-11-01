async function sayHello() {
  return "Hello, World!";
}

console.log(sayHello());

sayHello().then((value) => {
  console.log(value);
});

async function getSomething() {
  return new Promise((resolve) => resolve("Something"));
}

console.log(getSomething());

async function throwError() {
  throw new Error("This is an error!");
}

throwError().catch((err) => {
  console.error(err.message);
});
