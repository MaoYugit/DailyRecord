### **Vue 3 组件通信**

#### **目标**

从头开始，创建一个名为 **"VueTasker"** 的待办事项应用。完成这个功能完善的小项目，实践并彻底理解 Vue 3 的**九大核心通信模式**。

---

### **第一部分：奠定基石 (项目搭建与配置)**

#### **第 1 步：初始化 Vue 3 项目**

打开终端，导航到创建项目的目录，然后运行 Vite 官方脚手架：
```bash
npm create vue@latest
```
在交互式提问中，进行如下选择，以搭建所需的环境：
```bash
✔ Project name: … vuetasker
✔ Add TypeScript? … No
✔ Add JSX Support? … No
✔ Add Vue Router ...? … No
✔ Add Pinia for state management? … Yes  <-- 关键！
✔ Add Vitest for Unit Testing? … No
✔ Add an End-to-End Testing Solution? › No
✔ Add ESLint for code quality? … Yes
✔ Add Prettier for code formatting? … Yes

# 选择创建一个空项目
```

#### **第 2 步：安装依赖并启动**

脚手架完成后，根据提示进入项目目录并安装所有依赖：
```bash
cd vuetasker
npm install
```
安装实现“事件总线”所需的 `mitt` 库：
```bash
npm install mitt
```
启动开发服务器，确保一切正常：

```bash
npm run dev
```

在浏览器打开 `http://localhost:5173/`，可以看到 Vue 的欢迎页面。

#### **第 3 步：清理与规划文件结构**

为了保持项目整洁：
1.  **删除模板组件**：删除 `src/components/` 目录下的所有 `.vue` 文件。
2.  **创建我们的文件**：
    *   在 `src/components/` 目录下，新建以下 5 个文件：
        *   `BaseModal.vue`: 一个通用的模态框组件，用于删除确认。
        *   `TodoHeader.vue`: 包含一个输入框和添加按钮，用于创建新的待办事项。
        *   `TodoItem.vue`: 单个待办事项，展示内容并处理完成和删除操作。
        *   `TodoList.vue`: 列表容器，用于展示所有待办事项。
        *   `UINotification.vue`: 一个全局通知组件，用于显示操作反馈（如“添加成功”）。
    *   在 `src/` 下新建 `utils` 目录，并在其中创建 `emitter.js` (用于 Event Bus)。
    *   重命名 `src/stores/counter.js` 为 `src/stores/todoStore.js`。 (用于 Pinia)

至此，项目已准备就绪，可以开始编码了。

**项目结构:**

src/
├── components/
│   ├── TodoHeader.vue
│   ├── TodoList.vue
│   ├── TodoItem.vue
│   ├── BaseModal.vue
│   └── UINotification.vue
├── stores/
│   └── todoStore.js   (用于 Pinia)
├── utils/
│   └── emitter.js     (用于 Event Bus)
└── App.vue

---

### **第二部分：在实战中学习组件通信**

#### **1. `Props`：数据的单向流动 (父 -> 子)**

**场景**：我们需要将主应用 `App.vue` 中的待办事项数据列表，展示在 `TodoList.vue` 和 `TodoItem.vue` 中。

**实现**：
首先，我们用虚拟数据填充 `App.vue`，并通过 `props` 将其传给 `TodoList`。

**`src/App.vue` (父)**

```vue
<script setup>
import { ref } from 'vue';
import TodoList from './components/TodoList.vue';

const todos = ref([
  { id: 1, text: '学习 Vue Props', completed: true },
  { id: 2, text: '完成教学项目', completed: false },
]);
</script>

<template>
  <div class="todoapp">
    <!-- 将 todos 数组通过 :todos="todos" 传递下去 -->
    <TodoList :todos="todos" />
  </div>
</template>
```

**`src/components/TodoList.vue` (子)**
`TodoList` 接收整个列表，并循环将每一项再通过 `props` 传给 `TodoItem`。

```vue
<script setup>
import TodoItem from './TodoItem.vue';

// 1. 使用 defineProps 宏来声明并接收来自父组件的 props
defineProps({
  todos: {
    type: Array,
    required: true,
  }
});
</script>

<template>
  <ul class="todo-list">
    <TodoItem
      v-for="todo in todos"
      :key="todo.id"
      :todo-item="todo"
    />
  </ul>
</template>
```

