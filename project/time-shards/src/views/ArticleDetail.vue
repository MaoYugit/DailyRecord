<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useArticleStore } from '@/stores/article';
import { renderMarkdown } from '@/utils/markdown';
import CommentSection from '@/components/comment/CommentSection.vue';
import gsap from 'gsap';

const route = useRoute();
const articleStore = useArticleStore();
const article = ref(null);
const loading = ref(true);

const renderedContent = computed(() => {
  return article.value ? renderMarkdown(article.value.content) : '';
});

onMounted(async () => {
  const id = route.params.id;
  if (id) {
    article.value = await articleStore.fetchArticleById(id);
    loading.value = false;
    
    // Animate content entry
    gsap.from('.article-content', {
      opacity: 0,
      y: 20,
      duration: 0.8,
      delay: 0.2,
      ease: 'power2.out'
    });
  }
});
</script>

<template>
  <div class="article-detail-container">
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="article" class="article-wrapper">
      <header class="article-header">
        <h1 class="title">{{ article.title }}</h1>
        <div class="meta">
          <span>{{ new Date(article.createTime).toLocaleDateString() }}</span>
          <span v-if="article.category" class="category">{{ article.category }}</span>
        </div>
      </header>
      
      <div class="article-content markdown-body" v-html="renderedContent"></div>
      
      <CommentSection :articleId="article.id" />
    </div>
    <div v-else class="not-found">Article not found.</div>
  </div>
</template>

<style scoped>
.article-detail-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.article-header {
  margin-bottom: 2rem;
  border-bottom: 1px solid var(--color-border);
  padding-bottom: 1rem;
}

.title {
  font-size: 2.5rem;
  color: var(--color-text-main);
  margin-bottom: 0.5rem;
}

.meta {
  color: var(--color-text-sub);
  font-family: var(--font-mono);
  font-size: 0.9rem;
}

.category {
  margin-left: 1rem;
  color: var(--color-accent-rational);
}

.article-content {
  line-height: 1.8;
  color: var(--color-text-main);
  font-size: 1.1rem;
}

/* Add some basic markdown styles if not imported globally */
:deep(.markdown-body h2) {
  margin-top: 2rem;
  margin-bottom: 1rem;
  border-bottom: 1px solid var(--color-border-subtle);
  padding-bottom: 0.5rem;
}

:deep(.markdown-body p) {
  margin-bottom: 1.5rem;
}

:deep(.markdown-body code) {
  background: rgba(255, 255, 255, 0.1);
  padding: 0.2rem 0.4rem;
  border-radius: 3px;
}

:deep(.markdown-body pre) {
  background: #1e1e1e;
  padding: 1rem;
  border-radius: 8px;
  overflow-x: auto;
}
</style>
