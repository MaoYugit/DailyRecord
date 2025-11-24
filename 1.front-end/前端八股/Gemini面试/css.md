## 一、 基础概念类 (必考题)

这部分主要考察你对 CSS 基本语法的掌握程度。

### **1. 介绍一下 CSS 盒模型 (Box Model)？**

* **考点**：标准盒模型 (`content-box`) vs IE/怪异盒模型 (`border-box`) 的区别。

* **追问**：如何通过 CSS 设置这两种模型？(`box-sizing`)

  ### 回答：

  好的，面试官您好，关于 CSS 盒模型，我是这样理解的：

  CSS 盒模型（Box Model）是 CSS 布局的基石，它规定了网页元素如何解析和渲染。本质上，每个 HTML 元素都可以看作一个矩形的盒子，这个盒子由内到外由四个部分组成：**Content（内容）、Padding（内边距）、Border（边框）和 Margin（外边距）**。

  在实际开发中，最关键的点在于**浏览器如何计算一个元素的总宽度和总高度**。根据宽高的计算方式不同，盒模型主要分为两种：**标准盒模型**和**IE 盒模型（也称怪异盒模型）**。

  ### 1. 标准盒模型 (Standard Box Model)

  这是 W3C 的标准，也是现代浏览器默认的模式。

  *   **特点**：CSS 设置的 `width` 和 `height` 属性**只包含 Content（内容）** 的部分。
  *   **计算公式**：
      *   盒子占据的实际宽度 = `width` (内容) + `padding` + `border` + `margin`
  *   **问题**：比如我设置一个盒子 `width: 100px`，然后又加了 `padding: 10px` 和 `border: 1px`，那么这个盒子实际显示的宽度会变成 122px（100 + 20 + 2）。这在布局时往往会导致元素超出父容器而破坏布局。

  ### 2. IE 盒模型 / 怪异盒模型 (Alternative Box Model)

  这是早年 IE 浏览器的默认表现，但在现代开发中因为更符合直觉而被广泛使用。

  *   **特点**：CSS 设置的 `width` 和 `height` 属性**包含了 Content + Padding + Border**。
  *   **计算公式**：
      *   盒子占据的实际宽度 = `width` (已包含 padding 和 border) + `margin`
      *   **内容区的实际宽度** = `width` - `padding` - `border`
  *   **优势**：如果我设置 `width: 100px`，无论我怎么调整 padding 和 border，这个盒子在页面上始终占据 100px 的位置，内容区域会自动收缩。这对于响应式布局和栅格系统非常友好。

  ---

  ### 关于您的追问：如何通过 CSS 设置这两种模型？

  我们可以通过 CSS3 的 **`box-sizing`** 属性来切换这两种模式：

  1. **设置标准盒模型**（默认值）：

     ```css
     box-sizing: content-box;
     ```

  2. **设置 IE/怪异盒模型**：

     ```css
     box-sizing: border-box;
     ```

  **补充说明（实际开发习惯）：**
  在实际的项目开发中，为了便于计算尺寸和防止布局崩坏，我通常会在项目的全局样式（Reset CSS）中进行如下设置，将所有元素统一为怪异盒模型：

  ```css
  * {
    box-sizing: border-box;
  }
  ```

  这就是我对 CSS 盒模型的理解。

### **2. CSS 选择器的优先级（权重）是如何计算的？**

* **考点**：`!important` > 内联样式 > ID 选择器 > 类/伪类/属性 > 标签/伪元素 > 通配符。

