<template>
  <div class="search-page">
    <div class="search-header">
      <h1 class="page-title">搜索</h1>
      <div class="search-box-container">
        <input 
          v-model="searchKeyword" 
          @keyup.enter="handleSearch"
          type="text" 
          class="search-input" 
          placeholder="输入关键词搜索..."
          autofocus
        />
        <button class="search-btn" @click="handleSearch">
          🔍
        </button>
      </div>
    </div>

    <div class="search-results" v-if="hasSearched">
      <div v-if="loading" class="loading">搜索中...</div>
      
      <div v-else-if="articles.length > 0" class="results-list">
        <p class="results-count">找到 {{ articles.length }} 篇关于 "{{ lastKeyword }}" 的文章</p>
        
        <div 
          v-for="(article, index) in articles" 
          :key="article.id"
          class="result-card"
          @click="goToArticle(article.id)"
        >
          <h3 class="result-title" v-html="highlightText(article.title, lastKeyword)"></h3>
          <p class="result-summary" v-html="highlightText(article.summary || article.content.substring(0, 100), lastKeyword)"></p>
          <div class="result-meta">
            <span>📅 {{ formatDate(article.createTime) }}</span>
            <span v-if="article.categoryName">📁 {{ article.categoryName }}</span>
            <span>👁️ {{ article.views || 0 }}</span>
          </div>
        </div>
      </div>
      
      <div v-else class="no-results">
        <p>未找到关于 "{{ lastKeyword }}" 的相关内容</p>
        <div class="suggestions">
          <p>建议：</p>
          <ul>
            <li>检查输入的关键词是否有误</li>
            <li>尝试使用更通用的词汇</li>
            <li>尝试搜索其他关键词</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { getArticles } from '@/api/article';
import gsap from 'gsap';

const router = useRouter();
const route = useRoute();

const searchKeyword = ref('');
const lastKeyword = ref('');
const articles = ref([]);
const loading = ref(false);
const hasSearched = ref(false);

const handleSearch = async () => {
  const keyword = searchKeyword.value.trim();
  if (!keyword) return;
  
  loading.value = true;
  hasSearched.value = true;
  lastKeyword.value = keyword;
  
  // 更新 URL query，但不刷新页面
  router.replace({ query: { q: keyword } });
  
  try {
    const res = await getArticles({ 
      keyword,
      limit: 50 // 搜索结果通常不需要太多分页，或者后续加分页
    });
    articles.value = res.list || [];
    
    // 动画
    if (articles.value.length > 0) {
      setTimeout(() => {
        gsap.from('.result-card', {
          opacity: 0,
          y: 20,
          duration: 0.5,
          stagger: 0.1,
          ease: 'power2.out'
        });
      }, 100);
    }
  } catch (error) {
    console.error('Search failed:', error);
  } finally {
    loading.value = false;
  }
};

const highlightText = (text, keyword) => {
  if (!text || !keyword) return text;
  const regex = new RegExp(`(${keyword})`, 'gi');
  return text.replace(regex, '<span class="highlight">$1</span>');
};

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
};

const goToArticle = (id) => {
  router.push(`/article/${id}`);
};

onMounted(() => {
  // 如果 URL 中有查询参数，自动搜索
  if (route.query.q) {
    searchKeyword.value = route.query.q;
    handleSearch();
  }
});
</script>

<style scoped>
.search-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.search-header {
  text-align: center;
  margin-bottom: 4rem;
}

.page-title {
  font-size: 3rem;
  font-weight: bold;
  color: #00f3ff;
  margin-bottom: 2rem;
  text-shadow: 0 0 15px rgba(0, 243, 255, 0.5);
}

.search-box-container {
  display: flex;
  max-width: 600px;
  margin: 0 auto;
  position: relative;
}

.search-input {
  width: 100%;
  padding: 1rem 1.5rem;
  font-size: 1.2rem;
  background: rgba(20, 20, 40, 0.6);
  border: 2px solid rgba(0, 243, 255, 0.3);
  border-radius: 30px;
  color: white;
  outline: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.search-input:focus {
  border-color: #00f3ff;
  box-shadow: 0 0 20px rgba(0, 243, 255, 0.3);
  background: rgba(20, 20, 40, 0.8);
}

.search-btn {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0.5rem;
  transition: transform 0.2s ease;
}

.search-btn:hover {
  transform: translateY(-50%) scale(1.1);
}

.results-count {
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.results-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.result-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 1.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.result-card:hover {
  background: rgba(0, 243, 255, 0.1);
  border-color: rgba(0, 243, 255, 0.5);
  transform: translateX(10px);
}

.result-title {
  font-size: 1.4rem;
  color: #00f3ff;
  margin-bottom: 0.8rem;
}

.result-summary {
  color: rgba(255, 255, 255, 0.8);
  font-size: 1rem;
  line-height: 1.6;
  margin-bottom: 1rem;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.result-meta {
  display: flex;
  gap: 1.5rem;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
}

:deep(.highlight) {
  color: #ff00de;
  font-weight: bold;
  background: rgba(255, 0, 222, 0.1);
  padding: 0 2px;
  border-radius: 2px;
}

.no-results {
  text-align: center;
  color: rgba(255, 255, 255, 0.6);
  padding: 3rem;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 12px;
}

.suggestions {
  margin-top: 2rem;
  text-align: left;
  display: inline-block;
}

.suggestions ul {
  margin-top: 0.5rem;
  padding-left: 1.5rem;
}

.suggestions li {
  margin-bottom: 0.5rem;
}

.loading {
  text-align: center;
  font-size: 1.2rem;
  color: #00f3ff;
  padding: 2rem;
}
</style>
