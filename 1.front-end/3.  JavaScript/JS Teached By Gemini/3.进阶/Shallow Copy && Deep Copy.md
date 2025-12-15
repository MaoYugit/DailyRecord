### 🟢 第一阶段：核心基础与内存模型

在深入各种“拷贝”代码之前，你必须先理解 JavaScript 在内存中是如何存储数据的。这是面试官考察你是否“知其所以然”的关键。

#### 1. 两类数据类型与内存布局

JS 中的数据类型分为 **基本数据类型** 和 **引用数据类型**，它们存储的地方不同。

*   **基本数据类型 (Primitives)**：
    *   包括：`Number`, `String`, `Boolean`, `Null`, `Undefined`, `Symbol`, `BigInt`。
    *   **存储位置**：**栈内存 (Stack)**。
    *   **特点**：占据空间小、大小固定。变量直接存储**具体的值**。

*   **引用数据类型 (Reference Types)**：
    *   包括：`Object`, `Array`, `Function`, `Date`, `RegExp` 等。
    *   **存储位置**：**堆内存 (Heap)**。
    *   **特点**：占据空间大、大小不固定。
    *   **关键点**：变量在“栈”中存储的只是一个**内存地址（指针）**，这个地址指向“堆”里的实际数据。

---

#### 2. “赋值”的陷阱（面试必考点）

这是新手最容易混淆的地方：**赋值（`=`）不是拷贝！**

##### 🌰 例子 A：基本类型的赋值
```javascript
let a = 10;
let b = a; // 这里发生了“值拷贝”，b 拿到了 10 的副本

b = 20; // 修改 b

console.log(a); // 输出 10 (a 不受影响)
console.log(b); // 输出 20
```
> **结论**：基本类型互不影响，因为它们在栈里是独立存在的。

##### 🌰 例子 B：引用类型的赋值（共享地址）
```javascript
let obj1 = { name: "前端练习生" };

// 🚨 注意：这仅仅是把“地址”给了 obj2
// 就像把一把钥匙复制了一份，但房子还是同一间
let obj2 = obj1; 

obj2.name = "面试官"; // 修改 obj2

console.log(obj1.name); // 😱 输出 "面试官" (obj1 被改了！)
console.log(obj2.name); // 输出 "面试官"
```

> **图解**：
> `obj1` (栈: 0x001) ---> { name: ... } (堆)
> `obj2` (栈: 0x001) ---> { name: ... } (堆)
>
> 它们指向同一个堆内存地址。**这不叫拷贝，这叫“引用传递”。**

---

#### 3. 什么是浅拷贝 vs 深拷贝？

既然直接赋值 `obj2 = obj1` 无法创建独立副本，我们就需要“拷贝”。

假设我们有一个嵌套对象：
```javascript
let original = {
    name: "Tony",
    details: {
        age: 30,
        city: "New York"
    }
};
```

*   **浅拷贝 (Shallow Copy)**：
    *   创建一个新对象（房子是新的）。
    *   **但是**，对象里面的属性如果还是对象（如 `details`），拷贝的依然是地址（家具还是旧的，共用的）。
    *   **结果**：修改第一层属性（`name`）互不影响；修改第二层属性（`details.age`）会**互相影响**。

*   **深拷贝 (Deep Copy)**：
    *   创建一个新对象。
    *   递归地把对象里的所有嵌套属性全部复制一份新的。
    *   **结果**：彻底分离，修改任何层级都**互不影响**。

---

#### 4. 第一阶段总结与面试题

**✅ 核心结论：**
1.  基本类型赋值是**值**的复制，互不干扰。
2.  引用类型赋值是**地址**的复制，指向同一个对象。
3.  我们需要“拷贝”技术来切断这种引用联系。

**🔥 面试模拟题：**
> **面试官问**：代码 `const a = {x: 1}; const b = a;` 中，`b` 是 `a` 的浅拷贝吗？
>
> **你的回答**：不是。这只是**引用的赋值**。`a` 和 `b` 指向同一个内存地址，修改 `b` 会直接影响 `a`。浅拷贝会创建一个新对象，至少在第一层属性上是独立的，而赋值连第一层都是共享的。

---

### 🟢**第二阶段：浅拷贝全家桶**！

这一阶段的目标是掌握所有能产生“浅拷贝”的方法。在面试中，你不仅要能列举出这些方法，最重要的是要能**手写代码证明它们是“浅”的**。

---

#### 1. 什么是浅拷贝（Shallow Copy）？

