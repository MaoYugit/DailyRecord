<template>
  <div class="app-container">
    <!-- 背景管理器 -->
    <BackgroundManager :backgroundType="currentBackground" />
    
    <!-- 鼠标特效 -->
    <MouseEffectManager 
      :effectType="currentMouseEffect" 
      :enabled="mouseEffectEnabled" 
    />
    
    <!-- 特效控制面板 -->
    <EffectControlPanel 
      @update:mouseEffect="currentMouseEffect = $event"
      @update:mouseEffectEnabled="mouseEffectEnabled = $event"
      @update:background="currentBackground = $event"
    />
    
    <Navbar />
    <main>
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useThemeStore } from './stores/theme';
import Navbar from './components/layout/Navbar.vue';
import BackgroundManager from './components/three/BackgroundManager.vue';
import MouseEffectManager from './components/effects/MouseEffectManager.vue';
import EffectControlPanel from './components/common/EffectControlPanel.vue';

// Initialize theme
const themeStore = useThemeStore();

// 特效状态管理
const currentMouseEffect = ref('flower');
const mouseEffectEnabled = ref(true);
const currentBackground = ref('shards');
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
