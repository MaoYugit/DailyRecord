import { defineStore } from "pinia";

// 1. 定义并导出容器
// 参数1：'counter' 是这个 Store 的唯一 ID (必填)
// 参数2：配置对象 (Options API 写法)
export const useCounterStore = defineStore("counter", {
  // A. State: 存储数据的地方
  // 注意：这里必须是一个箭头函数，返回一个对象！
  state: () => ({
    count: 0,
    name: "Pinia 初学者",
    items: [] as string[], // TS 简单类型推导
  }),

  // B. Getters: 计算属性
  // 相当于 Vue 组件里的 computed，有缓存功能
  getters: {
    // 写法 1：接收 state 作为参数 (推荐，支持 TS 推导)
    doubleCount: (state) => state.count * 2,

    // 写法 2：不传参，使用 this (需要手动指定返回类型，否则 TS 可能会懵)
    // 这种写法通常用于 getter 内部访问其他 getter
    doubleCountPlusOne(): number {
      return this.doubleCount + 1;
    },
  },

  // C. Actions: 业务逻辑 & 修改数据
  // 相当于 Vue 组件里的 methods
  // 注意：这里不能用箭头函数，否则 this 指向会出错！
  actions: {
    // 1. 同步修改
    increment() {
      // 在 Pinia 里，直接用 this 读写 state，不需要 mutation！
      this.count++;
    },

    // 2. 异步修改 (模拟请求)
    async registerUser() {
      try {
        // 模拟等待 1 秒
        await new Promise((resolve) => setTimeout(resolve, 1000));
        this.name = "高级开发者"; // 异步回来后直接修改
        this.count = 100;
      } catch (error) {
        console.error(error);
      }
    },
  },
});
