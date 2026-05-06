<script setup lang="ts" name="send-one">
import emitter from '@/utils/bus'

function handleClick() {
  emitter.emit('send-message', 'Hello MOTHER FUCKER')
}
</script>

<template>
  <div class="send-one">
    <h3>Send One</h3>
    <button @click="handleClick">点击发送消息给 ReceiveOne</button>
  </div>
</template>

<style scoped>
.send-one {
  border: 1px solid #999;
  padding: 12px;
  margin: 12px;
  background-color: #eee;
  border-radius: 15px;
}
</style>
