<script setup>
import { ref } from 'vue'

const course = ref({
  name: 'Vue 3 大师课',
  price: 89.9,
  stock: 10,
  onSale: true,
  description:
    'Vue 3 大师课，带你<strong>从入门到实战</strong>，掌握 <strong>Vue 3 全家桶</strong>技术栈，打造高性能前端应用。',
  image: 'https://vuejs.org/images/logo.png',
  features: ['核心基础', 'Composition API', 'Vue Router', 'Pinia', '实战项目'],
})

const imageAttrs = {
  src: 'https://vuejs.org/images/logo.png',
  alt: 'Vue 3 Logo',
  title: 'Vue 3 Logo',
  class: 'lesson-img',
}

const isNameVisible = ref(true)

function toggleNameVisibility() {
  isNameVisible.value = !isNameVisible.value
}
</script>
<template>
  <h1 class="lesson-title" v-show="isNameVisible">{{ course.name }}</h1>
  <h1 class="lesson-title" v-if="isNameVisible">{{ course.name }}</h1>
  <button @click="toggleNameVisibility">{{ isNameVisible ? '隐藏名称' : '显示名称' }}</button>
  <p>价格：￥{{ course.price }}</p>
  <p>折扣价：￥{{ (course.price * 0.8).toFixed(2) }}</p>
  <h2>课程描述：</h2>
  <p v-html="course.description"></p>
  <img :src="course.image" :alt="course.name" :title="course.name" class="lesson-img" />
  <img v-bind="imageAttrs" />
  <h3>库存状态：</h3>
  <p v-if="course.stock > 10">库存充足</p>
  <p v-else-if="course.stock > 0">库存紧张</p>
  <p v-else>已售罄</p>
  <h3>课程特色：</h3>
  <ul>
    <li v-for="(feature, index) in course.features" :key="feature">
      {{ index + 1 }}. {{ feature }}
    </li>
  </ul>
</template>
<style scope>
.lesson-img {
  width: 100px;
  height: 100px;
}
</style>
