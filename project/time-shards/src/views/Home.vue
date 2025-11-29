<script setup>
import { ref, onMounted } from "vue";
import { useArticleStore } from "../stores/article";
import ShardCard from "../components/common/ShardCard.vue";
import gsap from "gsap";
import { useRouter } from "vue-router";

const articleStore = useArticleStore();
const router = useRouter();
const articles = ref([]);
const page = ref(1);
const loading = ref(false);
const finished = ref(false);

const loadArticles = async () => {
  if (loading.value || finished.value) return;

  loading.value = true;
  try {
    const res = await articleStore.fetchArticles({
      page: page.value,
      limit: 10,
    });
    const newArticles = res.list || [];
    if (newArticles.length === 0) {
      finished.value = true;
    } else {
      articles.value.push(...newArticles);
      page.value++;
    }
  } catch (error) {
    console.error("Failed to load articles:", error);
  } finally {
    loading.value = false;
  }
};

const goToArticle = (id) => {
  router.push(`/article/${id}`);
};

const onEnter = (el, done) => {
  gsap.from(el, {
    opacity: 0,
    y: 50,
    duration: 0.6,
    ease: "power2.out",
    onComplete: done,
    delay: el.dataset.index * 0.1,
  });
};

onMounted(() => {
  loadArticles();
});
</script>

<template>
  <div class="home-container">
    <div class="hero-section">
      <h1 class="glitch-title" data-text="Time Shards">Time Shards</h1>
      <p class="subtitle">Fragments of thought in the digital void.</p>
    </div>

    <div class="articles-list">
      <transition-group appear @enter="onEnter" :css="false">
        <ShardCard
          v-for="(article, index) in articles"
          :key="article.id"
          :article="article"
          :data-index="index % 10"
          @click="goToArticle(article.id)"
        />
      </transition-group>
    </div>

    <div v-if="loading" class="loading-indicator">Loading...</div>
    <div v-if="finished && articles.length > 0" class="end-indicator">
      - End of Transmission -
    </div>
  </div>
</template>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.hero-section {
  text-align: center;
  margin-bottom: 4rem;
  padding-top: 2rem;
}

.glitch-title {
  font-size: 4rem;
  font-weight: bold;
  color: var(--color-text-main);
  position: relative;
  display: inline-block;
  /* Add glitch effect CSS here or use GlitchText component if available */
  /* For now simple styling */
}

.subtitle {
  font-size: 1.2rem;
  color: var(--color-text-sub);
  margin-top: 1rem;
}

.articles-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.loading-indicator,
.end-indicator {
  text-align: center;
  margin-top: 2rem;
  color: var(--color-text-sub);
  font-family: var(--font-mono);
}
</style>
