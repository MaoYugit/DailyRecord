```bash
npm create vite@latest
```



1. Project name: ... 给你的项目起个名字吧，比如 my-blog。
2. Select a framework: ... 选择 Vue。
3. Select a variant: ... 选择 JavaScript。

```bash
cd  my-blog
npm install
```

**1. 安装 TailwindCSS 相关的依赖包**

运行以下命令来安装 tailwindcss 和它的两个伙伴 postcss 和 autoprefixer。它们能确保 Tailwind 生成的样式有最好的兼容性。

```bash
npm install -D tailwindcss@^3.0.0 postcss autoprefixer
```

- -D 的意思是将它们作为“开发依赖”来安装，因为它们只在开发过程中需要。

**2. 生成配置文件**

接下来，运行这个命令来自动创建 TailwindCSS 和 PostCSS 的配置文件：

```
npx tailwindcss init -p
```

执行成功后，你会在项目的根目录下看到两个新文件：tailwind.config.js 和 postcss.config.js。

**3. 配置 Tailwind 的扫描路径**

这是**非常关键的一步**。我们需要告诉 Tailwind 去扫描哪些文件，以便它能找到我们用到的所有类名，并把对应的样式生成出来。

打开 tailwind.config.js 文件，用下面的内容替换掉它原来的内容：

```JavaScript
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```

- 这行配置的意思是：请扫描根目录的 index.html 文件，以及 src 目录下所有以 .vue, .js 等结尾的文件。

**4. 引入 Tailwind 的基础样式**

Vite 创建的项目里有一个 src/style.css 文件，它包含了一些默认样式。现在我们不再需要它们了。

请打开 src/style.css 文件，**删除里面的所有内容**，然后替换为下面这三行：

```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

- 这三行是 Tailwind 的“指令”，它们会把 Tailwind 所有好用的基础样式、组件类和工具类注入到你的项目中。



```bash
npm install vue-router@4 pinia gsap
```

