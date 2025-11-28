import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  // --- 新增以下部分 ---
  server: {
    port: 5173, // 前端运行端口
    proxy: {
      // 假设你的接口请求是以 /api 开头的
      "/api": {
        target: "http://localhost:8080", // 【注意】这里必须改成你后端实际运行的地址和端口！
        changeOrigin: true,
        // 如果后端接口本身没有 /api 前缀，需要把路径中的 /api 去掉，解开下面这行的注释
        // rewrite: (path) => path.replace(/^\/api/, '')
      },
    },
  },
});
