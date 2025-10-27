<template>
  <div class="father">
    <h3>1.父组件</h3>
    <h4>汽车品牌：{{ car }}</h4>
    <h4 v-if="childToy">孩子给的玩具：{{ childToy }}</h4>
    <PropsChild :car="car" :sendToy="getToy" />
  </div>
</template>

<script setup lang="ts" name="Father">
import PropsChild from './PropsChild.vue'
import { ref } from 'vue'

let car = ref('奔驰')
let childToy = ref('')

function getToy(value) {
  console.log('从孩子那得到了玩具：', value)
  childToy.value = value
}
</script>

<style scoped>
.father {
  border: 2px solid #42b983;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>
