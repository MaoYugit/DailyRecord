// ── 图标注册表 ──
// 当你从 Figma 导出 SVG 图标到 src/icons/ 后，
// 运行 scripts/generate-icon-registry.cjs 自动生成此文件。
// 
// 手动注册示例：
// import { IconHome } from "../../icons";
// export const ICON_REGISTRY: Record<string, { label: string; component: any }> = {
//     "home": { label: "Home", component: IconHome },
// };

export const ICON_REGISTRY: Record<string, { label: string; component: any }> = {};
