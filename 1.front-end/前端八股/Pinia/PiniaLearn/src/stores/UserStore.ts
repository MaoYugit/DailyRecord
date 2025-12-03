import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("user", () => {
  // A. 定义 State (使用 ref)
  const balance = ref(100);
  const name = ref("Jack");

  // C. 定义 Actions (普通函数)
  function deductMoney(amount: number) {
    if (balance.value >= amount) {
      balance.value -= amount;
      return true;
    }
    return false;
  }

  // D. 必须 return 出去
  return { balance, name, deductMoney };
});
