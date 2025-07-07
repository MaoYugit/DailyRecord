# JS进阶

---

<div style="display: flex; justify-content: space-between;">
  <a href="./JS 进阶-3.md">‹ 上一篇：JS进阶-3</a>
  <a href="./JS 进阶-5.md">下一篇：JS进阶-5 ›</a>
</div>

### 16. 数组 `reduce` 累计方法

`reduce` 是数组方法中最强大、最灵活的一个，但也是初学者最容易感到困惑的一个。我们可以把它想象成一个“浓缩机”或“汇总器”。

#### 核心思想

`reduce` 方法接收一个函数作为累加器，数组中的每个值（从左到右）都会被这个累加器处理，最终将数组**缩减（reduce）**为一个**单一的值**。

#### 超详细讲解

它的完整语法是：`array.reduce(callback(accumulator, currentValue, currentIndex, array), initialValue)`

别被这长长的语法吓到，我们把它拆开看，99%的情况下你只需要关心前两个参数：

* `accumulator` (累加器)：我们叫它 `acc`。它是上一次回调函数执行的返回值。在第一次执行时，它要么是 `initialValue`（如果提供了），要么是数组的第一个元素。
* `currentValue` (当前值)：我们叫它 `cur`。它是数组中正在被处理的那个元素。
* `initialValue` (初始值)：可选。它是累加器第一次执行时的初始值。**强烈建议总是提供这个初始值**，这样可以避免很多边界问题（比如处理空数组）。

**场景一：数组求和 (最经典用法)**

```javascript
const numbers = [10, 20, 30, 40];

// 我们想把它们加起来得到 100
// acc: 累加的结果
// cur: 当前遍历到的数字
const sum = numbers.reduce((acc, cur) => {
    console.log(`当前累加值(acc): ${acc}, 当前数字(cur): ${cur}`);
    return acc + cur; // 本次计算的结果，会成为下一次的 acc
}, 0); // 0 是初始值，第一次执行时，acc 就等于 0

console.log("最终结果:", sum); // 100
```

**执行过程详解：**

1. **初始值:** `acc` 被设置为 `0`。
2. **第一轮:** `cur` 是 `10`。函数执行 `0 + 10`，返回 `10`。这个 `10` 成为下一轮的 `acc`。
3. **第二轮:** `acc` 是 `10`，`cur` 是 `20`。函数执行 `10 + 20`，返回 `30`。这个 `30` 成为下一轮的 `acc`。
4. **第三轮:** `acc` 是 `30`，`cur` 是 `30`。函数执行 `30 + 30`，返回 `60`。这个 `60` 成为下一轮的 `acc`。
5. **第四轮:** `acc` 是 `60`，`cur` 是 `40`。函数执行 `60 + 40`，返回 `100`。
6. 数组遍历完毕，`reduce` 将最后一次的返回值 `100` 作为最终结果。

**场景二：计算数组中元素出现的次数**

```javascript
const fruits = ["apple", "banana", "apple", "orange", "banana", "apple"];

const fruitCount = fruits.reduce((acc, cur) => {
    if (acc[cur]) {
        acc[cur]++; // 如果这个水果在对象里已经存在，就+1
    } else {
        acc[cur] = 1; // 如果是第一次见到，就设为1
    }
    return acc;
}, {}); // 初始值是一个空对象 {}

console.log(fruitCount); // 输出: { apple: 3, banana: 2, orange: 1 }
```

**总结一下：**
`reduce` 是一个高级工具，它可以完成求和、求积、找最大值、数组去重、数组扁平化等等几乎所有数组遍历能干的事。它的核心就是**“汇总”**，将一个数组浓缩成一个你想要的任何形式的最终值（数字、字符串、对象、甚至另一个数组）。

---

### 17. 数组 `find`、`every` 和 `Array.from`

这些都是ES6+提供的非常实用的数组工具。

#### 1. `find` - 找东西

* **核心思想：** 遍历数组，**找到并返回第一个**满足你所提供测试函数的元素。如果找不到，就返回 `undefined`。
* **生活化比喻：** 你在一排书架上找一本叫《JavaScript权威指南》的书。你从第一本开始看，只要一找到，你就立刻拿着这本书走人，后面的书你连看都懒得看了。

