### **GSAP 入门中文教学大纲**

#### **第一部分：GSAP 入门基础**

1.  **GSAP 是什么？**

    - 介绍 GSAP（GreenSock Animation Platform）是一个强大的 JavaScript 动画库。
    - 为什么选择 GSAP？（高性能、兼容性好、功能强大）
    - 与 CSS 动画和原生 Web Animations API 的简单对比。

2. **安装与引入**

   - 如何通过 CDN 链接在项目中快速引入 GSAP。

   ```javascript
   <script src="https://cdn.jsdelivr.net/npm/gsap@3.13.0/dist/gsap.min.js"></script>
   ```

   - （可选）如何通过 NPM/Yarn 在现代前端框架（如 React, Vue）中安装和导入。

3.  **你的第一个动画：`gsap.to()`**
    - **核心概念：补间动画 (Tween)**。解释动画就是“从一个状态到另一个状态的过渡”。
    - **语法解析**：`gsap.to("目标", { ...动画属性 })`
      - **目标 (Target)**：如何选中你想要动的元素（比如用 CSS 选择器，`.box`, `#logo`）。
      - **动画属性 (Variables)**：你想要改变元素的哪些 CSS 属性，以及变成什么值（比如 `x: 500`, `rotation: 360`, `duration: 1`）。
    - **动手实践**：创建一个 HTML 盒子，并用`gsap.to()`让它移动和旋转。

#### **第二部分：掌握核心动画方法**

1.  **另外两种基础动画：`gsap.from()` 和 `gsap.fromTo()`**

    - **`gsap.from()`**：从你设定的状态，动画到它本来的样子。非常适合做“入场”动画。
    - **`gsap.fromTo()`**：完全控制动画的“起始状态”和“结束状态”。
    - **场景对比**：讲解在什么情况下应该使用 `to`, `from` 或 `fromTo`。

2.  **通用属性详解**
    - **`duration`**: 动画持续时间（单位：秒）。
    - **`delay`**: 动画开始前的延迟时间。
    - **`repeat`**: 重复次数（`-1` 代表无限循环）。
    - **`yoyo`**: “悠悠球”效果，设置为`true`时，动画会在结束后反向播放回来。
    - **`ease`**: 缓动函数，控制动画的速度曲线（比如 "power1.inOut", "bounce.out"），让动画看起来更自然。

#### **第三部分：GSAP 的精髓——时间轴 (Timeline)**

1.  **为什么需要时间轴？**

    - 解释单个补间动画的局限性。
    - 引出时间轴是用来编排和管理多个动画序列的“容器”和“指挥家”。

2.  **创建你的第一个时间轴：`gsap.timeline()`**

    - 基本语法：`let tl = gsap.timeline();`
    - 如何将多个动画（`to`, `from` 等）链接到时间轴上，形成一个连续的动画序列。
    - **示例**：先让一个方块向右移动，然后旋转，最后变色。

3.  **精确控制时间**

    - **位置参数 (Position Parameter)**：
      - 默认情况：动画一个接一个播放。
      - `"<"`：与上一个动画同时开始。
      - `"+=1"`：在上一个动画结束后，再等待 1 秒。
      - `"-=1"`：在上一个动画结束前 1 秒开始。
    - **标签 (Labels)**：在时间轴上设置标记点 (`tl.add("someLabel")`)，方便跳转和对齐动画。

4.  **控制整个时间轴**
    - 学习如何像控制视频播放器一样控制整个动画序列：
      - `tl.play()`: 播放
      - `tl.pause()`: 暂停
      - `tl.reverse()`: 反向播放
      - `tl.restart()`: 重新播放
      - `tl.timeScale()`: 加速或减速播放

#### **第四部分：进阶与实用技巧**

1.  **Stagger：制作酷炫的交错动画**

    - 如何轻松地让一组元素（比如多个盒子、文字）一个接一个地、有间隔地播放同一个动画。
    - `stagger` 属性详解。

