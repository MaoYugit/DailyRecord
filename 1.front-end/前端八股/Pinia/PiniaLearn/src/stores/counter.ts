import { defineStore } from "pinia";

export const useCounterStore = defineStore("counter", {
  state: () => ({
    count: 0,
    name: "Pinia 初学者",
    items: [] as string[],
  }),

  getters: {
    doubleCount: (state) => state.count * 2,

    doubleCountPlusOne(): number {
      return this.doubleCount + 1;
    },
  },

  actions: {
    increment() {
      this.count++;
    },

    async registerUser() {
      try {
        await new Promise((resolve) => setTimeout(resolve, 1000));
        this.name = "高级开发者";
        this.count = 100;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
