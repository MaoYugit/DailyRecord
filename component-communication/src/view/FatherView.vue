<script setup lang="ts" name="father">
import { ref } from 'vue'
import FirstChild from './FirstChild.vue'

const treasure = ref('Gold Coin')
</script>

<template>
  <div class="father-view">
    <h2>Father</h2>
    <FirstChild :treasure="treasure" />
  </div>
</template>
