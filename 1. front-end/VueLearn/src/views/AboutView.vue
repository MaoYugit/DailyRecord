<script setup></script>
<template>
  <div class="about-container">
    <h1 class="title">关于本网站</h1>
    <p class="description">
      这是一个使用 Vue 3 和 Vue Router 构建的单页面应用，旨在帮助学习和实践 Vue.js
      的各种功能和概念。
    </p>
  </div>
</template>

<style scoped>
.about-container {
  display: flex;
  align-items: center;
  flex-direction: column;
  text-align: center;

  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: #42b983;
}
.description {
  font-size: 1.2rem;
  line-height: 1.6;
}
</style>
