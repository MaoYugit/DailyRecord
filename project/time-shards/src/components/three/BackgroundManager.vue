<template>
  <div class="background-manager">
    <transition name="fade" mode="out-in">
      <component :is="currentBackgroundComponent" :key="backgroundType" />
    </transition>
  </div>
</template>

<script setup>
import { computed, defineAsyncComponent } from 'vue';

const props = defineProps({
  backgroundType: {
    type: String,
    default: 'shards',
    validator: (value) => ['shards', 'nebula', 'matrix', 'galaxy'].includes(value)
  }
});

// 动态加载背景组件
const backgroundComponents = {
  shards: defineAsyncComponent(() => import('./TimeShardsBackground.vue')),
  nebula: defineAsyncComponent(() => import('./CosmicNebulaBackground.vue')),
  matrix: defineAsyncComponent(() => import('./MatrixRainBackground.vue')),
  galaxy: defineAsyncComponent(() => import('./GalaxySpiralBackground.vue'))
};

const currentBackgroundComponent = computed(() => {
  return backgroundComponents[props.backgroundType] || backgroundComponents.shards;
});
</script>

<style scoped>
.background-manager {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