**`src/components/TodoItem.vue` (孙子)**
```vue
<script setup>
// 2. 再次声明并接收来自 TodoList 的单个 todo 对象
defineProps({
  todoItem: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <li :class="{ completed: todoItem.completed }">
    <div class="view">
      <label>{{ todoItem.text }}</label>
    </div>
  </li>
</template>
```

> **学习要点**：`Props` 是最基础、最核心的父子通信方式。数据总是从父级单向流向子级，子组件应将 `props` 视为只读。

---

#### **2. `$emit`：子组件的“呐喊” (子 -> 父)**

**场景**：当用户在 `TodoItem.vue` 中点击“删除”或“完成”时，必须通知顶层的 `App.vue` 去修改原始数据。

**实现**：
我们在 `TodoItem` 中触发事件，`App` 中监听并处理。

**`src/components/TodoItem.vue`**
```vue
<script setup>
// 1. 除了 defineProps，我们还需要 defineEmits
import { defineEmits } from 'vue';

defineProps({ todoItem: Object });

// 2. 声明本组件会触发哪些自定义事件
const emit = defineEmits(['toggle-complete', 'delete-todo']);

// 3. 在事件处理器中，调用 emit 触发事件，并可传递参数
function handleDelete() {
  emit('delete-todo', props.todoItem.id);
}
</script>

<template>
  <li :class="{ completed: todoItem.completed }">
    <div class="view">
      <input
        type="checkbox"
        class="toggle"
        :checked="todoItem.completed"
        @change="emit('toggle-complete', todoItem.id)"
      />
      <label>{{ todoItem.text }}</label>
      <button class="destroy" @click="handleDelete">×</button>
    </div>
  </li>
</template>
```

**`src/App.vue`**
现在，我们需要在 `App.vue` 中监听这些来自深层子组件的事件。事件会逐层冒泡。

```vue
<script setup>
// ...
const handleToggleComplete = (id) => {
  const todo = todos.value.find((t) => t.id === id);
  if (todo) todo.completed = !todo.completed;
};

const handleDeleteTodo = (id) => {
  todos.value = todos.value.filter((t) => t.id !== id);
};
</script>

<template>
  <div class="todoapp">
    <TodoList
      :todos="todos"
      @toggle-complete="handleToggleComplete"
      @delete-todo="handleDeleteTodo"
    />
  </div>
</template>
```
*注意：`TodoList.vue` 需要充当“中间人”，监听 `TodoItem` 的事件再原样向上抛出。*

**`src/components/TodoList.vue`**
```vue
<script setup>
// ...
const emit = defineEmits(['toggle-complete', 'delete-todo']);
</script>

<template>
  <ul class="todo-list">
    <TodoItem
      v-for="todo in todos"
      :key="todo.id"
      :todo-item="todo"
      @toggle-complete="emit('toggle-complete', $event)"
      @delete-todo="emit('delete-todo', $event)"
    />
  </ul>
</template>

```

**学习要点**：`$emit` 遵循“谁的数据，谁负责修改”的原则。它实现了子对父的通信，构成了 Vue 单向数据流的闭环：**Props down, Events up**。

---

#### **3. `defineModel`：双向绑定的终极简化 (Vue 3.4+)**

**场景**：我们需要将 `TodoHeader.vue` 输入框的内容与 `App.vue` 中的一个状态（如 `newTodoText`）进行双向绑定。

**实现**：
使用 `defineModel`，我们可以用一行代码实现过去 `props` + `emit` 的复杂组合。

**`src/components/TodoHeader.vue`**

```vue
<script setup>
// 1. 引入并使用 defineModel。它会返回一个可读写的 ref
const model = defineModel();
</script>

<template>
  <header class="header">
    <h1>VueTasker</h1>
    <!-- 2. 直接将 v-model 绑定到这个 ref 上 -->
    <input
      class="new-todo"
      placeholder="想做些什么？"
      v-model="model"
    />
  </header>
</template>
```

**`src/App.vue`**

```vue
<script setup>
import { ref } from 'vue';
import TodoHeader from './components/TodoHeader.vue';
// ...
const newTodoText = ref('');
</script>

<template>
  <div class="todoapp">
    <!-- 3. 在父组件中正常使用 v-model 即可 -->
    <TodoHeader v-model="newTodoText" />
    <!-- ... -->
  </div>
</template>
```
> **学习要点**：`defineModel` 是实现 `v-model` 的最佳现代方式，它极大简化了组件代码，使其更易读、易维护。

---

