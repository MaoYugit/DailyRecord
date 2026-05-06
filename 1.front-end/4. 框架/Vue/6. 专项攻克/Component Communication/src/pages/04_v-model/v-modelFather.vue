<template>
  <div class="father">
    <h3>4. 父组件</h3>
    <p>
      当前录入的用户名：<mark>{{ username }}</mark>
    </p>

    <hr />

    <!-- 使用自定义组件的 v-model -->
    <AtguiguInput v-model="username" label="用户名" />

    <!--
      v-model 的底层原理演示（对比学习）：
      <AtguiguInput
        :modelValue="username"
        @update:modelValue="username = $event"
        label="密码"
      />
    -->

    <button @click="username = '李四'">重置为李四</button>
  </div>
</template>

<script setup lang="ts" name="VModelFather">
import { ref } from 'vue'
import AtguiguInput from './AtguiguInput.vue'

// 定义响应式变量
let username = ref('张三')
</script>

<style scoped>
.father {
  border: 2px solid #27ae60;
  padding: 20px;
  border-radius: 8px;
  background-color: #e8f5e9;
  min-height: 200px;
}

mark {
  background-color: #ffeaa7;
  padding: 0 5px;
  border-radius: 4px;
  font-weight: bold;
}
</style>