* **追问**：场景题，给一段 HTML 和 CSS，问最后显示的颜色是什么？

  面试官您好，关于 CSS 选择器的优先级（权重），我是这样理解的：

  当有多个 CSS 规则应用到同一个元素上时，浏览器需要通过**权重计算**（Specificity）来决定使用哪一组样式。这个计算规则可以量化为一个 **(a, b, c, d)** 的四位数值模型。

  ### 1. 权重的具体等级（由高到低）

  我们可以把权重看作一个金字塔，从上往下依次是：

  1.  **`!important`**：严格来说它不属于权重计算的一部分，但它是最高指令，能够覆盖任何正常的样式规则（除非遇到了另一个 `!important`）。
  2.  **行内样式 (Inline Styles)**：写在 HTML 标签 `style` 属性里的样式。
      *   权重值：`(1, 0, 0, 0)`
  3.  **ID 选择器**：例如 `#header`。
      *   权重值：`(0, 1, 0, 0)`
  4.  **类选择器 (Class)、伪类 (Pseudo-class)、属性选择器**：例如 `.btn`, `:hover`, `[type="text"]`。
      *   权重值：`(0, 0, 1, 0)`
  5.  **标签选择器 (Element)、伪元素 (Pseudo-element)**：例如 `div`, `p`, `::before`。
      *   权重值：`(0, 0, 0, 1)`
  6.  **通配符 (`*`)、组合符 (`+`, `>`, `~`)**：这些对权重没有贡献。
      *   权重值：`(0, 0, 0, 0)`
      *   *注意：继承样式的优先级是最低的，甚至低于通配符。*

  ### 2. 权重的计算规则

  当浏览器解析 CSS 时，会将选择器拆解并对应上面的等级进行**累加**。

  比如选择器：`body #content .data img:hover`
  *   **ID 选择器**：1个 (`#content`) -> b=1
  *   **类/伪类**：2个 (`.data`, `:hover`) -> c=2
  *   **标签**：2个 (`body`, `img`) -> d=2
  *   **结果**：该选择器的权重为 **(0, 1, 2, 2)**

  ### 3. 比较规则

  1.  **从左向右逐位比较**：先比较 ID 位，如果 ID 数量相同，再比较 Class 位，以此类推。
      *   例如：`#id` (0,1,0,0) **>** `.class .class .class ...` (0,0,10,0)。即便有 100 个类名相加，也比不过 1 个 ID（在现代浏览器实现中是这样的，虽然理论上旧标准说是 256 进制，但现在基本视为无限大）。
  2.  **后来居上**：如果权重完全相同，则写在 CSS 文件**后面**的规则会覆盖前面的规则（层叠性）。

  ---

  ### 针对您的追问（场景题模拟）

  如果您给出一断代码，我通常会按照上面的逻辑快速计算。

  **举个例子：**
  ```html
  <div id="box" class="container">Text</div>
  ```

  ```css
  /* 规则 A */
  #box {
    color: red;
  }
  
  /* 规则 B */
  div.container {
    color: blue;
  }
  
  /* 规则 C */
  .container {
    color: green !important;
  }
  ```

  **我的分析过程会是：**
  1.  **规则 A**：`#box` -> 权重 (0, 1, 0, 0)
  2.  **规则 B**：`div.container` -> 1个标签 + 1个类 -> 权重 (0, 0, 1, 1)
  3.  **规则 C**：`.container` -> 虽然权重只有 (0, 0, 1, 0)，但它加了 `!important`。

  **最终结果**：颜色是 **Green**。
  *原因*：`!important` 优先级最高。

  *如果不考虑 `!important`*：
  颜色是 **Red**。
  *原因*：规则 A 有 ID，权重 (1,0,0) 大于 规则 B 的 (0,1,1)。

  这也是我在开发中排查样式不生效问题时的主要思路。

### **3. 伪类 (Pseudo-class) 和伪元素 (Pseudo-element) 的区别？**

*   **考点**：概念区别（一个描述状态如 `:hover`，一个创建虚拟元素如 `::before`）。
*   **用法**：`::before` 和 `::after` 的常见用途（清除浮动、画图标）。

### **4. `display: none` 与 `visibility: hidden` 的区别？**

*   **考点**：
    *   **空间占据**：前者不占位，后者占位。
    *   **重排重绘**：前者触发 Reflow（回流），后者只触发 Repaint（重绘）。
    *   **子元素**：前者父元素消失子元素必消失，后者父元素隐藏但子元素可设置 `visible` 显示。
*   **补充**：`opacity: 0` 的区别。

---

### 二、 布局与定位 (核心高频)

这是 CSS 面试的重灾区，通常伴随着**手写代码**。

5.  **说说 `position` 的属性值及其参照物？**
    *   **考点**：
        *   `relative`：相对于自身原位置。
        *   `absolute`：相对于最近的非 `static` 祖先元素。
        *   `fixed`：相对于视口 (Viewport)。
        *   `sticky`：粘性定位，结合了 relative 和 fixed。

6.  **如何实现水平垂直居中？(至少说出 3 种)**
    *   **Flex 方案**：`justify-content: center; align-items: center;` (最推荐)。
    *   **Absolute + transform**：`left: 50%; top: 50%; transform: translate(-50%, -50%);`
    *   **Absolute + margin auto**：`left:0; right:0; top:0; bottom:0; margin: auto;`
    *   **Grid 方案**：`display: grid; place-items: center;`

7.  **讲讲 Flexbox (弹性布局)？**
    *   **考点**：主轴与交叉轴的概念。
    *   **常见属性**：`flex-direction`, `flex-wrap`, `justify-content`, `align-items`.
    *   **难点**：`flex: 1` 代表什么？(它是 `flex-grow`, `flex-shrink`, `flex-basis` 的缩写，默认值分别为 0, 1, auto)。

