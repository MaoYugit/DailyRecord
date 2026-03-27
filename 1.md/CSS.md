## CSS

1. **Transform** (练好形变)
2. **Transition** (练好简单的交互)
3. **Keyframes/Animation** (练好复杂的循环)
4. **Filter & Clip-path** (练好视觉特效)
5. **Scroll-driven** (进阶现代网页动效)

好的，我们开始深入讲解 `transform`（变换）。

### 一、核心概念：什么是 Transform？

`transform` 属性允许你修改元素的**坐标空间**。你可以理解为：元素原本在网页里占了一个坑，`transform` 就像是在它上面套了一个“幻影”，让它可以在不影响其他元素位置（不破坏布局）的情况下，进行移动、旋转、缩放或倾斜。

---

#### 1. 准备工作：基础 HTML 结构

为了方便演示，我们先写一个通用的 HTML。所有的例子都会基于这个 `.box` 容器。

```html
<!DOCTYPE html>
<html>
<head>
<style>
  .container {
    display: flex;
    gap: 50px;
    padding: 50px;
    flex-wrap: wrap;
  }

  /* 基础方块样式 */
  .box {
    width: 100px;
    height: 100px;
    background-color: #3498db;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    transition: transform 0.3s; /* 为了让你看清变化过程，加个过渡 */
    border: 2px dashed #333; /* 虚线表示原位 */
  }
</style>
</head>
<body>
  <div class="container">
    <div class="box box-translate">平移</div>
    <div class="box box-scale">缩放</div>
    <div class="box box-rotate">旋转</div>
    <div class="box box-skew">倾斜</div>
    <div class="box box-multi">组合</div>
  </div>
</body>
</html>
```

---

#### 2. 四大核心函数

我们将 CSS 代码分块讲解：

#### A. Translate（平移）
`translate(x, y)` 让元素水平或垂直移动。
*   `translateX(px/%)`: 横向移动。
*   `translateY(px/%)`: 纵向移动。
*   **重点：** 使用百分比时，是相对于**元素自身**的宽高（这在做居中定位时非常有用）。

```css
.box-translate:hover {
  /* 向右移动 50px，向下移动 20px */
  transform: translate(50px, 20px);
}
```

#### B. Scale（缩放）
`scale(x, y)` 改变元素的大小。
*   `1` 是原始大小。
*   `> 1` 放大（如 `1.5` 是 1.5倍）。
*   `< 1` 缩小（如 `0.5` 是 0.5倍）。
*   负数（如 `-1`）会产生镜像翻转。

```css
.box-scale:hover {
  /* 整体放大 1.2 倍 */
  transform: scale(1.2);
  /* 也可以单独指定：transform: scaleX(1.5); */
}
```

#### C. Rotate（旋转）
`rotate(angle)` 让元素绕着一个轴旋转。
*   单位通常是 `deg`（角度），也可以是 `turn`（圈）。
*   正数顺时针，负数逆时针。

```css
.box-rotate:hover {
  /* 顺时针旋转 45 度 */
  transform: rotate(45deg);
}
```

#### D. Skew（倾斜）
`skew(x-angle, y-angle)` 让元素像平行四边形一样扭曲。

```css
.box-skew:hover {
  /* 在 X 轴方向倾斜 20 度 */
  transform: skewX(20deg);
}
```

---

#### 3. 进阶：Transform Origin（变换原点）

这是一个容易被遗忘但极其重要的属性。默认情况下，所有变换（旋转、缩放等）都是以元素的**中心点**为准的。

如果你想让元素绕着左上角旋转，或者从底部向上放大，就需要改 `transform-origin`。

```css
.box-rotate {
  /* 设置旋转中心为左上角 */
  transform-origin: left top; 
  /* 也可以用像素：transform-origin: 0px 0px; */
}
```

---

#### 4. 进阶：组合使用（Order Matters!）

你可以同时写多个函数，但**顺序非常重要**。
*   `transform: translateX(100px) rotate(45deg);` —— 先往右走，再原地转。
*   `transform: rotate(45deg) translateX(100px);` —— 先原地转 45 度，此时坐标轴也转了 45 度，再往“新的右边”（斜下方）走。

