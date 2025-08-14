这份大合集从**核心基础**到**高级进阶**，再到**浏览器环境**和**工程化实践**，覆盖了现代前端开发所需的绝大部分 JS 知识。你可以根据这个清单来逐项检查，看看自己在哪些方面需要巩固或深入学习。

---

## JavaScript 知识体系大合集

### 第一部分：核心语言基础 (The Foundation)

这是学习一切后续知识的基石，必须滚瓜烂熟。

*   **1. 变量与常量**
    *   `var`, `let`, `const` 的区别（作用域、变量提升、暂时性死区、是否可重复声明/赋值）。
*   **2. 数据类型 (Data Types)**
    *   **原始类型 (Primitives)**
        *   `String`：字符串常用方法 (`slice`, `substring`, `split`, `includes`, `trim` 等)。
        *   `Number`：`NaN`, `Infinity`, `parseInt`, `parseFloat`, `toFixed`, `Number.isInteger`。
        *   `Boolean`：`true`, `false` 以及所有 "falsy" 值 (`false`, `0`, `""`, `null`, `undefined`, `NaN`)。
        *   `Null`：表示“空值”或“无对象”。
        *   `Undefined`：表示“未定义”。
        *   `Symbol` (ES6)：创建唯一标识符，用于对象属性避免冲突。
        *   `BigInt` (ES2020)：处理超出安全整数范围的大数。
    *   **引用类型 (Object)**
        *   `Object`：属性的增删改查，`Object.keys`, `Object.values`, `Object.entries`, `hasOwnProperty`。
        *   `Array`：见下文“数组专题”。
        *   `Function`：见下文“函数专题”。
        *   `Date`：日期对象的操作。
        *   `RegExp`：正则表达式。
*   **3. 运算符 (Operators)**
    *   算术运算符 (`+`, `-`, `*`, `/`, `%`, `++`, `--`, `**`)。
    *   赋值运算符 (`=`, `+=`, `-=`, `*=`, `/=`)。
    *   比较运算符 (`==`, `!=`, `===`, `!==`, `>`, `<`, `>=`, `<=`)，深刻理解 `==` 的隐式类型转换。
    *   逻辑运算符 (`&&`, `||`, `!`)，以及短路求值特性。
    *   位运算符 (了解即可, 如 `&`, `|`, `^`, `~`, `<<`, `>>`)。
    *   三元运算符 (`condition ? expr1 : expr2`)。
*   **4. 控制流 (Control Flow)**
    *   条件语句：`if...else`, `switch...case`。
    *   循环语句：`for`, `while`, `do...while`, `for...in` (遍历对象属性), `for...of` (遍历可迭代对象)。
    *   `break`, `continue` 的使用。
*   **5. 类型转换 (Type Coercion)**
    *   显式转换：`Number()`, `String()`, `Boolean()`。
    *   隐式转换：`==`, `+`, `if()` 等场景下的转换规则，这是 JS 的一个重点和难点。

---

### 第二部分：核心概念进阶 (Intermediate Concepts)

掌握这些是区分初级和中级开发者的关键。

*   **1. 函数 (Functions)**
    *   函数声明 vs. 函数表达式 vs. 箭头函数。
    *   函数参数：默认参数、剩余参数 (`...rest`)。
    *   箭头函数：词法 `this` 绑定、没有 `arguments` 对象、不能用作构造函数。
*   **2. `this` 指向**
    *   全局上下文中的 `this` (`window` 或 `undefined` in strict mode)。
    *   函数调用中的 `this` (默认绑定)。
    *   对象方法中的 `this` (隐式绑定)。
    *   构造函数中的 `this` (`new` 绑定)。
    *   使用 `call`, `apply`, `bind` 显式改变 `this` 指向。
*   **3. 作用域与作用域链 (Scope & Scope Chain)**
    *   全局作用域、函数作用域、块级作用域 (ES6)。
    *   词法作用域（静态作用域）的理解。
    *   作用域链的查找机制。
