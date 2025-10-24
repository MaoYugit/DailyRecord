<script setup>
import { ref } from 'vue'
defineProps({
  stock: {
    type: Number,
    required: true,
  },
})

const emit = defineEmits(['addToCart', 'addStock'])

const quantity = ref(1)

function handleAddToCart() {
  emit('addToCart', quantity.value)
}

function handleAddToStock() {
  emit('addToStock', quantity.value)
}
</script>
<template>
  <div class="actions">
    <div class="quantity-control">
      <label for="quantity">数量:</label>
      <input type="number" id="quantity" v-model.number="quantity" min="1" :max="stock" />
    </div>
    <button @click="handleAddToCart" :disabled="stock === 0" class="add-to-cart-btn">
      {{ stock > 0 ? '加入购物车' : '已售罄' }}
    </button>
    <button @click="handleAddToStock" class="add-to-stock-btn">增加库存</button>
  </div>
</template>
<style scoped>
/* 操作区域 */
.actions {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.quantity-control label {
  font-size: 1rem;
  color: #4a5568;
}

.quantity-control input {
  width: 70px;
  padding: 0.5rem;
  border: 1px solid #cbd5e0;
  border-radius: 6px;
  text-align: center;
  font-size: 1rem;
}

.add-to-cart-btn {
  background-color: #42b983;
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

.add-to-cart-btn:hover:not(:disabled) {
  background-color: #36a46e;
  transform: translateY(-2px);
}

.add-to-cart-btn:disabled {
  background-color: #a0aec0;
  cursor: not-allowed;
}

.add-to-stock-btn {
  background-color: #3182ce;
  color: white;
  border: none;
  padding: 0.8rem 1.5rem;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

.add-to-stock-btn:hover {
  background-color: #2b6cb0;
  transform: translateY(-2px);
}
</style>
