# JS进阶

---

<div style="display: flex; justify-content: space-between;">
  <a href="./JS 进阶-1.md">‹ 上一篇：JS进阶-1</a>
  <a href="./JS 进阶-3.md">下一篇：JS进阶-3 ›</a>
</div>


### 6. ES6 箭头函数 (Arrow Functions) 

​	    ES6 箭头函数是 ECMAScript 2015 (ES6) 中引入的一种新的函数定义方式，它提供了一种更简洁的语法来书写函数表达式。 箭头函数是**一种更简洁的函数写法**，并且它有一个超级重要的特性：它**没有自己的 `this`**，它会像个“乖孩子”一样，直接使用外层“爸爸妈妈”的 `this`。

 #### 与传统函数的区别 

要全面理解箭头函数，关键在于了解它与传统 `function` 关键字定义的函数之间的几个核心区别： 

 **1. 更简洁的语法** 

 箭头函数的语法非常简洁，这也是它最直观的优点。 

 *  **单一参数：** 当只有一个参数时，可以省略参数外面的括号。 
 *  **单一表达式：** 如果函数体只包含一个表达式，可以省略花括号 `{}`，并且该表达式的结果会作为函数的返回值被隐式返回。 

 比如，一个将参数乘以 2 的函数： 

 * **传统函数：** 
 ```javascript
 const double = function(x) {
     return x * 2
 };
 ```

 * **箭头函数：** 
 ```javascript
 const double = x => x * 2;
 ```

**普通函数:**

```javascript
// 完整版
const regularAdd = function(a, b) {
    return a + b;
};
```

**箭头函数：**

1. **去掉 `function` 关键字，加上胖箭头 `=>`**

   ```javascript
   const arrowAdd1 = (a, b) => {
       return a + b;
   };
   ```

2. **如果函数体只有一行代码，并且是 `return` 语句，可以去掉 `{}` 和 `return`**

   ```javascript
   const arrowAdd2 = (a, b) => a + b; // 哇，好短！
   ```

3. **如果只有一个参数，可以把参数的 `()` 也去掉**

   ```javascript
   const square = x => x * x; // 计算一个数的平方
   ```

4. **如果没有参数，`()` 必须保留**

   ```javascript
   const sayHi = () => console.log("Hi!");
   ```

 **2. `this` 的词法绑定** 

 这是箭头函数最重要的特性，也是它和传统函数最本质的区别。 

 * **传统函数：** `this` 的值是在函数被调用时动态决定的，它指向调用该函数的对象。在很多情况下，比如在回调函数中，这会导致 `this` 指向非预期的对象（例如 `window`），因此我们经常需要使用 `let that = this;` 或 `.bind(this)` 这样的技巧来保存 `this` 的指向。 

 * **箭头函数：** 箭头函数没有自己的 `this` 绑定。 它会捕获其所在上下文（即定义时所在的词法作用域）的 `this` 值作为自己的 `this` 值。 这就完美地解决了传统函数中 `this` 指向不定的问题。 

这是箭头函数的“杀手锏”功能，也是面试时最喜欢问的。

* **普通函数里的 `this`**：像个“变色龙”。谁调用它，`this` 就指向谁。这导致 `this` 经常乱跑，非常烦人。
* **箭头函数里的 `this`**：像个“忠犬”。它在被定义的那一刻，就锁定了外层作用域的 `this`，永远不会改变。

来看一个经典的“头痛”场景：

假设我们有一个 `Timer` 对象，它想在1秒后打印出自己的名字。

**用普通函数的失败尝试：**

```javascript
const timer = {
    name: "滴答定时器",
    start: function() {
        console.log(this.name + " 启动了！"); // 这里的 this 指向 timer 对象，没问题

        // 1秒后，想再次打印名字
        setTimeout(function() {
            // 问题来了！
            // setTimeout 里的函数是被浏览器（window对象）调用的
            // 所以这里的 this 指向了 window，而不是 timer！
            console.log("一秒后，我的名字是：" + this.name); // 输出: 一秒后，我的名字是：undefined
        }, 1000);
    }
};

timer.start();
```

为了解决这个问题，以前的程序员发明了一种“丑陋”的办法：
`const that = this;` // 先把正确的 `this` 存起来

```javascript
const timer = {
    name: "滴答定时器",
    start: function() {
        console.log(this.name + " 启动了！");

        const that = this; // 在这里，this还是timer对象，我们用that把它存起来！
                           // "that" "self" "_this" 都是常见的名字

        setTimeout(function() {
            // 这里面的 this 虽然还是 window，但我们不用它了！
            // 我们用之前保存好的 that，它永远指向 timer 对象。
            console.log("一秒后，我的名字是：" + that.name); 
        }, 1000);
    }
};
timer.start();
```

