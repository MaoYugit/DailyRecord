<template>
  <div v-if="visible" class="notification">
    {{ message }}
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import emitter from '../utils/emitter'

const visible = ref(false)
const message = ref('')
let timeoutId = null

const showNotification = (msg) => {
  if (timeoutId) {
    clearTimeout(timeoutId)
  }
  message.value = msg
  visible.value = true
  timeoutId = setTimeout(() => {
    visible.value = false
  }, 3000)
}

onMounted(() => {
  emitter.on('show-notification', showNotification)
})

onUnmounted(() => {
  emitter.off('show-notification', showNotification)
})
</script>
