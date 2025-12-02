<script setup lang="ts">
// 1. 引入我们定义好的 Store 钩子
import { useCounterStore } from "./stores/counter";

// 2. 实例化 Store
// 注意：一定要在 setup() 内部调用，否则会报错（找不到 Pinia 实例）
const counterStore = useCounterStore();

// 这里的 counterStore 是一个 Proxy 对象（Reactive）
console.log(counterStore);
</script>

<template>
  <div class="card">
    <!-- 3. 读取 State -->
    <h2>用户名：{{ counterStore.name }}</h2>

    <!-- 读取 Getters (用法和 State 一模一样) -->
    <h3>当前数字：{{ counterStore.count }}</h3>
    <h3>两倍数字：{{ counterStore.doubleCount }}</h3>
    <h3>两倍数字+1：{{ counterStore.doubleCountPlusOne }}</h3>

    <hr />

    <!-- 4. 调用 Actions -->
    <!-- 像调用普通函数一样简单 -->
    <button @click="counterStore.increment()">点击 +1 (同步)</button>

    <button @click="counterStore.registerUser()">一键升级 (异步)</button>
  </div>
</template>

<style scoped>
.card {
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 8px;
  width: 600px;
}

button {
  margin-right: 10px;
  padding: 8px 16px;
  cursor: pointer;
}
</style>
