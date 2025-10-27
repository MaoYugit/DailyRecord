<template>
  <div class="father">
    <h3>3.父组件</h3>
    <MittChild1 />
    <MittChild2 />
  </div>
</template>

<script setup lang="ts" name="MittFather">
import MittChild1 from './MittChild1.vue'
import MittChild2 from './MittChild2.vue'
</script>

<style scoped>
.father {
  border: 2px solid #27ae60;
  padding: 20px;
  border-radius: 8px;
  background-color: #e8f5e9;
}
</style>