**用箭头函数的完美解决：**

```javascript
const timerArrow = {
    name: "箭头函数定时器",
    start: function() {
        console.log(this.name + " 启动了！"); // 这里的 this 指向 timerArrow 对象

        setTimeout(() => {
            // 箭头函数没有自己的 this!
            // 它会沿着作用域链往外找，找到了 start 函数的 this
            // 而 start 函数的 this 就是 timerArrow 对象！完美！
            console.log("一秒后，我的名字是：" + this.name); // 输出: 一秒后，我的名字是：箭头函数定时器
        }, 1000);
    }
};

timerArrow.start();
```

 **在对象方法中使用定时器：** 

 ```javascript
 function Timer() {
  this.seconds = 0;
 
  setInterval(() => {
  this.seconds++; // 这里的 this 指向 Timer 实例
  }, 1000);
 }
 
 const timer = new Timer();
 ```
 在这个例子中，`setInterval` 的回调函数是一个箭头函数，它内部的 `this` 继承自 `Timer` 函数的 `this`，所以可以正确地访问到 `this.seconds`。 

 **3. 其他区别** 

 * **不能作为构造函数：** 不能使用 `new` 关键字来调用箭头函数，否则会抛出错误。 这是因为箭头函数没有自己的 `this`，也没有 `prototype` 属性。 
 * **没有 `arguments` 对象：** 在箭头函数内部，不能直接访问 `arguments` 对象。如果需要获取所有传入的参数，可以使用剩余参数（rest parameters） `...args` 的语法。 
 * **不能用作 `Generator` 函数：** 在箭头函数内部不能使用 `yield` 关键字。 

 ### 适用场景 

 基于以上特点，我认为箭头函数特别适合以下场景： 

 *  **需要保持 `this` 指向的简洁回调函数：** 这是最常见的应用场景，例如在 `setTimeout`, `setInterval`, `Promise.then`, `Array.prototype.map` 等方法中。 
 * **语法简单的纯函数：** 对于那些只接受输入并返回输出，不依赖 `this` 或 `arguments` 的简单函数，箭头函数能让代码更简短易读。 

 ### 不适用场景 

 当然，箭头函数并非在所有情况下都优于传统函数。在以下情况，我还是会选择使用传统函数： 

 * **定义对象的方法：** 当需要让方法中的 `this` 指向该对象实例时，应该使用传统函数。 
 * **需要动态 `this` 的场景：** 例如在事件监听中，如果需要 `this` 指向触发事件的 DOM 元素，就需要使用传统函数。 
 * **需要 `arguments` 对象时。** 
 * **需要作为构造函数来创建实例时。** 

 总的来说，我对箭头函数的理解是，它不仅仅是一个语法糖，更是对 JavaScript 中 `this` 机制的一种重要优化和补充。在开发中，我会根据具体的需求和场景，判断是使用箭头函数还是传统函数，以编写出更清晰、更健壮的代码。

​	箭头函数是写函数的“快捷方式”，特别适合用在回调函数里（比如 `setTimeout`, `forEach`）。它最大的优点是解决了传统函数 `this` 指向漂移的世纪难题。

---

### 7. 数组解构 (Array Destructuring)

这个功能就像“批量取出”。你有一袋水果，你想直接拿到苹果、香蕉，而不是一个一个从袋子里掏。

#### 核心思想

**按照位置**，从数组中提取值，然后赋给新的变量。

#### 超详细讲解

**没有解构的“古代”：**

```javascript
const fruits = ["苹果", "香蕉", "橙子"];

const apple = fruits[0];
const banana = fruits[1];
const orange = fruits[2];

console.log(apple, banana, orange); // 写好多行，好累...
```

**有了数组解构的“现代”：**

```javascript
const fruits = ["苹果", "香蕉", "橙子"];

// 一行代码搞定！
// 把 fruits[0] 赋值给 a, fruits[1] 赋值给 b, fruits[2] 赋值给 c
const [a, b, c] = fruits; 

console.log(a, b, c); // 输出: 苹果 香蕉 橙子
```

等号左边的 `[...]` 是一种**模式**，它告诉JS：“请把右边数组里的东西，按照这个模式（位置）放进我指定的变量里。”

**更多酷炫玩法：**

1. **我只想要部分值，可以跳过**
   
   ```javascript
   const numbers = [10, 20, 30, 40, 50];
   const [first, , third, , fifth] = numbers; // 用逗号占位，跳过不想要的
   console.log(first, third, fifth); // 输出: 10 30 50
   ```

