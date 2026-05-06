<script setup lang="ts" name="father">
import { ref } from 'vue'
import ChildView from './ChildView.vue'

const message = ref('Waiting for message from Child...')

function handleMessage(value: string) {
  message.value = value
}
</script>

<template>
  <div class="father">
    <h2>Father Component</h2>
    <p>Message from Child: {{ message }}</p>
    <ChildView @send-msg="handleMessage" />
  </div>
</template>

<style scoped>
.father {
  border: 1px solid #ccc;
  padding: 16px;
  margin: auto;
  width: 400px;
  height: 300px;
  border-radius: 5%;
  background-color: #ccc;
  text-align: center;
}
</style>
