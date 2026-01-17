// src/index.js
// 项目的入口文件

// React 必要的两个核心包
import React from "react";
import ReactDOM from "react-dom/client";

// 引入根组件
import App from "./App";

// 把 App 组件渲染到 id 为 root 的 dom 节点上
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
