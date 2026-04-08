/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      // 你的设计 Token 放在这里
      // 例如：从 Figma 导出的颜色、字体、间距等
    },
  },
  plugins: [],
};
