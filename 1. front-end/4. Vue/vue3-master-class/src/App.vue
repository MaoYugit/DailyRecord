<script>
import { ref } from 'vue'

const course = ref({
  name: 'Vue 3 大师课',
  price: 89.9,
  stock: 15,
  onSale: true,
  description: '这是一门帮助你<strong>从入门到精通</strong>Vue 3的课程。',
  image: './assets/logo.svg',
  features: ['核心基础', 'Composition API', 'Vue Router', 'Pinia', '实战项目'],
})
</script>

<template>
  <div>
    <h1>{{ course.name }}</h1>
    <p>价格：￥{{ course.price }}</p>
    <p>8折后的价格：￥{{ (coures.price * 0.8).toFixed(2) }}</p>
  </div>
</template>
