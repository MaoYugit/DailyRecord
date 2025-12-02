<template>
  <nav class="navbar">
    <!-- 1. 左侧区域：Logo 和 搜索框 -->
    <div class="navbar-left">
      <div class="navbar-brand">
        <router-link to="/">{{ t("brand") }}</router-link>
      </div>

      <!-- 搜索框：在桌面端显示，移动端隐藏(放入菜单中) -->
      <div class="search-container desktop-only">
        <input
          type="text"
          v-model="searchQuery"
          :placeholder="t('searchPlaceholder')"
          @keyup.enter="handleSearch"
        />
        <button class="search-icon-btn" @click="handleSearch">
          <!-- 搜索 SVG 图标 -->
          <svg
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </button>
      </div>
    </div>

    <!-- 2. 中间区域：导航链接 (桌面端) -->
    <div class="navbar-links desktop-only">
      <router-link to="/">{{ t("home") }}</router-link>
      <router-link to="/categories">分类</router-link>
      <router-link to="/tags">标签</router-link>
      <router-link to="/archive">归档</router-link>
      <!-- 知识库 -->
      <router-link to="/editor" v-if="userStore.isLoggedIn">{{
        t("new")
      }}</router-link>
      <router-link to="/about">{{ t("about") }}</router-link>
    </div>

    <!-- 3. 右侧区域：操作按钮 -->
    <div class="navbar-actions">
      <!-- 语言切换 -->
      <button @click="toggleLang" class="icon-btn" title="Switch Language">
        <span class="lang-text">{{ locale === "zh" ? "EN" : "中" }}</span>
      </button>

      <!-- 主题切换 -->
      <button
        @click="toggleTheme"
        class="icon-btn theme-toggle"
        :title="isDark ? 'Switch to Light' : 'Switch to Dark'"
      >
        <span v-if="isDark">🌙</span>
        <span v-else>☀️</span>
      </button>

      <!-- 设置 -->
      <button @click="router.push('/settings')" class="icon-btn" title="Settings">
        <span>⚙️</span>
      </button>

      <!-- 用户信息 (桌面端精简显示，移动端在菜单里) -->
      <div v-if="userStore.isLoggedIn" class="user-menu desktop-only">
        <router-link to="/profile" class="username-link">{{ userStore.user?.username }}</router-link>
        <button @click="handleLogout" class="logout-btn">Logout</button>
      </div>
      <div v-else class="auth-links desktop-only">
        <router-link to="/login">Login</router-link>
      </div>

      <!-- 移动端菜单切换按钮 -->
      <button class="mobile-toggle" @click="toggleMobileMenu">
        <svg
          v-if="!isMobileMenuOpen"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
        >
          <line x1="3" y1="12" x2="21" y2="12"></line>
          <line x1="3" y1="6" x2="21" y2="6"></line>
          <line x1="3" y1="18" x2="21" y2="18"></line>
        </svg>
        <svg
          v-else
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
        >
          <line x1="18" y1="6" x2="6" y2="18"></line>
          <line x1="6" y1="6" x2="18" y2="18"></line>
        </svg>
      </button>
    </div>

    <!-- 4. 移动端下拉菜单 -->
    <div class="mobile-menu" :class="{ 'is-open': isMobileMenuOpen }">
      <!-- 移动端搜索 -->
      <div class="search-container mobile-search">
        <input
          type="text"
          v-model="searchQuery"
          :placeholder="t('searchPlaceholder')"
          @keyup.enter="handleSearch"
        />
        <button class="search-icon-btn" @click="handleSearch">
          <svg
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
          >
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
        </button>
      </div>

      <router-link to="/" @click="closeMobileMenu">{{ t("home") }}</router-link>
      <router-link to="/categories" @click="closeMobileMenu">分类</router-link>
      <router-link to="/tags" @click="closeMobileMenu">标签</router-link>
      <router-link to="/archive" @click="closeMobileMenu">归档</router-link>
      <router-link
        to="/editor"
        v-if="userStore.isLoggedIn"
        @click="closeMobileMenu"
        >{{ t("new") }}</router-link
      >
      <router-link to="/about" @click="closeMobileMenu">{{ t("about") }}</router-link>
      <router-link to="/settings" @click="closeMobileMenu">设置</router-link>

      <div class="mobile-auth" v-if="userStore.isLoggedIn">
        <router-link to="/profile" @click="closeMobileMenu">{{ userStore.user?.username }}</router-link>
        <button @click="handleLogout" class="logout-btn">Logout</button>
      </div>
      <div class="mobile-auth" v-else>
        <router-link to="/login" @click="closeMobileMenu">Login</router-link>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { computed, ref } from "vue";
import { useThemeStore } from "../../stores/theme";
import { useUserStore } from "../../stores/user";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";

const themeStore = useThemeStore();
const userStore = useUserStore();
const router = useRouter();

const { t, locale } = useI18n();

