<template>
  <div class="father">
    <h3>4.父组件</h3>
    <!-- v-model 用在 HTML 标签上 -->
    <!-- <input type="text" v-model="username" /> -->
    <br />
    <!-- 上面那个的底层原理 -->
    <!-- <input type="text" :value="username" @input="username = $event.target.value" /> -->
    <AtguiguInput />
  </div>
</template>

<script setup name="VModelFather">
// import { ref } from 'vue'
import AtguiguInput from './AtguiguInput.vue'

// let username = ref('zhangsan')
</script>

<style scoped>
.father {
  border: 2px solid #27ae60;
  padding: 20px;
  border-radius: 8px;
  background-color: #e8f5e9;
}
</style>
