<template>
  <div class="father">
    <h3>2.父组件</h3>
    <h4 v-if="toy">孩子给的玩具：{{ toy }}</h4>
    <!-- 给子组件绑定事件 -->
    <CustomEventChild @send-toy="getToy" />
  </div>
</template>

<script setup lang="ts" name="Father">
import CustomEventChild from './CustomEventChild.vue'
import { ref } from 'vue'

let toy = ref('')
function getToy(value) {
  toy.value = value
}
</script>

<style scoped>
.father {
  border: 2px solid #e67e22;
  padding: 20px;
  border-radius: 8px;
  background-color: #fff3e0;
}
</style>
