import { defineStore } from "pinia";
import { ref } from "vue";
// 1. 引入别的 Store
import { useUserStore } from "./UserStore";

export const useProductStore = defineStore("product", () => {
  const goodsName = ref("Laptop");
  const price = ref(20);

  function buy() {
    // 2. 【关键】在 Action 内部实例化另一个 Store
    // 注意：不要在最外层实例化，要在函数内部！
    const userStore = useUserStore();
    // 3. 像在组件里一样使用它
    console.log(`当前用户：${userStore.name}, 余额：${userStore.balance}`);

    // 调用 UserStore 的方法扣钱
    if (userStore.deductMoney(price.value)) {
      console.log(`购买成功！购买了：${goodsName.value}，花费：${price.value}`);
    } else {
      console.log("余额不足，购买失败！");
    }
  }

  return { goodsName, price, buy };
});
