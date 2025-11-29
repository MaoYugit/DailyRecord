<template>
  <nav class="navbar">
    <div class="navbar-brand">
      <router-link to="/">Time Shards</router-link>
    </div>
    <div class="navbar-links">
      <router-link to="/">首页</router-link>
      <!-- <router-link to="/category">分类</router-link> -->
      <!-- <router-link to="/archive">归档</router-link> -->
      <!-- <router-link to="/about">关于</router-link> -->
      <!-- <router-link to="/search">搜索</router-link> -->
    </div>
    <div class="navbar-actions">
      <div v-if="userStore.isLoggedIn" class="user-menu">
        <router-link to="/editor" class="new-shard-btn">New Shard</router-link>
        <span>{{ userStore.user?.username }}</span>
        <button @click="handleLogout" class="logout-btn">Logout</button>
      </div>
      <div v-else class="auth-links">
        <router-link to="/login">Login</router-link>
      </div>
      
      <button @click="toggleTheme" class="theme-toggle" :title="isDark ? 'Switch to Light' : 'Switch to Dark'">
        <span v-if="isDark">🌙</span>
        <span v-else>☀️</span>
      </button>
    </div>
  </nav>
</template>

<script setup>
import { computed } from 'vue';
import { useThemeStore } from '../../stores/theme';
import { useUserStore } from '../../stores/user';
import { useRouter } from 'vue-router';

const themeStore = useThemeStore();
const userStore = useUserStore();
const router = useRouter();
const isDark = computed(() => themeStore.theme === 'dark');

const toggleTheme = () => {
  themeStore.toggleTheme();
};

const handleLogout = () => {
  userStore.logout();
  router.push('/');
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: var(--color-bg);
  border-bottom: 1px solid var(--color-container-bg);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
}

.navbar-brand a {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--color-text-main);
  text-transform: uppercase;
  letter-spacing: 2px;
}

.navbar-links {
  display: flex;
  gap: 2rem;
}

.navbar-links a {
  color: var(--color-text-sub);
  font-size: 0.9rem;
  font-weight: 500;
}

.navbar-links a:hover,
.navbar-links a.router-link-active {
  color: var(--color-accent-rational);
}

.theme-toggle {
  background: none;
  border: 1px solid var(--color-text-sub);
  color: var(--color-text-main);
  padding: 0.5rem;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.theme-toggle:hover {
  border-color: var(--color-accent-mystic);
  color: var(--color-accent-mystic);
  box-shadow: 0 0 10px var(--color-accent-mystic);
}

.navbar-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 1rem;
  color: var(--color-text-main);
  font-family: var(--font-mono);
  font-size: 0.9rem;
}

.logout-btn {
  background: none;
  border: 1px solid var(--color-border);
  color: var(--color-text-sub);
  padding: 0.3rem 0.8rem;
  cursor: pointer;
  border-radius: 4px;
  font-size: 0.8rem;
}

.logout-btn:hover {
  color: #ff4d4d;
  border-color: #ff4d4d;
}

.auth-links a {
  color: var(--color-text-main);
  font-weight: bold;
  margin-right: 1rem;
}

.new-shard-btn {
  background: var(--color-accent-rational);
  color: #000 !important;
  padding: 0.3rem 0.8rem;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: bold;
  text-decoration: none;
  margin-right: 1rem;
  transition: all 0.2s;
}

.new-shard-btn:hover {
  box-shadow: 0 0 10px var(--color-accent-rational);
  transform: translateY(-1px);
}
</style>
