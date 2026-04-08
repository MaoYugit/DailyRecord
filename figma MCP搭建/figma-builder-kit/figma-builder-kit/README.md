# Figma Builder Kit

把你的 Figma 组件库喂给 AI，自动还原成 React 代码，然后在可视化搭建器里拖拽搭页面。

> 不是让 AI 从零设计页面（像抽卡），而是**用你自己的组件库**让 AI 按你的风格搭。

## 快速启动

```bash
# 1. 安装依赖
npm install

# 2. 启动开发服务器
npm run dev
```

打开浏览器：

| 页面 | 地址 |
|------|------|
| **搭建器** | http://localhost:5173/?builder |
| **预览页** | http://localhost:5173/?preview |

## 工作流

```
① Figma 选中组件 → 复制链接
② 粘贴给 AI IDE（Claude Code / Cursor / Windsurf）
③ AI 通过 Figma MCP 读取设计 → 自动还原成 React 组件
④ 注册到搭建器 BuilderApp.tsx
⑤ 打开 ?builder 拖拽搭页面 → 点发布导出 JSON
```

## 需要的 AI 技能包（Skills）

项目已内置 2 个 Skill，位于 `.codex/skills/` 目录：

| Skill | 作用 |
|-------|------|
| **figma** | Figma MCP 集成 — 获取设计上下文、截图、变量 |
| **figma-implement-design** | 把 Figma 设计节点高保真还原成代码 |

### 配置步骤

1. 在你的 AI IDE 中添加 Figma MCP：
   ```
   codex mcp add figma --url https://mcp.figma.com/mcp
   ```
2. 登录 Figma：`codex mcp login figma`
3. Skills 已随项目附带，无需额外安装

## 项目结构

```
src/
├── components/              ← 你的组件放在这里（AI 自动生成）
├── icons/                   ← SVG 图标（从 Figma 导出）
├── pages/
│   └── builder/
│       ├── BuilderApp.tsx   ← 搭建器主文件（注册组件的地方）
│       ├── PreviewPage.tsx  ← 预览渲染页
│       ├── iconRegistry.ts  ← 图标注册表
│       └── IconPicker.tsx   ← 图标选择器
docs/
└── tutorial.md              ← 完整教程 + AI 提示词模板
.codex/skills/               ← AI 技能包（figma + figma-implement-design）
```

## 详细教程

见 [docs/tutorial.md](docs/tutorial.md)，包含：
- 完整的 AI 提示词模板（复制粘贴即可用）
- 组件分类参考（原子设计理论）
- 导出与协作流程

## 技术栈

- React 19 + TypeScript
- Vite
- Tailwind CSS 3
- [Puck Editor](https://github.com/measurable/puck) — 可视化页面搭建引擎
- Figma MCP — AI 读取 Figma 设计

## License

MIT
