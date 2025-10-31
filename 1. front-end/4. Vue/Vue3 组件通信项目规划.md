### **项目行动规划 (Action Plan)**

### **第一步：创建项目**

**前提：** 请确保你的电脑上已经安装了 [Node.js](https://nodejs.org/) (推荐 LTS 版本)，因为 `npm` (Node 包管理器) 是我们执行所有命令的基础。

#### 1. 打开你的终端

*   在 Windows 上，你可以使用 `Command Prompt (CMD)`、`PowerShell` 或者 `Git Bash`。
*   在 macOS 或 Linux 上，你可以使用 `Terminal`。

#### 2. 运行项目创建命令

在终端里，进入你希望存放项目的文件夹（比如 `Desktop` 或 `Documents`）

当你运行 `npm create vite@latest` 后，Vite 的脚手架会启动一个交互式的配置过程。

#### 3. 跟随交互式提示进行配置

终端会依次询问你几个问题，请按照下面的建议进行选择：

1.  **`✔ Project name: …`**
    项目名称。我们输入 `vue3-communication-lab` 然后按回车。

2.  **`✔ Select a framework: › - Use arrow-keys. Return to submit.`**
    选择框架。使用键盘的上下箭头，选中 `Vue`，然后按回车。

3.  **`✔ Select a variant: › - Use arrow-keys. Return to submit.`**
    选择变体。同样，使用箭头选中 `TypeScript`，然后按回车。

4.  **`Use rolldom-vite (Experimental)`**  **No**

5.  **`Install with npm and start now`** **Yes**

#### 4. 验证

**所以，现在你会看到：**

1. 终端开始自动执行 npm install，安装所有依赖。
2. 安装完成后，它会无缝衔接，自动执行 npm run dev，启动开发服务器。
3. 最终，终端会显示出本地服务器的地址，例如 http://localhost:5173/。。

**请在浏览器中查看这个地址。**

当你看到那个经典的 Vite + Vue 欢迎页面时，就代表我们的**第一步已经完美完成**！

---

### **第二步：集成 Vue Router**

#### 1. 停止开发服务器

如果你的终端还在运行 `npm run dev`，请先按下 `Ctrl + C` 来停止它。

#### 2. 安装 `vue-router`

在你的项目根目录 (`vue3-communication-lab`) 下，运行以下命令来安装 Vue Router：

```bash
npm install vue-router@4
```

#### 3. 创建路由配置文件

我们需要一个专门的地方来管理我们所有的页面路由。

1.  在 `src` 文件夹下，创建一个新的文件夹，命名为 `router`。
2.  在刚刚创建的 `src/router` 文件夹内，创建一个新文件，命名为 `index.ts`。

现在你的项目结构应该看起来像这样：

```
vue3-communication-lab/
├── src/
│   ├── assets/
│   ├── components/
│   ├── router/
│   │   └── index.ts  <-- 新建的文件
│   ├── App.vue
│   ├── main.ts
│   └── ...
└── ...
```

#### 4. 配置路由 (`src/router/index.ts`)

打开 `src/router/index.ts` 文件，将以下代码粘贴进去。这段代码会创建路由实例并定义一个初始的“首页”路由。

```typescript
// src/router/index.ts

import { createRouter, createWebHistory } from 'vue-router'

// 1. 定义路由组件。
// 我们稍后会创建这个文件
import HomeView from '../views/HomeView.vue'

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
const routes = [
  { path: '/', name: 'Home', component: HomeView }
]

// 3. 创建路由实例并传递 `routes` 配置
const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHistory(),
  routes, // `routes: routes` 的缩写
})

export default router
```

#### 5. 创建我们的第一个视图（页面）

上面的代码引用了一个我们还未创建的文件 `HomeView.vue`。现在我们就来创建它。

1.  在 `src` 文件夹下，创建一个新的文件夹，命名为 `views`。
2.  在 `src/views` 文件夹内，创建一个新文件，命名为 `HomeView.vue`。
3.  打开 `HomeView.vue` 并粘贴以下内容：

```vue
<!-- src/views/HomeView.vue -->
<template>
  <div>
    <h1>Vue 3 组件通信学习项目</h1>
    <p>请从左侧导航选择一个要学习的通信方式。</p>
  </div>
</template>
```

#### 6. 让 Vue 应用“使用”路由

我们已经创建了路由，但还需要告诉我们的主 Vue 实例去使用它。

打开 `src/main.ts` 文件，修改成如下内容：

```typescript
// src/main.ts

import { createApp } from 'vue'
import App from './App.vue'
// 1. 引入我们创建的 router
import router from './router'
import './style.css'


const app = createApp(App)

// 2. 使用 router
app.use(router)

app.mount('#app')

```

#### 7. 在主组件中指定路由显示位置

最后一步，我们需要告诉 Vue 在哪里显示路由匹配到的组件。

打开 `src/App.vue`，**完全替换**其所有内容为以下代码。这将清空 Vite 的默认欢迎页面，并为我们的路由视图腾出空间。

```vue
<!-- src/App.vue -->
<template>
  <router-view />
</template>

<script setup lang="ts">
</script>

<style>
/* 我们可以暂时保留或清空样式 */
</style>
```

#### 8. 验证成果

所有配置都已完成！现在，让我们重新启动开发服务器来验证一下。

在终端中运行：

```bash
npm run dev
```

现在，回到你的浏览器并访问 `http://localhost:5173/`。你应该不再看到 Vite 的欢迎页，而是看到我们 `HomeView.vue` 组件的内容：“Vue 3 组件通信学习项目”。

**如果看到了这个页面，恭喜你，路由系统已经成功集成！**



完美！截图确认，第二步“集成 Vue Router”已经顺利完成。我们的项目现在有了页面导航的基础。

接下来，我们将一鼓作气，完成**第三步和第四步**，把 Pinia 和 Mitt 这两个工具也集成进来。这两个步骤都很快，主要是安装和基础配置，为我们后续的页面开发做好准备。

---

### **第三步：集成 Pinia (状态管理)**

#### 1. 停止开发服务器

如果终端还在运行，请按下 `Ctrl + C` 停止。

#### 2. 安装 `pinia`

在项目根目录下，运行安装命令：

```bash
npm install pinia
```

#### 3. 配置 Pinia

我们需要在 Vue 应用启动时挂载 Pinia 实例。

打开 `src/main.ts` 文件，添加两行代码：

```typescript
// src/main.ts

import { createApp } from 'vue'
// 1. 引入 createPinia
import { createPinia } from 'pinia' 
import App from './App.vue'
import router from './router'
import './style.css'

// 2. 创建 pinia 实例
const pinia = createPinia()
const app = createApp(App)

app.use(router)
app.use(pinia) // 3. 使用 pinia

app.mount('#app')
```

#### 4. 创建 `stores` 目录

为了保持代码整洁，我们按照约定在 `src` 目录下创建一个 `stores` 文件夹。我们现在还不会在里面放任何东西，但这个文件夹会在我们开发 Pinia 页面时用到。

*   在 `src` 目录下，新建一个文件夹，命名为 `stores`。

---

### **第四步：集成 Mitt (事件总线)**

#### 1. 安装 `mitt`

在项目根目录下，运行安装命令：

```bash
npm install mitt
```

#### 2. 创建全局 emitter 实例

我们需要一个全局共享的 `emitter` 实例，以便任何组件都可以导入和使用它。

1.  在 `src` 目录下，创建一个新的文件夹，命名为 `utils`。
2.  在 `src/utils` 文件夹内，创建一个新文件，命名为 `emitter.ts`。
3. 打开 `src/utils/emitter.ts` 文件，并粘贴以下代码：

   ```ts
   // src/utils/emitter.ts
   
   import mitt from 'mitt'
   
   // 定义一个通用的事件类型，如果需要更严格的类型检查
   // type Events = {
   //   [propName: string]: any;
   // };
   
   const emitter = mitt()
   
   export default emitter
   ```

---



好的，项目基建已经全部完成，一切正常。

现在，我们正式进入**阶段二：核心骨架搭建**。

在这个阶段，我们的目标只有一个：**修改 `App.vue` 文件，创建出应用的整体布局**。这个布局将包含一个固定的侧边栏导航和右侧的内容展示区。

---

### **第五步：设计主布局 `App.vue`**

我们将把 `App.vue` 从一个只有一个 `<router-view />` 的空壳，改造成一个有血有肉的应用程序框架。

#### 1. 定义我们的页面结构

首先，我们需要规划好侧边栏要显示哪些导航链接。在 `<script setup>` 中，我们创建一个数组来管理这些信息。这样做的好处是，以后想增加或修改页面，只需要维护这个数组即可。

#### 2. 创建 HTML 布局

我们会使用一个 `div` 作为主容器，内部包含一个 `<nav>` (导航) 和一个 `<main>` (主内容区)。`<router-view />` 将被移动到 `<main>` 区域中。

#### 3. 添加样式

我们会添加一些简单的 CSS，使用 Flexbox 布局来轻松实现经典的侧边栏布局，并美化导航链接的样式，包括为当前激活的链接设置高亮效果。

#### 4. 修改全局css

把全局css替换成基本样式

---

#### **验证成果**

保存文件后，回到你的浏览器查看 `http://localhost:5173/`。

**你将会看到一个全新的界面：**

*   **左侧**：一个白色的侧边栏，上面列出了我们刚刚在 `pages` 数组中定义的所有通信方式的链接。
*   **右侧**：依然是我们之前创建的首页内容。

点击侧边栏的 "首页" 链接，右侧内容区不会有任何变化，但链接会高亮。如果你点击其他链接（比如 "1. Props & Emit"），**页面会报错或者显示空白**，这是完全正常的！因为我们还没有为这些路径创建对应的页面组件和路由规则。



当然记得！我们的页面设计思路是**“自解释、自演示”**。

每个页面都将包含三个核心区域：

1.  **介绍区**：用简洁的文字解释这个通信方式的理论知识。
2.  **演示区 (Live Demo)**：一个可以实时互动的区域，让你亲手操作，直观地感受数据是如何流动的。
3.  **代码区**：展示实现这个 Demo 的核心代码，方便你对照学习。

现在，我们将按照这个蓝图，一步步构建出 `Props & Emit` 页面。

---

### **开始行动：实现 `Props & Emit` 页面**

#### **第一步：创建所需文件**

我们需要一个视图文件作为“父组件”，以及一个普通组件文件作为“子组件”。

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `PropsEmitView.vue`。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `props-emit-demo`。
3.  在 `src/components/props-emit-demo` 文件夹下，创建一个新文件，命名为 `ChildComponent.vue`。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── HomeView.vue
│   └── PropsEmitView.vue  <-- 新建
└── components/
    └── props-emit-demo/
        └── ChildComponent.vue  <-- 新建
```

#### **第二步：更新路由**

我们需要告诉 Vue Router，当用户访问 `/props-emit` 路径时，应该显示 `PropsEmitView.vue` 组件。

打开 `src/router/index.ts` 文件，添加一条新的路由记录：

```typescript{2,9-12}
// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  { path: '/', name: 'Home', component: HomeView },
  // 👇 添加下面这段新的路由配置
  {
    path: '/props-emit',
    name: 'Props & Emit',
    component: () => import('../views/PropsEmitView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
```
*我们这里使用了路由懒加载 `() => import(...)`，这是一个最佳实践，它意味着只有当用户访问这个页面时，对应的组件代码才会被下载。*

#### **第三步：实现子组件 (`ChildComponent.vue`)**

这个子组件很简单，它的任务是：接收并显示来自父组件的消息，并提供一个输入框和一个按钮来向父组件发送新消息。

打开 `src/components/props-emit-demo/ChildComponent.vue` 并粘贴以下代码：

```vue
<!-- src/components/props-emit-demo/ChildComponent.vue -->
<template>
  <div class="child-component">
    <h3>子组件</h3>
    <p>从父组件收到的消息: <span class="message">{{ message }}</span></p>

    <div class="input-area">
      <input v-model="childMessage" placeholder="在这里输入消息发送给父组件" />
      <button @click="sendMessage">发送消息</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// 1. 【Props】使用 defineProps 声明从父组件接收一个名为 'message' 的 prop
// 我们使用对象语法进行类型校验，确保它是字符串且必填
defineProps({
  message: {
    type: String,
    required: true
  }
});

// 2. 【Emit】使用 defineEmits 声明该组件会触发一个名为 'message-from-child' 的事件
const emit = defineEmits(['message-from-child']);

// 用于绑定子组件输入框的本地响应式数据
const childMessage = ref('');

// 3. 点击按钮时，调用此方法
function sendMessage() {
  // 使用 emit 触发事件，并将 childMessage.value 作为载荷（payload）发送出去
  emit('message-from-child', childMessage.value);
  // 清空输入框
  childMessage.value = '';
}
</script>

<style scoped>
.child-component {
  padding: 20px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.message {
  color: #1890ff;
  font-weight: bold;
}
.input-area {
  margin-top: 15px;
  display: flex;
  gap: 10px;
}
input {
  flex-grow: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  padding: 8px 15px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #36a374;
}
</style>
```

#### **第四步：实现父组件 (`PropsEmitView.vue`)**

现在来构建我们的主页面。我们将严格按照“介绍区、演示区、代码区”三段式结构来编写。

打开 `src/views/PropsEmitView.vue` 并粘贴以下代码：

```vue
<!-- src/views/PropsEmitView.vue -->
<template>
  <div class="props-emit-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>1. Props & Emit</h1>
      <p>
        这是 Vue 中最基础也是最核心的通信方式，用于父子组件之间的数据传递。
      </p>
      <ul>
        <li><strong>Props (属性)</strong>: 数据从父组件单向流向子组件。子组件只能读取，不能修改。</li>
        <li><strong>Emit (触发事件)</strong>: 子组件通过触发自定义事件，将信息回传给父组件，形成通信闭环。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <div class="parent-component">
        <h3>父组件</h3>
        <div class="input-area">
          <label>在父组件中修改消息:</label>
          <input v-model="parentMessage" />
        </div>
        <p>从子组件收到的消息: <span class="message">{{ childMessage }}</span></p>

        <!-- 关键交互点 -->
        <ChildComponent 
          :message="parentMessage" 
          @message-from-child="handleChildMessage"
        />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h2>核心代码</h2>
      <div class="code-blocks">
        <div class="code-block">
          <h4>父组件 (PropsEmitView.vue)</h4>
          <pre><code>{{ parentCode }}</code></pre>
        </div>
        <div class="code-block">
          <h4>子组件 (ChildComponent.vue)</h4>
          <pre><code>{{ childCode }}</code></pre>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ChildComponent from '@/components/props-emit-demo/ChildComponent.vue';

// --- Demo Logic ---
const parentMessage = ref('来自父组件的初始消息');
const childMessage = ref('暂未收到子组件消息');

function handleChildMessage(payload: string) {
  childMessage.value = payload || '子组件发送了空消息';
}

// --- Code Snippets for Display ---
const parentCode = `
// 父组件中
const parentMessage = ref('...');

function handleChildMessage(payload) {
  childMessage.value = payload;
}

<ChildComponent 
  :message="parentMessage" 
  @message-from-child="handleChildMessage"
/>
`;

const childCode = `
// 子组件中
defineProps({
  message: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['message-from-child']);

function sendMessage() {
  emit('message-from-child', '...');
}
`;
</script>

<style scoped>
.props-emit-view {
  max-width: 900px;
}

section {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

h1, h2 {
  margin-top: 0;
}

.parent-component {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.input-area {
  margin-bottom: 15px;
}
.input-area label {
  margin-right: 10px;
}
.input-area input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.message {
  color: #d9534f;
  font-weight: bold;
}

.code-blocks {
  display: flex;
  gap: 20px;
}
.code-block {
  flex: 1;
}
pre {
  background-color: #2d2d2d;
  color: #f0f0f0;
  padding: 15px;
  border-radius: 5px;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>
```

#### **第五步：验证成果**

现在，是时候见证奇迹了！

1.  确保你的开发服务器仍在运行 (`npm run dev`)。
2.  在浏览器中，点击我们侧边栏的 **"1. Props & Emit"** 链接。
3.  你应该能看到我们刚刚创建的页面。

**请进行以下交互测试：**

*   **测试 Props (父 → 子)**：在**父组件**的输入框里输入任何文字，观察**子组件**中显示的“从父组件收到的消息”是否会**实时**变化。
*   **测试 Emit (子 → 父)**：在**子组件**的输入框里输入文字，然后点击“发送消息”按钮。观察**父组件**中显示的“从子组件收到的消息”是否会更新为你刚刚输入的内容。

如果你能成功完成以上两个测试，那么恭喜你！你已经亲手实现了 Vue 中最重要、最核心的通信模式！



好的，理论学习结束，实战开始！

我们将按照之前定好的三段式结构，构建 `v-model` 页面。这个页面的核心是封装一个自定义的输入框组件 `CustomInput.vue`，让它能像原生 `<input>` 一样使用 `v-model`。我们还会额外展示多个 `v-model` 的用法。

---

### **开始行动：实现 `v-model` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `VModelView.vue`。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `v-model-demo`。
3.  在 `src/components/v-model-demo` 文件夹下，创建两个新文件：`CustomInput.vue` 和 `UserInfoEditor.vue`。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── VModelView.vue          <-- 新建
└── components/
    ├── ...
    └── v-model-demo/
        ├── CustomInput.vue     <-- 新建
        └── UserInfoEditor.vue  <-- 新建
```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加 `v-model` 页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/v-model', name: 'v-model', component: () => import('../views/VModelView.vue') }
]
// ...
```

#### **第三步：实现子组件 (`CustomInput.vue`)**

这是我们默认 `v-model` 示例的核心。它是一个简单的输入框封装。

打开 `src/components/v-model-demo/CustomInput.vue` 并粘贴以下代码：

```vue
<!-- src/components/v-model-demo/CustomInput.vue -->
<template>
  <div class="custom-input">
    <label v-if="label">{{ label }}</label>
    <input 
      :value="modelValue" 
      @input="emit('update:modelValue', ($event.target as HTMLInputElement).value)"
      placeholder="这是一个自定义输入框"
    >
  </div>
</template>

<script setup lang="ts">
// 1. 接收 modelValue prop
// 2. 接收一个可选的 label prop，让组件更通用
defineProps({
  modelValue: String,
  label: String
});

// 3. 声明 update:modelValue 事件
const emit = defineEmits(['update:modelValue']);
</script>

<style scoped>
.custom-input {
  display: flex;
  flex-direction: column;
  gap: 5px;
}
input {
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}
</style>
```
*注意：这里我们使用了更简洁的 `@input` 内联写法，它和我们教学中 `handleInput` 方法的效果是完全一样的。*

#### **第四步：实现子组件 (`UserInfoEditor.vue`)**

这是我们多个 `v-model` 示例的子组件。

打开 `src/components/v-model-demo/UserInfoEditor.vue` 并粘贴以下代码：

```vue
<!-- src/components/v-model-demo/UserInfoEditor.vue -->
<template>
  <div class="user-info-editor">
    <CustomInput 
      label="姓 (First Name):"
      :modelValue="firstName" 
      @update:modelValue="emit('update:firstName', $event)"
    />
    <CustomInput 
      label="名 (Last Name):"
      :modelValue="lastName" 
      @update:modelValue="emit('update:lastName', $event)"
    />
  </div>
</template>

<script setup lang="ts">
import CustomInput from './CustomInput.vue';

// 接收 'firstName' 和 'lastName' 两个 prop
defineProps(['firstName', 'lastName']);

// 声明 'update:firstName' 和 'update:lastName' 两个事件
const emit = defineEmits(['update:firstName', 'update:lastName']);
</script>

<style scoped>
.user-info-editor {
  display: flex;
  gap: 20px;
  padding: 20px;
  border: 1px dashed #ccc;
  border-radius: 8px;
}
</style>```

#### **第五步：实现父组件 (`VModelView.vue`)**

现在，我们来构建主页面，它将使用上面创建的两个子组件。

打开 `src/views/VModelView.vue` 并粘贴以下代码：

```vue
<!-- src/views/VModelView.vue -->
<template>
  <div class="v-model-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>2. v-model</h1>
      <p>
        <code>v-model</code> 是 <code>props</code> 和 <code>emit</code> 的一个语法糖，用于轻松实现父子组件之间的双向数据绑定。
      </p>
      <ul>
        <li><strong>默认 v-model:</strong> 相当于传递 <code>modelValue</code> prop 并监听 <code>update:modelValue</code> 事件。</li>
        <li><strong>多个 v-model (Vue 3):</strong> 可以通过参数实现，例如 <code>v-model:title</code> 相当于传递 <code>title</code> prop 并监听 <code>update:title</code> 事件。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      
      <!-- 默认 v-model 示例 -->
      <div class="demo-block">
        <h3>默认 v-model</h3>
        <p>你在下面输入框输入的内容将实时同步到这里: <span class="value">{{ searchText }}</span></p>
        <CustomInput v-model="searchText" />
      </div>

      <!-- 多个 v-model 示例 -->
      <div class="demo-block">
        <h3>多个 v-model</h3>
        <p>
          用户信息: 
          <span class="value">{{ user.firstName }} {{ user.lastName }}</span>
        </p>
        <UserInfoEditor 
          v-model:firstName="user.firstName" 
          v-model:lastName="user.lastName" 
        />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h2>核心代码</h2>
      <div class="code-blocks">
        <div class="code-block">
          <h4>父组件 (VModelView.vue)</h4>
          <pre><code>{{ parentCode }}</code></pre>
        </div>
        <div class="code-block">
          <h4>子组件 (CustomInput.vue)</h4>
          <pre><code>{{ childCode }}</code></pre>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import CustomInput from '@/components/v-model-demo/CustomInput.vue';
import UserInfoEditor from '@/components/v-model-demo/UserInfoEditor.vue';

// --- Demo Logic ---
const searchText = ref('Hello v-model');
const user = reactive({
  firstName: 'John',
  lastName: 'Doe',
});

// --- Code Snippets for Display ---
const parentCode = `
// --- 父组件 ---
import { ref } from 'vue';
import CustomInput from './CustomInput.vue';

const searchText = ref('');

// 使用 v-model
<CustomInput v-model="searchText" />

// 等价于:
<CustomInput 
  :modelValue="searchText"
  @update:modelValue="newValue => searchText = newValue"
/>
`;

const childCode = `
// --- 子组件 (CustomInput.vue) ---
<template>
  <input 
    :value="modelValue" 
    @input="emit('update:modelValue', $event.target.value)"
  >
</template>

<script setup>
defineProps(['modelValue']);
const emit = defineEmits(['update:modelValue']);
<\/script>
`;
</script>

<style scoped>
.v-model-view {
  max-width: 900px;
}
section {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.demo-block {
  margin-bottom: 30px;
}
.value {
  color: #1890ff;
  font-weight: bold;
}
.code-blocks {
  display: flex;
  gap: 20px;
}
.code-block {
  flex: 1;
}
pre {
  background-color: #2d2d2d;
  color: #f0f0f0;
  padding: 15px;
  border-radius: 5px;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>
```
*在 `<script>` 标签的显示中，我使用了 `<\/script>` 来避免它被浏览器错误地解析为当前页面的脚本结束标签。*

#### **第六步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"2. v-model"** 链接。

**请进行以下交互测试：**

*   **默认 `v-model` 测试**：在“默认 v-model”区域的自定义输入框中输入文字。观察上面显示的文本是否与你输入的内容**实时双向同步**。
*   **多个 `v-model` 测试**：在“多个 v-model”区域的两个输入框（姓/名）中分别输入文字。观察上面显示的完整用户信息是否也**实时双向同步**。

如果两个示例都能完美地工作，那么你已经成功掌握了如何利用 `v-model` 来封装更简洁、更符合直觉的自定义组件！





好的，我们来构建 `$attrs` 的演示页面。

这个页面的核心是模拟**祖孙三代组件**的通信场景。祖父组件 (`GrandParent`) 会传递多个属性，一部分给父亲 (`Parent`)，另一部分希望直接透传给孙子 (`GrandChild`)。我们将通过 `$attrs` 优雅地实现这个需求。

---

### **开始行动：实现 `$attrs` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `AttrsView.vue`。这个文件将作为我们的“祖父”组件。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `attrs-demo`。
3.  在 `src/components/attrs-demo` 文件夹下，创建两个新文件：`ParentComponent.vue`（父组件）和 `ChildComponent.vue`（孙组件）。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── AttrsView.vue            <-- 新建
└── components/
    ├── ...
    └── attrs-demo/
        ├── ParentComponent.vue  <-- 新建
        └── ChildComponent.vue   <-- 新建
```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加 `$attrs` 页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/attrs', name: '$attrs', component: () => import('../views/AttrsView.vue') }
]
// ...
```

#### **第三步：实现孙组件 (`ChildComponent.vue`)**

我们从最深层的组件开始。它的任务很简单，就是接收并展示最终传递过来的所有属性。

打开 `src/components/attrs-demo/ChildComponent.vue` 并粘贴以下代码：

```vue
<!-- src/components/attrs-demo/ChildComponent.vue -->
<template>
  <div class="child-component">
    <h4>孙组件</h4>
    <p>我收到的所有属性 (来自 $attrs):</p>
    <ul>
      <!-- 遍历并显示 attrs 对象中的所有键值对 -->
      <li v-for="(value, key) in attrs" :key="key">
        <strong>{{ key }}:</strong> {{ value }}
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useAttrs } from 'vue';

// 使用 useAttrs() 获取所有透传过来的属性
const attrs = useAttrs();
</script>

<style scoped>
.child-component {
  padding: 15px;
  background-color: #e6f7ff;
  border: 1px solid #91d5ff;
  border-radius: 4px;
}
ul {
  padding-left: 20px;
}
</style>
```

#### **第四步：实现父组件 (`ParentComponent.vue`)**

这是中间的“快递中转站”。它会“认领”自己需要的 `prop`，然后把剩下的所有属性 (`$attrs`) 继续向下传递给子组件。

打开 `src/components/attrs-demo/ParentComponent.vue` 并粘贴以下代码：

```vue
<!-- src/components/attrs-demo/ParentComponent.vue -->
<template>
  <div class="parent-component">
    <h3>父组件</h3>
    <p>我只接收 'title' prop: <span class="value">{{ title }}</span></p>
    <p>剩下的属性 ($attrs) 我将全部透传给子组件:</p>
    
    <!-- 关键点：使用 v-bind="$attrs" 将所有未被 props 接收的属性继续向下传递 -->
    <ChildComponent v-bind="$attrs" />
  </div>
</template>

<script setup lang="ts">
import ChildComponent from './ChildComponent.vue';

// 父组件只声明接收 'title' 这一个 prop
defineProps({
  title: String
});

// Vue 3 推荐的做法，明确禁用属性继承，因为我们是手动 v-bind
defineOptions({
  inheritAttrs: false
});
</script>

<style scoped>
.parent-component {
  padding: 20px;
  background-color: #fffbe6;
  border: 1px solid #ffe58f;
  border-radius: 8px;
}
.value {
  color: #faad14;
  font-weight: bold;
}
</style>
```

#### **第五步：实现祖父组件 (`AttrsView.vue`)**

现在来构建主页面。

打开 `src/views/AttrsView.vue` 并粘贴以下代码：

```vue
<!-- src/views/AttrsView.vue -->
<template>
  <div class="attrs-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>3. $attrs</h1>
      <p>
        <code>$attrs</code> 是一个非常有用的特性，它包含父组件传递的所有属性，但**排除**了子组件通过 <code>props</code> 声明接收的属性。它常用于属性的“透传”。
      </p>
      <ul>
        <li><strong>核心用法:</strong> 在中间层组件上使用 <code>v-bind="$attrs"</code>，将属性批量传递给更深层的子组件。</li>
        <li><strong>注意:</strong> 在 <code>&lt;script setup&gt;</code> 中，需要通过 <code>useAttrs()</code> API 来访问 <code>$attrs</code> 对象。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <div class="grandparent-component">
        <h2>祖父组件</h2>
        <p>我将传递以下所有属性给父组件:</p>
        <ul>
          <li><code>title</code> (父组件会接收)</li>
          <li><code>message</code> (将透传给孙组件)</li>
          <li><code>user-id</code> (将透传给孙组件)</li>
          <li><code>is-active</code> (将透传给孙组件)</li>
        </ul>
        
        <!-- 
          我们在这里传递了 4 个属性。
          ParentComponent 只会接收 title。
          剩下的 3 个会进入 ParentComponent 的 $attrs，
          并被 v-bind="$attrs" 传递给 ChildComponent。
        -->
        <ParentComponent 
          title="一个重要的标题"
          message="这是要给孙子的秘密消息"
          :user-id="123"
          :is-active="true"
        />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h4>核心代码 (ParentComponent.vue)</h4>
      <pre><code>{{ parentCode }}</code></pre>
    </section>
  </div>
</template>

<script setup lang="ts">
import ParentComponent from '@/components/attrs-demo/ParentComponent.vue';

// --- Code Snippets for Display ---
const parentCode = `
<!-- ParentComponent.vue (中间层) -->
<template>
  <div class="parent-component">
    <h3>父组件</h3>
    <p>我只接收 'title' prop: {{ title }}</p>
    
    <!-- 关键点: 将所有未被 props 接收的属性
         (message, user-id, is-active) 
         继续向下传递给 ChildComponent -->
    <ChildComponent v-bind="$attrs" />
  </div>
</template>

<script setup>
import ChildComponent from './ChildComponent.vue';

// 父组件只声明接收 'title'
defineProps({
  title: String
});

// 推荐禁用默认的属性继承
defineOptions({
  inheritAttrs: false
});
<\/script>
`;
</script>

<style scoped>
.attrs-view {
  max-width: 900px;
}
section {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.grandparent-component {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}
ul {
  line-height: 1.8;
}
pre {
  background-color: #2d2d2d;
  color: #f0f0f0;
  padding: 15px;
  border-radius: 5px;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>
```

#### **第六步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"3. $attrs"** 链接。

**请仔细观察页面显示：**

*   在**父组件**区域，你应该能看到它正确地显示了 “我只接收 'title' prop: 一个重要的标题”。
*   在**孙组件**区域，你应该能看到一个列表，清晰地展示了它通过 `$attrs` 收到的那 3 个透传属性：
    *   `message: 这是要给孙子的秘密消息`
    *   `userId: 123`
    *   `isActive: true`

这个结果完美地演示了 `$attrs` 的核心功能：中间组件只取自己所需，然后将其他的属性无感知、无损地“快递”给下一层组件。这就完成了 `$attrs` 的学习！

好的，理论学习完毕，我们马上开始构建 `ref & defineExpose` 的演示页面。

我们的场景是模拟一个常见的表单校验需求：父组件中有一个“提交”按钮，点击时需要触发子组件 `MyForm` 内部的校验方法，并获取校验结果。

---

### **开始行动：实现 `ref & defineExpose` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `RefExposeView.vue`。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `ref-expose-demo`。
3.  在 `src/components/ref-expose-demo` 文件夹下，创建一个新文件，命名为 `MyForm.vue`。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── RefExposeView.vue      <-- 新建
└── components/
    ├── ...
    └── ref-expose-demo/
        └── MyForm.vue         <-- 新建
```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加新页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/ref-expose', name: 'ref & defineExpose', component: () => import('../views/RefExposeView.vue') }
]
// ...
```

#### **第三步：实现子组件 (`MyForm.vue`)**

这个子组件是一个简单的表单，它内部有自己的数据和校验逻辑。最关键的是，它会通过 `defineExpose` 暴露一个 `validate` 方法。

打开 `src/components/ref-expose-demo/MyForm.vue` 并粘贴以下代码：

```vue
<!-- src/components/ref-expose-demo/MyForm.vue -->
<template>
  <div class="my-form">
    <h3>子组件 (MyForm.vue)</h3>
    <div class="form-item">
      <label>用户名:</label>
      <input v-model="username" placeholder="用户名不能为空" />
    </div>
    <div class="form-item">
      <label>密码:</label>
      <input v-model="password" type="password" placeholder="密码不能少于6位" />
    </div>
    <p class="internal-state">内部校验状态: {{ validationStatus }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const validationStatus = ref('待校验');

// 这是一个内部方法，父组件无法访问
function runValidationLogic() {
  if (!username.value) {
    validationStatus.value = '失败：用户名不能为空！';
    return false;
  }
  if (password.value.length < 6) {
    validationStatus.value = '失败：密码长度不能少于6位！';
    return false;
  }
  validationStatus.value = '成功！';
  return true;
}

// 关键点：使用 defineExpose 暴露一个公共方法
defineExpose({
  // 将内部的校验逻辑包装成一个名为 validate 的公共方法暴露出去
  validate: runValidationLogic
});
</script>

<style scoped>
.my-form {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}
.form-item {
  margin-bottom: 15px;
}
.form-item label {
  display: inline-block;
  width: 80px;
}
input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.internal-state {
  font-style: italic;
  color: #888;
}
</style>
```

#### **第四步：实现父组件 (`RefExposeView.vue`)**

父组件将使用 `ref` 来获取 `MyForm` 组件的实例，并在点击按钮时调用其暴露的 `validate` 方法。

打开 `src/views/RefExposeView.vue` 并粘贴以下代码：

```vue
<!-- src/views/RefExposeView.vue -->
<template>
  <div class="ref-expose-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>4. ref & defineExpose</h1>
      <p>
        这是一种命令式的通信方式，允许父组件获取子组件的引用，并直接调用子组件通过 <code>defineExpose</code> 暴露的方法或访问其属性。
      </p>
      <ul>
        <li><strong>父组件:</strong> 使用 <code>ref</code> 模板引用来获取子组件实例。</li>
        <li><strong>子组件:</strong> 在 <code>&lt;script setup&gt;</code> 中，必须使用 <code>defineExpose</code> 来明确指定哪些属性和方法是公开的。</li>
        <li><strong>注意:</strong> 这种方式应谨慎使用，因为它会增加组件间的耦合度。优先考虑 props/emit。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <div class="parent-component">
        <h3>父组件</h3>
        <p>校验结果: <span class="result" :class="resultClass">{{ validationResult }}</span></p>
        <button @click="handleValidate">点击这里，调用子组件的 validate 方法</button>
        
        <!-- 
          1. 创建一个 ref: const formRef = ref(null)
          2. 将 ref 绑定到子组件上: ref="formRef"
        -->
        <MyForm ref="formRef" />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h2>核心代码</h2>
      <div class="code-blocks">
        <div class="code-block">
          <h4>父组件 (RefExposeView.vue)</h4>
          <pre><code>{{ parentCode }}</code></pre>
        </div>
        <div class="code-block">
          <h4>子组件 (MyForm.vue)</h4>
          <pre><code>{{ childCode }}</code></pre>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import MyForm from '@/components/ref-expose-demo/MyForm.vue';

// --- Demo Logic ---
// 1. 创建一个 ref 来持有 MyForm 组件的实例
// InstanceType<typeof MyForm> 是获取组件实例类型的 TypeScript 高级用法
const formRef = ref<InstanceType<typeof MyForm> | null>(null);
const validationResult = ref('等待校验...');

// 动态计算结果的 CSS 类
const resultClass = computed(() => {
  if (validationResult.value.includes('成功')) return 'success';
  if (validationResult.value.includes('失败')) return 'error';
  return '';
});

// 2. 点击按钮时，通过 ref 调用子组件暴露的方法
function handleValidate() {
  if (formRef.value) {
    const isValid = formRef.value.validate(); // 调用子组件的 validate 方法
    validationResult.value = isValid ? '校验成功！' : '校验失败！';
  } else {
    validationResult.value = '获取子组件实例失败';
  }
}

// --- Code Snippets for Display ---
const parentCode = `
// --- 父组件 ---
import { ref } from 'vue';
import MyForm from './MyForm.vue';

// 1. 创建 ref
const formRef = ref(null);

function handleValidate() {
  // 3. 通过 .value 调用子组件方法
  if (formRef.value) {
    formRef.value.validate();
  }
}

// 2. 绑定 ref
<MyForm ref="formRef" />
<button @click="handleValidate">校验</button>
`;

const childCode = `
// --- 子组件 (MyForm.vue) ---
import { ref } from 'vue';

function runValidationLogic() {
  // ... 内部校验逻辑 ...
  return true; // or false
}

// 关键点: 暴露公共 API
defineExpose({
  validate: runValidationLogic
});
`;
</script>

<style scoped>
.ref-expose-view { max-width: 900px; }
section { margin-bottom: 30px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
.parent-component { padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
button { margin-bottom: 15px; padding: 8px 15px; background-color: #1890ff; color: white; border: none; border-radius: 4px; cursor: pointer; }
.result { font-weight: bold; }
.success { color: #52c41a; }
.error { color: #f5222d; }
.code-blocks { display: flex; gap: 20px; }
.code-block { flex: 1; }
pre { background-color: #2d2d2d; color: #f0f0f0; padding: 15px; border-radius: 5px; white-space: pre-wrap; word-wrap: break-word; }
</style>
```

#### **第五步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"4. ref & defineExpose"** 链接。

**请进行以下交互测试：**

*   **测试校验失败**：不要在子组件的输入框里输入任何内容，直接点击父组件的“调用子组件的 validate 方法”按钮。观察父组件显示的校验结果是否变为红色的“校验失败！”。同时，子组件内部的“内部校验状态”也会更新。
*   **测试校验成功**：在子组件的用户名输入框里输入任意内容，在密码输入框里输入至少 6 位字符。然后再次点击父组件的按钮。观察父组件显示的校验结果是否变为绿色的“校验成功！”。

这个交互过程完美地演示了 `ref` 和 `defineExpose` 的核心价值：父组件在需要时，可以命令式地触发子组件的内部行为，并获取其执行结果，而无需通过 `props` 来控制子组件的状态。

好的，我们马上开始构建 `provide` & `inject` 的演示页面。

我们的场景将模拟一个经典且非常实用的案例：**全局主题切换**。祖父组件 (`ProvideInjectView`) 将提供一个响应式的主题状态（亮色/暗色）和一个切换主题的方法。无论组件嵌套多深，最深处的孙子组件都能注入并使用这个主题，还能调用方法来改变它。

---

### **开始行动：实现 `provide` & `inject` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `ProvideInjectView.vue` (祖父)。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `provide-inject-demo`。
3.  在 `src/components/provide-inject-demo` 文件夹下，创建两个新文件：`MiddleComponent.vue` (父) 和 `DeepChild.vue` (孙)。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── ProvideInjectView.vue    <-- 新建
└── components/
    ├── ...
    └── provide-inject-demo/
        ├── MiddleComponent.vue  <-- 新建
        └── DeepChild.vue        <-- 新建
```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加新页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/provide-inject', name: 'Provide & Inject', component: () => import('../views/ProvideInjectView.vue') }
]
// ...
```

#### **第三步：实现孙组件 (`DeepChild.vue`)**

我们从最深层的“消费者”开始。它将 `inject` 主题数据和切换方法。

打开 `src/components/provide-inject-demo/DeepChild.vue` 并粘贴以下代码：

```vue
<!-- src/components/provide-inject-demo/DeepChild.vue -->
<template>
  <!-- 
    根据注入的主题动态改变 class。
    在 scoped style 中，我们可以用 :deep() 选择器来影响子组件的样式，
    但这里我们直接绑定 class 并在父组件中定义样式更简单。
  -->
  <div class="deep-child" :class="theme">
    <h4>孙组件</h4>
    <p>我通过 <code>inject</code> 直接获取了顶层组件提供的主题: <span class="value">{{ theme }}</span></p>
    <button @click="toggleTheme">点我切换主题 (调用顶层方法)</button>
  </div>
</template>

<script setup lang="ts">
import { inject } from 'vue';
import type { Ref } from 'vue';

// 定义期望注入的类型，提供更好的类型安全
interface ThemeContext {
  theme: Ref<string>;
  toggleTheme: () => void;
}

// 注入顶层提供的 'themeContext'
// 提供一个默认值以防万一，这让组件更健壮
const { theme, toggleTheme } = inject<ThemeContext>('themeContext', {
  theme: ref('default'),
  toggleTheme: () => console.warn('toggleTheme not provided')
});
</script>

<style scoped>
.deep-child {
  padding: 15px;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
  border: 1px solid;
}
.value {
  font-weight: bold;
  text-transform: uppercase;
}
button {
  padding: 8px 12px;
  border-radius: 4px;
  cursor: pointer;
  border: 1px solid;
}

/* 主题样式 */
.light {
  background-color: #f0f9ff;
  border-color: #d9ecff;
  color: #333;
}
.light button {
  background-color: #fff;
  border-color: #dcdfe6;
  color: #606266;
}
.dark {
  background-color: #333;
  border-color: #666;
  color: #fff;
}
.dark button {
  background-color: #555;
  border-color: #888;
  color: #fff;
}
</style>
```

#### **第四步：实现父组件 (`MiddleComponent.vue`)**

这个中间层组件是关键的演示点：**它什么都不用做**。它只是一个普通的组件，完全不知道有“主题”这回事。

打开 `src/components/provide-inject-demo/MiddleComponent.vue` 并粘贴以下代码：

```vue
<!-- src/components/provide-inject-demo/MiddleComponent.vue -->
<template>
  <div class="middle-component">
    <h3>父组件 (中间层)</h3>
    <p>我是一个“绝缘”的中间组件，我不需要知道关于主题的任何事。</p>
    <p>但我会把我自己的子组件渲染出来:</p>
    <DeepChild />
  </div>
</template>

<script setup lang="ts">
// 这个组件完全不需要引入 provide 或 inject
import DeepChild from './DeepChild.vue';
</script>

<style scoped>
.middle-component {
  padding: 20px;
  background-color: #f3f3f3;
  border: 1px solid #e7e7e7;
  border-radius: 8px;
}
</style>
```

#### **第五步：实现祖父组件 (`ProvideInjectView.vue`)**

现在来构建主页面，也就是“提供者”。

打开 `src/views/ProvideInjectView.vue` 并粘贴以下代码：

```vue
<!-- src/views/ProvideInjectView.vue -->
<template>
  <div class="provide-inject-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>5. Provide & Inject</h1>
      <p>
        <code>provide</code> 和 <code>inject</code> 用于解决跨越多层级的组件通信问题（即“属性钻孔” Prop Drilling）。祖先组件作为“提供者”(Provider)，其所有后代组件都可以作为“注入者”(Consumer)来获取这份数据，无论层级多深。
      </p>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <div class="grandparent-component" :class="theme">
        <h2>祖父组件 (Provider)</h2>
        <p>当前主题: <span class="value">{{ theme }}</span></p>
        <p>我在这一层 provide 数据和方法，请看最深处的孙组件如何响应。</p>
        
        <MiddleComponent />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h2>核心代码</h2>
       <div class="code-blocks">
        <div class="code-block">
          <h4>祖父组件 (ProvideInjectView.vue)</h4>
          <pre><code>{{ providerCode }}</code></pre>
        </div>
        <div class="code-block">
          <h4>孙组件 (DeepChild.vue)</h4>
          <pre><code>{{ consumerCode }}</code></pre>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, provide, readonly } from 'vue';
import MiddleComponent from '@/components/provide-inject-demo/MiddleComponent.vue';

// --- Demo Logic ---
// 1. 创建一个响应式的数据 (ref)
const theme = ref('light');

// 2. 创建一个可以修改数据的方法
function toggleTheme() {
  theme.value = theme.value === 'light' ? 'dark' : 'light';
}

// 3. 使用 provide 将数据和方法提供给所有后代组件
// 使用 Symbol 作为 key 是一个好习惯，但在简单示例中用字符串也可以
provide('themeContext', {
  theme: readonly(theme), // 使用 readonly 包装，防止子组件直接修改
  toggleTheme
});

// --- Code Snippets for Display ---
const providerCode = `
// 祖父组件中
import { ref, provide, readonly } from 'vue';

const theme = ref('light');

function toggleTheme() {
  theme.value = theme.value === 'light' 
    ? 'dark' 
    : 'light';
}

provide('themeContext', {
  theme: readonly(theme),
  toggleTheme
});
`;

const consumerCode = `
// 孙组件中
import { inject } from 'vue';

// 注入 'themeContext'，并提供默认值
const { theme, toggleTheme } = 
  inject('themeContext', {
    theme: ref('default'),
    toggleTheme: () => {}
  });

// 在模板中使用
<p>主题: {{ theme }}</p>
<button @click="toggleTheme">
  切换主题
</button>
`;
</script>

<style scoped>
.provide-inject-view { max-width: 900px; }
section { margin-bottom: 30px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
.grandparent-component { padding: 20px; border-radius: 8px; transition: background-color 0.3s, color 0.3s; border: 1px solid; }
.value { font-weight: bold; text-transform: uppercase; }
.light { background-color: #fff; border-color: #eee; color: #333; }
.dark { background-color: #222; border-color: #555; color: #fff; }
.code-blocks { display: flex; gap: 20px; }
.code-block { flex: 1; }
pre { background-color: #2d2d2d; color: #f0f0f0; padding: 15px; border-radius: 5px; white-space: pre-wrap; word-wrap: break-word; }
</style>
```

#### **第六步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"5. Provide & Inject"** 链接。

**请进行以下交互测试：**

*   **观察初始状态**：你应该能看到祖父组件和最深处的孙组件都显示为亮色（light）主题。中间的父组件则不受影响。
*   **点击孙组件的按钮**：点击孙组件内部的“点我切换主题”按钮。
*   **观察变化**：你应该能看到，**祖父组件**和**孙组件**的背景色、文字颜色会**同时**在亮色和暗色主题之间切换。而中间的父组件始终保持不变。

这个现象完美地证明了 `provide` 和 `inject` 的强大之处：数据和行为能力像 Wi-Fi 信号一样，“穿透”了中间层组件，在顶层和深层之间建立了直接的响应式连接。





好的，我们来构建 `mitt` 的演示页面。

我们的场景是模拟一个常见的应用需求：在一个组件中触发一个全局通知（比如操作成功后的提示），然后在另一个完全不相关的组件中显示这个通知。我们将创建一个“发射器”组件和一个“接收器”组件来演示这个过程。



### **开始行动：实现 `Pinia` 页面**

#### **第一步：定义 `userStore`**

我们在 `src/stores` 目录下创建我们的第一个 Store。

1. 在 `src/stores` 文件夹下，创建一个新文件，命名为 `userStore.ts`。

2. 打开 `src/stores/userStore.ts` 并粘贴以下代码：

   ```typescript
   // src/stores/userStore.ts
   import { defineStore } from 'pinia';
   import { ref, computed } from 'vue';
   
   // Composition API (setup store) 写法
   // 这是 Pinia 官方更推荐的写法，因为它能更好地利用组合式 API 的优势
   export const useUserStore = defineStore('user', () => {
     // --- State ---
     const isLoggedIn = ref(false);
     const userInfo = ref({
       name: '',
       email: '',
     });
   
     // --- Getters ---
     const welcomeMessage = computed(() => {
       return isLoggedIn.value 
         ? `欢迎回来, ${userInfo.value.name}!` 
         : '你好, 游客!';
     });
   
     // --- Actions ---
     function login(name: string, email: string) {
       isLoggedIn.value = true;
       userInfo.value = { name, email };
     }
   
     function logout() {
       isLoggedIn.value = false;
       userInfo.value = { name: '', email: '' };
     }
   
     // 必须返回所有需要暴露给外部的状态、getters 和 actions
     return {
       isLoggedIn,
       userInfo,
       welcomeMessage,
       login,
       logout,
     };
   });
   ```

   *我们这里使用了 Pinia 的 Composition API 写法 (`setup store`)，这是目前更流行和灵活的方式，它和我们之前教学中介绍的 Options API 写法是完全等价的。*

#### **第二步：创建所需组件文件**

我们将创建两个独立的子组件，它们之间没有直接联系。

1.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `pinia-demo`。
2.  在 `src/components/pinia-demo` 文件夹下，创建两个新文件：`LoginStatus.vue` 和 `LoginControls.vue`。

你的文件结构现在应该是：

```
src/
├── stores/
│   └── userStore.ts       <-- 新建
└── components/
    ├── ...
    └── pinia-demo/
        ├── LoginStatus.vue    <-- 新建
        └── LoginControls.vue  <-- 新建
```

#### **第三步：创建视图文件并更新路由**

1. 在 `src/views` 文件夹下，创建一个新文件，命名为 `PiniaView.vue`。

2. 打开 `src/router/index.ts` 文件，添加 `Pinia` 页面的路由记录：

   ```typescript{5}
   // src/router/index.ts
   // ...
   const routes = [
     // ... (保留之前的路由)
     { path: '/pinia', name: 'Pinia', component: () => import('../views/PiniaView.vue') }
   ]
   // ...
   ```

#### **第四步：实现子组件**

* **`LoginStatus.vue` (状态展示组件)**
  这个组件只负责从 Store 中读取数据并展示。

  打开 `src/components/pinia-demo/LoginStatus.vue` 并粘贴以下代码：

  ```vue
  <!-- src/components/pinia-demo/LoginStatus.vue -->
  <template>
    <div class="login-status">
      <h4>状态展示组件 (LoginStatus.vue)</h4>
      <!-- 直接使用 store 的 getter -->
      <p>{{ userStore.welcomeMessage }}</p>
      <div v-if="isLoggedIn" class="user-info">
        <!-- 解构出来的 ref，在模板中可以直接使用 -->
        <p><strong>Email:</strong> {{ userInfo.email }}</p>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { useUserStore } from '@/stores/userStore';
  
  const userStore = useUserStore();
  
  // 使用 storeToRefs 来解构 state 和 getters，以保持其响应性
  const { isLoggedIn, userInfo } = storeToRefs(userStore);
  </script>
  
  <style scoped>
  .login-status {
    padding: 15px;
    background-color: #e6f7ff;
    border: 1px solid #91d5ff;
    border-radius: 4px;
  }
  .user-info {
    font-size: 0.9em;
  }
  </style>
  ```

* **`LoginControls.vue` (操作控制组件)**
  这个组件只负责调用 Store 的 actions 来改变状态。

  打开 `src/components/pinia-demo/LoginControls.vue` 并粘贴以下代码：

  ```vue
  <!-- src/components/pinia-demo/LoginControls.vue -->
  <template>
    <div class="login-controls">
      <h4>操作控制组件 (LoginControls.vue)</h4>
      <p>这两个按钮会调用 Pinia store 的 actions。</p>
      <button @click="handleLogin" :disabled="userStore.isLoggedIn">登录</button>
      <button @click="handleLogout" :disabled="!userStore.isLoggedIn">退出</button>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useUserStore } from '@/stores/userStore';
  
  const userStore = useUserStore();
  
  function handleLogin() {
    userStore.login('Coder Gemini', 'gemini@google.com');
  }
  
  function handleLogout() {
    userStore.logout();
  }
  </script>
  
  <style scoped>
  .login-controls {
    padding: 15px;
    background-color: #f6ffed;
    border: 1px solid #b7eb8f;
    border-radius: 4px;
  }
  button {
    margin-right: 10px;
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
    border: 1px solid #d9d9d9;
  }
  button:disabled {
    cursor: not-allowed;
    opacity: 0.5;
  }
  </style>
  ```

#### **第五步：实现主页面 (`PiniaView.vue`)**

主页面会将这两个独立的子组件放在一起，并提供介绍和代码片段。

打开 `src/views/PiniaView.vue` 并粘贴以下代码：

```vue
<!-- src/views/PiniaView.vue -->
<template>
  <div class="pinia-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>6. Pinia</h1>
      <p>
        Pinia 是 Vue 官方推荐的状态管理库。它允许你创建集中的、全局共享的“数据仓库”(Store)，让任何组件都能方便地读取和修改状态，非常适合管理如用户信息、购物车等全局数据。
      </p>
      <ul>
        <li><strong>State:</strong> 核心数据源 (响应式)。</li>
        <li><strong>Getters:</strong> 基于 State 的计算属性 (带缓存)。</li>
        <li><strong>Actions:</strong> 修改 State 的方法 (可以是异步的)。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <p>下面这两个组件没有父子关系，它们通过同一个 Pinia Store 进行通信。</p>
      <div class="demo-container">
        <LoginStatus />
        <LoginControls />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h4>核心代码 (stores/userStore.ts)</h4>
      <pre><code>{{ storeCode }}</code></pre>
    </section>
  </div>
</template>

<script setup lang="ts">
import LoginStatus from '@/components/pinia-demo/LoginStatus.vue';
import LoginControls from '@/components/pinia-demo/LoginControls.vue';

const storeCode = `
import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useUserStore = defineStore('user', () => {
  // State
  const isLoggedIn = ref(false);
  const userInfo = ref({ name: '', email: '' });

  // Getters
  const welcomeMessage = computed(() => {
    return isLoggedIn.value 
      ? \`欢迎回来, \${userInfo.value.name}!\` 
      : '你好, 游客!';
  });

  // Actions
  function login(name: string, email: string) {
    isLoggedIn.value = true;
    userInfo.value = { name, email };
  }

  function logout() {
    isLoggedIn.value = false;
    userInfo.value = { name: '', email: '' };
  }

  return { isLoggedIn, userInfo, welcomeMessage, login, logout };
});
`;
</script>

<style scoped>
.pinia-view { max-width: 900px; }
section { margin-bottom: 30px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
.demo-container {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
}
pre { background-color: #2d2d2d; color: #f0f0f0; padding: 15px; border-radius: 5px; white-space: pre-wrap; word-wrap: break-word; }
</style>```


```

#### **第六步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"6. Pinia"** 链接。

**请进行以下交互测试：**

*   **初始状态**：`LoginStatus` 组件应显示 “你好, 游客!”。`LoginControls` 组件的 “登录” 按钮可点击，“退出” 按钮被禁用。
*   **点击登录**：点击 `LoginControls` 组件的 “登录” 按钮。
*   **观察变化**：几乎在同一瞬间，`LoginStatus` 组件的欢迎语会变为 “欢迎回来, Coder Gemini!” 并显示 Email 信息。同时，`LoginControls` 组件的按钮状态会反转，“登录” 被禁用，“退出” 变为可点击。
*   **点击退出**：点击 “退出” 按钮，观察所有状态是否都恢复到了初始状态。

这个无缝的同步过程完美地展示了 Pinia 的威力：无论组件在哪里，它们都共享着同一个中央数据源，一个组件触发的 `action` 可以立即被所有依赖该状态的组件所响应。



---

### **开始行动：实现 `mitt` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `MittView.vue`。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `mitt-demo`。
3.  在 `src/components/mitt-demo` 文件夹下，创建两个新文件：`MittEmitter.vue`（事件发射器）和 `MittReceiver.vue`（事件接收器）。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── MittView.vue           <-- 新建
└── components/
    ├── ...
    └── mitt-demo/
        ├── MittEmitter.vue    <-- 新建
        └── MittReceiver.vue   <-- 新建
```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加 `mitt` 页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/mitt', name: 'Mitt', component: () => import('../views/MittView.vue') }
]
// ...
```

#### **第三步：实现子组件**

*   **`MittEmitter.vue` (事件发射器)**
    这个组件的任务很简单，就是提供几个按钮，点击时通过全局的 `emitter` 实例发送不同类型的通知。

    打开 `src/components/mitt-demo/MittEmitter.vue` 并粘贴以下代码：
    ```vue
    <!-- src/components/mitt-demo/MittEmitter.vue -->
    <template>
      <div class="emitter-component">
        <h4>事件发射器 (MittEmitter.vue)</h4>
        <p>点击下方按钮，通过 mitt 向整个应用广播事件。</p>
        <div class="buttons">
          <button class="success" @click="emitEvent('success', '操作成功！')">发送成功通知</button>
          <button class="warning" @click="emitEvent('warning', '这是一个警告。')">发送警告通知</button>
          <button class="error" @click="emitEvent('error', '发生了一个错误！')">发送错误通知</button>
        </div>
      </div>
    </template>
    
    <script setup lang="ts">
    import emitter from '@/utils/emitter';
    
    type NotificationType = 'success' | 'warning' | 'error';
    
    function emitEvent(type: NotificationType, message: string) {
      // 触发一个全局事件，并附带一个包含类型和消息的对象
      emitter.emit('show-notification', { type, message });
    }
    </script>
    
    <style scoped>
    .emitter-component {
      padding: 15px;
      background-color: #f6ffed;
      border: 1px solid #b7eb8f;
      border-radius: 4px;
    }
    .buttons button {
      margin-right: 10px;
      padding: 8px 15px;
      border-radius: 4px;
      cursor: pointer;
      color: white;
      border: none;
    }
    .success { background-color: #52c41a; }
    .warning { background-color: #faad14; }
    .error { background-color: #f5222d; }
    </style>
    ```

*   **`MittReceiver.vue` (事件接收器)**
    这个组件将**监听**全局事件，并在收到事件时显示通知。**最关键的是，它必须在卸载时清理监听器**。

    打开 `src/components/mitt-demo/MittReceiver.vue` 并粘贴以下代码：
    ```vue
    <!-- src/components/mitt-demo/MittReceiver.vue -->
    <template>
      <div class="receiver-component">
        <h4>事件接收器 (MittReceiver.vue)</h4>
        <div v-if="notification" class="notification" :class="notification.type">
          <p><strong>{{ notification.type.toUpperCase() }}:</strong> {{ notification.message }}</p>
        </div>
        <p v-else class="placeholder">等待接收通知...</p>
      </div>
    </template>
    
    <script setup lang="ts">
    import { ref, onMounted, onUnmounted } from 'vue';
    import emitter from '@/utils/emitter';
    
    interface NotificationPayload {
      type: 'success' | 'warning' | 'error';
      message: string;
    }
    
    const notification = ref<NotificationPayload | null>(null);
    
    // 1. 定义一个处理函数
    function handleNotification(payload: NotificationPayload) {
      notification.value = payload;
      // 3秒后自动清除通知
      setTimeout(() => {
        notification.value = null;
      }, 3000);
    }
    
    // 2. 在组件挂载后，开始监听 'show-notification' 事件
    onMounted(() => {
      emitter.on('show-notification', handleNotification as any);
    });
    
    // 3. ‼️‼️‼️ 最关键的一步：在组件卸载前，解绑事件监听器
    onUnmounted(() => {
      emitter.off('show-notification', handleNotification as any);
    });
    </script>
    
    <style scoped>
    .receiver-component {
      padding: 15px;
      background-color: #e6f7ff;
      border: 1px solid #91d5ff;
      border-radius: 4px;
      min-height: 100px;
    }
    .placeholder {
      color: #999;
    }
    .notification {
      padding: 10px 15px;
      border-radius: 4px;
      color: white;
    }
    .notification.success { background-color: #52c41a; }
    .notification.warning { background-color: #faad14; }
    .notification.error { background-color: #f5222d; }
    </style>
    ```

#### **第四步：实现主页面 (`MittView.vue`)**

主页面会将这两个独立的子组件放在一起，并强调 `mitt` 的用法和注意事项。

打开 `src/views/MittView.vue` 并粘贴以下代码：

```vue
<!-- src/views/MittView.vue -->
<template>
  <div class="mitt-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>7. Mitt (Event Bus)</h1>
      <p>
        <code>mitt</code> 是一个轻量级的事件总线库，它实现了全局的发布/订阅模式。任何组件都可以触发 (emit) 一个全局事件，其他任何组件都可以监听 (on) 这个事件，从而实现任意组件间的通信。
      </p>
      <p class="warning">
        <strong>⚠️ 注意:</strong> 事件总线容易导致数据流混乱，难以追踪，并且有内存泄漏的风险。请谨慎使用，对于复杂场景，<strong>Pinia 是更好的选择</strong>。
      </p>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <p>下面这两个组件没有父子关系。左边的组件会广播事件，右边的组件会监听并显示通知。</p>
      <div class="demo-container">
        <MittEmitter />
        <MittReceiver />
      </div>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h4>核心代码 (MittReceiver.vue - 事件监听方)</h4>
      <pre><code>{{ receiverCode }}</code></pre>
    </section>
  </div>
</template>

<script setup lang="ts">
import MittEmitter from '@/components/mitt-demo/MittEmitter.vue';
import MittReceiver from '@/components/mitt-demo/MittReceiver.vue';

const receiverCode = `
import { onMounted, onUnmounted } from 'vue';
import emitter from '@/utils/emitter';

// 定义处理函数
function handleNotification(payload) {
  // ... 更新组件状态以显示通知 ...
}

// 组件挂载时，订阅事件
onMounted(() => {
  emitter.on('show-notification', handleNotification);
});

// ‼️ 组件卸载时，必须取消订阅，防止内存泄漏！
onUnmounted(() => {
  emitter.off('show-notification', handleNotification);
});
`;
</script>

<style scoped>
.mitt-view { 
  max-width: 900px; 
}

section { 
  margin-bottom: 30px; 
  padding: 20px; 
  background-color: #fff; 
  border-radius: 8px; 
  box-shadow: 0 2px 8px rgba(0,0,0,0.05); 
}

.warning {
  padding: 10px;
  background-color: #fffbe6;
  border: 1px solid #ffe58f;
  border-radius: 4px;
}

.demo-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

pre { 
  background-color: #2d2d2d; 
  color: #f0f0f0; 
  padding: 15px; 
  border-radius: 5px; 
  white-space: pre-wrap; 
  word-wrap: break-word; 
}
</style>
```

#### **第五步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"7. Mitt"** 链接。

**请进行以下交互测试：**

*   **初始状态**：`MittReceiver` 组件应显示 “等待接收通知...”。
*   **点击发射器按钮**：点击 `MittEmitter` 组件中的任意一个按钮，例如“发送成功通知”。
*   **观察接收器**：几乎在同一瞬间，`MittReceiver` 组件中会显示出对应颜色和内容的通知框。
*   **通知消失**：等待 3 秒钟，观察通知是否会自动消失，恢复到“等待接收通知...”的状态。
*   **重复测试**：尝试点击其他按钮，观察接收器是否能正确响应不同类型的通知。

这个流程清晰地展示了事件总线的工作模式：一个组件发布消息，另一个完全解耦的组件接收并处理该消息。同时，代码也体现了安全使用 `mitt` 的关键——在 `onUnmounted` 中解绑事件。



好的，理论学习结束，我们来构建最后一个，也是功能最丰富的 `Slots` 演示页面。

我们的场景是封装一个通用的 `TodoList` 组件。这个组件将综合运用**默认插槽、具名插槽**和**作用域插槽**，来允许父组件最大程度地自定义其外观和行为。

---

### **开始行动：实现 `Slots` 页面**

#### **第一步：创建所需文件**

1.  在 `src/views` 文件夹下，创建一个新文件，命名为 `SlotsView.vue`。
2.  在 `src/components` 文件夹下，创建一个新文件夹，命名为 `slots-demo`。
3.  在 `src/components/slots-demo` 文件夹下，创建一个新文件，命名为 `TodoList.vue`。

你的文件结构现在应该是：

```
src/
├── views/
│   ├── ...
│   └── SlotsView.vue          <-- 新建
└── components/
    ├── ...
    └── slots-demo/
        └── TodoList.vue       <-- 新建```

#### **第二步：更新路由**

打开 `src/router/index.ts` 文件，添加 `Slots` 页面的路由记录：

```typescript{5}
// src/router/index.ts
// ...
const routes = [
  // ... (保留之前的路由)
  { path: '/slots', name: 'Slots', component: () => import('../views/SlotsView.vue') }
]
// ...
```

#### **第三步：实现子组件 (`TodoList.vue`)**

这是我们功能丰富的“插槽容器”组件。它负责管理待办事项的数据，但把渲染的权力通过三种不同的插槽让渡出去。

打开 `src/components/slots-demo/TodoList.vue` 并粘贴以下代码：

```vue
<!-- src/components/slots-demo/TodoList.vue -->
<template>
  <div class="todo-list-card">
    <!-- 1. 具名插槽: header -->
    <header class="card-header">
      <slot name="header">
        <!-- 插槽的默认内容 -->
        <h2>默认标题</h2>
      </slot>
    </header>

    <!-- 2. 作用域插槽: default -->
    <main class="card-body">
      <p v-if="!todos.length" class="empty-state">
        <!-- 3. 具名插槽: empty -->
        <slot name="empty">
          暂无待办事项
        </slot>
      </p>
      <ul v-else>
        <li v-for="todo in todos" :key="todo.id">
          <!-- 
            通过在 <slot> 上绑定属性，将 todo 对象暴露给父组件。
            这就是作用域插槽的核心。
          -->
          <slot :todo-item="todo">
            <!-- 作用域插槽的默认内容 -->
            <span>{{ todo.text }}</span>
          </slot>
        </li>
      </ul>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

interface Todo {
  id: number;
  text: string;
  completed: boolean;
}

// 子组件负责管理核心数据
const todos = ref<Todo[]>([
  { id: 1, text: '学习 Vue Slots', completed: true },
  { id: 2, text: '完成项目文档', completed: false },
  { id: 3, text: '休息一下', completed: false },
]);
</script>

<style scoped>
.todo-list-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}
.card-header {
  padding: 15px;
  background-color: #f5f5f5;
  border-bottom: 1px solid #ddd;
}
.card-body {
  padding: 15px;
}
ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}
li {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}
li:last-child {
  border-bottom: none;
}
.empty-state {
  color: #999;
  text-align: center;
}
</style>
```

#### **第四步：实现父组件 (`SlotsView.vue`)**

现在，我们作为 `TodoList` 组件的使用者，将利用它提供的所有插槽来构建一个完全自定义的待办事项列表。

打开 `src/views/SlotsView.vue` 并粘贴以下代码：

```vue
<!-- src/views/SlotsView.vue -->
<template>
  <div class="slots-view">
    <!-- 1. 介绍区 -->
    <section class="intro-section">
      <h1>8. Slots (插槽)</h1>
      <p>
        插槽是一种“内容分发”机制，它允许父组件向子组件的特定区域插入自定义的 HTML 结构，是构建高度可复用 UI 组件的基石。
      </p>
      <ul>
        <li><strong>具名插槽:</strong> 使用 <code>#header</code> 语法，将内容放入指定名称的插槽。</li>
        <li><strong>作用域插槽:</strong> 使用 <code>#default="{ todoItem }"</code> 语法，接收来自子组件的数据 (<code>todoItem</code>)，并用它来动态渲染内容。</li>
      </ul>
    </section>

    <!-- 2. 演示区 -->
    <section class="demo-section">
      <h2>Live Demo</h2>
      <p>下面是一个完全通过插槽自定义的 TodoList 组件：</p>
      
      <TodoList>
        <!-- 使用 #header 具名插槽自定义标题 -->
        <template #header>
          <div class="custom-header">
            <h3>🚀 我的待办事项</h3>
            <span>共 3 项</span>
          </div>
        </template>

        <!-- 
          使用 #default 作用域插槽自定义列表项的渲染。
          通过解构 { todoItem } 获取子组件暴露的数据。
        -->
        <template #default="{ todoItem }">
          <div class="custom-todo-item" :class="{ completed: todoItem.completed }">
            <input type="checkbox" :checked="todoItem.completed" disabled />
            <span>{{ todoItem.text }}</span>
            <span v-if="todoItem.completed" class="status-badge">已完成</span>
            <span v-else class="status-badge pending">待办</span>
          </div>
        </template>
        
        <!-- (可选) 演示 #empty 插槽 -->
        <!-- <template #empty>
          <p>🎉 所有任务都已完成！</p>
        </template> -->
      </TodoList>
    </section>

    <!-- 3. 代码区 -->
    <section class="code-section">
      <h4>核心代码 (父组件如何使用插槽)</h4>
      <pre><code>{{ parentCode }}</code></pre>
    </section>
  </div>
</template>

<script setup lang="ts">
import TodoList from '@/components/slots-demo/TodoList.vue';

const parentCode = `
<TodoList>
  <!-- 1. 使用 #header 具名插槽 -->
  <template #header>
    <h3>🚀 我的待办事项</h3>
  </template>

  <!-- 2. 使用 #default 作用域插槽 -->
  <!--    通过 { todoItem } 接收子组件的数据 -->
  <template #default="{ todoItem }">
    <div :class="{ completed: todoItem.completed }">
      <input type="checkbox" :checked="todoItem.completed" />
      <span>{{ todoItem.text }}</span>
    </div>
  </template>
</TodoList>
`;
</script>

<style scoped>
.slots-view { max-width: 900px; }
section { margin-bottom: 30px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }

/* 自定义样式 */
.custom-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.custom-header h3 {
  margin: 0;
}

.custom-todo-item {
  display: flex;
  align-items: center;
  gap: 10px;
}
.custom-todo-item.completed span {
  text-decoration: line-through;
  color: #999;
}

.status-badge {
  padding: 2px 8px;
  border-radius: 12px;
  font-size: 0.8em;
  color: white;
}
.status-badge.pending {
  background-color: #faad14;
}
.status-badge {
  background-color: #52c41a;
}

pre { background-color: #2d2d2d; color: #f0f0f0; padding: 15px; border-radius: 5px; white-space: pre-wrap; word-wrap: break-word; }
</style>
```

#### **第五步：验证成果**

1.  确保你的开发服务器仍在运行。
2.  在浏览器中，点击侧边栏的 **"8. Slots"** 链接。

**请仔细观察页面显示：**

*   **自定义头部**：列表的头部不再是子组件中的“默认标题”，而是我们通过 `#header` 插槽提供的、带有火箭图标和计数器的复杂结构。
*   **自定义列表项**：每一项待办事项都按照我们在父组件中定义的模板进行渲染，包含了复选框、文本和状态徽章。已完成的任务有删除线，未完成的有“待办”徽章。

这个结果有力地证明了插槽的强大之处。子组件 `TodoList` 就像一个“数据引擎”，它只负责提供数据，而父组件 `SlotsView` 就像一个“渲染引擎”，它利用子组件提供的数据，完全掌控了最终的 UI 呈现。

**恭喜你！到此为止，我们已经完成了教学大纲中所有核心组件通信方式的学习和实践！**