```javascript
const users = [
    { id: 1, name: "张三", age: 20 },
    { id: 2, name: "李四", age: 25 },
    { id: 3, name: "王五", age: 20 }
];

// 找到 id 为 2 的用户
const userLiSi = users.find(user => user.id === 2);
console.log(userLiSi); // { id: 2, name: '李四', age: 25 }

// 找到第一个年龄为 20 的用户
const first20 = users.find(user => user.age === 20);
console.log(first20); // { id: 1, name: '张三', age: 20 } (找到张三就停了，不会再找王五)

// 找一个不存在的用户
const userZhaoLiu = users.find(user => user.id === 99);
console.log(userZhaoLiu); // undefined
```

#### 2. `every` - 做政审

* **核心思想：** 遍历数组，测试**是否所有元素**都满足你提供的测试函数。如果全部满足，返回 `true`；只要有一个不满足，立刻返回 `false`（并且停止遍历）。
* **生活化比喻：** 检查一箱苹果是不是都是好苹果。你一个一个检查，只要发现一个烂的，你立刻得出结论：“这箱苹果不行！”，然后就不用再检查剩下的了。只有当你检查完最后一个，发现全都是好的，你才能说：“这箱苹果是好的”。

```javascript
const scores = [85, 92, 78, 95];
const allPassed = scores.every(score => score >= 60);
console.log(allPassed); // true (因为所有分数都大于等于60)

const scores2 = [85, 45, 90];
const allPassed2 = scores2.every(score => score >= 60);
console.log(allPassed2); // false (因为检查到45时，就不满足条件了)
```

#### 3. `Array.from()` - 变身真数组

* **核心思想：** 这是一个**静态方法**，能将**类数组对象**（Array-like objects）或**可迭代对象**（iterable objects）转换成一个**真正的数组**。
* **什么是类数组对象？** 就是有 `length` 属性和索引的对象，比如函数里的 `arguments` 对象，或者通过 `document.querySelectorAll` 获取的DOM节点列表。它们看起来像数组，但不能使用 `forEach`, `map` 等数组方法。

```javascript
// 场景一：转换类数组对象
function sumArguments() {
    console.log(arguments); // { '0': 1, '1': 2, '2': 3, length: 3 } (这是个类数组对象)
    // arguments.reduce is not a function (直接用会报错)

    const realArray = Array.from(arguments); // 变身！
    return realArray.reduce((acc, cur) => acc + cur, 0);
}
console.log(sumArguments(1, 2, 3)); // 6

// 场景二：转换字符串
const str = "hello";
const charArray = Array.from(str);
console.log(charArray); // ["h", "e", "l", "l", "o"]

// 场景三：结合第二个参数（映射函数）使用
const fakeArray = { length: 3 }; // 一个空的类数组
// 创建一个长度为3的数组，并且每个元素都是它的索引值
const newArray = Array.from(fakeArray, (item, index) => index);
console.log(newArray); // [0, 1, 2]
```

**总结一下：**

* `find`: 找**一个**就收手。
* `every`: 查**所有**，一票否决。
* `Array.from`: 把“假”的变成**真**的数组。

---

### 18. 字符串常见方法

字符串是JS中最常用的数据类型之一，掌握它的常用方法能极大提高你的开发效率。

#### 1. `slice(startIndex, endIndex)` - 切片

* **功能：** 提取字符串的一部分，并返回一个新字符串，不修改原字符串。
* **参数：** `startIndex`（包含），`endIndex`（不包含）。可以是负数，表示从后往前数。
  
  ```javascript
  const message = "Hello, world!";
  console.log(message.slice(0, 5)); // "Hello" (从索引0到5，不含5)
  console.log(message.slice(7));    // "world!" (从索引7到末尾)
  console.log(message.slice(-6));   // "world!" (从倒数第6个到末尾)
  ```

#### 2. `split(separator)` - 分割

* **功能：** 使用指定的分隔符将一个字符串分割成一个**字符串数组**。
  
  ```javascript
  const csv = "2023,Tesla,Model Y";
  const parts = csv.split(",");
  console.log(parts); // ["2023", "Tesla", "Model Y"]
  
  ```