2.  **ScrollTrigger：滚动触发动画（最受欢迎的插件）**

    - 基本概念：当元素滚动到视窗的特定位置时，触发动画。
    - 创建一个简单的滚动触发效果。
    - 关键属性：`trigger` (触发器), `start` (开始位置), `end` (结束位置), `scrub` (动画与滚动条同步), `markers` (显示辅助标记)。

3.  **（可选）其他实用功能**
    - **动画 SVG**：如何对 SVG 的路径、形状进行动画。
    - **动画文本**：使用 TextPlugin 实现打字机效果。
    - **Setters**: `gsap.set()`，用于立即设置元素的初始状态，没有动画过程。

---

### **第一部分：GSAP入门基础**

#### **1. GSAP是什么？**

想象一下，你想让网页上的一个图片、一个标题或者一个按钮“动起来”，比如从左边飞入、旋转一下或者变个颜色。在没有工具的情况下，你需要写很多复杂的代码，并且要处理各种浏览器的兼容问题，非常麻烦。

**GSAP (GreenSock Animation Platform)** 就是来解决这个问题的。它是一个专门用来创建网页动画的JavaScript库。你可以把它想象成一个“网页动画的超级工具箱”，里面有各种强大又好用的工具，让你可以用非常简单、直观的代码，创作出流畅、高性能的动画效果。

**为什么大家都喜欢用GSAP？**

*   **性能极高**：GSAP是出了名的快，用它做的动画非常流畅，几乎不会出现卡顿。
*   **兼容性好**：你不用担心动画在Chrome、Firefox、Safari等不同浏览器上表现不一样，GSAP已经帮你把这些麻烦事都处理好了。
*   **功能强大**：除了简单的移动、旋转，GSAP还能做出非常复杂的序列动画，比如让几个动画按照精确的顺序播放，这用纯CSS是很难做到的。

简单来说，**GSAP让你从繁琐的动画代码中解放出来，专注于动画创意本身。**

#### **2. 安装与引入**

学习GSAP最快的方式就是直接在你的HTML文件中引入它。我们不需要下载任何东西，只需要一个CDN链接。这就像是在你的HTML里引用一个外部的“工具箱”，之后我们就可以直接使用里面的工具了。

**操作步骤：**

1.  创建一个HTML文件（比如 `index.html`）。
2.  将下面的代码模板完整地复制粘贴到你的文件中。

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的第一个GSAP动画</title>
    <style>
        /* 这是一个简单的样式，让我们的方块更好看 */
        body {
            padding: 50px;
        }
        .box {
            width: 100px;
            height: 100px;
            background-color: #28a745; /* 绿色 */
            border-radius: 10px;
        }
    </style>
</head>
<body>

    <h1>GSAP入门</h1>
    <div class="box"></div>

    <!-- 1. 引入GSAP核心库 -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js"></script>

    <!-- 2. 在这里写我们自己的动画代码 -->
    <script>
        // 我们的GSAP代码将写在这里！

    </script>

