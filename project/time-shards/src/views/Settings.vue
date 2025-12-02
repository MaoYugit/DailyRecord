<template>
  <div class="settings-page">
    <div class="page-header">
      <h1 class="page-title">系统设置</h1>
      <p class="page-subtitle">个性化你的 Time Shards 体验</p>
    </div>

    <div class="settings-container">
      <!-- 视觉体验设置 -->
      <section class="settings-section">
        <h2 class="section-title">🎨 视觉体验</h2>
        
        <!-- 鼠标特效 -->
        <div class="setting-group">
          <div class="group-header">
            <h3>鼠标特效</h3>
            <label class="switch">
              <input 
                type="checkbox" 
                :checked="settingsStore.mouseEffectEnabled" 
                @change="settingsStore.toggleMouseEffect($event.target.checked)"
              >
              <span class="slider"></span>
            </label>
          </div>
          
          <div class="options-grid" :class="{ disabled: !settingsStore.mouseEffectEnabled }">
            <button 
              v-for="effect in settingsStore.mouseEffectOptions" 
              :key="effect.type"
              class="option-card"
              :class="{ active: settingsStore.mouseEffect === effect.type }"
              @click="settingsStore.setMouseEffect(effect.type)"
            >
              <div class="option-icon">{{ effect.icon }}</div>
              <div class="option-info">
                <span class="option-name">{{ effect.name }}</span>
                <span class="option-desc">{{ effect.description }}</span>
              </div>
            </button>
          </div>
        </div>

        <!-- 背景风格 -->
        <div class="setting-group">
          <div class="group-header">
            <h3>背景风格</h3>
          </div>
          
          <div class="options-grid">
            <button 
              v-for="bg in settingsStore.backgroundOptions" 
              :key="bg.type"
              class="option-card"
              :class="{ active: settingsStore.backgroundType === bg.type }"
              @click="settingsStore.setBackgroundType(bg.type)"
            >
              <div class="option-icon">{{ bg.icon }}</div>
              <div class="option-info">
                <span class="option-name">{{ bg.name }}</span>
                <span class="option-desc">{{ bg.description }}</span>
              </div>
            </button>
          </div>
        </div>
      </section>

      <!-- 常规设置 -->
      <section class="settings-section">
        <h2 class="section-title">⚙️ 常规设置</h2>
        
        <div class="setting-row">
          <div class="setting-label">
            <h3>界面主题</h3>
            <p>切换明亮/暗黑模式</p>
          </div>
          <button class="theme-toggle-btn" @click="themeStore.toggleTheme">
            <span v-if="themeStore.theme === 'dark'">🌙 暗黑模式</span>
            <span v-else>☀️ 明亮模式</span>
          </button>
        </div>

        <div class="setting-row">
          <div class="setting-label">
            <h3>语言设置</h3>
            <p>切换系统显示语言</p>
          </div>
          <button class="lang-toggle-btn" @click="toggleLang">
            <span>{{ locale === 'zh' ? '🇨🇳 中文' : '🇺🇸 English' }}</span>
          </button>
        </div>
      </section>

      <!-- 账户操作 -->
      <section class="settings-section danger-zone" v-if="userStore.isLoggedIn">
        <h2 class="section-title">👤 账户操作</h2>
        
        <div class="setting-row">
          <div class="setting-label">
            <h3>退出登录</h3>
            <p>安全退出当前账户</p>
          </div>
          <button class="logout-btn" @click="handleLogout">
            退出登录
          </button>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { useSettingsStore } from '@/stores/settings';
import { useThemeStore } from '@/stores/theme';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import gsap from 'gsap';
import { onMounted } from 'vue';

const settingsStore = useSettingsStore();
const themeStore = useThemeStore();
const userStore = useUserStore();
const router = useRouter();
const { locale } = useI18n();

const toggleLang = () => {
  locale.value = locale.value === 'zh' ? 'en' : 'zh';
  localStorage.setItem('user-locale', locale.value);
};

const handleLogout = () => {
  if (confirm('确定要退出登录吗？')) {
    userStore.logout();
    router.push('/');
  }
};

onMounted(() => {
  gsap.from('.settings-section', {
    y: 30,
    opacity: 0,
    duration: 0.6,
    stagger: 0.2,
    ease: 'power2.out'
  });
});
</script>

<style scoped>
.settings-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 4rem;
}

.page-title {
  font-size: 2.5rem;
  color: #00f3ff;
  margin-bottom: 0.5rem;
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.6);
}

.settings-section {
  background: rgba(20, 20, 40, 0.6);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(0, 243, 255, 0.2);
  border-radius: 16px;
  padding: 2rem;
  margin-bottom: 2rem;
}

.section-title {
  font-size: 1.5rem;
  color: #00f3ff;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid rgba(0, 243, 255, 0.2);
}

.setting-group {
  margin-bottom: 2.5rem;
}

.group-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.group-header h3 {
  font-size: 1.2rem;
  color: white;
}

/* Switch Toggle */
.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 26px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.1);
  transition: .4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 18px;
  width: 18px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #00f3ff;
}

input:checked + .slider:before {
  transform: translateX(24px);
}

/* Options Grid */
.options-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  transition: opacity 0.3s ease;
}

.options-grid.disabled {
  opacity: 0.5;
  pointer-events: none;
}

.option-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-align: left;
}

.option-card:hover {
  background: rgba(0, 243, 255, 0.1);
  transform: translateY(-2px);
}

.option-card.active {
  background: rgba(0, 243, 255, 0.2);
  border-color: #00f3ff;
  box-shadow: 0 0 15px rgba(0, 243, 255, 0.2);
}

.option-icon {
  font-size: 2rem;
}

.option-name {
  display: block;
  font-weight: bold;
  color: white;
  margin-bottom: 0.2rem;
}

.option-desc {
  display: block;
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.6);
}

/* Setting Row */
.setting-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.setting-row:last-child {
  border-bottom: none;
}

.setting-label h3 {
  font-size: 1.1rem;
  color: white;
  margin-bottom: 0.3rem;
}

.setting-label p {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
}

.theme-toggle-btn,
.lang-toggle-btn,
.logout-btn {
  padding: 0.6rem 1.5rem;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.05);
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 0.95rem;
}

.theme-toggle-btn:hover,
.lang-toggle-btn:hover {
  border-color: #00f3ff;
  color: #00f3ff;
}

.logout-btn {
  border-color: rgba(255, 77, 77, 0.5);
  color: #ff4d4d;
}

.logout-btn:hover {
  background: rgba(255, 77, 77, 0.1);
  border-color: #ff4d4d;
}

@media (max-width: 768px) {
  .options-grid {
    grid-template-columns: 1fr;
  }
  
  .setting-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
  
  .theme-toggle-btn,
  .lang-toggle-btn,
  .logout-btn {
    width: 100%;
  }
}
</style>