*   **4. 闭包 (Closures)**
    *   什么是闭包：函数及其词法环境的组合。
    *   闭包的用途：数据私有化、创建模块、函数柯里化。
    *   闭包导致的内存泄漏问题及预防。
*   **5. 原型与原型链 (Prototype & Prototype Chain)**
    *   `__proto__` 和 `prototype` 的区别与联系。
    *   构造函数、实例、原型对象之间的关系。
    *   原型链的继承机制和属性查找过程。
    *   `instanceof` 的工作原理。
    *   `Object.create()` 的使用。
*   **6. 执行上下文与调用栈 (Execution Context & Call Stack)**
    *   执行上下文的类型（全局、函数）。
    *   执行上下文的生命周期（创建、执行、销毁）。
    *   调用栈的 LIFO (后进先出) 特性。
    *   变量提升 (Hoisting) 与函数提升。

---

### 第三部分：异步编程 (Asynchronous Programming)

现代 Web 应用的必备技能。

*   **1. 事件循环 (Event Loop)**
    *   调用栈、任务队列 (Task Queue / Callback Queue)、微任务队列 (Microtask Queue)。
    *   宏任务 (Macrotask) vs. 微任务 (Microtask) 的区别与执行顺序 (如 `setTimeout` vs. `Promise.then`)。
*   **2. 异步解决方案的演进**
    *   **回调函数 (Callbacks)**：以及“回调地狱” (Callback Hell) 的问题。
    *   **Promise (ES6)**
        *   三种状态：`pending`, `fulfilled`, `rejected`。
        *   核心 API：`.then()`, `.catch()`, `.finally()`。
        *   静态方法：`Promise.resolve()`, `Promise.reject()`, `Promise.all()`, `Promise.race()`, `Promise.allSettled()`, `Promise.any()`。
    *   **Async/Await (ES2017)**
        *   `async` 函数的本质（返回一个 Promise）。
        *   `await` 的作用（等待 Promise 完成）。
        *   使用 `try...catch` 进行错误处理。
*   **3. Generators & Iterators (ES6)**
    *   `function*` 和 `yield` 的使用。
    *   迭代器协议和可迭代协议。
    *   作为 `async/await` 的底层实现原理之一。

---

### 第四部分：常用内置对象 API 专题

这些是日常开发中使用频率极高的 API。

*   **1. 数组 (Array)**
    *   **改变原数组的方法**：`push`, `pop`, `shift`, `unshift`, `splice`, `sort`, `reverse`, `fill`。
    *   **不改变原数组的方法**：`concat`, `slice`, `join`, `toString`。
    *   **遍历方法**：`forEach`, `map`, `filter`, `reduce`, `every`, `some`, `find`, `findIndex`, `keys`, `values`, `entries`。
    *   `Array.from()`, `Array.isArray()`, `includes()`。
*   **2. 对象 (Object)**
    *   `Object.assign()`：合并对象。
    *   `Object.freeze()`：冻结对象。
    *   `Object.defineProperty()` / `Object.defineProperties()`：定义对象属性（getter/setter）。
    *   `Object.getPrototypeOf()`。
*   **3. 字符串 (String) 与 正则表达式 (RegExp)**
    *   字符串模板 (Template Literals)。
    *   正则的创建、修饰符 (`g`, `i`, `m`)。
    *   常用方法：`test()`, `exec()`, `match()`, `search()`, `replace()`, `split()`。
*   **4. Map & Set (ES6)**
    *   `Map` vs. `Object` 的区别。
    *   `Set` vs. `Array` 的区别，以及如何用于数组去重。
    *   `WeakMap` 和 `WeakSet` 的弱引用特性。

---

### 第五部分：浏览器环境 API (Web APIs)

让 JavaScript 与网页交互的能力。

