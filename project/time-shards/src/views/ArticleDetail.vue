<script setup>
import { onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useArticleStore } from "@/stores/article";
import { renderMarkdown } from "@/utils/markdown";
import CommentSection from "@/components/comment/CommentSection.vue";
import gsap from "gsap";

const route = useRoute();
const articleStore = useArticleStore();
const article = computed(() => articleStore.currentArticle);
const loading = computed(() => articleStore.loading);
const error = computed(() => articleStore.error);

const renderedContent = computed(() => {
  return article.value ? renderMarkdown(article.value.content) : "";
});

onMounted(async () => {
  await articleStore.fetchArticleById(route.params.id);

  if (article.value) {
    gsap.from(".article-header", {
      y: -50,
      opacity: 0,
      duration: 0.8,
      ease: "power3.out",
    });

    gsap.from(".article-content", {
      y: 50,
      opacity: 0,
      duration: 0.8,
      delay: 0.2,
      ease: "power3.out",
    });
  }
});
</script>

<template>
  <div class="article-container">
    <div v-if="loading" class="loading">Loading shard...</div>
    <div v-else-if="error" class="error">Shard not found or corrupted.</div>
    <div v-else-if="article" class="article-detail">
      <header class="article-header">
        <h1 class="title">{{ article.title }}</h1>
        <div class="meta">
          <span class="date">{{
            new Date(article.create_time).toLocaleDateString()
          }}</span>
          <span class="author" v-if="article.user_id"
            >Author ID: {{ article.user_id }}</span
          >
          <!-- Add category/tags here if available in article object -->
        </div>
        <div v-if="article.cover_image" class="cover-image">
          <img :src="article.cover_image" alt="Cover" />
        </div>
      </header>

      <div class="article-content markdown-body" v-html="renderedContent"></div>

      <CommentSection :articleId="article.id" />
    </div>
  </div>
</template>

<style scoped>
.article-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.loading,
.error {
  text-align: center;
  margin-top: 4rem;
  font-family: var(--font-mono);
  color: var(--color-text-sub);
}

.article-header {
  margin-bottom: 3rem;
  text-align: center;
}

.title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: var(--color-text-main);
  text-shadow: 0 0 10px rgba(0, 243, 255, 0.3);
}

.meta {
  color: var(--color-text-sub);
  font-family: var(--font-mono);
  font-size: 0.9rem;
  margin-bottom: 2rem;
}

.cover-image img {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
  border-radius: 12px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
}

.article-content {
  line-height: 1.8;
  color: var(--color-text-main);
  font-size: 1.1rem;
}

/* Markdown Styles Override */
:deep(.markdown-body h1),
:deep(.markdown-body h2),
:deep(.markdown-body h3) {
  color: var(--color-accent-mystic);
  margin-top: 2rem;
  margin-bottom: 1rem;
}

:deep(.markdown-body p) {
  margin-bottom: 1.5rem;
}

:deep(.markdown-body pre) {
  background: #1e1e1e;
  padding: 1rem;
  border-radius: 8px;
  overflow-x: auto;
  margin-bottom: 1.5rem;
}

:deep(.markdown-body code) {
  font-family: "Fira Code", monospace;
}

:deep(.markdown-body blockquote) {
  border-left: 4px solid var(--color-accent-rational);
  padding-left: 1rem;
  color: var(--color-text-sub);
  font-style: italic;
}

:deep(.markdown-body img) {
  max-width: 100%;
  border-radius: 8px;
}
</style>
