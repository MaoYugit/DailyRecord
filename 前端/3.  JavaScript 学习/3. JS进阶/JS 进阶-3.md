# JS进阶

---

### 11. 构造函数 (Constructor Function)

这个概念是理解JS中“类”和“对象”的基石。

#### 核心思想

**构造函数就是一个“对象蓝图”或“模具”**。它本身是一个普通的函数，但我们以一种特殊的方式（使用 `new` 关键字）来调用它，目的是为了**批量生产结构相同的对象**。

#### 超详细讲解

想象一下，你要开一个宠物店，店里有很多只猫。每只猫都有名字（name）和年龄（age）。

**没有构造函数的笨办法：**
你得一只一只地手动创建。

```javascript
const cat1 = { name: "咪咪", age: 2 };
const cat2 = { name: "花花", age: 3 };
const cat3 = { name: "小黑", age: 1 };
// ... 如果有100只猫，你要写100遍，太可怕了！
```

**使用构造函数的聪明办法：**
我们先设计一个“猫的蓝图”。

```javascript
// 构造函数有一个约定俗成的规矩：函数名首字母大写，以区分普通函数。
function Cat(name, age) {
    // 1. `this` 在这里是一个神奇的关键字。它指向“即将被创建出来的那个新对象”。
    // 2. 下面这两行代码，就是在给这个“未来的新对象”添加属性。
    this.name = name;
    this.age = age;

    // 也可以添加方法
    this.meow = function() {
        console.log(`我叫 ${this.name}，喵~`);
    };

    // 3. 构造函数默认会“隐式地”返回 this，你不需要写 return this;
}

// --- 使用蓝图来生产猫！---
// 使用 `new` 关键字，就像按下了机器的“生产”按钮
const cat1 = new Cat("咪咪", 2);
const cat2 = new Cat("花花", 3);

// --- 检查产品 ---
console.log(cat1.name); // 输出: 咪咪
console.log(cat2.age);  // 输出: 3
cat1.meow();           // 输出: 我叫 咪咪，喵~
cat2.meow();           // 输出: 我叫 花花，喵~
```

**总结一下：**
构造函数就是一个模板。`new` + `构造函数` 这个组合，就是JS里创建特定类型对象实例的经典方式。它解决了“重复创建相似对象”的问题。

---

### 12. `new` 实例化执行过程

`new` 看起来只是一个简单的关键字，但它在背后默默地为你做了四件大事。理解这四步，你就洞悉了JS对象创建的秘密。

#### 核心思想

`new` 是一个自动化的“四步生产线”，它接收一个“蓝图”（构造函数），产出一个“成品”（实例对象）。

#### 超详细讲解

当我们执行 `const cat1 = new Cat("咪咪", 2);` 这行代码时，JS引擎在背后悄悄地做了以下四件事：

1. **创建一个全新的空对象**
   
   * JS在内存中凭空创建了一个崭新的、光秃秃的空对象。
   * `// 伪代码: const newObject = {};`

2. **将新对象的原型链接到构造函数的原型**
   
   * 这是最关键也最抽象的一步。你可以把它理解为“认祖归宗”。
   * 新创建的空对象内部有一个 `__proto__` 属性，`new` 会让这个属性指向构造函数 `Cat` 的 `prototype` 属性。
   * `// 伪代码: newObject.__proto__ = Cat.prototype;`
   * **说人话就是：** “告诉这个新来的小猫，你的‘家族技能’（比如所有猫都会的爬树方法）都记录在 `Cat.prototype` 这个本子上，以后要用就去那里找。” (我们后面会更详细地讲原型)

3. **将构造函数的 `this` 指向这个新对象，并执行构造函数内部的代码**
   
   * `new` 会把构造函数 `Cat` 里的 `this` 强行绑定到第一步创建的那个空对象上。
   * 然后执行函数体里的代码：`this.name = "咪咪";` 和 `this.age = 2;`。
   * 于是，这个原本空空如也的对象，现在就被填充了属性和方法。
   * `// 伪代码: Cat.call(newObject, "咪咪", 2);`

4. **返回这个新对象**
   
   * 如果构造函数里没有手动 `return` 一个其他对象，那么 `new` 命令会自动 `return` 这个已经加工好的新对象（也就是 `newObject`）。
   * 最终，`cat1` 变量就接收到了这个包含了 `name` 和 `age` 属性的新对象。

**生活化比喻：DIY电脑**
你拿着一份“电脑组装说明书”（构造函数 `Cat`）。