2. **我想要第一个，剩下的全给我（结合剩余参数）**
   
   ```javascript
   const scores = [100, 95, 88, 76, 65];
   const [champion, ...others] = scores;
   console.log(champion); // 输出: 100
   console.log(others);   // 输出: [95, 88, 76, 65] (一个新数组！)
   ```

3. **万一数组里没那么多东西呢？给个默认值**
   
   ```javascript
   const team = ["张三"];
   const [leader, member = "李四"] = team; // 如果 team[1] 不存在，member 就用默认值 "李四"
   console.log(leader);  // 输出: 张三
   console.log(member);  // 输出: 李四
   ```

**总结一下：**
数组解构是根据**位置**从数组中快速取值的语法糖。它让代码更短、更易读。

---

### 8. 对象解构 (Object Destructuring)

这个和数组解构是亲兄弟，但它更智能，因为它不靠位置，而是靠“名字”。

#### 核心思想

**按照属性名**，从对象中提取值，然后赋给变量。

#### 超详细讲解

**没有解构的“古代”：**

```javascript
const person = {
    name: "小美",
    age: 28,
    city: "上海"
};

const name = person.name;
const age = person.age;
console.log(name, age); // 好麻烦，尤其是对象属性多的时候
```

**有了对象解构的“现代”：**

```javascript
const person = {
    name: "小美",
    age: 28,
    city: "上海"
};

// 一行代码搞定！
// 变量名 {name} 必须和对象的属性名 "name" 对应
const { name, age } = person; 

console.log(name, age); // 输出: 小美 28
```

等号左边的 `{...}` 也是一种**模式**，它告诉JS：“请到右边的对象里，找到和我**同名**的属性，把它的值给我。”

**更多酷炫玩法：**

1. **我拿到的变量想换个名字**
   
   ```javascript
   const user = { id: 42, username: "SuperCoder" };
   // 从 user 中找到 username 属性，然后把它赋值给一个叫 newName 的新变量
   const { username: newName } = user; 
   console.log(newName); // 输出: SuperCoder
   // console.log(username); // 报错！因为已经改名叫 newName 了
   ```

2. **万一对象里没这个属性呢？给个默认值**
   
   ```javascript
   const product = { title: "一本书", price: 99 };
   const { title, stock = 0 } = product; // 如果 product.stock 不存在，stock 就用默认值 0
   console.log(title);  // 输出: 一本书
   console.log(stock);  // 输出: 0
   ```

3. **解构一个嵌套很深的对象**
   
   ```javascript
   const order = {
       orderId: "SN12345",
       shippingInfo: {
           address: "未来路1号",
           contact: {
               phone: "13800138000"
           }
       }
   };
   // 我只想拿到电话号码
   const { shippingInfo: { contact: { phone } } } = order;
   console.log(phone); // 输出: 13800138000
   ```
   
   这个看起来复杂，其实就是一层一层扒开对象，直到拿到你想要的东西。

**总结一下：**
对象解构是根据**属性名**从对象中快速取值的语法糖。它在处理复杂的JSON数据或组件props时超级有用。

---

### 9. forEach 遍历数组

这是循环遍历数组最常用、最直观的方法之一。

#### 核心思想

`forEach` 就像一个“数组导游”，它会不偏不倚地**访问数组中的每一个元素**，并对每个元素执行你指定的**同一个操作**。

#### 超详细讲解

**传统的 `for` 循环：**

```javascript
const colors = ["red", "green", "blue"];

for (let i = 0; i < colors.length; i++) {
    console.log("颜色是：" + colors[i]);
}
// 需要自己管索引 i，还要判断长度，有点繁琐
```

**优雅的 `forEach`：**
`forEach` 是一个数组方法，所以你用 `数组.forEach()` 的形式来调用它。它接收一个函数作为参数（这个函数叫回调函数）。

```javascript
const colors = ["red", "green", "blue"];

colors.forEach(function(color) {
    // 这个匿名函数，会为数组里的每个元素都执行一次
    // 第一次执行，color 是 "red"
    // 第二次执行，color 是 "green"
    // 第三次执行，color 是 "blue"
    console.log("颜色是：" + color);
});
```

**结合箭头函数，代码更美观：**

```javascript
const colors = ["red", "green", "blue"];
colors.forEach(color => console.log("颜色是：" + color));
```

**`forEach` 的回调函数还能提供更多信息**
`forEach` 在调用你的回调函数时，会默默地传给它三个参数：

