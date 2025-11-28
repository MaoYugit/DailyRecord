<template>
  <div class="app-container">
    <ParticleBackground />
    <Navbar />
    <main>
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { useThemeStore } from './stores/theme';
import Navbar from './components/layout/Navbar.vue';
import ParticleBackground from './components/common/ParticleBackground.vue';

// Initialize theme
const themeStore = useThemeStore();
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  background-color: var(--color-bg);
  color: var(--color-text-main);
  transition: background-color var(--transition-speed), color var(--transition-speed);
  display: flex;
  flex-direction: column;
}

main {
  flex: 1;
}
</style>