1. **第一步：** 你拿出一个空的电脑机箱 (`new` 创建空对象)。
2. **第二步：** 你心里清楚，这台电脑以后要能装Windows系统（将机箱和“标准PC规范”`prototype` 关联起来）。
3. **第三步：** 你打开说明书，按照指示，把CPU、内存条、硬盘（`name`, `age`等参数）装进机箱里（执行构造函数，`this` 就是机箱）。
4. **第四步：** 盖上机箱盖，一台完整的电脑组装好了，可以拿去用了（`return` 这个新对象）。

**总结一下：**
`new` 不仅仅是“创建”，它是一个包含**创建空对象、链接原型、执行构造、返回对象**的完整流程。

---

### 13. 实例成员和静态成员

这个概念区分了什么是“个人财产”，什么是“集体财产”。

#### 核心思想

* **实例成员**：是属于每个**实例对象**私有的属性和方法。就像每个人的身份证号和姓名，是独一无二的。
* **静态成员**：是属于**构造函数（或类）本身**的属性和方法。就像“人类”这个物种的平均身高，是属于整个群体的特征，而不是某个具体的人。

#### 超详细讲解

我们继续用 `Cat` 的例子，这次用 ES6 `class` 语法，因为它更清晰地体现了这个区别。

```javascript
class Cat {
    // --- 实例成员 ---
    // constructor 内部定义的，都是实例成员，通过 this 添加
    constructor(name) {
        this.name = name; // `name` 是实例属性
        this.personalSecret = "我偷偷藏了小鱼干";
    }

    // 直接定义在 class 里的方法，也是实例方法
    meow() {
        console.log(`${this.name} 说：喵~`);
    }

    // --- 静态成员 ---
    // 使用 `static` 关键字定义的，都是静态成员
    static species = "猫科动物"; // `species` 是静态属性

    static sayFamily() { // `sayFamily` 是静态方法
        // 注意：静态方法里不能用 this.name，因为 this 指向 Cat 类本身，而不是某个实例
        console.log("我们都属于" + this.species);
    }
}

// 创建实例
const cat1 = new Cat("小白");
const cat2 = new Cat("小黄");

// --- 访问实例成员 ---
// 必须通过实例对象来访问
console.log(cat1.name); // 输出: 小白
console.log(cat2.personalSecret); // 输出: 我偷偷藏了小鱼干
cat1.meow(); // 输出: 小白 说：喵~

// 你不能通过类来访问实例成员
// console.log(Cat.name); // 报错或 undefined

// --- 访问静态成员 ---
// 必须通过类本身来访问
console.log(Cat.species); // 输出: 猫科动物
Cat.sayFamily();         // 输出: 我们都属于猫科动物

// 你不能通过实例来访问静态成员（虽然某些情况下可以，但不推荐，也不符合规范）
// console.log(cat1.species); // undefined
```

**总结一下：**

* **实例成员**：定义在 `constructor` 里或直接在 `class` 里写的方法。需要 `new` 一个对象出来，然后用 `对象.成员` 的方式访问。
* **静态成员**：用 `static` 关键字修饰。不需要 `new`，直接用 `类名.成员` 的方式访问。

---

### 14. 基本包装类型 (Primitive Wrapper Types)

这是一个JS为了让你方便而设计的“隐身仆人”。

#### 核心思想

JavaScript中有两种数据类型：**基本类型**（string, number, boolean, null, undefined, symbol, bigint）和**引用类型**（Object, Array, Function等）。基本类型本身只是一个简单的值，它没有属性和方法。但JS为了让我们能方便地操作它们，提供了一个“幕后机制”：当你试图对一个基本类型值调用方法时，JS会**临时**地把它包装成一个对应的对象，执行完操作后，再把这个临时对象销毁。

#### 超详细讲解

我们来看一个你肯定写过的代码：

```javascript
let myString = "hello world";
let upperString = myString.toUpperCase(); // 把字符串转为大写
console.log(upperString); // HELLO WORLD
```

**你有没有想过：** `myString` 明明只是一个基本类型的值，为什么它会有 `.toUpperCase()` 这么一个像对象才有的方法呢？

**这就是“基本包装类型”在起作用！** 当你执行 `myString.toUpperCase()` 的那一瞬间，JS引擎在背后做了如下事情：

1. **发现**：你正在对一个字符串（基本类型）执行 `. `操作。
2. **创建临时对象**：JS引擎在内部默默地创建了一个`String`对象的实例。
   `// 伪代码: const tempObj = new String(myString);`
3. **执行方法**：在这个临时对象上调用`toUpperCase()`方法。
   `// 伪代码: const result = tempObj.toUpperCase();`
