<script setup lang="ts">
import { ref } from "vue";

const isOpen = ref(false);
</script>

<template>
  <div>
    <!-- 导航栏 -->
    <nav class="bg-gray-800 shadow-lg">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between h-16">
          <!-- 1. Logo 和品牌信息 (已修改) -->
          <div class="flex-shrink-0">
            <router-link to="/">
              <!-- 使用 img 标签，并控制其大小 -->
              <img class="h-8 w-auto" src="/logo-1.svg" alt="MyLogo" />
            </router-link>
          </div>

          <!-- 2. 桌面端导航菜单 -->
          <div class="hidden md:block">
            <div class="ml-10 flex items-baseline space-x-4">
              <router-link
                to="/"
                class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >Home</router-link
              >
              <router-link
                to="/blog"
                class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >Blog</router-link
              >
              <a
                href="#"
                class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >Projects</a
              >
              <router-link
                to="/design"
                class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >Design System</router-link
              >
              <a
                href="#"
                class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >About</a
              >
            </div>
          </div>

          <!-- 3. 移动端汉堡包按钮 -->
          <div class="md:hidden">
            <button
              @click="isOpen = !isOpen"
              class="bg-gray-800 inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white"
            >
              <span class="sr-only">Open main menu</span>
              <svg
                :class="{ hidden: isOpen, block: !isOpen }"
                class="h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
              <svg
                :class="{ hidden: !isOpen, block: isOpen }"
                class="h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- 移动端展开菜单 -->
      <div v-show="isOpen" class="md:hidden">
        <div class="px-2 pt-2 pb-3 space-y-1 sm:px-3">
          <router-link
            to="/"
            class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >Home</router-link
          >
          <router-link
            to="/blog"
            class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >Blog</router-link
          >
          <a
            href="#"
            class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >Projects</a
          >
          <router-link
            to="/design"
            class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >Design System</router-link
          >
          <a
            href="#"
            class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium"
            >About</a
          >
        </div>
      </div>
    </nav>

    <!-- 页面内容将在这里渲染 -->
    <main>
      <router-view />
    </main>
  </div>
</template>
