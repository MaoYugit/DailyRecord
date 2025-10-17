<template>
  <div class="bg-gray-50 min-h-screen p-12">
    <h1 class="text-3xl font-bold mb-8">Our Brand Components</h1>

    <!-- 将按钮放在一个 Flex 容器中，方便展示 -->
    <div class="flex items-center space-x-4">
      <!-- 1. 主按钮 (实心) -->
      <button
        class="px-5 py-2.5 text-white font-semibold rounded-lg shadow-md bg-brand-primary hover:bg-brand-primary-dark focus:outline-none focus:ring-4 focus:ring-blue-300 transition-all duration-300"
      >
        Primary Button
      </button>

      <!-- 2. 次要按钮 (描边) -->
      <button
        class="px-5 py-2.5 font-semibold rounded-lg shadow-md bg-transparent border border-brand-primary text-brand-primary hover:bg-brand-primary hover:text-white focus:outline-none focus:ring-4 focus:ring-blue-300 transition-all duration-300"
      >
        Secondary Button
      </button>

      <!-- 3. 危险按钮 (描边) -->
      <button
        class="px-5 py-2.5 font-semibold rounded-lg shadow-md bg-transparent border border-brand-danger text-brand-danger hover:bg-brand-danger hover:text-white focus:outline-none focus:ring-4 focus:ring-red-300 transition-all duration-300"
      >
        Delete Action
      </button>
    </div>
  </div>
</template>
