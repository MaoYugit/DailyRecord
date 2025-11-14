<template>
  <!-- 1. 全局背景和居中容器：让卡片始终在屏幕正中间 -->
  <div class="bg-slate-100 min-h-screen flex items-center justify-center">

    <!-- 2. 卡片主题：白色背景，有圆角、边框和阴影 -->
    <div class="w-full max-w-xs bg-white border border-gray-200 rounded-xl shadow-lg p-8 space-y-4">

      <!-- 3. 头像：圆形，居中，带一点阴影增加立体感 -->
      <img class="w-24 h-24 rounded-full mx-auto shadow-md ring-4 ring-slate-200" src="https://randomuser.me/api/portraits/men/32.jpg" alt="User avatar">
      
      <!-- 4. 文本信息：居中对齐，使用不同字号和颜色区分层级 -->
      <div class="text-center">
        <p class="text-2xl font-semibold text-gray-900">John Doe</p>
        <p class="text-gray-500 font-medium">Frontend Developer</p>
      </div>

      <!-- 5. 按钮：占满宽度，有悬停和聚焦效果，提升交互体验 -->
      <div class="flex justify-center">
        <router-link to="/blog" class="block text-center w-40 px-4 py-2 bg-sky-500 text-white rounded-lg font-semibold hover:bg-sky-600 focus:outline-none focus:ring-4 focus:ring-sky-300 transition-all duration-300">Contact Me</router-link>
      </div>
    </div>
  </div>
</template>