</body>
</html>
```

**代码解释：**

*   `<style>`标签里是一些简单的CSS，用来定义一个名为 `box` 的绿色方块样式。
*   `<body>`里有一个 `div` 元素，它的 `class` 是 `box`，这就是我们接下来要让它“动起来”的目标。
*   最关键的是 `<body>` 底部的那两对 `<script>` 标签。
    *   **第一个 `<script>`** 通过 `src` 属性引入了GSAP的核心库。有了这行代码，我们就能使用GSAP的所有功能了。
    *   **第二个 `<script>`** 是我们自己写代码的地方。**一定要确保它在GSAP库的后面**，因为我们必须先加载“工具箱”，才能使用里面的工具。

现在，你已经搭建好了GSAP的运行环境，下一步我们就要开始写第一行动画代码了！

#### **3. 你的第一个动画：`gsap.to()`**

`gsap.to()` 是GSAP中最常用、最核心的一个命令。它的作用是：**告诉GSAP，把一个元素“动画到”我们指定的状态**。

它的语法结构非常清晰：

`gsap.to( "目标" , { ...动画属性 } );`

*   **第一个参数（目标）**：你想要哪个元素动起来？这里我们使用CSS选择器来选中它，比如 `".box"` 就是选中所有 class 为 box 的元素。
*   **第二个参数（动画属性）**：这是一个JavaScript对象（用 `{}` 包裹），里面写着你希望动画结束时元素变成什么样子。我们把它叫做“变量对象 (vars object)”。

**变量对象里可以写的常用属性：**

*   `duration`: 动画持续的时间，单位是秒。比如 `duration: 2` 就是动画在2秒内完成。
*   `x`: 元素在水平方向（X轴）移动的距离，单位是像素。`x: 500` 就是向右移动500像素。
*   `y`: 元素在垂直方向（Y轴）移动的距离。
*   `rotation`: 旋转的角度。`rotation: 360` 就是顺时针旋转360度。
*   `backgroundColor`: 背景颜色。
*   `scale`: 缩放。`scale: 2` 就是放大到原来的2倍。

**动手实践！**

现在，让我们把下面的动画代码写到刚才HTML文件里那个空的 `<script>` 标签中：

```javascript
gsap.to(".box", {
    duration: 2,         // 动画持续2秒
    x: 500,              // 向右移动500像素
    rotation: 360,       // 旋转360度
    backgroundColor: "#007bff", // 颜色变为蓝色
    scale: 1.5           // 放大1.5倍
});
```

**你的完整 `index.html` 文件现在应该是这样：**

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的第一个GSAP动画</title>
    <style>
        body { padding: 50px; }
        .box { width: 100px; height: 100px; background-color: #28a745; border-radius: 10px; }
    </style>
</head>
<body>

    <h1>GSAP入门</h1>
    <div class="box"></div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js"></script>
    <script>
        // 我们的GSAP代码！
        gsap.to(".box", {
            duration: 2,
            x: 500,
            rotation: 360,
            backgroundColor: "#007bff",
            scale: 1.5
        });
    </script>

</body>
</html>
```

现在，用浏览器打开这个 `index.html` 文件。你应该能看到那个绿色的方块在2秒内平滑地向右移动、旋转、变色并放大！

恭喜你！你已经成功创建了你的第一个GSAP动画。

---

**小结一下**：在这一部分，我们了解了GSAP是什么，如何快速地在项目中引入它，并学习了最核心的 `gsap.to()` 方法来创建一个基础动画。

感觉怎么样？如果没问题的话，我们就可以继续学习 **“第二部分：掌握核心动画方法”**，去了解 `gsap.from()` 和 `gsap.fromTo()` 这两个同样重要的方法了。

非常好！我们继续前进。

现在你已经掌握了 `gsap.to()`，它是从元素的 **当前状态** 动画到 **目标状态**。接下来，我们学习另外两个同样重要的核心方法，它们能让你更灵活地控制动画的开始和结束。

---

### **第二部分：掌握核心动画方法**

#### **1. 入场动画神器：`gsap.from()`**

`gsap.from()` 的作用和 `gsap.to()` 刚好相反。它负责：**把一个元素从你设定的状态，动画到它的当前状态（也就是它在CSS里本来的样子）**。

这听起来可能有点绕，但它非常适合制作元素的“入场”动画。比如，你希望一个标题从屏幕外面、透明度为0的状态，动画到它本来该在的位置。

**语法结构：**

`gsap.from( "目标" , { ...动画属性 } );`

*   **第一个参数（目标）**：和 `to()` 一样，是你要动的元素。
*   **第二个参数（动画属性）**：这是你为动画指定的 **起始状态**。

**动手实践！**

我们来创建一个新的标题 `h2`，让它从左侧500像素远、完全透明的状态，“进入”到页面中。

1.  在你的 HTML 文件的 `<body>` 里，添加一个 `h2` 标签和一个新的 `green-box`。

```html
<!-- ... 你之前的代码 ... -->
<body>
    <h1>GSAP入门</h1>
    <div class="box"></div>

    <!-- 新增内容 -->
    <h2 class="title">这是一个标题</h2>
    <div class="green-box"></div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js"></script>
    <script>
        // ... 你之前的 gsap.to() 代码可以先注释掉或删除 ...

        // 我们在这里写新的代码
    </script>
</body>
```

