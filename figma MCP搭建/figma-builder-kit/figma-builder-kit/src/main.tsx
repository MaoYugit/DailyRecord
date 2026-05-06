import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import BuilderApp from "./pages/builder/BuilderApp";
import { PreviewPage } from "./pages/builder/PreviewPage";

const params = new URLSearchParams(window.location.search);

function Root() {
  if (params.has("builder")) return <BuilderApp />;
  if (params.has("preview")) return <PreviewPage />;

  // 默认首页：引导用户进入搭建器
  return (
    <div style={{
      display: "flex", flexDirection: "column", alignItems: "center",
      justifyContent: "center", height: "100vh", gap: "24px",
      fontFamily: "-apple-system, sans-serif", color: "#334155",
    }}>
      <h1 style={{ fontSize: "28px", fontWeight: 700 }}>Figma Builder Kit</h1>
      <p style={{ color: "#64748b", fontSize: "16px" }}>
        把你的 Figma 组件库喂给 AI，用搭建器拖拽搭页面
      </p>
      <div style={{ display: "flex", gap: "12px" }}>
        <a href="?builder" style={{
          padding: "12px 24px", background: "#2563eb", color: "#fff",
          borderRadius: "8px", textDecoration: "none", fontWeight: 500,
        }}>
          打开搭建器
        </a>
        <a href="?preview" style={{
          padding: "12px 24px", background: "#f1f5f9", color: "#334155",
          borderRadius: "8px", textDecoration: "none", fontWeight: 500,
        }}>
          预览页面
        </a>
      </div>
    </div>
  );
}

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Root />
  </StrictMode>
);
