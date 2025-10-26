<template>
  <div class="container">
    <h1 class="title">Vue3 组件通信</h1>
  </div>
  <hr />
  <div>
    <div>
      <!-- 导航区 -->
      <router-link to="/props">1. Props 属性传递</router-link>
      <router-link to="/event">2. 自定义事件 $emit</router-link>
      <router-link to="/bus">3. Mitt 总线通信</router-link>
      <router-link to="/model">4. v-model 双向绑定</router-link>
      <router-link to="/attrs-listeners">5. $attrs 和 $listeners</router-link>
      <router-link to="/refs-children-parent">6. $refs 和 $parent</router-link>
      <router-link to="/provide-inject">7. Provide / Inject</router-link>
      <router-link to="/pinia">8. Pinia 状态管理</router-link>
      <router-link to="/slot">9. 插槽 Slot</router-link>
    </div>
    <div>
      <div>
        <!-- 展示区 -->
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style scoped></style>
