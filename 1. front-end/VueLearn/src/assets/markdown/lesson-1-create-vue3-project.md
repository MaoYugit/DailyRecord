# 创建 Vue3 工程

好的，欢迎开始学习 Vue 3。本系列教学将以专业和详细的方式进行，避免使用过多不必要的比喻。

### **第一课：创建 Vue 3 工程**

在开始编写 Vue 3 应用程序之前，首要任务是搭建一个标准的工程化开发环境。Vue 官方提供了一套现代化的前端构建工具，可以帮助我们快速生成项目骨架。目前，官方首选的方案是使用基于 [Vite](https://vitejs.dev/) 的脚手架工具 `create-vue`。Vite 是一个旨在提供极致开发体验的新一代前端构建工具，其特点是启动速度快、热更新即时。

#### **1. 环境准备**

在创建 Vue 3 项目之前，你需要确保你的计算机上已经安装了 Node.js。Vite 要求 Node.js 版本为 14.18+ 或 16+。

你可以通过在终端（Terminal）或命令提示符（Command Prompt）中运行以下命令来检查你的 Node.js 版本：

```bash
node -v
```

如果命令返回了版本号（例如 `v18.17.0`），并且版本符合要求，则说明环境已准备就绪。如果未安装，请从 [Node.js 官网](https://nodejs.org/) 下载并安装推荐的 LTS (长期支持) 版本。 安装 Node.js 的同时会自动安装 `npm`（Node 包管理器），我们后续将使用 `npm` 来管理项目依赖。

#### **2. 使用 `create-vue` 创建项目**

现在，我们将通过一个命令来创建 Vue 3 项目。

1.  **打开终端**：在你希望存放项目的文件夹路径下打开你的终端。

2.  **运行创建命令**：在终端中执行以下命令。

    ```bash
    npm create vue@latest
    ```

3.  **配置项目选项**：该命令会启动一个交互式的配置向导，让你根据项目需求定制功能。 你需要通过键盘上下箭头选择，按回车键确认。

    下面是对每个主要选项的专业说明：

    *   **Project name:**
        *   **说明**：为你的项目设定一个名称。这将会是你的项目文件夹名。
        *   **操作**：输入项目名称后按回车，例如 `vue3-learning`。

    *   **Add TypeScript?**
        *   **说明**：TypeScript 是 JavaScript 的一个超集，它增加了静态类型检查，可以提升代码的健壮性和可维护性，尤其适合大型项目。Vue 3 对 TypeScript 提供了出色的原生支持。
        *   **操作**：对于初学者，可以选择 `No` 以专注于 Vue 本身。如果你已有 TypeScript 基础，强烈建议选择 `Yes`。

    *   **Add JSX Support?**
        *   **说明**：JSX 是一种 JavaScript 的语法扩展，允许你在 JavaScript 代码中编写类似 HTML 的结构。它在 React 中被广泛使用，Vue 也提供了支持。
        *   **操作**：通常情况下，Vue 的模板语法更具优势且更易于理解，初学者建议选择 `No`。

    *   **Add Vue Router for Single-Page Application development?**
        *   **说明**：Vue Router 是 Vue 官方的路由管理器。如果你的应用需要多个页面（视图）之间的导航，例如从“主页”切换到“关于”页面，你就需要它。
        *   **操作**：这是一个非常常用的功能，建议选择 `Yes` 以便后续学习。

    *   **Add Pinia for state management?**
        *   **说明**：Pinia 是 Vue 官方推荐的新一代状态管理库（取代了 Vuex）。当你的应用有多个组件需要共享状态（数据）时，Pinia 提供了一个集中式、类型安全的方式来管理这些状态。
        *   **操作**：建议选择 `Yes`，这是现代 Vue 应用开发的核心部分。

    *   **Add Vitest for Unit Testing?**
        *   **说明**：单元测试是用于验证代码中最小可测试单元（如单个函数或组件）是否按预期工作的软件测试方法。Vitest 是一个由 Vite 驱动的高速单元测试框架。
        *   **操作**：对于入门教学，可以暂时选择 `No`。

    *   **Add an End-to-End Testing Solution?**
        *   **说明**：端到端（E2E）测试用于模拟真实用户场景，从用户的角度测试整个应用的流程。
        *   **操作**：入门阶段可以暂时选择 `No`。

    *   **Add ESLint for code quality?**
        *   **说明**：ESLint 是一个代码检查工具，用于发现并修复代码中的语法错误和不符合规范的写法，保障代码质量和团队风格统一。
        *   **操作**：强烈建议选择 `Yes`。

    *   **Add Prettier for code formatting?**
        *   **说明**：Prettier 是一个代码格式化工具，它能够自动调整你的代码格式以符合预设的规范，例如统一的缩进、分号等。与 ESLint 结合使用，可以极大地提升开发效率。
        *   **操作**：强烈建议选择 `Yes`。

    完成选择后，脚手架工具会自动生成项目文件。

#### **3. 启动开发服务器**

项目文件创建成功后，终端会提示你后续需要执行的命令。

1.  **进入项目目录**：

    ```bash
    cd <your-project-name> 
    ```
    将 `<your-project-name>` 替换为你之前设定的项目名，例如 `cd vue3-learning`。

2.  **安装依赖**：
    此命令会读取 `package.json` 文件，并下载项目所需的所有第三方库（例如 Vue、Vue Router、Pinia 等）。

    ```bash
    npm install
    ```

3.  **启动开发服务器**：
    该命令会启动一个本地的开发服务器，并编译你的 Vue 应用。

    ```bash
    npm run dev
    ```

    启动成功后，你会在终端看到类似下面的输出：

    ```
      VITE v5.x.x  ready in xxx ms
    
      ➜  Local:   http://localhost:5173/
      ➜  Network: use --host to expose
      ➜  press h + enter to show help
    ```

    这表明你的 Vue 3 应用已在本地 `http://localhost:5173` 地址成功运行。

#### **4. 查看成果**

打开你的网页浏览器，访问终端中显示的 `Local` 地址（通常是 `http://localhost:5173`）。 你将看到一个由 `create-vue` 生成的默认欢迎页面。

至此，你已经成功创建并运行了你的第一个 Vue 3 工程。在下一课中，我们将深入探索这个项目的目录结构，并开始编写你的第一个 Vue 组件。