2.  为了让 `green-box` 和之前的 `box` 区分开，在 `<style>` 里给它一个新的样式。

```css
/* ... 你之前的样式 ... */
.green-box {
    width: 80px;
    height: 80px;
    background-color: green;
    margin-top: 20px;
}
```

3.  现在，在 `<script>` 标签里，使用 `gsap.from()` 来创建动画。

```javascript
gsap.from(".green-box", {
    duration: 1.5,     // 动画持续1.5秒
    x: -500,           // 动画从左侧500像素的位置开始（x:-500）
    opacity: 0,        // 动画从完全透明开始
    rotation: -90      // 动画从逆时针旋转90度开始
});
```

刷新你的页面，你会看到那个新的绿色方块，瞬间从左侧看不见的地方，旋转着飞入到它本来的位置上。这就是 `gsap.from()` 的魔力！

#### **2. 完全掌控：`gsap.fromTo()`**

`gsap.to()` 控制结束状态，`gsap.from()` 控制开始状态。那么，如果你想同时精确地定义 **“从哪里开始”** 和 **“到哪里结束”** 怎么办？

答案就是 `gsap.fromTo()`。它给了你最完全的控制权。

**语法结构：**

`gsap.fromTo( "目标" , { ...起始状态 } , { ...结束状态 } );`

注意，它有 **三个参数**！
*   **第一个参数**：目标元素。
*   **第二个参数**：动画的 **起始状态**。
*   **第三个参数**：动画的 **结束状态**。

**动手实践！**

我们来给刚才那个 `h2` 标题也加上动画。我们希望它从右边、下面、旋转的状态，动画到左边、上面、并且放大。

在你的 `<script>` 标签里，添加以下代码：

```javascript
gsap.fromTo(".title",
    { // 这是起始状态
        opacity: 0,
        x: 300,
        y: 50
    },
    { // 这是结束状态
        duration: 2,
        opacity: 1,
        x: 0,
        y: 0,
        scale: 1.2
    }
);
```

刷新页面，你会发现标题完全按照我们设定的 `from` 和 `to` 两个状态进行了动画。

#### **3. 通用属性详解 (让动画更生动)**

除了 `x`, `opacity` 这些改变元素样式的属性外，GSAP还提供了一系列“特殊属性”，它们不控制元素的样子，而是控制 **动画的行为方式**。这些属性在 `to`, `from`, `fromTo` 里都可以使用。

*   **`delay`**: 延迟。让动画在等待指定秒数后才开始。
*   **`repeat`**: 重复次数。`repeat: 1` 会让动画总共播放两次（重复1次）。如果你想让它无限循环，可以设置为 `repeat: -1`。
*   **`yoyo`**: “悠悠球”效果。当和 `repeat` 一起使用时，设置为 `true` 会让动画在每次重复时反向播放回来，形成一个来回往复的效果。
*   **`ease`**: 缓动函数。这是GSAP的灵魂之一！它决定了动画的速度曲线。默认情况下，动画是“缓进缓出”的，而不是匀速的。GSAP内置了大量缓动效果，让动画看起来更自然、更有趣。
    *   `"power1.inOut"`: 温和的缓入缓出（类似默认效果）
    *   `"bounce.out"`: 在动画结束时产生弹跳效果。
    *   `"elastic.out"`: 在动画结束时产生橡皮筋一样的弹性效果。

**动手实践！**

让我们回到最初的那个 `.box`，给它加上这些有趣的属性。

```js
gsap.to(".box", {
    duration: 2,
    x: 600,
    rotation: 360,
    ease: "bounce.out", // 使用弹跳缓动
    delay: 1,           // 延迟1秒开始
    repeat: -1,         // 无限重复
    yoyo: true          // 开启yoyo效果
});
```

现在刷新页面，你会看到：页面加载1秒后，第一个方块开始向右移动，并在到达终点时像皮球一样弹跳几下。然后，它又会反向弹跳着回到原点，无限循环这个过程。