再次复习一下定义：
浅拷贝会创建一个**新对象**，这个对象有着原始对象属性值的一份精确拷贝。
*   如果属性是**基本类型**，拷贝的就是**值**（互相独立）。
*   如果属性是**引用类型**，拷贝的就是**内存地址**（共用数据）。

**通俗理解**：像是建了一个新房子，但房子里的家具（嵌套对象）还是用的旧房子的钥匙。

---

#### 2. 浅拷贝的常用方法（由高频到低频）

##### ① 展开运算符 `...` (Spread Operator) —— **最推荐**
这是目前实际开发（React/Vue）中**使用率最高**的方法，语法简洁。

```javascript
let obj1 = { name: "Mike", info: { age: 18 } };

// 使用 ... 进行浅拷贝
let obj2 = { ...obj1 }; 

console.log(obj2); // { name: "Mike", info: { age: 18 } }
```

##### ② `Object.assign()` —— **老牌标准**
ES6 推出的方法，用于将所有可枚举属性的值从一个或多个源对象复制到目标对象。

```javascript
let obj1 = { name: "Mike", info: { age: 18 } };

// 第一个参数必须是空对象 {}，否则就是修改原对象
let obj2 = Object.assign({}, obj1);

// 或者
let obi = {}
Object.assign({}, obj1)
```

##### ③ 数组专用方法 (`slice`, `concat`)
数组也是对象，日常开发中经常需要拷贝数组。

```javascript
let arr1 = [1, 2, { a: 3 }];

// 1. slice (切片) - 不传参代表截取整个数组
let arr2 = arr1.slice();

// 2. concat (合并) - 合并空数组
let arr3 = [].concat(arr1);

// 3. Array.from (ES6)
let arr4 = Array.from(arr1);
```

##### ④ 第三方库：Lodash 的 `_.clone`
虽然原生方法够用了，但在项目中如果已经引入了 Lodash，也可以用它的 API。
*(注意：Lodash 还有一个 `cloneDeep`，那个才是深拷贝，别搞混)*

```javascript
// 假设已引入 lodash
var objects = [{ 'a': 1 }, { 'b': 2 }];

var shallow = _.clone(objects);
console.log(shallow[0] === objects[0]); // true (说明引用还是同一个)
```

---

#### 3. 🚨 核心演示：证明它是“浅”拷贝（面试必杀技）

这是面试官一定会让你演示的环节：**“请写代码证明 Object.assign 是浅拷贝”。**

你需要展示：**修改第一层没影响，修改第二层（嵌套）有影响。**

```javascript
// === 浅拷贝实战演示 ===

// 1. 定义源数据
const source = {
    name: "前端练习生",      // 第一层（基本类型）
    meta: {                 // 第二层（引用类型）
        role: "admin",
        score: 90
    }
};

// 2. 执行浅拷贝
const copy = { ...source }; 

// 3. 修改第一层（基本类型）
copy.name = "面试官"; 
// 结果：源对象不受影响 ✅
// source.name 还是 "前端练习生"

// 4. 修改第二层（引用类型）
copy.meta.score = 0; 
// 结果：源对象也被改了！❌
// source.meta.score 变成了 0

console.log("Source:", source); // meta.score 是 0
console.log("Copy:", copy);     // meta.score 是 0
```

> **原理分析**：
> `source.meta` 存的是一个**地址**（比如 0x888）。
> 浅拷贝时，把 0x888 这个地址复制给了 `copy.meta`。
> 所以 `source.meta` 和 `copy.meta` 指向堆内存里的同一个对象。

---

#### 4. 实际开发场景

既然浅拷贝有“副作用”，为什么还要用它？
因为**快**，而且在很多场景下，我们只需要修改第一层数据。

*   **React 的 State 更新**：
    React 要求状态不可变（Immutable）。当我们更新状态时，通常使用 `...` 复制一份旧状态，然后覆盖修改的属性。
    
    ```javascript
    // React 中常见写法
    const [user, setUser] = useState({ name: 'Jack', settings: { theme: 'dark' } });
    
    // 只想改 name，settings 保持引用不变（节省性能）
    setUser({ ...user, name: 'Rose' }); 
    ```

---

#### 🔍 第二阶段总结