const sentence = "I love JavaScript";
const words = sentence.split(" ");
console.log(words); // ["I", "love", "JavaScript"]

const letters = "abc".split("");
console.log(letters); // ["a", "b", "c"]

```

#### 3. `toUpperCase()` / `toLowerCase()` - 大小写转换
*   **功能：** 返回一个新的字符串，分别是原字符串的大写或小写版本。
```javascript
const name = "John Doe";
console.log(name.toUpperCase()); // "JOHN DOE"
console.log(name.toLowerCase()); // "john doe"
```

#### 4. `includes(searchString)` / `startsWith(searchString)` / `endsWith(searchString)` - 包含判断

* **功能：** 判断字符串是否包含、以...开头、以...结尾，返回 `true` 或 `false`。
  
  ```javascript
  const url = "https://example.com/profile";
  console.log(url.includes("example"));   // true
  console.log(url.startsWith("https://")); // true
  console.log(url.endsWith("/profile"));   // true
  ```

#### 5. `replace(searchValue, newValue)` - 替换

* **功能：** 返回一个新字符串，其中第一个匹配 `searchValue` 的部分被 `newValue` 替换。
* **注意：** 默认只替换第一个匹配项。要全局替换，需要使用正则表达式和 `g` 标志。
  
  ```javascript
  let greeting = "Good morning, morning!";
  let newGreeting = greeting.replace("morning", "evening");
  console.log(newGreeting); // "Good evening, morning!" (只换了第一个)
  
  ```

// 全局替换
let globalGreeting = greeting.replace(/morning/g, "evening");
console.log(globalGreeting); // "Good evening, evening!"

```

#### 6. `trim()` - 去除首尾空白
*   **功能：** 返回一个新字符串，移除了原字符串两端的空白字符（空格、制表符、换行符等）。
```javascript
const userInput = "   my-username   ";
const cleanedInput = userInput.trim();
console.log(`'${cleanedInput}'`); // "'my-username'"
```

---

### 19. 两种编程思想

编程思想是比具体语法更高层次的东西，它指导我们如何组织和构建代码。

#### 1. 面向过程编程 (Procedural Programming)

* **核心思想：** 像一个**食谱**。将解决问题的步骤，一步一步地分解成一系列的**函数**。代码的焦点是**“过程”和“动作”**。数据和操作数据的函数是分离的。
* **生活化比喻：**
  * **任务：** 做一道番茄炒蛋。
  * **面向过程的思路：**
    1. `function 洗番茄()`
    2. `function 切番茄()`
    3. `function 打鸡蛋()`
    4. `function 倒油热锅()`
    5. `function 炒鸡蛋()`
    6. `function 炒番茄()`
    7. `function 放调料()`
    8. `function 出锅()`
  * 数据（番茄、鸡蛋）和动作（洗、切、炒）是分开的，你按照流程依次调用这些函数来完成任务。

#### 2. 面向对象编程 (Object-Oriented Programming, OOP)

* **核心思想：** 像一个**团队**。将现实世界的事物抽象成**对象（Object）**。每个对象都封装了自己的**数据（属性）**和**行为（方法）**。代码的焦点是**“谁（哪个对象）”**在做事情。程序由对象之间的相互协作来完成。
* **生活化比喻：**
  * **任务：** 做一道番茄炒蛋。
  * **面向对象的思路：**
    1. **创建一个“厨师”对象。**
       * **属性：** 姓名、厨艺等级。
       * **方法：** `做饭(食材)`。
    2. **创建一个“番茄”对象。**
       * **属性：** 颜色、重量。
       * **方法：** `被切()`。
    3. **创建一个“鸡蛋”对象。**
       * **属性：** 大小。
       * **方法：** `被打散()`。
  * **执行过程：** 你实例化一个`厨师`对象，然后调用它的`做饭`方法，把`番茄`和`鸡蛋`这两个对象作为参数传进去。所有的具体步骤（洗、切、炒）都被封装在`厨师`的`做饭`方法内部了。你不需要关心细节，你只关心让“厨师”这个对象去完成任务。

