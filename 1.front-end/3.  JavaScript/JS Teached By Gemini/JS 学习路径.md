
**学习阶段划分：**

1. **JavaScript 基础 (The Core Language)**
2. **DOM 操作与浏览器环境 (Client-Side JavaScript)**
3. **异步编程与现代特性 (Asynchronous & Modern JS)**
4. **面向对象与模块化 (OOP & Modules)**
5. **JavaScript 生态与进阶 (Ecosystem & Advanced Topics)**
6. **实践与深入 (Practice & Deep Dive)**

------



### **阶段一：JavaScript 基础**

- **1. JavaScript 简介**
  - JavaScript 是什么？（历史、用途、特点）
  - JavaScript 能做什么？（网页交互、服务器端、移动应用等）
  - 运行环境（浏览器控制台、Node.js）
  - 如何在 HTML 中引入 JavaScript（`<script>`标签内联和外链）
- **2. 基本语法**
  - 语句与注释
  - 变量声明 (var, let, const 的区别与使用场景)
  - 标识符命名规则
- **3. 数据类型**
  - **原始类型 (Primitive Types)**:
    - Number (包括 NaN, Infinity)
    - String (字符串方法：length, indexOf, slice, substring, toUpperCase, toLowerCase, trim, split, replace 等)
    - Boolean (true, false)
    - Undefined
    - Null
    - Symbol (ES6+)
    - BigInt (ES2020+)
  - **引用类型 (Object Type)**:
    - Object (Plain Objects)
    - Array
    - Function
    - Date
    - RegExp (正则表达式)
  - typeof 和 instanceof 操作符
  - 类型转换（显式与隐式转换）
- **4. 操作符**
  - 算术操作符 (+, -, *, /, %, ++, --, **)
  - 赋值操作符 (=, +=, -=, *=, /=, %=)
  - 比较操作符  (`==`, `===`, `!=`, `!==`, `>`, `<`, `>=`, `<=`)
  - 逻辑操作符 (&&, ||, !)
  - 位操作符 (了解即可)
  - 条件（三元）操作符 (condition ? expr1 : expr2)
  - 逗号操作符
  - 其他操作符 (void, in, delete 等)
- **5. 控制流程语句**
  - 条件语句 (if...else, switch)
  - 循环语句 (for, while, do...while, for...in, for...of)
  - 跳转语句 (break, continue, return)
  - 标签语句 (label)
- **6. 函数 (Functions)**
  - 函数声明与函数表达式
  - 函数参数（默认参数、剩余参数 ...rest）
  - 返回值 (return)
  - 函数作用域（全局作用域、函数作用域、块级作用域 ES6+）
  - 词法作用域 (Lexical Scoping)
  - 闭包 (Closures)
  - 高阶函数 (Higher-Order Functions)
  - 立即执行函数表达式 (IIFE)
  - 箭头函数 (Arrow Functions ES6+)
  - this 关键字（全局、函数内、构造函数内、对象方法内、箭头函数内、call, apply, bind）
- **7. 对象 (Objects)**
  - 对象字面量创建
  - new Object() 创建
  - 属性的访问（点表示法、方括号表示法）
  - 属性的添加、修改、删除
  - 对象的方法
  - 遍历对象属性 (for...in, Object.keys, Object.values, Object.entries)
  - 属性描述符 (Object.defineProperty, Object.getOwnPropertyDescriptor)
  - Getter 和 Setter
- **8. 数组 (Arrays)**
  - 数组字面量创建
  - new Array() 创建
  - 数组元素的访问、修改
  - 数组长度 (length)
  - 常用数组方法：
    - 修改器方法: push, pop, shift, unshift, splice, sort, reverse, fill
    - 访问器方法: concat, slice, join, indexOf, lastIndexOf, includes
    - 迭代方法: forEach, map, filter, reduce, reduceRight, some, every, find, findIndex
  - ES6+ 数组扩展 (Array.from, Array.of, copyWithin, find, findIndex, fill, entries, keys, values)
  - 稀疏数组与密集数组
- **9. 其他内置对象**
  - Math (常用方法: random, round, ceil, floor, abs, max, min, pow, sqrt, sin, cos, PI 等)
  - Date (创建日期对象, 获取/设置年月日时分秒, 格式化)
  - RegExp (正则表达式基础语法，test, exec 方法)
  - JSON (JSON.stringify, JSON.parse)
- **10. 错误处理**
  - try...catch...finally 语句
  - throw 语句
  - Error 对象及其子类 (SyntaxError, ReferenceError, TypeError 等)

------



### **阶段二：DOM 操作与浏览器环境**

- **1. DOM (Document Object Model) 简介**
  - 什么是 DOM？DOM 树结构
  - 节点类型 (Element, Text, Comment, Document, DocumentFragment)
- **2. 获取 DOM 元素**
  - document.getElementById()
  - document.getElementsByTagName()
  - document.getElementsByClassName()
  - document.querySelector()
  - document.querySelectorAll()
  - document.forms, document.images 等集合
