<script setup lang="ts" name="first-child">
const props = defineProps({
  treasure: {
    type: String,
    default: 'Gold Coin',
  },
})
</script>

<template>
  <div class="first-child-view">
    <h3>First Child</h3>
    <p>Received treasure: {{ props.treasure }}</p>
  </div>
</template>
