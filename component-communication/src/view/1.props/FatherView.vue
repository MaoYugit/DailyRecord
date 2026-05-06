<script setup lang="ts" name="father">
import { ref } from 'vue'
import ChildView from './ChildView.vue'

const treasure = ref('Gold Coin')
const money = ref()
function giveMoney(value: number) {
  money.value = value
}
</script>

<template>
  <div class="father-view">
    <h2>Father</h2>
    <p>Treasure to give: {{ treasure }}</p>
    <p>Money received from First Child: {{ money }}</p>
    <ChildView :treasure="treasure" :giveMoney="giveMoney" />
  </div>
</template>

<style scoped>
.father-view {
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
