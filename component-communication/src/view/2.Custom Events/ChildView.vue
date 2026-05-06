<script setup lang="ts" name="child">
const emit = defineEmits<{
  (e: 'send-msg', value: string): void
}>()

function handleClick() {
  emit('send-msg', 'Hello Father, this is your Child!')
}
</script>

<template>
  <div class="child">
    <h3>Child Component</h3>
    <button @click="handleClick">点击给父组件发送消息</button>
  </div>
</template>

<style scoped>
.child {
  border: 1px solid #999;
  padding: 12px;
  margin: 12px;
  background-color: #eee;
  border-radius: 15px;
}
</style>
