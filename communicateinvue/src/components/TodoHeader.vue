<script setup>
const model = defineModel()
</script>
<template>
  <header class="header">
    <h1>VueTasker</h1>
    <input class="new-todo" placeholder="what you plan to do ?" v-model="model" />
  </header>
</template>
