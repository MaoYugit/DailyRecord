import React from "react";
import { Puck, DropZone, type Config, type Data } from "@puckeditor/core";
import "@puckeditor/core/puck.css";

// ── 在这里 import 你的组件 ──
// import { MyComponent } from "../../components/MyComponent";

// ── 图标选择器（可选，有 SVG 图标时启用） ──
// import { IconPicker } from "./IconPicker";
// import { ICON_REGISTRY } from "./iconRegistry";

// ── Puck 配置：在这里注册你的组件 ──
export const config: Config = {
    root: {
        fields: {
            bgColor: {
                type: "text",
                label: "页面背景色",
            },
        },
        defaultProps: { bgColor: "#F5F7FA" },
        render: ({ children, bgColor }: any) => (
            <div style={{ backgroundColor: bgColor || "#F5F7FA", minHeight: "100%" }}>
                {children}
            </div>
        ),
    },
    categories: {
        layout: { title: "布局", components: ["TwoColumnGrid", "Spacer"] },
        // navigation: { title: "导航", components: [] },
        // input: { title: "输入", components: [] },
        // buttons: { title: "按钮", components: [] },
        // cards: { title: "卡片", components: [] },
        // content: { title: "内容", components: [] },
    },
    components: {
        // ── 布局容器（开箱即用） ──
        TwoColumnGrid: {
            label: "两列网格",
            fields: {
                gap: {
                    type: "select",
                    label: "间距",
                    options: [
                        { label: "8px", value: "8" },
                        { label: "10px", value: "10" },
                        { label: "12px", value: "12" },
                        { label: "16px", value: "16" },
                    ],
                },
                padding: {
                    type: "select",
                    label: "左右边距",
                    options: [
                        { label: "0", value: "0" },
                        { label: "15px", value: "15" },
                        { label: "16px", value: "16" },
                    ],
                },
            },
            defaultProps: { gap: "10", padding: "15" },
            render: ({ gap, padding }) => (
                <div style={{
                    display: "grid",
                    gridTemplateColumns: "1fr 1fr",
                    gap: `${gap}px`,
                    padding: `0 ${padding}px`,
                }}>
                    <DropZone zone="left" />
                    <DropZone zone="right" />
                </div>
            ),
        },

        Spacer: {
            label: "间距块",
            fields: {
                height: {
                    type: "select",
                    label: "高度",
                    options: [
                        { label: "8px", value: "8" },
                        { label: "12px", value: "12" },
                        { label: "16px", value: "16" },
                        { label: "24px", value: "24" },
                        { label: "32px", value: "32" },
                    ],
                },
            },
            defaultProps: { height: "16" },
            render: ({ height }) => (
                <div style={{ height: `${height}px`, width: "100%" }} />
            ),
        },

        // ╔══════════════════════════════════════════════════╗
        // ║  👇 在这里添加你从 Figma 还原的组件              ║
        // ║                                                  ║
        // ║  参考 docs/tutorial.md 的提示词模板，             ║
        // ║  AI 会自动帮你在这里注册组件。                    ║
        // ║                                                  ║
        // ║  示例：                                          ║
        // ║  MyButton: {                                     ║
        // ║      label: "按钮",                               ║
        // ║      fields: {                                    ║
        // ║          text: { type: "text", label: "文字" },   ║
        // ║      },                                           ║
        // ║      defaultProps: { text: "点击" },              ║
        // ║      render: ({ text }) => <button>{text}</button>║
        // ║  },                                               ║
        // ╚══════════════════════════════════════════════════╝
    },
};

// ── 初始页面数据（空画布） ──
const initialData: Data = {
    content: [],
    root: { props: { bgColor: "#F5F7FA" } as any },
};