| 方法            | 适用类型  | 优点                   | 缺点                     |
| :-------------- | :-------- | :--------------------- | :----------------------- |
| `Object.assign` | 对象      | 兼容性较好             | 写法稍微繁琐             |
| `...` (展开)    | 对象/数组 | **语法最简洁，最常用** | 属于 ES6+                |
| `slice/concat`  | 数组      | 数组原生支持           | 语义上不是专门用来拷贝的 |
| `_.clone`       | 通用      | 库函数封装好           | 需要引入额外依赖         |

**⚠️ 致命共同点**：无法切断嵌套对象的引用联系。如果你的数据有多层嵌套，且需要完全独立，必须使用**深拷贝**。

---

### 🟢 **第三阶段：深拷贝全家桶**。

在这一阶段，我们将学习如何创建一个**完全独立**的副本，无论对象嵌套了多少层，新旧对象互不干扰。这在处理复杂表单、撤销重做（Undo/Redo）、数据缓存等场景中非常关键。

---

#### 1. 网红“黑科技”：`JSON.parse(JSON.stringify())`

这是前端开发中最著名、最简单、也是面试中被问得最多的深拷贝方法。

##### 🔴  核心代码
```javascript
const obj1 = {
    a: 1,
    b: { c: 2 }
};

// 1. 转成字符串 (序列化) -> 2. 转回对象 (反序列化)
const obj2 = JSON.parse(JSON.stringify(obj1));

obj2.b.c = 999;
console.log(obj1.b.c); // 2 (源对象未受影响，深拷贝成功！)
```

##### 🔴 致命缺陷（面试必考！）
虽然它简单，但它只能处理**“标准 JSON 数据”**。如果对象包含非 JSON 标准的数据类型，就会出问题。

**请务必记住以下 5 个坑（面试官会让你举例说明）：**
1.  **函数（Function）**：会直接丢失（`undefined`）。
2.  **Undefined / Symbol**：会直接丢失。
3.  **正则（RegExp）**：变为空对象 `{}`。
4.  **日期（Date）**：变成字符串形式。
5.  **循环引用（Circular Reference）**：**直接报错**！

**❌ 翻车现场演示：**
```javascript
const badObj = {
    fn: function() { console.log("我是函数"); }, // 函数
    undef: undefined,                          // undefined
    reg: /abc/g,                               // 正则
    date: new Date(),                          // 日期
};

const copy = JSON.parse(JSON.stringify(badObj));

console.log(copy);
/* 输出结果：
{
    reg: {},                // 正则变成了空对象
    date: "2023-10-...",    // 日期变成了字符串
    // fn 和 undef 直接消失了！
}
*/
```

**❌ 循环引用报错演示：**
```javascript
const a = {};
const b = {};
a.child = b;
b.parent = a; // a 和 b 互相引用，形成闭环

// Uncaught TypeError: Converting circular structure to JSON
JSON.parse(JSON.stringify(a)); 
```

---

#### 2. 原生新标准：`structuredClone` (2022+ 推荐)

这是现代浏览器（Chrome 98+, Node.js 17+）原生提供的深拷贝 API。它修复了 `JSON` 方法的大部分缺陷。

##### 🔴  核心优势
*   **原生支持**：不需要引入第三方库。
*   **支持更多类型**：完美支持 `Date`、`RegExp`、`Map`、`Set`、`ArrayBuffer` 等。
*   **支持循环引用**：不会报错，能正确拷贝。

##### 🔴  代码演示
```javascript
const original = {
    set: new Set([1, 2, 3]),
    map: new Map([['name', 'Mike']]),
    date: new Date(),
    reg: /abc/g
};

// 循环引用测试
original.self = original;

// 使用原生 API
const copy = structuredClone(original);

console.log(copy.date instanceof Date); // true (保持了 Date 类型)
console.log(copy.self === copy);        // true (循环引用也被正确处理了)
```

##### 🔴  局限性
*   依然**不支持函数**（拷贝函数会抛出 `DataCloneError` 异常）。
*   兼容性：如果在非常老的浏览器运行，需要 Polyfill。

---

#### 3. 业界标杆：Lodash 的 `_.cloneDeep`

在实际企业级项目（尤其是需要兼容性、且数据类型极度复杂）中，Lodash 是最稳妥的选择。

##### 🔴  为什么它是标杆？
它处理了 JS 中几乎所有的边界情况（包括 Buffer、TypedArray、各种原型链问题等），虽然体积稍大，但最健壮。

