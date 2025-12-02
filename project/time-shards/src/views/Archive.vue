<template>
  <div class="archive-page">
    <div class="page-header">
      <h1 class="page-title">时间归档</h1>
      <p class="page-subtitle">时间碎片的编年史</p>
    </div>

    <div class="timeline-container" v-if="!loading && timelineData.length > 0">
      <div 
        v-for="(yearGroup, yearIndex) in timelineData" 
        :key="yearGroup.year"
        class="year-group"
        :data-index="yearIndex"
      >
        <div class="year-marker">
          <span class="year-text">{{ yearGroup.year }}</span>
          <span class="year-count">{{ yearGroup.total }} 篇</span>
        </div>

        <div class="months-container">
          <div 
            v-for="monthGroup in yearGroup.months" 
            :key="monthGroup.month"
            class="month-group"
          >
            <div class="month-header">
              <span class="month-text">{{ monthGroup.month }}月</span>
              <span class="month-count">{{ monthGroup.articles.length }} 篇</span>
            </div>

            <div class="articles-list">
              <div 
                v-for="article in monthGroup.articles" 
                :key="article.id"
                class="timeline-article"
                @click="goToArticle(article.id)"
              >
                <div class="article-dot"></div>
                <div class="article-content">
                  <div class="article-date">{{ formatDate(article.createTime) }}</div>
                  <h3 class="article-title">{{ article.title }}</h3>
                  <p class="article-summary" v-if="article.summary">{{ article.summary }}</p>
                  <div class="article-meta">
                    <span class="meta-item" v-if="article.categoryName">
                      📁 {{ article.categoryName }}
                    </span>
                    <span class="meta-item">
                      👁️ {{ article.views || 0 }}
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="!loading && timelineData.length === 0" class="empty">暂无归档</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getArticles } from '@/api/article';
import gsap from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

const router = useRouter();
const timelineData = ref([]);
const loading = ref(true);

const loadArchive = async () => {
  try {
    // 获取所有文章
    const res = await getArticles({ limit: 1000 });
    const articles = res.list || [];
    
    // 按年月分组
    const grouped = {};
    articles.forEach(article => {
      const date = new Date(article.createTime);
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      
      if (!grouped[year]) {
        grouped[year] = {};
      }
      if (!grouped[year][month]) {
        grouped[year][month] = [];
      }
      grouped[year][month].push(article);
    });
    
    // 转换为数组格式
    const timeline = [];
    Object.keys(grouped).sort((a, b) => b - a).forEach(year => {
      const months = [];
      let yearTotal = 0;
      
      Object.keys(grouped[year]).sort((a, b) => b - a).forEach(month => {
        const monthArticles = grouped[year][month];
        months.push({
          month: parseInt(month),
          articles: monthArticles.sort((a, b) => 
            new Date(b.createTime) - new Date(a.createTime)
          )
        });
        yearTotal += monthArticles.length;
      });
      
      timeline.push({
        year: parseInt(year),
        months,
        total: yearTotal
      });
    });
    
    timelineData.value = timeline;
    
    // GSAP 滚动动画
    setTimeout(() => {
      gsap.from('.year-group', {
        scrollTrigger: {
          trigger: '.timeline-container',
          start: 'top 80%',
          toggleActions: 'play none none reverse'
        },
        opacity: 0,
        x: -50,
        duration: 0.8,
        stagger: 0.2,
        ease: 'power2.out'
      });
      
      gsap.from('.timeline-article', {
        scrollTrigger: {
          trigger: '.timeline-container',
          start: 'top 60%',
        },
        opacity: 0,
        y: 30,
        duration: 0.6,
        stagger: 0.05,
        ease: 'power2.out'
      });
    }, 100);
  } catch (error) {
    console.error('Failed to load archive:', error);
  } finally {
    loading.value = false;
  }
};

const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return `${date.getMonth() + 1}月${date.getDate()}日`;
};

const goToArticle = (id) => {
  router.push(`/article/${id}`);
};

onMounted(() => {
  loadArchive();
});
</script>

<style scoped>
.archive-page {
  max-width: 900px;
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
  background: linear-gradient(135deg, #00f3ff, #00ff88);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 1rem;
}

.page-subtitle {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.7);
}

.timeline-container {
  position: relative;
}

.year-group {
  margin-bottom: 4rem;
  position: relative;
}

.year-marker {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid rgba(0, 243, 255, 0.3);
}

.year-text {
  font-size: 2.5rem;
  font-weight: bold;
  color: #00f3ff;
  text-shadow: 0 0 20px rgba(0, 243, 255, 0.5);
}

.year-count {
  padding: 0.25rem 0.75rem;
  background: rgba(0, 243, 255, 0.2);
  border-radius: 12px;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.8);
}

.months-container {
  padding-left: 2rem;
  border-left: 2px solid rgba(0, 243, 255, 0.2);
}

.month-group {
  margin-bottom: 2rem;
}

.month-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.month-text {
  font-size: 1.5rem;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.9);
}

.month-count {
  padding: 0.2rem 0.6rem;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.7);
}

.articles-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.timeline-article {
  position: relative;
  padding-left: 2rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.timeline-article::before {
  content: '';
  position: absolute;
  left: -2px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: transparent;
  transition: background 0.3s ease;
}

.timeline-article:hover::before {
  background: linear-gradient(to bottom, #00f3ff, transparent);
}

.article-dot {
  position: absolute;
  left: -8px;
  top: 8px;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: rgba(0, 243, 255, 0.5);
  border: 2px solid #00f3ff;
  transition: all 0.3s ease;
}

.timeline-article:hover .article-dot {
  transform: scale(1.5);
  box-shadow: 0 0 20px rgba(0, 243, 255, 0.8);
}

.article-content {
  background: rgba(20, 20, 40, 0.4);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 1.5rem;
  transition: all 0.3s ease;
}

.timeline-article:hover .article-content {
  background: rgba(20, 20, 40, 0.6);
  border-color: rgba(0, 243, 255, 0.5);
  transform: translateX(8px);
}

.article-date {
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 0.5rem;
}

.article-title {
  font-size: 1.3rem;
  font-weight: 600;
  color: #00f3ff;
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}

.timeline-article:hover .article-title {
  color: #00ff88;
}

.article-summary {
  font-size: 0.95rem;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.6;
  margin-bottom: 0.75rem;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-meta {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.meta-item {
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.6);
  padding: 0.25rem 0.5rem;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 6px;
}

.loading,
.empty {
  text-align: center;
  padding: 4rem;
  color: rgba(255, 255, 255, 0.5);
  font-size: 1.2rem;
}

@media (max-width: 768px) {
  .page-title {
    font-size: 2.5rem;
  }
  
  .year-text {
    font-size: 2rem;
  }
  
  .months-container {
    padding-left: 1rem;
  }
  
  .timeline-article {
    padding-left: 1.5rem;
  }
}
</style>
