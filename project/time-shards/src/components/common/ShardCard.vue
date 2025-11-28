<template>
  <div class="shard-card">
    <div class="card-content">
      <h3 class="card-title">{{ article.title }}</h3>
      <p class="card-summary">{{ article.summary }}</p>
      <div class="card-meta">
        <span class="date">{{ formatDate(article.createTime) }}</span>
        <span class="category" v-if="article.category">{{ article.category }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  article: {
    type: Object,
    required: true
  }
});

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  return new Date(dateStr).toLocaleDateString();
};
</script>

<style scoped>
.shard-card {
  background-color: var(--color-container-bg);
  border: 1px solid transparent;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.shard-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  border-color: var(--color-accent-mystic);
}

[data-theme='dark'] .shard-card:hover {
  box-shadow: 0 0 15px var(--color-accent-mystic);
}

.card-title {
  margin: 0 0 0.5rem 0;
  font-size: 1.25rem;
  color: var(--color-text-main);
}

.card-summary {
  font-size: 0.9rem;
  color: var(--color-text-sub);
  line-height: 1.5;
  margin-bottom: 1rem;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.8rem;
  color: var(--color-text-sub);
  font-family: var(--font-mono);
}

.category {
  color: var(--color-accent-rational);
  font-weight: bold;
}
</style>