1. **当前元素** (element) - 你最常用的
2. **当前索引** (index) - 有时候也需要
3. **原始数组** (array) - 很少用

```javascript
const fruits = ["苹果", "香蕉"];
fruits.forEach((fruit, index, originalArray) => {
    console.log(`在索引 ${index} 的位置是 ${fruit}`);
    // console.log(originalArray); // 会打印出 ["苹果", "香蕉"]
});
```

**重要提醒：**
`forEach` 有个特点：它**不能中途停止**（不能用 `break`），也不能跳过（不能用 `continue`）。一旦开始，就必须遍历完整个数组。如果你需要中途退出循环，还是得用传统的 `for` 循环。

**总结一下：**
当你需要对数组里的**每一个**元素都做同样一件事时，`forEach` 是最简洁、最易读的选择。

---

### 10. 创建对象的几种方式

在JS里，万物皆可对象。创建对象就像搭积木，有不同的搭建方法。

#### 核心思想

对象是“键值对”的集合。根据场景的复杂度和复用性，我们可以选择不同的方法来创建它们。

#### 超详细讲解

**方法一：对象字面量 (Object Literal) - 最常用！**
这是最简单、最直接的方法，就像你直接在纸上写一个清单。

```javascript
const myCat = {
    name: "咪咪",
    age: 2,
    hobbies: ["睡觉", "吃饭"],
    meow: function() {
        console.log("喵~");
    }
};

console.log(myCat.name); // 咪咪
myCat.meow(); // 喵~
```

* **优点：** 语法简单、直观、易读。
* **缺点：** 如果要创建很多只猫，你得复制粘贴很多次，代码重复。
* **适用场景：** 创建单个、唯一的对象。

**方法二：构造函数 (Constructor Function) - 经典模式**
这就像一个“模具”，可以批量生产同样结构的对象。

```javascript
// 构造函数名通常首字母大写
function Cat(name, age) {
    // `this` 指向即将被创建的新对象
    this.name = name;
    this.age = age;
    this.meow = function() {
        console.log("喵~");
    };
}

// 使用 `new` 关键字来创建实例
const cat1 = new Cat("花花", 3);
const cat2 = new Cat("小黑", 1);

console.log(cat1.name); // 花花
console.log(cat2.name); // 小黑
```

* **优点：** 实现了代码复用，可以创建一类对象。
* **缺点：** 语法相对老式，`this` 的用法需要注意。
* **适用场景：** 在 ES6 `class` 出现之前，这是JS面向对象编程的标准方式。

**方法三：ES6 Class - 现代标准**
`class` 是构造函数的“升级版”，是“语法糖”，让对象的创建更像其他面向对象语言，更清晰。

```javascript
class Cat {
    // 构造器，当 new 的时候自动调用
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    // 方法可以直接写在 class 内部
    meow() {
        console.log(`${this.name} 说：喵~`);
    }
}

const cat1 = new Cat("小白", 4);
cat1.meow(); // 小白 说：喵~
```

* **优点：** 语法清晰，结构化强，是目前JS中创建可复用对象的**主流方式**。
* **缺点：** 本质上还是基于原型和构造函数，对于新手来说，`this` 的概念依然存在。
* **适用场景：** 绝大多数需要复用对象结构的场景。

**方法四：工厂函数 (Factory Function) - 简单实用**
就是一个普通的函数，但它的唯一工作就是“生产”并返回一个对象。

```javascript
function createCat(name, age) {
    return {
        name: name,
        age: age,
        meow: function() {
            console.log("喵~");
        }
    };
}

const cat1 = createCat("灰灰", 5);
console.log(cat1.name); // 灰灰
```

* **优点：** 非常简单，不涉及 `this` 和 `new`，可以很好地封装私有数据（利用闭包）。
* **缺点：** 无法判断对象的具体类型（比如 `cat1` 是不是由 `createCat` 创建的）。
* **适用场景：** 需要创建对象但又想避免 `this` 和 `new` 的复杂性时。

**总结一下：**

* **随便创建一个用：** 用**对象字面量**。
* **要创建一堆相似的对象：** 优先使用 **ES6 Class**，这是现代JS的标配。
* **面试/了解历史：** 知道**构造函数**是怎么回事。
* **想换个口味/避免`this`：** 可以试试**工厂函数**。

---

<div style="display: flex; justify-content: space-between;">
  <a href="./JS 进阶-1.md">‹ 上一篇：JS进阶-1</a>
  <a href="./JS 进阶-3.md">下一篇：JS进阶-3 ›</a>
</div>