8.  **两栏布局/三栏布局（圣杯布局、双飞翼布局）如何实现？**
    *   **考点**：左右固定宽度，中间自适应。
    *   **解法**：Flex（推荐）、Float（经典但过时）、Position Absolute、Grid。

9.  **清除浮动 (Float) 有哪些方式？**
    *   **考点**：浮动带来的问题（父元素高度塌陷）。
    *   **解法**：`clear: both`，BFC，伪元素 `clearfix`（最佳实践）。

---

### 三、 核心原理与机制 (中高级必问)

这部分考察你是否理解 CSS 的渲染逻辑。

10. **什么是 BFC (Block Formatting Context)？如何触发？有什么用？**
    *   **定义**：块级格式化上下文，独立的渲染区域。
    *   **触发**：`overflow` 不为 visible，`float` 不为 none，`position` 为 absolute/fixed，`display` 为 inline-block/flex 等。
    *   **作用**：清除浮动、防止 Margin 重叠（塌陷）、自适应两栏布局。

11. **什么是外边距重叠 (Margin Collapse)？**
    *   **考点**：垂直方向上，相邻元素的 margin 会合并取最大值。如何解决？(创建 BFC)。

12. **什么是层叠上下文 (Stacking Context) / z-index 不生效？**
    *   **考点**：理解 `z-index` 只有在定位元素或特定属性（如 opacity < 1, transform, filter）下才生效。
    *   **层级关系**：背景 < 负 z-index < 块级盒子 < 浮动盒子 < 内联盒子 < z-index: auto / 0 < 正 z-index。

13. **回流 (Reflow) 和重绘 (Repaint) 的区别？如何优化？**
    *   **考点**：性能优化。
    *   **Reflow**：布局改变（大小、位置），成本高。
    *   **Repaint**：外观改变（颜色、背景），成本低。
    *   **优化**：使用 `transform` 代替 `top/left` 动画，批量修改 DOM，使用 `visibility` 替换 `display: none` (针对特定场景)。

---

### 四、 响应式与移动端 (实战类)

14. **CSS 中的单位有哪些？区别是什么？**
    *   **px**：绝对单位。
    *   **em**：相对于父元素字体大小。
    *   **rem**：相对于根元素 (`html`) 字体大小 (移动端适配常用)。
    *   **vw/vh**：视口宽度/高度的 1%。

15. **移动端 1px 边框问题如何解决？**
    *   **原因**：Retina 屏幕的物理像素与逻辑像素比例（DPR）不同。
    *   **解法**：伪元素 + `transform: scale(0.5)`。

16. **如何实现响应式布局？**
    *   **考点**：媒体查询 (`@media`)、Flex/Grid 布局、rem/vw 方案。

---

### 五、 CSS3 新特性与动画

17. **CSS3 有哪些新特性？**
    *   圆角 (`border-radius`)、阴影 (`box-shadow`)、渐变、Flex/Grid、Transform、Transition、Animation。

18. **Transition (过渡) 和 Animation (动画) 的区别？**
    *   **Transition**：需要事件触发（如 hover），只有开始和结束两帧。
    *   **Animation**：可以自动播放，可以定义关键帧 (`@keyframes`)，控制更精细。

---

### 六、 工程化与预处理

19. **Sass/Less 是什么？为什么要用？**
    *   **优点**：变量、嵌套、Mixin（混合）、继承、函数，提高 CSS 的可维护性和复用性。

20. **如何解决 CSS 样式冲突（污染）问题？**
    *   **命名约定**：BEM (Block Element Modifier) 规范。
    *   **CSS Modules**：编译生成唯一的 hash 类名。
    *   **CSS-in-JS**：Styled-components, Emotion。
    *   **Shadow DOM**：Web Components 的隔离机制。

21. **谈谈 Tailwind CSS (原子化 CSS) 的看法？**
    *   **考点**：是否关注前沿技术。优点（开发快、体积小、统一规范）vs 缺点（HTML 类名冗长）。

---

### 面试官最喜欢出的“手写题” (Coding)

面试官可能会直接给你笔或编辑器，让你实现：
1.  **画一个三角形** (利用 border)。
2.  **画一个 0.5px 的线**。
3.  **实现一个扇形**。
4.  **实现左边固定，右边自适应布局**。
5.  **文本溢出省略号** (单行/多行)。

**总结建议：**
对于初中级前端，重点复习 **盒模型、Flex 布局、定位、居中**。
对于高级前端，重点复习 **BFC 原理、性能优化（回流重绘）、工程化方案**。

---



