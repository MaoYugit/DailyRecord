<template>
  <!--
    v-bind="$attrs" 的作用：
    将父组件传过来的、且没有被 props 声明的所有属性，
    一股脑全部绑定在这个 button 元素上。
  -->
  <div class="child-container">
    <button v-bind="$attrs">
      {{ title }}
    </button>
  </div>
</template>

<script setup name="MyButton">
defineOptions({
  inheritAttrs: false,
})
// 孙子只领一个 title 属性，剩下的（如 disabled, class, @click）都在 $attrs 里
defineProps(['title'])
</script>

<style scoped>
button {
  padding: 10px 20px;
  cursor: pointer;
}
button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
.special-btn {
  background-color: #3498db;
  color: white;
  border: none;
}
</style>
