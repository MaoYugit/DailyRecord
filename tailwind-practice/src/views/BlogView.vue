<template>
  <div class="bg-gray-100 min-h-screen">
    <!-- 1. 内容容器：设置最大宽度并居中 -->
    <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
      <!-- 页面标题 -->
      <h2 class="text-3xl font-extrabold tracking-tight text-gray-900 mb-8">
        From the blog
      </h2>

      <!-- 2. 网格容器：这是布局魔法的核心 -->
      <div class="grid gap-8 grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
        <!-- 文章卡片 1 (我们会重复这个结构) -->
        <div
          class="bg-white rounded-lg shadow-md overflow-hidden transition-transform duration-300 hover:scale-105"
        >
          <img
            class="w-full h-48 object-cover"
            src="https://images.unsplash.com/photo-1496128858413-b36217c2ce36?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1679&q=80"
            alt="Article thumbnail"
          />
          <div class="p-6">
            <span
              class="inline-block bg-pink-100 text-pink-800 text-xs font-semibold px-2.5 py-0.5 rounded-full mb-2"
            >
              Marketing
            </span>
            <h3 class="text-xl font-bold text-gray-900">
              Boost your conversion rate
            </h3>
            <p class="mt-3 text-base text-gray-500">
              Lorem ipsum dolor sit amet consectetur adipisicing elit.
              Architecto accusantium praesentium eius, ut atque fuga culpa,
              similique sequi cum eos quis dolorum.
            </p>
          </div>
        </div>

        <!-- 文章卡片 2 -->
        <div
          class="bg-white rounded-lg shadow-md overflow-hidden transition-transform duration-300 hover:scale-105"
        >
          <img
            class="w-full h-48 object-cover"
            src="https://images.unsplash.com/photo-1547586696-ea22b4d4235d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1679&q=80"
            alt="Article thumbnail"
          />
          <div class="p-6">
            <span
              class="inline-block bg-indigo-100 text-indigo-800 text-xs font-semibold px-2.5 py-0.5 rounded-full mb-2"
            >
              Business
            </span>
            <h3 class="text-xl font-bold text-gray-900">
              How to use search engine optimization to drive sales
            </h3>
            <p class="mt-3 text-base text-gray-500">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Velit
              facilis asperiores porro quaerat doloribus, eveniet dolore.
              Adipisci tempora aut inventore optio animi., tempore temporibus
              quo laudantium.
            </p>
          </div>
        </div>

        <!-- 文章卡片 3 -->
        <div
          class="bg-white rounded-lg shadow-md overflow-hidden transition-transform duration-300 hover:scale-105"
        >
          <img
            class="w-full h-48 object-cover"
            src="https://images.unsplash.com/photo-1492724441997-5dc865305da7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1679&q=80"
            alt="Article thumbnail"
          />
          <div class="p-6">
            <span
              class="inline-block bg-green-100 text-green-800 text-xs font-semibold px-2.5 py-0.5 rounded-full mb-2"
            >
              Design
            </span>
            <h3 class="text-xl font-bold text-gray-900">
              Improve your customer experience
            </h3>
            <p class="mt-3 text-base text-gray-500">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Sint
              harum rerum voluptatem quo recusandae magni placeat saepe
              molestiae, sed excepturi cumque corporis perferendis hic.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
