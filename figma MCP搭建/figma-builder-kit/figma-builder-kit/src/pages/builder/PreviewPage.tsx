import { Render } from "@puckeditor/core";
import { config } from "./BuilderApp";

export function PreviewPage() {
    const raw = localStorage.getItem("puck-page-data");

    if (!raw) {
        return (
            <div style={{
                display: "flex", flexDirection: "column", alignItems: "center",
                justifyContent: "center", height: "100vh", gap: "16px",
                fontFamily: "-apple-system, sans-serif", color: "#64748b",
            }}>
                <p style={{ fontSize: "18px" }}>还没有页面数据</p>
                <p style={{ fontSize: "14px" }}>先去 <a href="?builder" style={{ color: "#2563eb" }}>搭建器</a> 搭一个页面，点发布后再来这里预览</p>
            </div>
        );
    }

    const data = JSON.parse(raw);

    return (
        <div style={{
            width: "375px",
            margin: "0 auto",
            minHeight: "100vh",
            boxShadow: "0 0 40px rgba(0,0,0,0.08)",
        }}>
            <Render config={config} data={data} />
        </div>
    );
}
