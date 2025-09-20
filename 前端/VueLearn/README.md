# VueLearn

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```



这是一个非常棒的想法！将我们创建的 Vue 项目本身作为学习笔记的载体，这会让我们的学习过程更具交互性和成就感。

好的，我们现在就来改造项目，实现这个目标。我们将完成以下几件事：

1.  安装必要的库来解析和展示 Markdown。
2.  创建一个新的“视图”组件来存放第一课的笔记。
3.  配置路由，以便通过特定 URL 访问这个新视图。
4.  在主导航中添加入口，链接到这个笔记页面。

---

### **第二步：将项目改造为学习笔记应用**

#### **1. 安装 Markdown 解析库**

Vue 组件本身不直接解析 Markdown 字符串。我们需要一个第三方库来将 Markdown 文本转换为 HTML，然后再由 Vue 进行渲染。`markdown-it` 是一个非常流行且功能强大的选择。同时，为了让代码块有高亮效果，我们再安装 `highlight.js`。

1.  **打开你的终端** (确保路径依然在你的项目文件夹 `vue3-learning` 下)。
2.  **执行安装命令**:

    ```bash
    npm install markdown-it highlight.js
    ```

    这会将这两个库添加到你的项目依赖中。

#### **2. 创建第一课的笔记视图**

“视图 (View)” 在 Vue Router 的语境中，通常指代一个完整的页面级组件。

1.  在你的项目代码编辑器中，找到 `src/views` 文件夹。
2.  在 `src/views` 文件夹内，创建一个新的文件，命名为 `Lesson1CreateProject.vue`。
3.  将以下代码完整地复制到 `Lesson1CreateProject.vue` 文件中：

    ```vue
    <script setup>
    import { ref } from 'vue';
    import MarkdownIt from 'markdown-it';
    import hljs from 'highlight.js';
    
    // 变化 1: 直接导入 .md 文件，并带上 ?raw 后缀
    // @ 是一个路径别名，默认指向 /src 目录
    import markdownContent from '@/assets/markdown/lesson1.md?raw';
    
    // 初始化 markdown-it 的逻辑保持不变
    const md = new MarkdownIt({
      highlight: function (str, lang) {
        if (lang && hljs.getLanguage(lang)) {
          try {
            return '<pre class="hljs"><code>' +
                   hljs.highlight(str, { language: lang, ignoreIllegals: true }).value +
                   '</code></pre>';
          } catch (__) {}
        }
        return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>';
      }
    });
    
    // 渲染逻辑也保持不变，只是数据源变了
    const htmlContent = ref(md.render(markdownContent));
    </script>
    
    <template>
      <div class="learning-note" v-html="htmlContent"></div>
    </template>
    
    <style scoped>
    /* 样式保持不变 */
    .learning-note {
      background-color: #fff;
      padding: 2rem;
      border-radius: 8px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      line-height: 1.7;
    }
    .learning-note :deep(h3) {
      border-bottom: 2px solid #eee;
      padding-bottom: 0.5rem;
      margin-top: 2rem;
      margin-bottom: 1rem;
    }
    .learning-note :deep(h4) {
        margin-top: 1.5rem;
        margin-bottom: 1rem;
    }
    .learning-note :deep(code) {
      background-color: #f4f4f4;
      padding: 0.2em 0.4em;
      border-radius: 3px;
      font-size: 85%;
    }
    .learning-note :deep(pre.hljs) {
      background-color: #282c34;
      color: #abb2bf;
      padding: 1em;
      border-radius: 5px;
      overflow-x: auto;
    }
    .learning-note :deep(pre.hljs code) {
      background-color: transparent;
      padding: 0;
    }
    </style>
    ```
    
    **代码解释:**
    *   `<script setup>`: 这是 Vue 3 的组合式 API 语法糖，让代码更简洁。
    *   我们导入了 `markdown-it` 和 `highlight.js`。
    *   `markdownContent` 变量是一个多行字符串，包含了我们上一课的全部笔记。
    *   我们创建了一个 `markdown-it` 实例，并在配置中提供了 `highlight` 函数。这个函数会在解析到代码块时被调用，并使用 `highlight.js` 为代码添加高亮所需的 HTML 标签和 class。
    *   `md.render()` 方法将 Markdown 字符串转换为 HTML 字符串。
    *   在 `<template>` 中，我们使用了 `v-html` 指令。这个指令会将其绑定的值作为原始 HTML 渲染出来。**请注意**：只在你信任内容来源的情况下使用 `v-html`，因为它可能导致 XSS 攻击。在这里，内容是我们自己编写的，所以是完全安全的。
    *   `<style scoped>` 中的样式是专门为这个笔记页面设计的，以提高可读性。`:deep()` 选择器用于穿透到 `v-html` 渲染出来的子元素。

#### **3. 添加路由配置**

现在我们有了页面组件，但还需要告诉 Vue Router 如何访问它。

1.  打开 `src/router/index.js` 文件。
2.  在 `routes` 数组中，添加一个新的路由对象。

    ```javascript
    // ... import { createRouter, createWebHistory } from 'vue-router'
    // ... import HomeView from '../views/HomeView.vue'
    
    const router = createRouter({
      history: createWebHistory(import.meta.env.BASE_URL),
      routes: [
        {
          path: '/',
          name: 'home',
          component: HomeView
        },
        {
          path: '/about',
          name: 'about',
          // route level code-splitting
          // this generates a separate chunk (About.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import('../views/AboutView.vue')
        },
        // 在这里添加我们的新路由
        {
          path: '/lesson-1-create-project',
          name: 'lesson-1',
          component: () => import('../views/Lesson1CreateProject.vue')
        }
      ]
    })
    
    export default router
    ```

    **代码解释:**
    *   `path`: 定义了访问这个页面的 URL 路径。
    *   `name`: 为这个路由提供一个唯一的名称。
    *   `component`: 指定这个路由要渲染的组件。这里我们使用了动态导入 `() => import(...)`，这是一种称为“懒加载”的优化技术，只有在用户访问该路由时，对应的组件代码才会被加载。

#### **4. 更新主导航**

最后一步是在应用的全局导航栏中添加一个链接，指向我们刚创建的页面。

1.  打开根组件 `src/App.vue`。
2.  在 `<nav>` 元素中，仿照已有的 `<RouterLink>`，添加一个新的链接。

    ```html
    <script setup>
    import { RouterLink, RouterView } from 'vue-router'
    import HelloWorld from './components/HelloWorld.vue'
    </script>
    
    <template>
      <header>
        <!-- ... -->
        <div class="wrapper">
          <HelloWorld msg="You Did It!" />
    
          <nav>
            <RouterLink to="/">Home</RouterLink>
            <RouterLink to="/about">About</RouterLink>
            <!-- 在这里添加我们的新链接 -->
            <RouterLink to="/lesson-1-create-project">创建Vue3项目</RouterLink>
          </nav>
        </div>
      </header>
    
      <RouterView />
    </template>
    
    <!-- ... style ... -->
    ```

#### **5. 添加代码高亮样式**

我们还需要引入 `highlight.js` 的 CSS 文件来让代码高亮生效。

1.  打开项目的主入口文件 `src/main.js`。
2.  在文件的顶部，添加一行导入语句。

    ```javascript
    import './assets/main.css'
    
    // 导入 highlight.js 的样式主题
    import 'highlight.js/styles/atom-one-dark.css' // 你可以选择其他主题
    
    import { createApp } from 'vue'
    import { createPinia } from 'pinia'
    
    import App from './App.vue'
    import router from './router'
    
    // ...
    ```
    这里我们选择了 `atom-one-dark` 主题，你也可以在 `node_modules/highlight.js/styles/` 目录中找到并替换成其他你喜欢的主题，例如 `github.css`。

---

### **查看成果**

现在，保存所有修改过的文件。由于 Vite 开发服务器是热更新的，你应该不需要重启它。

回到你的浏览器，刷新页面 (`http://localhost:5173`)。你会看到导航栏多了一个“创建Vue3项目”的链接。点击它，你就能看到被完美渲染出来的、带有代码高亮的第一课学习笔记了！

我们已经为后续的学习笔记建立了一个可扩展的框架。从下一课开始，每当我们学习一个新主题，都可以通过重复**第2、3、4步**，为其创建一个新的视图和路由。
