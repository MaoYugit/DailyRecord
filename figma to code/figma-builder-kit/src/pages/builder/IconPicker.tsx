import React from "react";
import { ICON_REGISTRY } from "./iconRegistry";

interface IconPickerProps {
    value: string;
    onChange: (v: string) => void;
    registry?: typeof ICON_REGISTRY;
}

/**
 * 图标选择器 — 在搭建器右侧面板中选择 SVG 图标
 * 需要先在 iconRegistry.ts 中注册图标才能使用
 */
export function IconPicker({ value, onChange, registry = ICON_REGISTRY }: IconPickerProps) {
    const [open, setOpen] = React.useState(false);
    const [search, setSearch] = React.useState("");
    const keys = Object.keys(registry);

    if (keys.length === 0) {
        return (
            <div style={{ padding: "8px", color: "#94a3b8", fontSize: "12px" }}>
                暂无图标，请先在 iconRegistry.ts 中注册
            </div>
        );
    }

    const filtered = search
        ? keys.filter(k => k.toLowerCase().includes(search.toLowerCase()) || registry[k].label.toLowerCase().includes(search.toLowerCase()))
        : keys;

    const current = value && registry[value];

    return (
        <div>
            <button
                onClick={() => setOpen(!open)}
                style={{
                    display: "flex", alignItems: "center", gap: "8px",
                    padding: "6px 10px", border: "1px solid #e2e8f0",
                    borderRadius: "6px", background: "#fff", cursor: "pointer",
                    width: "100%", fontSize: "13px",
                }}
                type="button"
            >
                {current ? (
                    <>
                        {React.createElement(current.component, { style: { width: "20px", height: "20px" } })}
                        <span>{current.label}</span>
                    </>
                ) : (
                    <span style={{ color: "#94a3b8" }}>{value || "选择图标"}</span>
                )}
            </button>

            {open && (
                <div style={{
                    position: "absolute", zIndex: 999, background: "#fff",
                    border: "1px solid #e2e8f0", borderRadius: "8px",
                    boxShadow: "0 8px 30px rgba(0,0,0,0.12)",
                    width: "260px", maxHeight: "320px", overflow: "hidden",
                    display: "flex", flexDirection: "column",
                }}>
                    <input
                        autoFocus
                        onChange={(e) => setSearch(e.target.value)}
                        placeholder="搜索图标..."
                        style={{
                            padding: "8px 12px", border: "none",
                            borderBottom: "1px solid #e2e8f0", outline: "none",
                            fontSize: "13px",
                        }}
                        value={search}
                    />
                    <div style={{ overflow: "auto", maxHeight: "270px", padding: "4px" }}>
                        <button
                            onClick={() => { onChange("none"); setOpen(false); }}
                            style={{
                                display: "block", width: "100%", padding: "6px 8px",
                                border: "none", background: value === "none" ? "#eff6ff" : "transparent",
                                cursor: "pointer", fontSize: "12px", textAlign: "left",
                                borderRadius: "4px", color: "#94a3b8",
                            }}
                            type="button"
                        >
                            无图标
                        </button>
                        {filtered.map(key => {
                            const entry = registry[key];
                            const Comp = entry.component;
                            return (
                                <button
                                    key={key}
                                    onClick={() => { onChange(key); setOpen(false); setSearch(""); }}
                                    style={{
                                        display: "flex", alignItems: "center", gap: "8px",
                                        width: "100%", padding: "6px 8px", border: "none",
                                        background: value === key ? "#eff6ff" : "transparent",
                                        cursor: "pointer", borderRadius: "4px",
                                    }}
                                    type="button"
                                >
                                    <Comp style={{ width: "20px", height: "20px" }} />
                                    <span style={{ fontSize: "12px", color: "#334155" }}>{entry.label}</span>
                                </button>
                            );
                        })}
                    </div>
                </div>
            )}
        </div>
    );
}