| 特点       | 面向过程         | 面向对象                   |
|:-------- |:------------ |:---------------------- |
| **核心**   | 动作、步骤、函数     | 实体、对象、数据和行为的统一体        |
| **组织方式** | 一系列的函数调用     | 对象之间的交互                |
| **优点**   | 简单、直观（对于小任务） | 易维护、易扩展、代码复用性高（对于复杂系统） |
| **缺点**   | 难以维护、扩展性差    | 抽象、有一定学习成本             |

---

### 20. 构造函数实现封装以及存在的问题

这是对前面构造函数知识的深化，涉及到性能问题。

#### 1. 用构造函数实现封装

封装是面向对象的三大特性之一（封装、继承、多态）。它的核心就是把**数据（属性）**和**操作数据的代码（方法）**捆绑在一起，并对外部隐藏内部的实现细节。构造函数天然地实现了这一点。

```javascript
function Cat(name, age) {
    // 属性被封装
    this.name = name;
    this.age = age;
    let _weight = 5; // 这是一个“私有”变量，外部无法直接访问

    // 方法被封装
    this.meow = function() {
        console.log(`我叫 ${this.name}，喵~`);
    };

    this.getWeight = function() {
        return _weight; // 提供一个公共方法来访问“私有”数据
    }
}

const cat1 = new Cat("咪咪", 2);
cat1.meow();
console.log(cat1.name); // 可以访问
// console.log(cat1._weight); // undefined，访问不到，实现了隐藏
console.log(cat1.getWeight()); // 只能通过指定接口访问
```

#### 2. 存在的问题：内存浪费

看起来很完美，但它有一个严重的性能问题。我们再创建一个实例：

```javascript
const cat2 = new Cat("花花", 3);

// 比较一下它们的方法
console.log(cat1.meow === cat2.meow); // 输出: false
```

为什么是 `false`？
因为**每当你 `new` 一个实例，构造函数内部的代码就会完完整整地重新执行一遍**。这意味着，`cat1` 有一个自己的 `meow` 函数，`cat2` 也有一个**全新的、一模一样的** `meow` 函数。

如果有一万只猫，内存里就会有一万个功能完全相同的 `meow` 函数副本。这是一种极大的内存浪费！因为“喵喵叫”这个技能，对于所有猫来说，应该是共享的，而不是每只猫都学一遍。

**如何解决这个问题？**
这就是下一个重要概念——**原型（Prototype）**——要解决的核心问题。我们会把共享的方法（比如 `meow`）放到构造函数的 `prototype` 对象上，这样所有实例就可以通过原型链共享同一个函数，从而大大节省内存。但这已经是我们下一个阶段的探险内容了！

---

我们已经掌握了JS中数据处理的“核武器”`reduce`，学会了更多实用的数组和字符串操作，并且开始从更高的“编程思想”层面来看待代码。最后，你也发现了构造函数模式的局限性，为我们接下来的“原型”学习埋下了完美的伏笔。非常棒，继续保持！

太棒了！我们即将进入JS中最核心、最精髓，也可能是最让新手感到“神秘”的领域——**原型与原型链**。这部分内容是理解JS继承、`instanceof`乃至框架底层原理的关键。

别担心，我会用最形象的比喻，把这层神秘的面纱彻底揭开。让我们开始第五阶段的深度探索！

---

### 21. 原型对象 `prototype`

#### 核心思想

每个**构造函数**（或者说 `class`）都有一个天生自带的、名为 `prototype` 的**对象**。这个 `prototype` 对象就像一个“**家族技能仓库**”。我们把所有实例都需要**共享**的属性和方法，都放在这个“仓库”里，而不是在构造函数里为每个实例单独创建。

#### 超详细讲解

我们回到之前那个有性能问题的 `Cat` 构造函数。

**之前的问题：**

```javascript
function Cat(name) {
    this.name = name;
    // 每 new 一次，就创建一个新的 meow 函数，浪费内存
    this.meow = function() { console.log("喵~"); };
}
```

**用 `prototype` 解决：**
我们把共享的 `meow` 方法，从构造函数内部“搬家”到 `Cat.prototype` 这个“公共仓库”里。

