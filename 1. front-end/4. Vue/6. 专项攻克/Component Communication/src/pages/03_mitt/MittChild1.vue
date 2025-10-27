<template>
  <div class="child">
    <h3>子组件1</h3>
    <h4>哥哥的玩具：{{ toy }}</h4>
    <button @click="emitter.emit('send-toy', toy)">给弟弟送玩具</button>
  </div>
</template>

<script setup lang="ts" name="Child1">
import emitter from '@/utils/emitter'
import { ref } from 'vue'

let toy = ref('奥特曼')
</script>

<style scoped>
.child {
  border: 2px solid #3498db;
  padding: 20px;
  border-radius: 8px;
  background-color: #f0f8ff;
  margin: 10px 0;
}
</style>