4. **返回结果**：将方法执行的结果（"HELLO WORLD"）返回给你的变量 `upperString`。
5. **销毁临时对象**：用完之后，立刻把那个临时创建的`tempObj`给扔掉，就好像它从没存在过一样。
   `// 伪代码: tempObj = null;`

所以，你感觉自己是在对一个字符串值调用方法，但实际上是JS帮你创建了一个“隐身的仆人”（临时包装对象）完成了这个任务。

`number` 和 `boolean` 也有对应的包装类型 `Number` 和 `Boolean`。

```javascript
let myNumber = 123.456;
console.log(myNumber.toFixed(2)); // 输出 "123.46"
// 背后也是 new Number(123.456).toFixed(2)
```

**注意：** 千万不要自己手动去创建包装类型的对象！

```javascript
let badString = new String("hello");
console.log(typeof badString); // "object"，而不是 "string"
if (badString) { // 作为对象，它永远是 true
    console.log("这会让人困惑");
}
```

这会带来很多意想不到的麻烦，所以，**永远直接使用基本类型值**，让JS在需要的时候自动为我们进行包装。

**总结一下：**
基本包装类型是JS提供的一种“语法糖”，它让基本类型值“看起来”像对象一样，可以方便地调用各种方法，但其本质仍然是基本类型。

---

### 15. Object 的静态方法

`Object` 是所有对象的“老祖宗”，它本身也提供了一系列非常有用的“工具函数”（静态方法），来帮助我们更好地操作任何对象。

#### 核心思想

这些方法是直接挂在 `Object` 这个构造函数上的，你不需要 `new` 一个对象出来，可以直接用 `Object.方法名()` 的形式调用它们，它们是处理对象的“瑞士军刀”。

#### 超详细讲解

这里介绍几个最常用、最有用的 `Object` 静态方法：

**1. `Object.keys(obj)` - 获取所有键**
返回一个由对象所有**可枚举属性的键名**组成的数组。

```javascript
const car = {
    brand: "Tesla",
    model: "Model 3",
    year: 2023
};

const keys = Object.keys(car);
console.log(keys); // 输出: ["brand", "model", "year"]
```

**2. `Object.values(obj)` - 获取所有值**
返回一个由对象所有**可枚举属性的值**组成的数组。

```javascript
const values = Object.values(car);
console.log(values); // 输出: ["Tesla", "Model 3", 2023]
```

**3. `Object.entries(obj)` - 获取所有键值对**
返回一个由对象所有**可枚举属性的 `[键, 值]` 数组对**组成的数组。这个在循环中特别好用！

```javascript
const entries = Object.entries(car);
console.log(entries);
// 输出:
// [
//   ["brand", "Tesla"],
//   ["model", "Model 3"],
//   ["year", 2023]
// ]

// 完美配合 for...of 和解构
for (const [key, value] of Object.entries(car)) {
    console.log(`${key}: ${value}`);
}
```

**4. `Object.assign(target, ...sources)` - 合并对象**
将一个或多个源对象 (`sources`) 的所有可枚举属性，**复制**到目标对象 (`target`)。它会返回修改后的目标对象。

```javascript
const defaults = { theme: "light", notifications: true };
const userSettings = { notifications: false, timezone: "GMT+8" };

// 将 defaults 和 userSettings 合并到一个新对象 {} 中
const finalSettings = Object.assign({}, defaults, userSettings);

console.log(finalSettings);
// 输出: { theme: "light", notifications: false, timezone: "GMT+8" }
// 注意：同名属性 `notifications`，后面的会覆盖前面的。
```

**重要：** `Object.assign` 是一个**浅拷贝**，如果属性值是对象，它只会复制那个对象的引用，而不是复制对象本身。

**5. `Object.freeze(obj)` - 冻结对象**
冻结一个对象。被冻结的对象不能再添加新属性，不能删除已有属性，也不能修改已有属性的值。

```javascript
const user = { name: "张三" };
Object.freeze(user);

user.age = 20; // 尝试添加，静默失败（严格模式下会报错）
user.name = "李四"; // 尝试修改，静默失败
delete user.name; // 尝试删除，静默失败

console.log(user); // 输出: { name: "张三" } (毫无变化)
```

**总结一下：**
`Object` 的静态方法是JS内置的、非常强大的对象工具箱。`keys`, `values`, `entries` 用于遍历，`assign` 用于合并，`freeze` 用于保护数据。熟练使用它们能让你的代码更简洁、更强大。

---

好的！我们继续深入，第四阶段的知识点充满了实用技巧和更高层次的思维方式。准备好，我们开始吧！

---

