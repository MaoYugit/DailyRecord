<template>
  <div class="child">
    <h3>子组件2</h3>
    <h4>弟弟的电脑：{{ computer }}</h4>
    <h4 v-if="toy">哥哥给的玩具：{{ toy }}</h4>
  </div>
</template>

<script setup name="Child2">
import { onUnmounted, ref } from 'vue'
import emitter from '@/utils/emitter'

let computer = ref('电脑')
let toy = ref('')

emitter.on('send-toy', (value) => {
  toy.value = value
})

onUnmounted(() => {
  emitter.off('send-toy')
})
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