export default function BuilderApp() {
    // 把 Puck 自带的英文标签替换为中文
    React.useEffect(() => {
        const translations: Record<string, string> = {
            "Blocks": "组件",
            "Outline": "大纲",
            "Page": "页面",
            "Publish": "发布",
        };
        function replaceLabels() {
            const walker = document.createTreeWalker(
                document.body,
                NodeFilter.SHOW_TEXT,
            );
            let node: Text | null;
            while ((node = walker.nextNode() as Text | null)) {
                const trimmed = node.textContent?.trim();
                if (trimmed && translations[trimmed]) {
                    node.textContent = node.textContent!.replace(trimmed, translations[trimmed]);
                }
            }
        }
        const timer = setTimeout(replaceLabels, 100);
        const observer = new MutationObserver(replaceLabels);
        observer.observe(document.body, { childList: true, subtree: true });
        const stopTimer = setTimeout(() => observer.disconnect(), 3000);
        return () => {
            clearTimeout(timer);
            clearTimeout(stopTimer);
            observer.disconnect();
        };
    }, []);

    return (
        <div style={{ height: "100vh" }}>
            <style>{`
                /* Hide the viewport/zoom controls bar above canvas */
                [class*="_PuckCanvas-controls"],
                [class*="_ViewportControls"] {
                    display: none !important;
                }
                /* Force iframe to 375px mobile */
                [class*="PuckPreview-frame"],
                [class*="Preview-frame"],
                [class*="_PuckCanvas-frame"] {
                    width: 375px !important;
                    max-width: 375px !important;
                    margin: 0 auto !important;
                    border: 1px solid #e2e8f0 !important;
                    border-radius: 12px !important;
                    box-shadow: 0 4px 24px rgba(0,0,0,0.08) !important;
                }
                /* Hide scrollbars inside the canvas iframe */
                [class*="_PuckCanvas"] ::-webkit-scrollbar,
                [class*="_PuckCanvas"] iframe {
                    scrollbar-width: none;
                }
                [class*="_PuckCanvas"] ::-webkit-scrollbar {
                    display: none !important;
                    width: 0 !important;
                    height: 0 !important;
                }
            `}</style>
            <Puck
                config={config}
                data={initialData}
                viewports={[{ width: 375, height: 812, label: "Mobile", icon: "Smartphone" }]}
                overrides={{
                    componentItem: ({ name }) => {
                        const compConfig = (config.components as any)[name];
                        if (!compConfig) return <div style={{ padding: "8px" }}>{name}</div>;
                        const Render = compConfig.render;
                        const label = compConfig.label || name;
                        const defaultProps = compConfig.defaultProps || {};

                        let previewNode = null;
                        if (name === "TwoColumnGrid") {
                            previewNode = (
                                <div style={{ display: "flex", gap: "6px", width: "120px", height: "40px" }}>
                                    <div style={{ flex: 1, border: "2px dashed #cbd5e1", borderRadius: "6px" }} />
                                    <div style={{ flex: 1, border: "2px dashed #cbd5e1", borderRadius: "6px" }} />
                                </div>
                            );
                        } else if (name === "Spacer") {
                            previewNode = <div style={{ width: "120px", height: "16px", background: "#e2e8f0", borderRadius: "4px" }} />;
                        } else if (Render) {
                            previewNode = (
                                <div style={{
                                    transform: "scale(0.35)",
                                    transformOrigin: "top center",
                                    width: "375px",
                                    pointerEvents: "none"
                                }}>
                                    <Render {...defaultProps} />
                                </div>
                            );
                        }

                        return (
                            <div style={{
                                display: "flex",
                                flexDirection: "column",
                                border: "1px solid #e2e8f0",
                                borderRadius: "8px",
                                background: "#fff",
                                overflow: "hidden",
                                marginBottom: "4px"
                            }}>
                                <div style={{
                                    background: "#f1f5f9",
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "flex-start",
                                    height: "72px",
                                    overflow: "hidden",
                                    paddingTop: "6px"
                                }}>
                                    {previewNode}
                                </div>
                                <div style={{
                                    padding: "8px",
                                    fontSize: "12px",
                                    fontWeight: 500,
                                    color: "#334155",
                                    borderTop: "1px solid #e2e8f0",
                                    textAlign: "center"
                                }}>
                                    {label}
                                </div>
                            </div>
                        );
                    }
                }}
                onPublish={(data) => {
                    const json = JSON.stringify(data, null, 2);
                    navigator.clipboard.writeText(json).catch(() => {});
                    localStorage.setItem("puck-page-data", json);

                    const fileName = `page-${new Date().toISOString().slice(0, 10)}.json`;
                    const dataUri = "data:application/json;charset=utf-8," + encodeURIComponent(json);
                    const a = document.createElement("a");
                    a.href = dataUri;
                    a.download = fileName;
                    a.style.display = "none";
                    document.body.appendChild(a);
                    a.click();
                    document.body.removeChild(a);

                    const toast = document.createElement("div");
                    toast.textContent = "✅ 页面数据已复制到剪贴板并下载！访问 ?preview 可预览";
                    Object.assign(toast.style, {
                        position: "fixed", bottom: "24px", left: "50%", transform: "translateX(-50%)",
                        background: "#1a1a1a", color: "#fff", padding: "12px 24px",
                        borderRadius: "8px", fontSize: "14px", zIndex: "99999",
                        boxShadow: "0 4px 20px rgba(0,0,0,0.3)", transition: "opacity 0.3s",
                    });
                    document.body.appendChild(toast);
                    setTimeout(() => { toast.style.opacity = "0"; setTimeout(() => toast.remove(), 300); }, 3000);
                }}
            />
        </div>
    );
}
