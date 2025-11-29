import { createI18n } from "vue-i18n";
import en from "./en";
import zh from "./zh";

// 尝试从浏览器缓存读取，如果没有则默认 'zh'
const savedLocale = localStorage.getItem("user-locale") || "en";

const i18n = createI18n({
  legacy: false, //  false 以支持 Composition API
  locale: savedLocale, // 默认语言 这里使用读取到的值
  fallbackLocale: "zh", // 备用语言
  globalInjection: true, // 全局注册 $t
  messages: {
    en,
    zh,
  },
});

export default i18n;
