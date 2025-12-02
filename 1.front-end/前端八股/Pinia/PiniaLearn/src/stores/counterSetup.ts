import { defineStore } from "pinia";
// 1. 引入 Vue 的组合式 API
import { ref, computed } from "vue";

// 2. defineStore 的第二个参数变成了 "函数"
export const useCounterStoreSetupStore = defineStore("counter-setup", () => {
  // A. 定义 State (使用 ref)
  const count = ref(0);
  const name = ref("Pinia Setuo 风格");

  // B. 定义 Getters (使用 computed)
  const doubleCount = computed(() => count.value * 2);
  const doubleCountPlusOne = computed(() => doubleCount.value + 1);

  // C. 定义 Actions (普通函数)
  function increment() {
    // 记得加 .value，就像在组件里写代码一样
    count.value++;
  }
  async function registerUser() {
    // 模拟异步 等待 1 秒
    await new Promise((r) => setTimeout(r, 1000));
    name.value = "Setup 高级开发者(Setup 版)";
    count.value = 200;
  }

  // D. 【关键】必须 return 出去
  // 只有 return 出去的内容，外界（组件）才能访问到
  // 没 return 的变量就是"私有变量" (Private State)，这是 Setup Store 的独门绝技
  return {
    // State
    count,
    name,
    // Getters
    doubleCount,
    doubleCountPlusOne,
    // Actions
    increment,
    registerUser,
  };
});
