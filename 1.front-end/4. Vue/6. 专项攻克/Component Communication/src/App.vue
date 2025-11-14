<template>
  <div class="app-container">
    <header class="app-header">
      <h1 class="title">Vue3 组件通信</h1>
    </header>
    <div class="main-content">
      <nav class="sidebar">
        <router-link to="/props" class="nav-link">1. Props 属性传递</router-link>
        <router-link to="/event" class="nav-link">2. 自定义事件 $emit</router-link>
        <router-link to="/bus" class="nav-link">3. Mitt 总线通信</router-link>
        <router-link to="/model" class="nav-link">4. v-model 双向绑定</router-link>
        <router-link to="/attrs-listeners" class="nav-link">5. $attrs</router-link>
        <router-link to="/refs-children-parent" class="nav-link">6. $refs 和 $parent</router-link>
        <router-link to="/provide-inject" class="nav-link">7. Provide / Inject</router-link>
        <router-link to="/pinia" class="nav-link">8. Pinia 状态管理</router-link>
        <router-link to="/slot" class="nav-link">9. 插槽 Slot</router-link>
      </nav>
      <main class="content-area">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
/* Main container for the entire application */
.app-container {
  display: flex;
  flex-direction: column;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
  background-color: #f4f7f9;
  min-height: 100vh;
}

/* Header styling */
.app-header {
  background-color: #ffffff;
  padding: 20px 40px;
  border-bottom: 1px solid #e0e0e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  text-align: center;
}

.title {
  color: #2c3e50;
  font-size: 2.5em;
  margin: 0;
  font-weight: 600;
}

/* Main content area layout */
.main-content {
  display: flex;
  flex-grow: 1;
}

/* Sidebar navigation */
.sidebar {
  width: 280px;
  background-color: #ffffff;
  border-right: 1px solid #e0e0e0;
  padding: 20px;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.05);
}

.nav-link {
  display: block;
  padding: 15px 20px;
  margin-bottom: 8px;
  color: #555;
  text-decoration: none;
  border-radius: 8px;
  transition:
    background-color 0.3s ease,
    color 0.3s ease,
    transform 0.2s ease;
  font-weight: 500;
  font-size: 1.1em;
}

.nav-link:hover {
  background-color: #eaf2f8;
  color: #2980b9;
  transform: translateX(5px);
}

/* Styling for the active route link */
.router-link-active,
.router-link-exact-active {
  background-color: #3498db;
  color: #ffffff;
  box-shadow: 0 4px 8px rgba(52, 152, 219, 0.3);
  transform: translateX(0);
}

.router-link-active:hover,
.router-link-exact-active:hover {
  background-color: #2980b9;
  color: #ffffff;
}

/* Content display area */
.content-area {
  flex-grow: 1;
  padding: 40px;
  background-color: #f4f7f9;
}

/* Add a subtle animation for the router view transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
