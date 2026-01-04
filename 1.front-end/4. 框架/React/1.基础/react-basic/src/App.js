// src/App.js
// 根组件 App
// App -> index.js -> public/index.html(root)

import { useState } from "react";

const count = 100;
function getName() {
  return "React";
}
const my_list = [
  { id: 1, name: "jack" },
  { id: 2, name: "tom" },
  { id: 3, name: "smith" },
];
const isLoggedIn = false;

const articleType = 0;

function getArticleType(type) {
  if (type === 0) {
    return <div>无图</div>;
  } else if (type === 1) {
    return <div>单图</div>;
  } else {
    return <div>三图</div>;
  }
}

function Button() {
  return <button>按钮</button>;
}
function App() {
  function handleClick(e) {
    console.log("按钮被点击了", e);
  }
  function handleClick2(name) {
    console.log("按钮被点击了", name);
  }
  const [count, setCount] = useState(0);
  const handleClick3 = () => {
    setCount(count + 1);
  };
  return (
    <div className="App">
      <h1>Hello, World!</h1>
      {"this is my first React App!"}
      {count}
      {getName()}
      {new Date().toLocaleDateString()}
      <div style={{ color: "red" }}>this is div</div>
      <ul>
        {my_list.map((item) => (
          <li key={item.id}>{item.name}</li>
        ))}
      </ul>
      {isLoggedIn && <span>登录成功</span>}
      {isLoggedIn ? <span>已登录</span> : <span>未登录</span>}
      {getArticleType(articleType)}
      <button onClick={handleClick}>点击我</button>
      <button onClick={() => handleClick2("jack")}>点击我</button>
      <Button />
      <button onClick={handleClick3}>{count}</button>
    </div>
  );
}

export default App;