#### **4. `Slots`：赋予组件“灵魂”的内容分发**

**场景**：我们需要一个通用的 `BaseModal.vue` 确认模态框。它的框架是固定的，但标题、内容、按钮等核心 UI 应该由使用它的父组件来决定。

**实现**：
`BaseModal` 使用 `<slot>` 标签作为占位符。

**`src/components/BaseModal.vue`**
```vue
<template>
  <div class="modal-overlay" v-if="show">
    <div class="modal-content">
      <header class="modal-header">
        <!-- 1. 具名插槽，用于放置标题 -->
        <slot name="header">默认标题</slot>
      </header>
      <main class="modal-body">
        <!-- 2. 默认插槽，用于放置主要内容 -->
        <slot></slot>
      </main>
      <footer class="modal-footer">
        <!-- 3. 作用域插槽：将子组件的方法(close)暴露给父组件的插槽内容使用 -->
        <slot name="footer" :close="() => emit('close')">
          <button @click="emit('close')">关闭</button>
        </slot>
      </footer>
    </div>
  </div>
</template>

<script setup>
defineProps({ show: Boolean });
const emit = defineEmits(['close']);
</script>
```

**在 `TodoItem.vue` 中使用它**
```vue
<script setup>
import { ref } from 'vue';
import BaseModal from './BaseModal.vue';
// ...
const showConfirmModal = ref(false);
</script>

<template>
  <!-- ... li 标签 ... -->
  <button class="destroy" @click="showConfirmModal = true">×</button>

  <BaseModal :show="showConfirmModal" @close="showConfirmModal = false">
    <!-- 使用 v-slot 指令 (简写为 #) 来填充指定名称的插槽 -->
    <template #header>
      <h3>确认删除操作</h3>
    </template>
    
    <!-- 未指定名称的内容会进入默认插槽 -->
    <p>你确定要删除 "{{ todoItem.text }}" 吗？此操作不可撤销。</p>

    <!-- 填充 footer 插槽，并接收来自作用域插槽的数据/方法 -->
    <template #footer="{ close }">
      <button @click="close">取消</button>
      <button class="btn-danger" @click="handleDelete">确认删除</button>
    </template>
  </BaseModal>
</template>
```
> **学习要点**：`Slots` 是组件通信中用于**传递 UI 结构**的机制。它让组件的可复用性和灵活性达到了新的高度。

---

#### **5. `provide`/`inject` & Pinia：全局与跨层级的终极方案**

到目前为止，我们的状态和事件逻辑都集中在 `App.vue` 中，这对于大型应用是不可维护的。现在我们引入终极解决方案：**Pinia** 进行状态管理，并用 **`provide`/`inject`** 来分发非核心状态（如主题）。

**实现**：
我们将所有与 `todo` 相关的逻辑都移入 Pinia Store。

**`src/stores/todoStore.js`**
```javascript
import { defineStore } from 'pinia';

export const useTodoStore = defineStore('todo', {
  state: () => ({
    todos: [
      { id: 1, text: '学习 Pinia', completed: false }
    ],
    newTodo: '',
  }),
  getters: {
    completedCount: (state) => state.todos.filter(t => t.completed).length,
  },
  actions: {
    addTodo() {
      if (!this.newTodo.trim()) return;
      this.todos.unshift({ id: Date.now(), text: this.newTodo, completed: false });
      this.newTodo = '';
    },
    deleteTodo(id) {
      this.todos = this.todos.filter(t => t.id !== id);
    },
    toggleComplete(id) {
      const todo = this.todos.find(t => t.id === id);
      if (todo) todo.completed = !todo.completed;
    },
  },
});
```

现在，任何组件都可以轻松访问这份中心化状态，**不再需要繁琐的 `props` 和 `emit` 逐层传递**。

**`src/components/TodoHeader.vue` (重构后)**
```vue
<script setup>
import { useTodoStore } from '../stores/todoStore';
const todoStore = useTodoStore();
</script>

<template>
  <header class="header">
    <h1>VueTasker</h1>
    <input
      class="new-todo"
      placeholder="想做些什么？"
      v-model="todoStore.newTodo"
      @keyup.enter="todoStore.addTodo"
    />
  </header>
</template>
```

