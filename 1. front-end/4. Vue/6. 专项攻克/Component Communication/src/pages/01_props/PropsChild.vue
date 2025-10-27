<template>
  <div class="child">
    <h3>子组件</h3>
    <h4>玩具名称：{{ toy }}</h4>
    <h4>父亲给的车：{{ car }}</h4>
    <button @click="sendToy(toy)">把玩具给父亲</button>
  </div>
</template>

<script setup lang="ts" name="Child">
import { ref } from 'vue'

defineProps({
  car: {
    type: String,
    required: true,
  },
  sendToy: {
    type: Function,
    required: true,
  },
})
let toy = ref('奥特曼')
</script>

<style scoped>
.child {
  border: 2px solid #3498db;
  padding: 20px;
  border-radius: 8px;
  background-color: #f0f8ff;
}
</style>