```javascript
// 构造函数只负责实例独有的属性
function Cat(name) {
    this.name = name;
}

// 把共享的方法，添加到构造函数的 prototype 对象上
Cat.prototype.meow = function() {
    console.log(this.name + " 说：喵~");
};

Cat.prototype.species = "猫科动物"; // 也可以放共享的属性

// --- 创建实例 ---
const cat1 = new Cat("咪咪");
const cat2 = new Cat("花花");

// --- 测试 ---
cat1.meow(); // 输出: 咪咪 说：喵~
cat2.meow(); // 输出: 花花 说：喵~
console.log(cat1.species); // 输出: 猫科动物

// 关键点：它们的 meow 方法和 species 属性是同一个吗？
console.log(cat1.meow === cat2.meow); // true! 共享成功！
console.log(cat1.species === cat2.species); // true!
```

**发生了什么？**
`cat1` 和 `cat2` 在被创建时，它们的内部并没有 `meow` 方法。当代码执行 `cat1.meow()` 时：

1. JS引擎先在 `cat1` 对象**自身**上找，发现没有 `meow`。
2. 于是，JS引擎会顺着一条“神秘通道”（后面会讲，它叫 `__proto__`），去到 `cat1` 的“家族技能仓库”，也就是 `Cat.prototype` 里去找。
3. 在 `Cat.prototype` 里，它找到了 `meow` 方法！于是就执行它。

**重要：** 在 `prototype` 的方法中，`this` 依然指向**调用该方法的实例对象**。当 `cat1.meow()` 执行时，`meow` 里的 `this` 就是 `cat1`；当 `cat2.meow()` 执行时，`this` 就是 `cat2`。

**总结一下：**
`prototype` 是**构造函数**的一个属性，它是一个对象。它的作用是存放所有实例**需要共享的成员（主要是方法）**，以达到节省内存和实现继承的目的。

---

### 22. `constructor` 属性以及应用

#### 核心思想

在每个**原型对象 (`prototype`)** 上，都有一个天生自带的、名为 `constructor` 的属性，它**指回**了这个原型对象所属的**构造函数本身**。

#### 超详细讲解

我们继续看 `Cat.prototype` 这个对象：

```javascript
function Cat(name) {
    this.name = name;
}
// Cat.prototype 是一个对象，我们看看它里面有什么
console.log(Cat.prototype); 
```

在浏览器控制台里展开 `Cat.prototype`，会看到它至少有一个属性：

```
{
    constructor: f Cat(name), // `constructor` 属性指向 Cat 函数自己！
    __proto__: Object
}
```

**这个 `constructor` 属性有什么用？**

1. **身份标识**：它告诉我们，一个对象实例是由哪个构造函数创建的。由于实例可以访问到原型上的属性，所以：
   
   ```javascript
   const cat1 = new Cat("咪咪");
   console.log(cat1.constructor);      // 输出: f Cat(name)
   console.log(cat1.constructor === Cat); // true!
   ```
   
   这让我们可以在不知道对象具体来源时，动态地判断它的“出身”。

2. **创建同类新对象**：在某些场景下，你可能只有一个对象实例，但想用它来创建另一个同类型的实例。
   
   ```javascript
   function createAnother(instance) {
       // 通过实例的 constructor 属性，就能拿到它的构造函数，然后创建新实例
       return new instance.constructor("我是新来的");
   }
   
   const cat1 = new Cat("咪咪");
   const cat3 = createAnother(cat1);
   
   console.log(cat3.name); // 输出: 我是新来的
   console.log(cat3 instanceof Cat); // true
   ```

**一个常见的坑：重写 `prototype`**
有时候为了方便，我们会直接用一个新对象覆盖掉原来的 `prototype`：

```javascript
function Dog(name) { this.name = name; }

Dog.prototype = {
    // 这样写会把原来的 prototype 对象整个替换掉
    // 原来 prototype 里的 constructor 属性也跟着丢了！
    bark: function() { console.log("汪！"); }
};

const dog1 = new Dog("旺财");
console.log(dog1.constructor === Dog); // false!
console.log(dog1.constructor === Object); // true! (因为新对象的 constructor 默认指向 Object)
```