**`src/components/TodoItem.vue` (重构后)**
```vue
<script setup>
import { useTodoStore } from '../stores/todoStore';
const todoStore = useTodoStore();
defineProps({ todoItem: Object });
</script>

<template>
  <li :class="{ completed: todoItem.completed }">
    <div class="view">
      <input
        type="checkbox"
        class="toggle"
        :checked="todoItem.completed"
        @change="todoStore.toggleComplete(todoItem.id)"
      />
      <label>{{ todoItem.text }}</label>
      <button class="destroy" @click="todoStore.deleteTodo(todoItem.id)">×</button>
    </div>
  </li>
</template>
```

**`App.vue` (重构后)**
`App.vue` 变得极其简洁，它现在只负责布局和**提供 (provide)** 全局主题。
```vue
<script setup>
import { ref, provide } from 'vue';
import { useTodoStore } from './stores/todoStore';
import TodoHeader from './components/TodoHeader.vue';
import TodoList from './components/TodoList.vue';

const todoStore = useTodoStore();
const theme = ref('light'); // 'light' or 'dark'

// 1. App 作为祖先，提供一个名为 'theme' 的响应式数据
provide('theme', theme);

function toggleTheme() {
  theme.value = theme.value === 'light' ? 'dark' : 'light';
}
</script>

<template>
  <div class="todoapp" :class="theme">
    <TodoHeader />
    <TodoList :todos="todoStore.todos" />
    <button @click="toggleTheme">切换主题</button>
  </div>
</template>
```

**`TodoItem.vue` (注入主题)**
```vue
<script setup>
import { inject } from 'vue';
// ...
// 2. 无论层级多深，TodoItem 都能注入祖先提供的 'theme'
const theme = inject('theme'); 
</script>

<template>
  <!-- 3. 在模板中使用注入的主题 -->
  <li :class="[{ completed: todoItem.completed }, `theme-${theme}`]">
    <!-- ... -->
  </li>
</template>
```

> **学习要点**：
> *   **Pinia** 是处理**全局业务状态**的最佳实践，它让状态管理变得简单、可预测，并从根本上解决了复杂通信问题。
> *   **`provide/inject`** 是处理**跨层级非业务状态**（如主题、用户信息、全局配置）的利器，它能有效避免“属性钻孔”(Prop Drilling)。

---

#### **6. 其他通信工具**

最后，我们快速过一下另外三个有特定用途的工具。

*   **`ref`/`defineExpose` (父调子)**: 当你需要命令式地调用子组件的方法时使用。例如，在 `App.vue` 中调用 `TodoHeader` 的一个 `focusInput()` 方法。
*   **`$attrs` (属性透传)**: 当你想将父组件传递的、但子组件 `props` 未声明的属性（如 `class`, `style`, `data-*`）应用到子组件的某个内部元素上时使用。
*   **Event Bus (mitt)**: 用于完全不相关的组件通信。例如，当 `addTodo` action 执行后，发出一个全局事件，让 `UINotification.vue` 显示一条“添加成功”的提示。这是 Pinia 等状态管理工具出现前的流行方案，现在应谨慎使用，以防数据流混乱。

---

### **最终总结：如何选择合适的通信方式**

| 通信方式             | 适用场景                    | 核心思想                                       |
| :------------------- | :-------------------------- | :--------------------------------------------- |
| **Props**            | **父 -> 子** 数据传递       | 最基础、最常用的数据分发方式。                 |
| **$emit**            | **子 -> 父** 事件通知       | 配合 Props 构成组件通信的基础闭环。            |
| **defineModel**      | **父 <-> 子** 双向绑定      | 编写自定义 `v-model` 组件的首选。              |
| **Slots**            | **父 -> 子** UI结构传递     | 构建高可复用性、高灵活性的布局组件。           |
| **Pinia**            | **全局状态管理**            | **中大型应用首选**，集中管理所有共享业务状态。 |
| **provide/inject**   | **祖先 -> 后代** 跨层级数据 | 解决“属性钻孔”，分发全局配置或主题。           |
| **ref/defineExpose** | **父 -> 子** 命令式调用     | 当你需要从父组件调用子组件的内部方法时。       |
| **$attrs**           | **父 -> 子** 属性透传       | 封装基础组件，透传原生 HTML 属性。             |
| **Event Bus**        | **任意组件间** 通信         | 用于简单的、完全解耦的通知，但需警惕滥用。     |

通过完成 "VueTasker" 项目，你已经将理论与实践相结合，全面掌握了 Vue 3 强大而灵活的组件生态系统。现在，你可以自信地为任何场景选择最恰当的通信策略了。