```javascript
// 假设已安装 lodash
import _ from 'lodash';

const obj = {
    a: 1,
    fn: function() { return 1; } // Lodash 甚至可以处理函数的引用的拷贝（具体看配置，一般函数还是引用复制）
};

const copy = _.cloneDeep(obj);
```
*(注：默认的 cloneDeep 会复制函数的引用，因为函数在 JS 中很难“深拷贝”一个全新的执行体，通常也不需要)*

---

#### 4. 历史遗留：jQuery 的 `$.extend`

如果你维护 10 年前的老项目，可能会看到这个。了解即可。

```javascript
// 第一个参数 true 代表深拷贝
var copied = $.extend(true, {}, originalObject);
```

---

#### 5. 面试加分项：`MessageChannel` (偏门技巧)

在 `structuredClone` 出来之前，有一种利用浏览器消息机制实现深拷贝的方法。它可以处理循环引用和 Date/RegExp，但是它是**异步**的。

```javascript
// 仅作了解，面试时提一下能显示你知识面广
function structuralClone(obj) {
  return new Promise(resolve => {
    const { port1, port2 } = new MessageChannel();
    port2.onmessage = ev => resolve(ev.data);
    port1.postMessage(obj);
  });
}

// 用法是异步的
const copy = await structuralClone(obj);
```

---

#### 🔍 第三阶段总结（神图表）

| 方法                 | 实现难度 | 循环引用   | 函数     | Date/RegExp | 推荐场景                      |
| :------------------- | :------- | :--------- | :------- | :---------- | :---------------------------- |
| **JSON.stringify**   | ⭐ (极简) | ❌ 报错     | ❌ 丢失   | ❌ 变字符/空 | 简单的后端 API 数据 (纯 JSON) |
| **structuredClone**  | ⭐ (原生) | ✅ 支持     | ❌ 报错   | ✅ 支持      | **现代前端开发首选**          |
| **Lodash cloneDeep** | ⭐ (引库) | ✅ 支持     | ➖ (引用) | ✅ 支持      | 复杂的大型项目/老旧环境       |
| **手写递归**         | ⭐⭐⭐⭐⭐    | 需手动处理 | 可定制   | 需手动处理  | **面试必考** (下一阶段内容)   |

---

### 🟢**第四阶段：手写深拷贝（面试终极 Boss）**！

这是所有前端面试题中含金量最高、也是最容易挂的一道题。面试官不让你用 `JSON.parse` 也不让你用 `structuredClone`，就是看你**逻辑是否严密**，以及**是否懂递归和内存管理**。

我们将分三个版本进阶，从“能用”到“完美”。

---

#### 🔴  版本 1：基础递归版（青铜段位）

这是深拷贝最骨架的逻辑。
**核心思想**：

1.  如果是基本类型，直接返回。
2.  如果是引用类型（对象/数组），创建一个新的容器。
3.  遍历旧容器的属性，递归赋值给新容器。

```javascript
function deepClone(target) {
    // 1. 判断是否是对象，如果是基本类型或 null，直接返回
    if (typeof target !== 'object' || target === null) {
        return target;
    }

    // 2. 创建新容器：判断是数组还是对象
    const result = Array.isArray(target) ? [] : {};

    // 3. 遍历属性
    for (let key in target) {
        // 保证 key 是对象自有的，不是原型链上的
        if (target.hasOwnProperty(key)) {
            // 4. 递归调用！(核心)
            result[key] = deepClone(target[key]);
        }
    }

    return result;
}
```

**❌ 存在的缺陷：**
1.  **无法处理循环引用**：如果 `a.self = a`，递归会无限进行，导致栈溢出（Stack Overflow）。
2.  **无法处理特殊对象**：`Date` 和 `RegExp` 会变成空对象 `{}`。

---

🔴  版本 2：解决循环引用（黄金段位 —— 面试合格线）

这是面试中最关键的一步。面试官通常会问：“如果对象循环引用怎么办？”

**解决方案**：使用一个“备忘录”（Map/WeakMap）来存储已经拷贝过的对象。
*   每次拷贝前，先去 Map 里查一下：这个对象之前拷贝过吗？
*   如果拷贝过，直接把 Map 里的副本拿来用，不再递归。
*   如果没拷贝过，就创建一个新副本，存到 Map 里，然后继续递归。

**为什么要用 `WeakMap`？**
因为 `WeakMap` 的键是弱引用。如果原对象被垃圾回收了，Map 里的记录也会自动消失，**防止内存泄漏**。

