// src/views/LessonView.vue
<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import MarkdownRenderer from '@/components/MarkdownRenderer.vue'
import { lessons } from '@/lessons.js' // 导入课程目录

// 1. 使用 import.meta.glob 预先获取所有 markdown 文件。
// 这会创建一个对象，键是文件路径，值是返回文件内容的异步函数。
const markdownModules = import.meta.glob('../assets/markdown/*.md', {
  query: '?raw',
  import: 'default',
})

const route = useRoute()
const markdownContent = ref('')

const loadLessonContent = async (lessonId) => {
  // 根据路由id查找课程信息
  const lesson = lessons.find((l) => l.id === lessonId)
  if (lesson) {
    // 2. 构造要查找的文件路径
    const path = `../assets/markdown/${lesson.markdownFile}`

    // 3. 检查文件是否存在于 `markdownModules` 中
    if (markdownModules[path]) {
      try {
        // 4. 调用对应的函数加载文件内容
        // 因为使用了 { as: 'raw' }，返回值直接就是文件内容的字符串，无需 .default
        markdownContent.value = await markdownModules[path]()
      } catch (e) {
        console.error('Failed to load markdown file:', e)
        markdownContent.value = '# 课程加载失败\n\n请检查文件是否存在。'
      }
    } else {
      // 虽然在 lessons.js 中找到了课程，但对应的 .md 文件不存在
      console.error(`Markdown file not found for lesson: ${lesson.markdownFile}`)
      markdownContent.value = '# 文件未找到\n\n课程数据存在，但对应的 Markdown 文件丢失。'
    }
  } else {
    markdownContent.value = '# 404 - 未找到课程\n\n请检查课程 ID 是否正确。'
  }
}

// watch 监听路由变化，这部分代码无需改动
watch(
  () => route.params.lessonId,
  (newId) => {
    if (newId) {
      loadLessonContent(newId)
    }
  },
  { immediate: true }, // 立即执行一次，以便在初次加载时获取内容
)
</script>

<template>
  <div class="lesson-container">
    <main class="content">
      <MarkdownRenderer :markdown-content="markdownContent" />
    </main>
  </div>
</template>

<style scoped>
.lesson-container {
  display: flex;
}
.content {
  flex-grow: 1;
}
</style>