**修正方法：手动指回来**

```javascript
Dog.prototype = {
    constructor: Dog, // 手动把 constructor 指回正确的构造函数
    bark: function() { console.log("汪！"); }
};

const dog2 = new Dog("来福");
console.log(dog2.constructor === Dog); // true! 问题解决。
```

**总结一下：**
`constructor` 属性是原型对象上的一个“回指指针”，它指向关联的构造函数。它主要用于**身份识别**和**创建同类对象**。

---

### 23. 对象原型 `__proto__`

`prototype` 是挂在**构造函数**上的，而 `__proto__` (前后各有两个下划线) 是挂在**每个实例对象**上的。

#### 核心思想

每个**对象实例**都有一个 `__proto__` 属性，它就是我们之前提到的那条“**神秘通道**”。它指向创建该实例的**构造函数的原型对象 (`prototype`)**。

#### 超详细讲解

`__proto__` 是连接“实例”和“公共仓库”的桥梁。

```javascript
function Cat(name) {
    this.name = name;
}
Cat.prototype.species = "猫科动物";

const cat1 = new Cat("咪咪");

// --- 揭开神秘通道的面纱 ---
console.log(cat1.__proto__); // 打印出的内容和 Cat.prototype 完全一样

// 验证一下
console.log(cat1.__proto__ === Cat.prototype); // true!
```

**关系图：**

```
[构造函数]              [原型对象]
  Cat ---------------> Cat.prototype
   ^                        ^
   |                        |
   | .constructor           | .__proto__
   |                        |
   +---------------------- [实例对象]
                             cat1
```

* `Cat` 通过 `.prototype` 指向它的原型对象。
* `Cat.prototype` 通过 `.constructor` 指回 `Cat`。
* 实例 `cat1` 通过 `.__proto__` 指向 `Cat.prototype`。

**`__proto__` 的作用是什么？**
它的唯一作用就是**构成原型链**（下一节讲），让JS引擎在查找属性时，能从实例自身顺着它一路找到原型，再到原型的原型...

**重要提示：**
`__proto__` 是一个非标准的历史遗留属性，虽然现在主流浏览器都支持，但在实际开发中，**不推荐直接操作它**。ES6 提供了标准的替代方法：

* `Object.getPrototypeOf(obj)`: 获取一个对象的原型（等同于 `obj.__proto__`）。
* `Object.setPrototypeOf(obj, proto)`: 设置一个对象的原型。

**总结一下：**
`__proto__` 是**实例对象**的一个内部属性，它指向其构造函数的 `prototype` 对象。它是实现原型继承的**核心链接**。

---

### 24. 原型继承

这是JS在 `class` 出现之前实现继承的经典方式。

#### 核心思想

继承的核心思想是**让一个构造函数（子类）的原型，继承自另一个构造函数（父类）的原型**，从而让子类的实例也能使用父类原型上的方法。

#### 超详细讲解

假设我们有一个 `Animal` 父类，和一个 `Cat` 子类。

```javascript
// 父类
function Animal(name) {
    this.name = name;
    this.sleep = function() { console.log("Zzz..."); };
}
Animal.prototype.eat = function(food) {
    console.log(this.name + " 正在吃 " + food);
};

// 子类
function Cat(name, color) {
    // 步骤一：借用父类构造函数，继承父类的实例属性
    // 使用 call/apply，把 Animal 里的 this 指向当前的 cat 实例
    Animal.call(this, name); 

    this.color = color; // 子类自己的实例属性
}

// 步骤二：实现原型继承 (最关键的一步)
// 让 Cat 的原型指向一个 Animal 的实例。
// 这样 Cat.prototype.__proto__ 就会指向 Animal.prototype
Cat.prototype = Object.create(Animal.prototype);

// 步骤三：修复 constructor 指向
Cat.prototype.constructor = Cat;

// 步骤四：给子类原型添加自己的方法
Cat.prototype.meow = function() {
    console.log("喵~");
};

// --- 测试 ---
const myCat = new Cat("小白", "白色");

myCat.sleep();   // "Zzz..." (继承自 Animal 的实例方法)
myCat.eat("鱼"); // "小白 正在吃 鱼" (继承自 Animal.prototype 的原型方法)
myCat.meow();    // "喵~" (自己的原型方法)
console.log(myCat.name);  // "小白"
console.log(myCat.color); // "白色"
```