```css
.box-multi:hover {
  /* 先平移，后缩放，再旋转 */
  transform: translate(20px, 20px) scale(0.8) rotate(10deg);
}
```

---

#### 5. 为什么要用 Transform 而不是 Top/Left？

这是面试常考题，也是性能优化的关键：

1.  **不触发重排 (Reflow)**：改变 `top` 或 `width` 会导致浏览器重新计算整个页面的布局，非常消耗资源。
2.  **GPU 加速**：`transform` 会被浏览器交给显卡（GPU）处理，动画会非常丝滑（通常能达到 60fps）。
3.  **不占用空间**：即使你把元素 `translate` 移动到了屏幕外面，它在文档流中的原始位置依然被保留，不会影响后面元素的排版。

---

#### 6. 3D 变换预览 (选学)

如果你想让方块像开门一样旋转，只需加一个 `perspective`（透视）和 `rotateY`：

```css
.box-3d {
  /* 在父级设置透视距离 */
  perspective: 500px;
}

.box-3d-child:hover {
  /* 绕着 Y 轴转，看起来就有 3D 翻转效果 */
  transform: rotateY(45deg);
}
```

#### 总结练习
你可以尝试把上面的代码复制到编辑器里。
*   **试一试：** 将 `scale` 设置为 `-1` 看看会发生什么？
*   **试一试：** 改变 `transform-origin` 到 `bottom right`，观察旋转有什么不同？

**接下来你想聊聊 Transition（过渡）是如何把这些瞬间的变换变成平滑动画的吗？**

---

---

如果说 `transform` 是**“目的地”**（我想变大、变斜），那么 `transition` 就是**“路上的风景”**。它决定了元素从“原样”变成“变换后”的过程是多快、多慢、还是先快后慢。

---

### 1. 核心公式：Transition 的语法

`transition` 是一个简写属性，包含四个部分：

```css
/* 语法：属性名 持续时间 时间曲线 延迟时间 */
transition: property duration timing-function delay;
```

1.  **`property` (属性)**: 你想让哪个 CSS 属性产生动画？（如 `width`, `transform`, `background-color`。常用 `all` 表示所有属性）。
2.  **`duration` (时长)**: 动画跑完要多久？（必须带单位，如 `0.3s` 或 `500ms`）。
3.  **`timing-function` (曲线)**: 是匀速运动还是加速运动？（常用 `ease`, `linear`, `ease-in-out`）。
4.  **`delay` (延迟)**: 等多久才开始动？（可选，默认为 0）。

---

### 2. 基础 HTML/CSS 演示

我们来做一个“属性全开”的过渡演示：

```html
<!DOCTYPE html>
<html>
<head>
<style>
  .box-container {
    display: flex;
    gap: 20px;
    padding: 50px;
  }

  .box {
    width: 100px;
    height: 100px;
    background-color: #e74c3c;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    
    /* --- 核心代码 --- */
    /* 1. 我们告诉浏览器：如果这个盒子的 transform 或 background 变了，请用 0.5s 平滑过渡 */
    transition: transform 0.5s ease, background-color 0.3s linear;
  }

  /* 鼠标悬停时的“目的地”状态 */
  .box:hover {
    background-color: #2ecc71; /* 颜色变绿 */
    transform: scale(1.2) rotate(15deg); /* 放大并旋转 */
  }

  /* 另一个盒子：演示 transition: all */
  .box-all {
    width: 100px;
    height: 100px;
    background-color: #3498db;
    transition: all 0.8s cubic-bezier(0.68, -0.55, 0.27, 1.55); /* 这是一个“弹簧”效果 */
  }

  .box-all:hover {
    border-radius: 50%; /* 变成圆形 */
    width: 150px;
    background-color: #9b59b6;
  }
</style>
</head>
<body>
  <div class="box-container">
    <div class="box">悬停我</div>
    <div class="box-all">弹簧效果</div>
  </div>
</body>
</html>
```

---

### 3. 时间曲线 (Timing Function) 的奥秘

