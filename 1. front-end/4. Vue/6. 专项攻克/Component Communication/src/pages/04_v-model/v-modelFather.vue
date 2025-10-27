<template>
  <div class="father">
    <h3>4.父组件</h3>
    <input type="text" v-model="username" />
  </div>
</template>

<script setup lang="ts" name="VModelFather">
import { ref } from 'vue'

let username = ref('zhangsan')
</script>

<style scoped>
.father {
  border: 2px solid #27ae60;
  padding: 20px;
  border-radius: 8px;
  background-color: #e8f5e9;
}
</style>
