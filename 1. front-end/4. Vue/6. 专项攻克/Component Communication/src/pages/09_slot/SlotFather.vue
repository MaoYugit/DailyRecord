<template>
  <div class="father">
    <h3>9.父组件</h3>
    <SlotChild />
  </div>
</template>

<script setup lang="ts" name="Father">
import SlotChild from './SlotChild.vue'
</script>

<style scoped></style>