// 状态管理
const isDark = computed(() => themeStore.theme === "dark");
const searchQuery = ref("");
const isMobileMenuOpen = ref(false);

// 动作处理
const toggleTheme = () => themeStore.toggleTheme();

const toggleLang = () => {
  locale.value = locale.value === "zh" ? "en" : "zh";
  localStorage.setItem("user-locale", locale.value);
};

const toggleMobileMenu = () => {
  isMobileMenuOpen.value = !isMobileMenuOpen.value;
};

const closeMobileMenu = () => {
  isMobileMenuOpen.value = false;
};

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({ path: '/search', query: { q: searchQuery.value } });
    closeMobileMenu();
    searchQuery.value = "";
  }
};

const handleLogout = () => {
  userStore.logout();
  router.push("/");
  closeMobileMenu();
};
</script>

<style scoped>
/* 基础导航栏样式 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.8rem 2rem;
  background-color: var(--color-bg);
  border-bottom: 1px solid var(--color-container-bg);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
  /* 确保相对定位，方便移动端菜单绝对定位 */
  position: relative;
}

/* 左侧区域布局 */
.navbar-left {
  display: flex;
  align-items: center;
  gap: 2rem;
  flex: 1;
}

.navbar-brand a {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--color-text-main);
  text-transform: uppercase;
  letter-spacing: 2px;
  text-decoration: none;
  white-space: nowrap;
}

/* 搜索框样式 */
.search-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 240px;
}

.search-container input {
  width: 100%;
  padding: 0.4rem 2.2rem 0.4rem 0.8rem;
  border-radius: 20px;
  border: 1px solid var(--color-border);
  background: var(--color-bg);
  color: var(--color-text-main);
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.search-container input:focus {
  outline: none;
  border-color: var(--color-accent-rational);
  box-shadow: 0 0 5px rgba(var(--color-accent-rational-rgb), 0.2);
}

.search-icon-btn {
  position: absolute;
  right: 5px;
  background: none;
  border: none;
  color: var(--color-text-sub);
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 4px;
}

.search-icon-btn:hover {
  color: var(--color-accent-rational);
}

/* 中间链接区域 */
.navbar-links {
  display: flex;
  gap: 2rem;
  justify-content: center;
  flex: 1;
}

.navbar-links a {
  color: var(--color-text-sub);
  font-size: 0.95rem;
  font-weight: 500;
  text-decoration: none;
  transition: color 0.2s;
  white-space: nowrap;
}

.navbar-links a:hover,
.navbar-links a.router-link-active {
  color: var(--color-accent-rational);
}

/* 右侧操作区域 */
.navbar-actions {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  flex: 1;
  justify-content: flex-end;
}

/* 通用图标按钮 (主题、语言) */
.icon-btn {
  background: none;
  border: 1px solid var(--color-text-sub);
  color: var(--color-text-main);
  width: 36px;
  height: 36px;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}

.icon-btn:hover {
  border-color: var(--color-accent-mystic);
  color: var(--color-accent-mystic);
  box-shadow: 0 0 8px var(--color-accent-mystic);
}

.lang-text {
  font-size: 0.8rem;
  font-weight: bold;
}

/* 用户相关 */
.user-menu {
  display: flex;
  align-items: center;
  gap: 1rem;
  color: var(--color-text-main);
  font-family: var(--font-mono);
  font-size: 0.9rem;
}

.auth-links a {
  color: var(--color-text-main);
  font-weight: bold;
  text-decoration: none;
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

/* 移动端汉堡包按钮 - 默认隐藏 */
.mobile-toggle {
  display: none;
  background: none;
  border: none;
  color: var(--color-text-main);
  cursor: pointer;
}

/* 移动端菜单 - 默认隐藏 */
.mobile-menu {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background-color: var(--color-bg);
  border-bottom: 1px solid var(--color-container-bg);
  flex-direction: column;
  padding: 1rem;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.mobile-menu a {
  padding: 1rem;
  color: var(--color-text-main);
  text-decoration: none;
  border-bottom: 1px solid var(--color-container-bg);
  text-align: center;
}

.mobile-search {
  max-width: 100%;
  margin-bottom: 1rem;
}

.mobile-auth {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
}

/* --- 响应式适配 --- */

/* 当屏幕小于 768px (常见平板和手机) */
@media (max-width: 768px) {
  .navbar {
    padding: 0.8rem 1rem;
  }

  .desktop-only {
    display: none !important;
  }

  .mobile-toggle {
    display: block;
  }

  /* 调整布局，中间空出来，把 actions 推到右边 */
  .navbar-left {
    flex: 0; /* 只占用内容宽度 */
  }

  .navbar-actions {
    flex: 1; /* 占据剩余空间 */
    justify-content: flex-end;
  }

  /* 菜单展开状态 */
  .mobile-menu.is-open {
    display: flex;
  }
}
</style>
