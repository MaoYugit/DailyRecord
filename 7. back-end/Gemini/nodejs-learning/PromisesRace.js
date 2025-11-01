function fetchFrom(url, delay) {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(`Data from ${url}`);
    }, delay);
  });
}

const fastServer = fetchFrom("server-A", 100);
const slowServer = fetchFrom("server-B", 500);

Promise.race([fastServer, slowServer])
  .then((winner) => {
    console.log("最快的结果是：", winner);
  })
  .catch((err) => {
    console.log("最快的那个请求失败：", err);
  });