*   **1. DOM (Document Object Model)**
    *   **节点查找**：`getElementById`, `getElementsByClassName`, `getElementsByTagName`, `querySelector`, `querySelectorAll`。
    *   **节点操作**：`createElement`, `createTextNode`, `appendChild`, `removeChild`, `replaceChild`, `insertBefore`。
    *   **属性操作**：`getAttribute`, `setAttribute`, `removeAttribute`, `className`, `classList`。
    *   **内容操作**：`innerHTML`, `innerText`, `textContent` 的区别。
    *   **节点遍历**：`parentNode`, `childNodes`, `children`, `firstChild`, `lastChild`, `nextSibling`, `previousSibling`。
*   **2. BOM (Browser Object Model)**
    *   `window` 对象：全局变量、`alert`, `confirm`, `prompt`。
    *   `navigator`：浏览器信息。
    *   `location`：URL 信息与跳转。
    *   `history`：浏览器历史记录操作。
    *   `screen`：屏幕信息。
*   **3. 事件 (Events)**
    *   事件绑定：`onclick` vs. `addEventListener`。
    *   事件流：事件冒泡 (Bubbling) 与事件捕获 (Capturing)。
    *   事件对象 (`Event Object`)：`target`, `currentTarget`, `preventDefault()`, `stopPropagation()`。
    *   事件委托/代理 (Event Delegation)。
*   **4. 网络请求 (Networking)**
    *   `XMLHttpRequest` (XHR) - (了解即可，现代较少用)。
    *   `fetch` API (Promise-based, 推荐使用)。
    *   跨域问题 (CORS) 的基本理解。
*   **5. 存储 (Storage)**
    *   `localStorage`：持久化本地存储。
    *   `sessionStorage`：会话级存储。
    *   `Cookies`：与服务器通信的小文件。
    *   `IndexedDB`：浏览器端的事务型数据库。
*   **6. 其他 Web API**
    *   `setTimeout` & `setInterval`。
    *   `Web Workers`：在后台线程运行脚本。
    *   `Geolocation`：地理位置。
    *   `Canvas` & `SVG`：图形绘制。

---

### 第六部分：面向对象、模块化与工程化 (Modern Development)

构建大型、可维护应用所需知识。

*   **1. 面向对象编程 (OOP)**
    *   工厂模式、构造函数模式。
    *   ES6 `class` 语法糖 (`constructor`, `extends`, `super`, `static`)。
*   **2. 模块化 (Modularity)**
    *   **CommonJS** (主要用于 Node.js): `require`, `module.exports`。
    *   **ES Modules (ESM)** (浏览器和现代 Node.js 的标准): `import`, `export` / `export default`。
*   **3. 性能优化与内存管理 (Performance & Memory)**
    *   **内存泄漏 (Memory Leaks)**：常见场景（闭包、定时器、DOM引用）及排查。
    *   **垃圾回收 (Garbage Collection)**：基本原理（标记-清除算法）。
    *   **性能优化技巧**：
        *   **防抖 (Debounce)** 和 **节流 (Throttle)**。
        *   懒加载 (Lazy Loading) / 虚拟滚动。
        *   使用 `requestAnimationFrame` 处理动画。
*   **4. JS 生态与工具**
    *   **包管理器**：`npm`, `yarn`, `pnpm`。
    *   **构建工具**：`Webpack`, `Vite`, `Rollup` 的基本概念（打包、转译、压缩）。
    *   **代码转译**：`Babel` (将 ES6+ 代码转为 ES5)。
    *   **代码质量**：`ESLint` (代码检查), `Prettier` (代码格式化)。
    *   **测试**：`Jest`, `Vitest`, `Cypress` 等测试框架的基本概念。

---

### 如何使用这份清单

1.  **从上到下**：按照顺序，逐个检查知识点。
2.  **自我提问**：对于每个知识点，问自己几个问题：
    *   它是什么？
    *   它解决了什么问题？
    *   它有哪些常见的应用场景？
    *   它有什么需要注意的坑或细节？
3.  **标记状态**：将每个知识点标记为“已掌握”、“需复习”或“完全不了解”。
4.  **查漏补缺**：针对“需复习”和“不了解”的项，回到 MDN、现代 JavaScript 教程等权威资源进行重点学习和实践。

祝你学习顺利，早日成为 JavaScript 大师！