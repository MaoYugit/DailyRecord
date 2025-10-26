<template>
  <div class="father">
    <h3>1.父组件</h3>
    <PropsChild />
  </div>
</template>

<script setup lang="ts" name="Father">
import PropsChild from './PropsChild.vue'
</script>

<style scoped></style>