这是决定动画“手感”的关键：

*   **`linear`**: 匀速（比较死板，适合进度条）。
*   **`ease`** (默认): 先快后慢（最常用，很自然）。
*   **`ease-in`**: 越来越快（适合物体掉落或飞走）。
*   **`ease-out`**: 越来越慢（适合弹出窗口停下）。
*   **`cubic-bezier(x1, y1, x2, y2)`**: 自定义贝塞尔曲线。你可以做出“回弹”或者“先缩后放”的酷炫效果。

---

### 4. Transition 的触发方式

`transition` 自己是不会动的，它需要一个**“状态改变”**来触发。常见的有三种方式：

1.  **伪类触发**: `:hover` (悬停), `:active` (点击不放), `:focus` (获得焦点)。
2.  **JS 类名切换**: 通过 JavaScript 给元素添加一个 `.active` 类，改变它的 CSS 属性。
3.  **动态修改样式**: 直接通过 JS 修改 `element.style.width`。

**JS 触发示例：**
```javascript
// 当点击按钮时，给方块加上 "move" 类
const box = document.querySelector('.box');
box.addEventListener('click', () => {
  box.classList.toggle('move');
});
```
```css
.box {
  transition: transform 0.4s ease;
}
.box.move {
  transform: translateX(200px);
}
```

---

### 5. Transition 的局限性（避坑指南）

1.  **不能从 `display: none` 过渡到 `display: block`**: 
    如果你尝试这么做，你会发现它会瞬间出现，没有任何过渡。
    *   *解决方法*：改用 `opacity: 0` 到 `opacity: 1`，或者现代 CSS 的 `starting-style`。
2.  **必须有明确的数值**: 
    你不能从 `width: auto` 过渡到 `width: 200px`。浏览器算不出 `auto` 是多少，所以无法计算中间值。
    *   *解决方法*：给一个确定的像素值 `px` 或百分比 `%`。
3.  **初次加载不触发**: 
    页面刚刷新时，属性从无到有的过程通常不会触发 transition。

---

### 总结：Transform 和 Transition 的关系

*   **Transform** 定义了**“变什么”**（位移、旋转、缩放）。
*   **Transition** 定义了**“怎么变”**（时间、快慢）。

**如果你学会了这两个，你已经可以做出 80% 的网页交互动画了。**

**接下来，你想挑战一下最复杂、但也是最强大的 `Animation`（关键帧动画）吗？它可以让动画循环往复，不需要鼠标悬停就能自己动。**

---

---

终于聊到 CSS 动画的**“大招”**了。如果说 `transition` 是**“从 A 点到 B 点”**的简单往返，那么 `animation` 就是**“一出完整的戏”**。

它不需要鼠标悬停，可以**自动播放**，可以**有无数个中间站**，还可以**无限循环**。

---

### 第一步：写剧本 —— `@keyframes` (关键帧)

在用 `animation` 之前，你必须先定义一个“剧本”，告诉浏览器在动画的每个阶段（0% 到 100%）元素该长什么样。

```css
@keyframes slideAndPulse {
  0% {
    transform: translateX(0) scale(1);
    background-color: blue;
  }
  50% {
    transform: translateX(200px) scale(1.5);
    background-color: red;
    border-radius: 50%; /* 中间变成圆 */
  }
  100% {
    transform: translateX(400px) scale(1);
    background-color: blue;
  }
}
```

---

### 第二步：请演员 —— `animation` 属性

有了剧本，你得把它关联到一个元素上。`animation` 是一个强大的缩写属性。

#### 基础 HTML 结构
```html
<div class="stage">
  <div class="actor"></div>
</div>
```

#### 核心 CSS 配置
```css
.stage {
  width: 500px;
  height: 150px;
  border-bottom: 2px solid #ccc;
  padding: 20px;
}

.actor {
  width: 50px;
  height: 50px;
  background-color: blue;
  
  /* --- 核心动画代码 --- */
  /* 1. 剧本名称 */
  animation-name: slideAndPulse;
  /* 2. 持续时间 */
  animation-duration: 3s;
  /* 3. 速度曲线 (同 transition) */
  animation-timing-function: ease-in-out;
  /* 4. 循环次数 (数字 或 infinite) */
  animation-iteration-count: infinite;
  /* 5. 播放方向 (normal, reverse, alternate-往返) */
  animation-direction: alternate;
}
```

