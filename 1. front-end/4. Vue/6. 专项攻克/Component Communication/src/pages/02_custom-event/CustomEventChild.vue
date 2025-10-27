<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>玩具：{{ toy }}</h4>
    <button @click="emit('send-toy', toy)">测试</button>
  </div>
</template>

<script setup lang="ts" name="Child">
import { ref, onMounted } from 'vue'

let toy = ref('奥特曼')
let toy2 = ref('变形金刚')

// emits 定义事件
const emit = defineEmits(['send-toy'])
onMounted(() => {
  // 模拟异步操作，2秒后触发事件
  setTimeout(() => {
    emit('send-toy', toy2.value)
  }, 1000)
})
</script>

<style scoped>
.child {
  border: 2px solid #3498db;
  padding: 20px;
  border-radius: 8px;
  background-color: #f0f8ff;
}
</style>
