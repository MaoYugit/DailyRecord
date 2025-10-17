/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "brand-primary": "#3B82F6", // 我们的品牌主色 (蓝色)
        "brand-primary-dark": "#2563EB", // 用于悬停效果的深色版本
        "brand-success": "#10B981", // 成功状态 (绿色)
        "brand-danger": "#EF4444", // 危险/错误状态 (红色)
      },
    },
  },
  plugins: [],
};