你可以尝试把 `ease` 的值改成 `"elastic.out"` 看看效果有什么不同！要探索所有有趣的缓动效果，可以访问 [GreenSock官方的缓动效果演示器](https://greensock.com/docs/v3/Eases)，这是一个非常直观好用的工具。

---

**小结一下**：在第二部分，我们学会了：
*   使用 `gsap.from()` 制作入场动画。
*   使用 `gsap.fromTo()` 完全控制动画的始末状态。
*   使用 `delay`, `repeat`, `yoyo`, `ease` 等通用属性来丰富动画的行为，让它不再是简单的线性运动。

你现在已经掌握了创建单个动画（GSAP里称为 **补间动画 Tween**）的所有核心方法。但是，当页面上有很多动画，并且需要它们按顺序、分步骤进行时，只用这些方法会变得很麻烦。

所以，下一步，我们将学习GSAP最强大的功能，也是它的精髓所在——**“第三部分：时间轴 (Timeline)”**。准备好了吗？

---

### **第三部分：GSAP的精髓——时间轴 (Timeline)**

#### **1. 为什么需要时间轴？**

想象一个场景：你想让一个方块先向右移动2秒，*然后* 向上移动1秒，*最后* 旋转360度。

用我们目前学到的知识，你可能会想到用 `delay` (延迟) 来实现：

```javascript
// 不推荐的写法 👎
gsap.to(".box", { duration: 2, x: 300 }); // 第1个动画
gsap.to(".box", { duration: 1, y: -100, delay: 2 }); // 第2个动画，延迟2秒（等第1个结束）
gsap.to(".box", { duration: 1.5, rotation: 360, delay: 3 }); // 第3个动画，延迟3秒（等前2个结束）
```

这样做确实能实现效果，但有一个巨大的问题：**它非常脆弱！**

如果有一天，你决定把第一个动画的 `duration` 从2秒改成4秒，会发生什么？你必须手动去修改后面所有动画的 `delay` 值，一个一个地重新计算。在一个复杂的动画序列里，这将是一场噩梦！

**时间轴 (Timeline) 就是为了解决这个问题而生的。**

你可以把时间轴想象成一个**“动画容器”**或者一个**“电影导演”**。你只需要按照顺序，把一个个独立的动画（Tweens）放进这个容器里，它就会自动管理播放顺序，一个接一个地播放，你完全不用关心延迟时间。

#### **2. 创建你的第一个时间轴：`gsap.timeline()`**

创建和使用时间轴非常简单。

**语法：**
1.  首先，创建一个时间轴实例：`let tl = gsap.timeline();` ( `tl` 是 `timeline` 的缩写，是一种常用写法)
2.  然后，用这个实例来创建动画，而不是用 `gsap`。例如，用 `tl.to()` 代替 `gsap.to()`。
3.  把动画像链条一样串起来。

**动手实践！**

我们来用时间轴的方式，重新实现上面那个“移动-移动-旋转”的动画。

1.  在HTML里准备一个方块。

```html
<div class="blue-box"></div>
```

2.  在CSS里给它一些样式。

```css
.blue-box {
    width: 80px;
    height: 80px;
    background-color: #007bff; /* 蓝色 */
}
```

3.  在JavaScript里，用时间轴来编写动画序列。

```javascript
// 推荐的写法 👍
let tl = gsap.timeline();

// 把动画添加到时间轴上
tl.to(".blue-box", { duration: 2, x: 300 })
  .to(".blue-box", { duration: 1, y: -100 })
  .to(".blue-box", { duration: 1.5, rotation: 360 });
```

现在刷新页面，效果和之前用 `delay` 的版本一模一样。但代码变得无比清晰和稳健！如果你现在想把第一个动画的 `duration` 改成5秒，你只需要改一个数字，后面的动画依然会完美地衔接，自动等待第一个动画结束后再播放。

#### **3. 精确控制时间：位置参数 (Position Parameter)**

默认情况下，时间轴上的动画是一个接一个播放的。但GSAP提供了极其强大的“位置参数”，让你可以精确控制每个动画在时间轴上的“插入点”。

位置参数写在动画属性对象的后面，作为第三个参数：`tl.to( target, vars, position )`

**最常用的几个位置参数值：**

*   **（无）**: 默认值，插入到整个时间轴的末尾。
*   **`<`**: 一个非常特殊的符号，表示“**与上一个动画的开始时间点对齐**”。换句话说，让这个动画和上一个动画同时开始。
*   **`+=1`**: 在时间轴的末尾，再**增加**1秒的空白，然后插入动画。（`+=` 是在末尾追加）
*   **`-`**`=0.5`: 在时间轴的末尾，**回退**0.5秒，然后插入动画。这会造成两个动画的重叠效果。（`-=` 是在末尾回退）

**动手实践！**

我们来创建一个更复杂的序列，体会一下位置参数的威力。假设我们有三个不同颜色的方块。

1.  HTML:

```html
<div class="dot red"></div>
<div class="dot green"></div>
<div class="dot purple"></div>
```

2.  CSS:

```css
.dot {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    position: relative; /* 方便 GSAP 控制 */
}
.red { background-color: red; }
.green { background-color: green; }
.purple { background-color: purple; }
```

3.  JavaScript:

```javascript
let tl = gsap.timeline();

// 1. 红色dot向右移动
tl.to(".red", { x: 500, duration: 2 });

// 2. 绿色dot向上移动，与红色dot的动画【同时开始】
tl.to(".green", { y: -100, duration: 1 }, "<");

// 3. 紫色dot旋转，在绿色dot动画结束【前0.5秒】开始
tl.to(".purple", { rotation: 360, duration: 1.5 }, "-=0.5");
```

刷新页面，仔细观察：
*   红色和绿色圆点会同时开始移动。
*   在绿色圆点移动了0.5秒后（也就是结束前0.5秒），紫色圆点开始旋转。

通过位置参数，你可以像编排音乐一样，精确地控制每个动画的节奏和同步，创造出丰富多样的视觉效果。

#### **4. 控制整个时间轴**

当你把一系列动画组合成一个时间轴后，这个时间轴实例 (`tl`) 就变成了一个“**遥控器**”，你可以用它来控制整个动画序列。

**常用的控制方法：**

*   `tl.play()`: 播放
*   `tl.pause()`: 暂停
*   `tl.resume()`: 从暂停处继续播放
*   `tl.reverse()`: 反向播放（从结尾倒带到开头）
*   `tl.restart()`: 重新从头播放
*   `tl.timeScale(2)`: 将播放速度设为2倍速。`tl.timeScale(0.5)` 则是半速播放。

**动手实践！**

我们来为动画添加控制按钮。

1.  在HTML里添加几个按钮：

```html
<div>
    <button id="playBtn">播放</button>
    <button id="pauseBtn">暂停</button>
    <button id="reverseBtn">反向</button>
    <button id="restartBtn">重播</button>
</div>
```

2.  在JavaScript里，首先在创建时间轴时，让它默认暂停，然后为按钮绑定事件。

```javascript
// 在时间轴配置里，设置 paused: true，让它初始状态为暂停
let controlTl = gsap.timeline({ paused: true });

controlTl.to(".red", { x: 500, duration: 2 })
         .to(".green", { y: -100, duration: 1 }, "<")
         .to(".purple", { rotation: 360, duration: 1.5 }, "-=0.5");

// 绑定按钮事件
document.getElementById("playBtn").onclick = () => controlTl.play();
document.getElementById("pauseBtn").onclick = () => controlTl.pause();
document.getElementById("reverseBtn").onclick = () => controlTl.reverse();
document.getElementById("restartBtn").onclick = () => controlTl.restart();
```

现在，你可以像操作视频播放器一样，通过点击按钮来自由地控制整个动画序列了！

---

**小结一下**：在第三部分，你学会了GSAP的精髓——时间轴。
*   你知道了为什么要用时间轴来**组织动画序列**。
*   你学会了如何使用 `gsap.timeline()` 和链式调用来**创建时间轴**。
*   你掌握了强大的**位置参数** (`<`, `+=`, `-=`) 来精确控制动画的同步和节奏。
*   你学会了如何像遥控器一样**控制整个时间轴**的播放、暂停、反转等。

你已经具备了构建复杂、有叙事性的网页动画的能力。在最后一部分，我们将学习一些非常实用的进阶技巧和GSAP最受欢迎的插件，让你的动画项目更上一层楼。准备好了吗？



---

### **第四部分：进阶与实用技巧**

#### **1. Stagger：轻松制作酷炫的交错动画**

想象一下，你有一排卡片或者一串文字，你希望它们不是“一起”出现，而是一个接一个地、像多米诺骨牌一样，带着漂亮的节奏感依次入场。这就是交错动画（Stagger）。

在GSAP里实现这种效果极其简单，你只需要在动画属性里加上一个 `stagger` 属性即可。

**动手实践！**

1.  在HTML里创建一组方块。

```html
<div class="grid-container">
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
    <div class="grid-box"></div>
</div>
```

2.  用CSS把它们排列成一个网格。

```css
.grid-container {
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    gap: 10px;
    width: 320px;
}
.grid-box {
    width: 50px;
    height: 50px;
    background-color: #6c757d; /* 灰色 */
    border-radius: 5px;
}
```

3.  在JavaScript里，我们只用 **一个** `gsap.from()` 动画，加上 `stagger` 属性，来控制 **所有** 的方块。

```javascript
gsap.from(".grid-box", {
    duration: 0.8,
    scale: 0,
    opacity: 0,
    rotation: 360,
    ease: "power2.inOut",
    // stagger 的核心在这里！
    stagger: 0.1 // 每个方块之间动画开始的时间间隔为0.1秒
});
```

刷新页面，你会看到所有方块不再是同时出现，而是一个接一个地、间隔0.1秒，以旋转缩放的方式华丽入场。

`stagger` 还可以是一个对象，进行更高级的配置，比如 `stagger: { amount: 1, from: "center" }`，意思是“在1秒内，从中心开始，依次完成所有元素的交错动画”。

#### **2. ScrollTrigger：滚动触发动画（最受欢迎的插件）**

这是GSAP的王牌插件。现代网页设计中充满了“滚动叙事”（Scrollytelling）——当你向下滚动页面时，各种元素会随着滚动条的位置，适时地飞入、淡出、变形。**ScrollTrigger就是实现这一切的钥匙。**

**引入ScrollTrigger插件：**

和GSAP核心库一样，插件也需要单独引入。**请确保它在`gsap.min.js`的后面。**

```html
<!-- 1. 引入GSAP核心库 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/gsap.min.js"></script>
<!-- 2. 引入ScrollTrigger插件 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.12.5/ScrollTrigger.min.js"></script>

<!-- 3. 在这里写我们自己的动画代码 -->
<script>
    // 在使用前，必须先“注册”插件
    gsap.registerPlugin(ScrollTrigger);

    // ... 我们的滚动动画代码 ...
</script>
```

**核心概念：**

你只需要在你正常的GSAP动画中，添加一个 `scrollTrigger` 对象，并对它进行配置即可。

**动手实践！**

我们来做一个经典效果：当一个图片滚动到屏幕中央时，让它从侧面滑入。

1.  在HTML里创建一些区域，撑开页面高度，并放入一个图片。

```html
<section class="section-a"><h1>向下滚动</h1></section>

<section class="section-b">
    <img src="https://via.placeholder.com/300" alt="占位图片" class="content-image">
</section>

<section class="section-c"><h1>滚动结束</h1></section>
```

2.  给这些区域一些样式。

```css
section {
    height: 100vh; /* 每个区域占满整个屏幕高度 */
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 2em;
}
.section-b {
    background-color: #f0f0f0;
}
/* 初始时图片在屏幕外且透明 */
.content-image {
    opacity: 0;
    transform: translateX(-200px);
}
```

3.  在JavaScript里，创建动画并配置ScrollTrigger。

```javascript
// 别忘了先注册插件！
gsap.registerPlugin(ScrollTrigger);

gsap.to(".content-image", { // 这是一个普通的gsap.to动画
    x: 0, // 移动到本来位置
    opacity: 1,
    duration: 1.5,
    // scrollTrigger 的魔法在这里！
    scrollTrigger: {
        trigger: ".content-image", // 触发器：指定哪个元素进入视窗时触发动画
        start: "top center",       // 触发时机：当元素的顶部(top)碰到视窗的中心(center)
        end: "bottom center",      // 结束时机：当元素的底部(bottom)碰到视窗的中心(center)
        markers: true,             // 开启标记！这是调试神器，会显示触发的起止位置
        toggleActions: "play none none reverse" // 行为：进入时播放，离开时反向播放
    }
});
```

现在刷新页面并开始滚动。你会看到页面右侧出现了一些标记线（`markers: true`的效果）。当你向下滚动，让图片的 `start` 标记线碰到中间的 `scroller-start` 线时，动画就会播放！当你向上滚动回去，让它再次离开时，动画会反向播放。

**ScrollTrigger最强大的属性：`scrub`**

`scrub` (擦洗) 可以将动画的播放进度和滚动条的滚动进度 **完全绑定** 在一起。用户滚动得快，动画就快；用户停下，动画也停下；用户往回滚，动画也倒放。

只需要把上面的 `toggleActions` 换成 `scrub`：

```javascript
scrollTrigger: {
    trigger: ".content-image",
    start: "top center",
    markers: true,
    scrub: 1 // 值可以是 true，或者一个数字，代表动画跟上滚动条的延迟秒数
}
```

现在再滚动试试，图片会如丝般顺滑地随着你的滚动条移动，给你完全的掌控感。这就是高端网站动画交互的核心。

#### **3. （补充）立即设置状态：`gsap.set()`**

有时候，你不想看到动画，只想立即把一个元素设置到某个状态。比如，在 `gsap.from()` 动画开始前，你希望元素就已经在屏幕外面了，而不是等JS加载完才“跳”一下。`gsap.set()` 就是做这个的。

它相当于一个 `duration` 为0的 `gsap.to()`。

`gsap.set(".box", { x: -500, opacity: 0 }); // 立即把.box放到左边500px并设为透明`

这在准备动画的初始状态时非常有用。

---

### **总结与后续学习**

恭喜你！你已经完成了GSAP从入门到进阶的核心课程！我们来回顾一下：

*   **第一部分**：你学会了GSAP是什么，如何引入，以及用 `gsap.to()` 创建了第一个 **补间动画 (Tween)**。
*   **第二部分**：你掌握了 `gsap.from()` 和 `gsap.fromTo()`，并学会了用 `ease`, `repeat` 等属性丰富动画细节。
*   **第三部分**：你学习了GSAP的精髓——**时间轴 (Timeline)**，学会了如何编排和控制复杂的动画序列。
*   **第四部分**：你掌握了用 `stagger` 制作交错动画，并学会了使用王牌插件 **ScrollTrigger** 来创建强大的滚动交互动画。

你现在已经拥有了创建专业级网页动画所需的核心知识。

**接下来该做什么？**

1.  **练习，练习，再练习！** 动画是实践的艺术。尝试复刻一些你喜欢的网站上的小动效。
2.  **访问官方文档**：当你遇到具体问题时，[GreenSock官方文档](https://greensock.com/docs/v3)是你最好的朋友。
3.  **探索CodePen社区**：CodePen上有无数GSAP的精彩案例。这是寻找灵感和学习高级技巧的最佳场所。搜索 “gsap” 或 “scrolltrigger” 就能打开新世界的大门。
4.  **了解其他插件**：当你游刃有余后，可以去了解一下 `Flip` (实现神奇的DOM位置变换动画)、`Draggable` (拖拽) 等其他强大插件。

希望这个中文教学能帮助你顺利地踏入GSAP动画的精彩世界。祝你创造愉快！