```javascript
// 增加 map 参数，默认值是一个新的 WeakMap
function deepClone(target, map = new WeakMap()) {
    // 1. 基本类型直接返回
    if (typeof target !== 'object' || target === null) {
        return target;
    }

    // 2. 【核心】检查循环引用
    if (map.has(target)) {
        return map.get(target); // 如果拷贝过，直接返回之前存的副本
    }

    // 3. 创建新容器
    const result = Array.isArray(target) ? [] : {};

    // 4. 【核心】存入 Map (在递归之前存！)
    // 意思是：现在的 target 对应的新对象是 result
    map.set(target, result);

    // 5. 递归遍历
    for (let key in target) {
        if (target.hasOwnProperty(key)) {
            // 把 map 传下去
            result[key] = deepClone(target[key], map);
        }
    }

    return result;
}
```

**✅ 这个版本通常能应付 80% 的面试。**

---

#### 🔴 版本 3：处理特殊类型（王者段位）

如果面试官继续追问：“那 `Date` 和 `RegExp` 怎么处理？`Symbol` 做 key 怎么办？”

这时候你需要一个更完善的版本。
**核心逻辑**：
1.  针对 `Date` 和 `RegExp`，直接用它们的构造函数生成新实例。
2.  针对 `Symbol` 属性，`for...in` 遍历不到，需要用 `Reflect.ownKeys`。

```javascript
function deepClone(target, map = new WeakMap()) {
    // 1. 处理 null 和基本类型
    if (target === null || typeof target !== 'object') {
        return target;
    }

    // 2. 处理 Date 和 RegExp (特殊引用类型)
    if (target instanceof Date) return new Date(target);
    if (target instanceof RegExp) return new RegExp(target);

    // 3. 处理循环引用
    if (map.has(target)) return map.get(target);

    // 4. 创建新容器 (保持原型的构造)
    // new target.constructor() 是一种黑魔法，能自动识别是 Array 还是 Object
    const result = new target.constructor();

    // 5. 记录到 Map
    map.set(target, result);

    // 6. 遍历属性 (Reflect.ownKeys 可以拿到 Symbol 属性和不可枚举属性)
    // 如果不需要考虑 Symbol，用 for...in 也可以
    Reflect.ownKeys(target).forEach(key => {
        result[key] = deepClone(target[key], map);
    });

    return result;
}
```

---

#### 🧪 验证测试（面试时你可以口述这个测试用例）

```javascript
const obj = {
    num: 1,
    date: new Date(),
    reg: /abc/g,
    arr: [1, 2],
    [Symbol('id')]: 123
};
// 制造循环引用
obj.self = obj;

const copy = deepClone(obj);

console.log(copy.self === copy); // true (循环引用解决)
console.log(copy.date instanceof Date); // true (日期类型正常)
console.log(copy.reg instanceof RegExp); // true (正则正常)
console.log(copy[Object.getOwnPropertySymbols(copy)[0]]); // 123 (Symbol 拷贝成功)
```

---

#### 📝 第四阶段总结（面试话术）

当面试官让你手写深拷贝时，你的思路应该是：

1.  **先写骨架**：判断类型 + 递归。
2.  **补全循环引用**：嘴里念叨“为了防止栈溢出，我需要一个 WeakMap 来做缓存”。（这点说出来，面试官就好感度 +1）。
3.  **补全特殊类型**：如果时间充裕，或者面试官问到了，再补上 Date/RegExp 的判断。
4.  **不建议过度设计**：不要试图去写一个兼容 Buffer、Map、Set 的超全版本，那样代码会太长且容易出错。如果面试官问，就说“Map 和 Set 同理，通过 `instanceof` 判断后 new 一个新的即可”。

---

### **🟢第五阶段：总结、选型与模拟面试**

这一阶段我们将从“造轮子”的思维回到“实际工程”中。面试是为了展示能力，但实际开发是为了解决问题。我们需要知道何时使用什么工具，以及如何回答面试官的“追问”。

---

#### 1. 为什么实际开发中不用手写的 `deepClone`？

虽然我们在第四阶段手写了一个很棒的深拷贝函数，但在公司实际项目中，**千万不要直接把自己手写的代码用到生产环境！**

**理由如下（体现你的工程化思维）：**
1.  **边界情况极多**：你处理了 `Date` 和 `RegExp`，那 `Map`、`Set`、`WeakMap`、`Error`、`ArrayBuffer`、`TypedArray`、`Blob` 呢？
2.  **原型链安全**：手写代码容易受到“原型污染”攻击。
3.  **栈溢出风险**：虽然我们解决了循环引用，但如果对象层级过深（比如 10000 层），递归依然会导致爆栈。成熟的库（如 Lodash）通常会优化递归算法或改用循环栈。

