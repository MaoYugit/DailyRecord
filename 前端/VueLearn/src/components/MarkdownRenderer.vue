<script setup>
import { computed } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'

// 1. 通过 props 接收父组件传来的 markdown 字符串
const props = defineProps({
  markdownContent: {
    type: String,
    required: true,
    default: '',
  },
})

// 2. 初始化 markdown-it 的逻辑保持不变
const md = new MarkdownIt({
  highlight: function (str, lang) {
    if (lang && hljs.getLanguage(lang)) {
      try {
        return (
          '<pre class="hljs"><code>' +
          hljs.highlight(str, { language: lang, ignoreIllegals: true }).value +
          '</code></pre>'
        )
      } catch (e) {
        console.log(e)
      }
    }
    return '<pre class="hljs"><code>' + md.utils.escapeHtml(str) + '</code></pre>'
  },
})

// 3. 使用 computed 来确保 props 更新时，HTML 也跟着更新
const htmlContent = computed(() => md.render(props.markdownContent))
</script>

<template>
  <div class="learning-note" v-html="htmlContent"></div>
</template>

<style scoped>
/* 样式保持不变 */
.learning-note {
  background-color: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  line-height: 1.7;
}
.learning-note :deep(h3) {
  border-bottom: 2px solid #eee;
  padding-bottom: 0.5rem;
  margin-top: 2rem;
  margin-bottom: 1rem;
}
.learning-note :deep(h4) {
  margin-top: 1.5rem;
  margin-bottom: 1rem;
}
.learning-note :deep(code) {
  background-color: #f4f4f4;
  padding: 0.2em 0.4em;
  border-radius: 3px;
  font-size: 85%;
}
.learning-note :deep(pre.hljs) {
  background-color: #282c34;
  color: #abb2bf;
  padding: 1em;
  border-radius: 5px;
  overflow-x: auto;
}
.learning-note :deep(pre.hljs code) {
  background-color: transparent;
  padding: 0;
}
</style>