---

### 第三步：Animation 的 8 个参数详解（速查表）

通常我们用一行简写：
`animation: name duration timing-function delay iteration-count direction fill-mode play-state;`

1.  **`name`**: `@keyframes` 的名字。
2.  **`duration`**: 走完一轮剧本要多久（如 `2s`）。
3.  **`timing-function`**: 节奏（`linear`, `ease`, `cubic-bezier`）。
4.  **`delay`**: 等多久才动。
5.  **`iteration-count`**: 动几次。常用 `infinite`（无限循环）。
6.  **`direction`**: 
    *   `normal`: 0% -> 100%, 0% -> 100%...
    *   `alternate`: 0% -> 100% -> 0% (像钟摆一样自然)。
7.  **`fill-mode` (敲黑板！重要！)**:
    *   `none`: 动完瞬间弹回初始状态（默认）。
    *   `forwards`: **停在最后一帧**（常用）。
    *   `backwards`: 动画没开始前，先应用第一帧的样子。
8.  **`play-state`**: 
    *   `running`: 播放中。
    *   `paused`: **暂停**（常配合 JS 或 `:hover` 使用，让动画停下）。

---

### 第四步：实战演练 —— 一个“呼吸的按钮”

这不需要 JS，也不需要鼠标，它会自己动：

```html
<style>
  .heartbeat-btn {
    padding: 15px 30px;
    font-size: 18px;
    background-color: #ff4757;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;

    /* 简写模式：剧本 2s 节奏 循环 */
    animation: heartbeat 1.5s ease-in-out infinite;
  }

  /* 鼠标放上去暂停，增加互动感 */
  .heartbeat-btn:hover {
    animation-play-state: paused;
    background-color: #ff6b81;
  }

  @keyframes heartbeat {
    0% { transform: scale(1); box-shadow: 0 0 0 0 rgba(255, 71, 87, 0.7); }
    50% { transform: scale(1.1); box-shadow: 0 0 20px 10px rgba(255, 71, 87, 0); }
    100% { transform: scale(1); box-shadow: 0 0 0 0 rgba(255, 71, 87, 0); }
  }
</style>

<button class="heartbeat-btn">立即抢购</button>
```

---

### Transition vs Animation：我该选哪个？

*   **选 Transition (过渡)**：
    *   只需要“状态 A”到“状态 B”的平滑切换。
    *   由交互（点击、悬停）触发。
    *   逻辑简单，比如变个颜色、改个宽度。

*   **选 Animation (动画)**：
    *   需要**循环**运行。
    *   页面**一加载**就要动。
    *   有**多个中间环节**（0%, 20%, 50%, 100%）。
    *   需要制作复杂的入场/退场特效。

---

### 进阶：如何让你的动画看起来很专业？