**结论**：面试手写是为了证明**基础扎实**；工作中使用 Lodash 或原生 API 是为了**稳健高效**。

---

#### 2. 终极选型指南（Cheat Sheet）

在开发中遇到需要拷贝的场景，请按以下优先级选择：

| 场景                             | 推荐方案                               | 理由                                                         |
| :------------------------------- | :------------------------------------- | :----------------------------------------------------------- |
| **日常 UI 更新** (React/Vue)     | **浅拷贝** `...` 或 `Object.assign`    | 速度最快，通常只需要改第一层数据，框架会自动处理更新。       |
| **纯数据对象** (API 返回的 JSON) | **`structuredClone`** (首选) 或 `JSON` | 原生支持，性能好，无额外依赖。                               |
| **复杂对象** (含 Date/Map/Set)   | **`structuredClone`**                  | 现代浏览器原生支持，解决了 JSON 的痛点。                     |
| **极度复杂 / 老旧项目**          | **Lodash `_.cloneDeep`**               | 工业级标准，兼容性最强，你能想到的坑它都填了。               |
| **特殊需求** (只要函数不拷贝)    | **手写 / 定制**                        | 只有当你需要非常特殊的拷贝逻辑（比如过滤掉某些属性）时才手写。 |

---

#### 3. 性能对比（了解即可）

*   **速度王者**：浅拷贝 (`...`) >>>>> 深拷贝。
*   **深拷贝内部对比**：
    *   `JSON.parse(JSON.stringify)`：处理大数据时通常比 `structuredClone` 慢（因为要涉及字符串解析）。
    *   `structuredClone`：这是浏览器底层 C++ 实现的，通常比 JS 库（Lodash）更快。

**经验法则**：除非数据量达到 MB 级别，否则不要过度纠结深拷贝的性能差异，**准确性 > 性能**。

---

#### 4. 🎓 模拟面试自测（Mock Interview）

请尝试在心里回答以下 4 个问题，如果能流畅回答，说明你已经通关了！

##### Q1: "请说一下浅拷贝和深拷贝的区别？"
> **参考回答**：
> *   **浅拷贝**：只复制对象的第一层属性。如果属性是基本类型，拷贝值；如果属性是引用类型，拷贝内存地址。两个对象在嵌套属性上依然共享。常用方法有 `Object.assign` 和展开运算符 `...`。
> *   **深拷贝**：递归复制对象的所有层级，创建一个完全独立的新副本。修改新对象不会影响原对象。

##### Q2: "JSON.stringify 实现深拷贝有什么缺点？"
> **参考回答**：
> 它只能处理标准 JSON 数据。
> 1.  **丢失**：函数、`undefined`、`Symbol` 会丢失。
> 2.  **变形**：`NaN`、`Infinity` 变成 `null`，`Date` 变成字符串，`RegExp` 变成空对象。
> 3.  **报错**：遇到循环引用会直接抛出异常。

##### Q3: "Object.assign 是深拷贝还是浅拷贝？如果拷贝只有一层的对象呢？"
> **参考回答**：
> 它是**浅拷贝**。即使对象只有一层，它在定义上也属于浅拷贝（虽然效果上看起来像深拷贝，因为它复制了第一层的值）。但如果未来给这个对象加了嵌套属性，它就露馅了。

##### Q4: "手写深拷贝时，如何解决循环引用的问题？"
> **参考回答**：
> 需要使用一个缓存容器（通常是 `WeakMap`）来存储已经拷贝过的对象。
> 在递归之前，先用 `map.has(target)` 检查。如果存在，直接返回 `map.get(target)`；如果不存在，先将新对象存入 Map，再进行递归。使用 `WeakMap` 是为了防止内存泄漏。

---

#### 🌟 结语

恭喜你完成了 **JS 浅拷贝与深拷贝** 的完整学习！🏆

这块知识点从“内存原理”贯穿到“手写算法”再到“工程实践”，掌握了这些，你在面试这道题时已经可以碾压 90% 的初级/中级候选人了。

**下一步建议**：
1.  打开你的代码编辑器，**不要看教程，尝试手写一遍第四阶段的 `deepClone`**。
2.  在实际写 React/Vue 项目时，有意识地去想：“这里我用了 `...`，我是不是只改了第一层？有没有隐患？”