**`Object.create(proto)`** 是实现原型继承的现代标准方法。它会创建一个新对象，并将这个新对象的 `__proto__` 指向你传入的 `proto` 对象。这比 `Cat.prototype = new Animal()` 的旧方法更好，因为它不会执行 `Animal` 的构造函数，避免了不必要的属性创建。

**总结一下：**
JS的原型继承组合了**构造函数借用**（继承实例属性）和**原型链继承**（继承共享方法）两种技术，以达到完整的继承效果。

---

### 25. 原型链 (Prototype Chain) 和 `instanceof`

#### 核心思想

* **原型链：** 当你试图访问一个对象的属性时，JS引擎会先在对象自身上查找。如果找不到，就会通过 `__proto__` 指向的原型对象上查找。如果还找不到，就再通过原型对象的 `__proto__` 继续向上查找，直到找到属性或者到达原型链的终点 `null`。这条由 `__proto__` 串联起来的**查找路径**，就是**原型链**。
* **`instanceof` 运算符：** 用来检测一个**构造函数的 `prototype`** 是否出现在一个**实例对象的原型链**上。

#### 超详细讲解

**原型链的终点**
所有对象的原型链最终都会指向 `Object.prototype`，而 `Object.prototype` 的 `__proto__` 是 `null`，这就是原型链的尽头。

我们用 `myCat` 实例来画出它的完整原型链：

```
myCat (实例)
   |
   | .__proto__
   V
Cat.prototype (Cat的原型)
   |
   | .__proto__
   V
Animal.prototype (Animal的原型)
   |
   | .__proto__
   V
Object.prototype (所有对象的最终原型)
   |
   | .__proto__
   V
null (原型链终点)
```

当执行 `myCat.toString()` 时（`toString`是`Object.prototype`上的方法）：

1. 在 `myCat` 自身找 `toString` -> 找不到。
2. 去 `Cat.prototype` 找 -> 找不到。
3. 去 `Animal.prototype` 找 -> 找不到。
4. 去 `Object.prototype` 找 -> 找到了！执行它。

**`instanceof` 的工作原理**
`instanceof` 就是沿着这条原型链进行检查。

`object instanceof Constructor`

它的检查逻辑是：

1. 查看 `Constructor.prototype` 是否等于 `object.__proto__`？如果是，返回 `true`。
2. 如果不是，再看 `Constructor.prototype` 是否等于 `object.__proto__.__proto__`？如果是，返回 `true`。
3. ...以此类推，沿着原型链一直找下去，直到原型链末端。如果一直没找到，就返回 `false`。

```javascript
const myCat = new Cat("小白", "白色");

console.log(myCat instanceof Cat);      // true (因为 Cat.prototype 在 myCat 的原型链上)
console.log(myCat instanceof Animal);   // true (因为 Animal.prototype 也在 myCat 的原型链上)
console.log(myCat instanceof Object);   // true (因为 Object.prototype 也在 myCat 的原型链上)

function Dog() {}
console.log(myCat instanceof Dog);      // false (因为 Dog.prototype 不在 myCat 的原型链上)
```

**总结一下：**
原型链是JS实现继承的**底层机制**，它定义了属性和方法的**查找顺序**。`instanceof` 是一个**检测工具**，它通过检查原型链来判断一个对象和一个构造函数之间是否存在**继承关系**。

---

现在我们已经理解了JS面向对象编程的底层逻辑。`prototype`, `constructor`, `__proto__` 这“三位一体”的关系，以及它们如何共同构建起强大的原型链继承体系。这是从“会用JS”到“懂JS”的巨大飞跃！

<div style="display: flex; justify-content: space-between;">
  <a href="./JS 进阶-3.md">‹ 上一篇：JS进阶-3</a>
  <a href="./JS 进阶-5.md">下一篇：JS进阶-5 ›</a>
</div>

