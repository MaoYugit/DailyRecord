import { defineStore } from 'pinia';
import { ref, watch } from 'vue';

export const useThemeStore = defineStore('theme', () => {
  const theme = ref(localStorage.getItem('theme') || 'light');

  const toggleTheme = () => {
    theme.value = theme.value === 'light' ? 'dark' : 'light';
  };

  const applyTheme = () => {
    document.documentElement.setAttribute('data-theme', theme.value);
    localStorage.setItem('theme', theme.value);
  };

  // Watch for changes and apply
  watch(theme, applyTheme, { immediate: true });

  return {
    theme,
    toggleTheme
  };
});