1.  **善用 `transform`**：尽量用 `transform` 代替 `width/height/top/left`。`transform` 动画由显卡渲染，不会引起页面卡顿。
2.  **贝塞尔曲线**：不要总是用 `linear`（太死板）。去 [cubic-bezier.com](https://cubic-bezier.com) 找一个有弹性的曲线，会让你的网页瞬间变高级。
3.  **负延迟**：如果你给 `animation-delay` 设置一个负数（如 `-1s`），动画会立即开始，但会从第 1 秒的位置开始跳跃播放。这在做一排波浪进度条时非常有用。

---

### 复习练习：

尝试写一个简单的“Loading”加载条：
1. 建立一个 200px 宽的灰色长条背景。
2. 里面有一个 50px 的蓝色进度条。
3. 让蓝色进度条在 2 秒内从左向右无限滑动。

**你需要我写这个 Loading 练习的答案给你吗？还是你想尝试自己写写看？**

在实际开发中（比如上传文件、下载进度、加载进度），进度条通常是 **CSS（外壳与丝滑感）** 和 **JavaScript（大脑/真实数据）** 的结合。

在这种场景下，我们通常使用 **`transition`** 而不是 `animation`，因为进度是**不可预测且断断续续的**，`transition` 能把这些“跳跃”的数据补齐成丝滑的动画。

下面我分三步教你如何实现一个真实的进度条：

---

### 第一步：HTML 结构（外壳与填充物）

我们需要一个容器（背景板）和一个内部填充条。

```html
<div class="progress-container">
  <!-- 内部填充条，初始宽度为 0 -->
  <div id="progress-bar" class="progress-fill"></div>
</div>
<!-- 显示数字百分比 -->
<p id="status-text">等待上传... 0%</p>
<button onclick="simulateUpload()">模拟开始上传</button>
```

---

### 第二步：CSS 样式（加入 Transition 补间动画）

这是最关键的一步。如果没有 `transition`，进度条会一顿一顿地跳动；有了它，即便数据每秒只更新一次，进度条也会平滑地滑过去。

```css
.progress-container {
  width: 100%;
  height: 20px;
  background-color: #eee;
  border-radius: 10px;
  overflow: hidden; /* 确保内部填充不超出圆角 */
  margin: 20px 0;
}

.progress-fill {
  width: 0%; /* 初始宽度 */
  height: 100%;
  background-color: #2ecc71;
  
  /* --- 核心代码 --- */
  /* 当 JS 修改 width 时，在 0.4 秒内平滑过渡，看起来就很专业 */
  transition: width 0.4s ease-out;
}
```

---

### 第三步：JavaScript 获取真实数据并更新

在真实场景（如 `axios` 或原生 `XMLHttpRequest`）中，你会得到一个 `progress` 事件。

#### 1. 模拟上传逻辑（为了演示）：
```javascript
function simulateUpload() {
  const bar = document.getElementById('progress-bar');
  const text = document.getElementById('status-text');
  let progress = 0;

  // 定时器模拟数据不断传来
  const interval = setInterval(() => {
    progress += Math.random() * 15; // 随机增加进度
    if (progress >= 100) {
      progress = 100;
      clearInterval(interval);
      text.innerText = "上传完成！";
    } else {
      text.innerText = `正在上传... ${Math.round(progress)}%`;
    }

    // --- 修改 CSS 触发 Transition ---
    bar.style.width = progress + '%'; 
  }, 500);
}
```

#### 2. 真实上传逻辑（使用 Axios 示例）：
如果你正在使用 `axios` 上传文件，代码长这样：
```javascript
axios.post('/upload', formData, {
  onUploadProgress: (progressEvent) => {
    // 计算百分比
    let percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
    
    // 直接更新 DOM
    const bar = document.getElementById('progress-bar');
    bar.style.width = percentCompleted + '%';
    document.getElementById('status-text').innerText = percentCompleted + '%';
  }
});
```

---

### 进阶优化：性能更好的方案 (`transform`)

在低端手机上，频繁修改 `width` 可能会导致页面卡顿（因为修改 `width` 会触发浏览器的“重排” Layout）。

**大厂的做法**通常是修改 `transform: scaleX()`：

```css
.progress-fill {
  width: 100%; /* 预设为 100% */
  transform: scaleX(0); /* 初始缩放为 0 */
  transform-origin: left; /* 确保从左边开始放大 */
  transition: transform 0.4s ease-out;
}
```
**JS 部分改为：**
```javascript
// 如果进度是 50%，就缩放 0.5
bar.style.transform = `scaleX(${percentCompleted / 100})`;
```
这样不仅动画更流畅，而且能充分利用显卡加速。

---

### 总结：开发进度条的公式

1.  **HTML**: 父容器 + 子填充。
2.  **CSS**: 给子填充加上 `transition: width 0.3s`（或 `transform`）。
3.  **JS**: 监听到数据变化，把数值赋给 `style.width` 或 `style.transform`。

**你现在手头是有具体的上传业务要做吗？如果有，我可以帮你针对具体的代码框架（比如 React 或 Vue）写一个组件示例。**