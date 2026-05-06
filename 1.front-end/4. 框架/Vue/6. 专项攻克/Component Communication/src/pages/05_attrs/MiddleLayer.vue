<template>
  <div class="middle-box">
    <h4>中间件 (快递员)</h4>
    <!--
       关键点：这里不需要声明 props。
       直接用 v-bind="$attrs" 把从祖组件收到的所有包裹传给 MyButton。
    -->
    <MyButton v-bind="$attrs" />
  </div>
</template>

<script setup name="MiddleLayer">
import MyButton from './MyButton.vue'

// 如果这里定义了 defineProps(['title'])，那么 $attrs 就会变少，
// 剩下的属性才会继续往下传。
defineOptions({
  inheritAttrs: false,
})
</script>

<style scoped>
.middle-box {
  background: #eee;
  padding: 20px;
  margin: 10px 0;
  /* width: 300px;
  height: 300px; */
}
</style>