- **3. DOM 节点操作**
  - 创建节点 (createElement, createTextNode, createComment, createDocumentFragment)
  - 添加节点 (appendChild, insertBefore)
  - 删除节点 (removeChild)
  - 替换节点 (replaceChild)
  - 克隆节点 (cloneNode)
  - 节点属性 (attributes, getAttribute, setAttribute, removeAttribute, hasAttribute)
  - 修改元素内容 (innerHTML, innerText, textContent)
  - 修改元素样式 (element.style, element.className, element.classList)
- **4. DOM 遍历**
  - 父节点 (parentNode, parentElement)
  - 子节点 (childNodes, children, firstChild, lastChild, firstElementChild, lastElementChild)
  - 兄弟节点 (previousSibling, nextSibling, previousElementSibling, nextElementSibling)
- **5. 事件处理 (Event Handling)**
  - 事件流（事件冒泡、事件捕获）
  - 事件监听器 (addEventListener, removeEventListener, onclick 等 HTML 属性方式，element.onclick DOM0级方式)
  - 事件对象 (Event Object)，event.target, event.currentTarget, event.preventDefault(), event.stopPropagation()
  - 常见的事件类型：
    - 鼠标事件 (click, dblclick, mousedown, mouseup, mouseover, mouseout, mousemove, contextmenu)
    - 键盘事件 (keydown, keypress, keyup)
    - 表单事件 (submit, change, focus, blur, input)
    - 页面/窗口事件 (load, unload, resize, scroll)
  - 事件委托 (Event Delegation)
- **6. BOM (Browser Object Model)**
  - window 对象 (全局对象)
    - 对话框 (alert, confirm, prompt)
    - 定时器 (setTimeout, clearTimeout, setInterval, clearInterval)
    - 窗口操作 (open, close, resizeTo, moveTo)
  - navigator 对象 (浏览器信息)
  - location 对象 (URL 操作, href, protocol, host, pathname, search, hash, assign, reload, replace)
  - history 对象 (back, forward, go)
  - screen 对象 (屏幕信息)
- **7. Web 存储**
  - Cookies (基本操作)
  - localStorage
  - sessionStorage
- **8. AJAX 与数据交互**
  - XMLHttpRequest 对象 (基本用法, open, send, onreadystatechange, status, responseText, responseXML)
  - 跨域问题 (CORS, JSONP 原理)
  - Fetch API (ES6+)

------



### **阶段三：异步编程与现代特性 (ES6+ 及更高版本)**

- **1. 异步编程基础回顾**
  - 回调函数 (Callback Functions) 与回调地狱 (Callback Hell)
- **2. Promise**
  - Promise 状态 (pending, fulfilled, rejected)
  - .then(), .catch(), .finally()
  - Promise.resolve(), Promise.reject()
  - Promise.all(), Promise.race(), Promise.allSettled(), Promise.any()
- **3. Async/Await**
  - async 函数
  - await 表达式
  - 错误处理 (结合 try...catch)
- **4. 事件循环 (Event Loop) 与任务队列 (Task Queue)**
  - 宏任务 (Macrotasks: setTimeout, setInterval, I/O, UI rendering)
  - 微任务 (Microtasks: Promise.then/catch/finally, MutationObserver, process.nextTick [Node.js])
  - 执行机制理解
- **5. ES6+ 核心新特性回顾与深入**
  - let 和 const (块级作用域)
  - 模板字符串 (Template Literals)
  - 解构赋值 (Destructuring Assignment: 数组, 对象)
  - 函数默认参数
  - 剩余参数 (...rest) 与展开语法 (...spread)
  - 箭头函数 (Arrow Functions)
  - 对象字面量增强 (Shorthand properties, computed property names, method definitions)
  - for...of 循环 (遍历可迭代对象)
  - Symbol 类型
  - Set 和 Map 数据结构
  - WeakSet 和 WeakMap
  - 迭代器 (Iterators) 和生成器 (Generators)
  - Proxy 和 Reflect
- **6. JavaScript 引擎与运行时 (Runtime)**
  - V8 引擎简介
  - 调用栈 (Call Stack)
  - 堆 (Heap)
  - 垃圾回收机制 (Garbage Collection) (了解基本原理)

------



### **阶段四：面向对象与模块化**

- **1. 面向对象编程 (OOP) 概念**
  - 封装 (Encapsulation)
  - 继承 (Inheritance)
  - 多态 (Polymorphism) (JS 中主要通过鸭子类型体现)
- **2. JavaScript 中的 OOP 实现**
  - **原型与原型链 (Prototype & Prototype Chain)**
    - prototype 属性
    - __proto__ (或 Object.getPrototypeOf())
    - 构造函数 (Constructor Functions)
    - instanceof 原理
    - 原型继承的多种方式 (原型链继承, 借用构造函数, 组合继承, 原型式继承, 寄生式继承, 寄生组合式继承)
  - **ES6 Classes**
    - class 关键字
    - constructor 方法
    - 实例方法与静态方法 (static)
    - extends 实现继承
    - super 关键字
    - Getter 和 Setter (类中)
