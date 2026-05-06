<template>
  <div class="father">
    <h3>5. $attrs 透传演示</h3>

    <!--
      这里传了：
      1. title (孙子需要的 prop)
      2. type, class (HTML 属性)
      3. disabled (响应式属性)
      4. @click (事件监听器)
    -->
    <MiddleLayer
      title="我是透传按钮"
      type="submit"
      class="special-btn"
      :disabled="isBtnDisabled"
      @click="handleNotice"
    />

    <button @click="isBtnDisabled = !isBtnDisabled">切换按钮禁用状态</button>
  </div>
</template>

<script setup name="AttrsFather">
import { ref } from 'vue'
import MiddleLayer from './MiddleLayer.vue'

const isBtnDisabled = ref(false)

function handleNotice() {
  alert('收到点击！注意：点击事件也是通过 $attrs 透传下去的')
}
</script>

<style scoped>
.father {
  border: 2px solid #34495e;
  padding: 20px;
  border-radius: 8px;
}
/* 这个类在祖组件定义，但最终会生效在孙子组件的 button 上 */
.special-btn {
  background-color: #3498db;
  color: white;
  border: none;
}
</style>
