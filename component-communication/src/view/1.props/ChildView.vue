<script setup lang="ts" name="first-child">
const props = defineProps({
  treasure: {
    type: String,
  },
  giveMoney: {
    type: Function,
    required: true,
  },
})
</script>

<template>
  <div class="first-child-view">
    <h3>First Child</h3>
    <p>Received treasure: {{ props.treasure }}</p>
    <button @click="props.giveMoney(500)">Give 500 Money to Father</button>
  </div>
</template>

<style scoped>
.first-child-view {
  border: 1px solid #999;
  padding: 12px;
  margin-top: 12px;
  background-color: #eee;
  border-radius: 15px;
}
</style>
