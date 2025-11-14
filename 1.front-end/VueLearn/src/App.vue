<!--  src/App.vue -->
<script setup>
import NavHeader from '@/components/NavHeader.vue'
</script>

<template>
  <header>
    <NavHeader />
  </header>

  <main>
    <!-- 路由匹配到的组件将在这里渲染 -->
    <router-view />
  </main>
</template>