- **3. 模块化 (Modularity)**
  - 为什么需要模块化？
  - CommonJS (主要用于 Node.js: require, module.exports, exports)
  - AMD (Asynchronous Module Definition - 如 RequireJS) (了解)
  - CMD (Common Module Definition - 如 SeaJS) (了解)
  - UMD (Universal Module Definition) (了解)
  - **ES6 模块 (ESM)**
    - export (命名导出, 默认导出)
    - import (静态导入, 动态导入 import())
    - 在浏览器和 Node.js 中的使用

------



### **阶段五：JavaScript 生态与进阶**

- **1. 包管理工具**
  - npm (Node Package Manager)
  - yarn
  - pnpm
  - package.json 文件详解 (dependencies, devDependencies, scripts 等)
- **2. 构建工具 (Build Tools)**
  - 概念：代码压缩、合并、转译、模块打包等
  - Webpack (核心概念: entry, output, loader, plugin, mode)
  - Rollup (适用于库打包)
  - Parcel (零配置)
  - Vite (基于 ESM 的下一代前端构建工具)
- **3. 代码质量与规范**
  - ESLint (代码风格检查，错误检查)
  - Prettier (代码格式化)
  - EditorConfig (编辑器配置统一)
- **4. 前端框架/库 (选择一个或多个深入学习)**
  - React.js (组件化, JSX, Virtual DOM, Hooks)
  - Vue.js (渐进式框架, 模板语法, 组件化, 响应式系统)
  - Angular (完整的 MVC/MVVM 框架, TypeScript, RxJS)
  - Svelte (编译型框架)
  - (其他如 SolidJS, Qwik 等)
- **5. Node.js (服务器端 JavaScript)**
  - 基本概念，事件驱动，非阻塞 I/O
  - 核心模块 (fs, http, path, os 等)
  - Express.js / Koa.js / NestJS 等 Web 框架
- **6. TypeScript (JavaScript 的超集)**
  - 静态类型检查
  - 接口 (Interfaces), 类型别名 (Type Aliases)
  - 泛型 (Generics)
  - 装饰器 (Decorators)
- **7. 测试 (Testing)**
  - 单元测试 (Unit Testing): Jest, Mocha, Jasmine
  - 集成测试 (Integration Testing)
  - 端到端测试 (E2E Testing): Cypress, Playwright, Puppeteer
- **8. Web APIs 深入**
  - fetch 进阶 (headers, request, response, options)
  - FormData
  - URLSearchParams
  - WebSockets (实时通讯)
  - Service Workers (离线缓存, 推送通知)
  - WebAssembly (Wasm) (了解)
  - IndexedDB (浏览器端数据库)
  - WebRTC (点对点音视频通讯)
- **9. 性能优化 (Performance Optimization)**
  - 减少 HTTP 请求
  - 代码压缩与合并
  - 图片优化 (格式选择, 压缩,懒加载)
  - CDN 使用
  - 浏览器缓存策略
  - 代码层面优化 (避免重绘回流, 节流防抖, 算法优化)
  - 性能分析工具 (Chrome DevTools Performance/Profiler)
- **10. Web 安全 (Security)**
  - XSS (Cross-Site Scripting)
  - CSRF (Cross-Site Request Forgery)
  - CORS (Cross-Origin Resource Sharing) 深入理解
  - HTTPS
  - Content Security Policy (CSP)

------



### **阶段六：实践与深入**

- **1. 设计模式 (Design Patterns)**
  - 单例模式, 工厂模式, 观察者模式, 发布订阅模式, 策略模式, 代理模式, 装饰器模式等在 JS 中的应用。
- **2. 函数式编程 (Functional Programming)**
  - 纯函数, 不可变性, 커링 (Currying), 组合 (Composition), Functor, Monad (概念理解)。
- **3. 数据结构与算法**
  - 用 JavaScript 实现常见的排序算法、查找算法、树、图、链表等。
- **4. 阅读优秀开源项目源码**
- **5. 参与开源项目贡献**
- **6. 持续学习新技术和趋势**

------



**建议：**

1. **动手实践**：理论结合实践，每个知识点都自己敲代码尝试。
2. **构建项目**：从简单的 Todo List 到复杂的 Web 应用，项目是检验学习成果的最佳方式。
3. **阅读文档**：MDN Web Docs 是 JavaScript 最好的学习资源之一。
4. **提问与交流**：遇到问题多思考，然后向社区（如 Stack Overflow, GitHub Discussions, 技术群）提问。
5. **打好基础**：不要急于学习框架，JavaScript 基础非常重要。
6. **循序渐进**：按照路径逐步学习，不要试图一次性掌握所有内容。
7. **版本控制**：熟练使用 Git 进行代码版本管理。
