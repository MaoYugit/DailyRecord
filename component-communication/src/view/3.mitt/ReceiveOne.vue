<script setup lang="ts" name="receive-one">
import { onMounted, ref, onUnmounted } from 'vue'
import emitter from '@/utils/bus'

const msg = ref('')

const handler = (data: string) => {
  msg.value = data
}

onMounted(() => {
  emitter.on('send-message', handler)
})

onUnmounted(() => {
  emitter.off('send-message', handler)
})
</script>

<template>
  <div class="receive-one">
    <h3>Receive One</h3>
    <p>Message received: {{ msg }}</p>
  </div>
</template>

<style scoped>
.receive-one {
  border: 1px solid #999;
  padding: 12px;
  margin: 12px;
  background-color: #eee;
  border-radius: 15px;
}
</style>
