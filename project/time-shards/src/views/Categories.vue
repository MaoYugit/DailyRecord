<template>
  <div class="categories-page">
    <div class="page-header">
      <h1 class="page-title glitch" data-text="分类">分类</h1>
      <p class="page-subtitle">探索不同主题的内容碎片</p>
    </div>

    <div class="categories-grid" v-if="!loading">
      <div 
        v-for="(category, index) in categories" 
        :key="category.id" 
        class="category-card"
        :data-index="index"
        @click="goToCategory(category)"
      >
        <div class="card-inner">
          <div class="category-icon">📁</div>
          <h3 class="category-name">{{ category.name }}</h3>
          <p class="category-desc" v-if="category.description">{{ category.description }}</p>
          <div class="category-meta">
            <span class="article-count">{{ category.articleCount || 0 }} 篇文章</span>
          </div>
        </div>
      </div>
    </div>

    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="!loading && categories.length === 0" class="empty">暂无分类</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getCategories } from '@/api/category';
import gsap from 'gsap';

const router = useRouter();
const categories = ref([]);
const loading = ref(true);

const loadCategories = async () => {
  try {
    const res = await getCategories();
    categories.value = res || [];
    
    // GSAP 动画
    setTimeout(() => {
      gsap.from('.category-card', {
        opacity: 0,
        y: 50,
        duration: 0.6,
        stagger: 0.1,
        ease: 'power2.out'
      });
    }, 100);
  } catch (error) {
    console.error('Failed to load categories:', error);
  } finally {
    loading.value = false;
  }
};

const goToCategory = (category) => {
  router.push(`/category/${category.slug || category.id}`);
};

onMounted(() => {
  loadCategories();
});
</script>

<style scoped>
.categories-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 4rem;
}

.page-title {
  font-size: 3.5rem;
  font-weight: bold;
  color: #00f3ff;
  margin-bottom: 1rem;
  text-shadow: 0 0 20px rgba(0, 243, 255, 0.5);
  position: relative;
}

.glitch::before,
.glitch::after {
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.glitch::before {
  left: 2px;
  text-shadow: -2px 0 #ff00de;
  animation: glitch-1 2s infinite;
}

.glitch::after {
  left: -2px;
  text-shadow: 2px 0 #00fff9;
  animation: glitch-2 2s infinite;
}

@keyframes glitch-1 {
  0%, 100% { clip-path: inset(0 0 0 0); }
  20% { clip-path: inset(40% 0 35% 0); }
  40% { clip-path: inset(60% 0 20% 0); }
  60% { clip-path: inset(10% 0 70% 0); }
  80% { clip-path: inset(80% 0 5% 0); }
}

@keyframes glitch-2 {
  0%, 100% { clip-path: inset(0 0 0 0); }
  20% { clip-path: inset(20% 0 60% 0); }
  40% { clip-path: inset(70% 0 10% 0); }
  60% { clip-path: inset(5% 0 80% 0); }
  80% { clip-path: inset(35% 0 40% 0); }
}

.page-subtitle {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.7);
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 2rem;
}

.category-card {
  background: rgba(20, 20, 40, 0.6);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(0, 243, 255, 0.2);
  border-radius: 12px;
  padding: 2rem;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.category-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(0, 243, 255, 0.1), transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.category-card:hover {
  transform: translateY(-8px);
  border-color: #00f3ff;
  box-shadow: 0 8px 32px rgba(0, 243, 255, 0.3);
}

.category-card:hover::before {
  opacity: 1;
}

.card-inner {
  position: relative;
  z-index: 1;
}

.category-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.category-name {
  font-size: 1.5rem;
  font-weight: bold;
  color: #00f3ff;
  margin-bottom: 0.5rem;
}

.category-desc {
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.9rem;
  margin-bottom: 1rem;
  line-height: 1.5;
}

.category-meta {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.article-count {
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.85rem;
  padding: 0.25rem 0.75rem;
  background: rgba(0, 243, 255, 0.1);
  border-radius: 12px;
}

.loading,
.empty {
  text-align: center;
  padding: 4rem;
  color: rgba(255, 255, 255, 0.5);
  font-size: 1.2rem;
}

@media (max-width: 768px) {
  .categories-grid {
    grid-template-columns: 1fr;
  }
  
  .page-title {
    font-size: 2.5rem;
  }
}
</style>
