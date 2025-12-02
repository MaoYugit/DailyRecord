<template>
  <div class="about-page">
    <div class="page-header">
      <h1 class="page-title">关于</h1>
      <div class="tab-switcher">
        <button 
          class="tab-btn" 
          :class="{ active: activeTab === 'site' }"
          @click="activeTab = 'site'"
        >
          关于网站
        </button>
        <button 
          class="tab-btn" 
          :class="{ active: activeTab === 'resume' }"
          @click="activeTab = 'resume'"
        >
          个人简历
        </button>
      </div>
    </div>

    <div class="content-container" v-if="!loading">
      <transition name="fade" mode="out-in">
        <div v-if="activeTab === 'site'" key="site" class="tab-content site-intro">
          <div class="markdown-body" v-html="renderMarkdown(configs.site_intro || '暂无网站介绍')"></div>
        </div>
        
        <div v-else key="resume" class="tab-content resume">
          <div class="markdown-body" v-html="renderMarkdown(configs.resume || '暂无个人简历')"></div>
        </div>
      </transition>
    </div>

    <div v-if="loading" class="loading">加载中...</div>
    
    <!-- 管理员编辑入口 (仅演示，实际应在后台管理) -->
    <div v-if="userStore.user?.role == 1" class="admin-actions">
      <button class="edit-btn" @click="showEditModal = true">编辑内容</button>
    </div>

    <!-- 编辑模态框 -->
    <div v-if="showEditModal" class="modal-overlay" @click.self="showEditModal = false">
      <div class="modal-content">
        <h3>编辑{{ activeTab === 'site' ? '网站介绍' : '个人简历' }}</h3>
        <textarea 
          v-model="editContent" 
          rows="15" 
          placeholder="支持 Markdown 格式..."
        ></textarea>
        <div class="modal-actions">
          <button class="cancel-btn" @click="showEditModal = false">取消</button>
          <button class="save-btn" @click="handleSave" :disabled="saving">
            {{ saving ? '保存中...' : '保存' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { getConfigs, updateConfigs } from '@/api/config';
import { useUserStore } from '@/stores/user';
import MarkdownIt from 'markdown-it';
import gsap from 'gsap';

const userStore = useUserStore();
const activeTab = ref('site');
const configs = ref({});
const loading = ref(true);
const showEditModal = ref(false);
const editContent = ref('');
const saving = ref(false);

// 配置 Markdown 解析器
const md = new MarkdownIt({
  breaks: true,
  html: true
});

const renderMarkdown = (text) => {
  return md.render(text || '');
};

const loadConfigs = async () => {
  try {
    const res = await getConfigs();
    // 假设后端返回的是对象 { config_key: config_value, ... }
    // 如果返回的是数组，需要转换
    if (Array.isArray(res)) {
      configs.value = res.reduce((acc, curr) => {
        acc[curr.config_key] = curr.config_value;
        return acc;
      }, {});
    } else {
      configs.value = res || {};
    }
    
    // 动画
    setTimeout(() => {
      gsap.from('.tab-content', {
        opacity: 0,
        y: 20,
        duration: 0.5,
        ease: 'power2.out'
      });
    }, 100);
  } catch (error) {
    console.error('Failed to load configs:', error);
  } finally {
    loading.value = false;
  }
};

// 监听 Tab 切换，更新编辑内容
watch([activeTab, showEditModal], () => {
  if (showEditModal.value) {
    const key = activeTab.value === 'site' ? 'site_intro' : 'resume';
    editContent.value = configs.value[key] || '';
  }
});

const handleSave = async () => {
  saving.value = true;
  const key = activeTab.value === 'site' ? 'site_intro' : 'resume';
  
  try {
    await updateConfigs({
      [key]: editContent.value
    });
    
    configs.value[key] = editContent.value;
    showEditModal.value = false;
    alert('保存成功');
  } catch (error) {
    console.error('Save failed:', error);
    alert('保存失败');
  } finally {
    saving.value = false;
  }
};

onMounted(() => {
  loadConfigs();
});
</script>

<style scoped>
.about-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 3rem;
}

.page-title {
  font-size: 3rem;
  color: #00f3ff;
  margin-bottom: 2rem;
  text-shadow: 0 0 15px rgba(0, 243, 255, 0.5);
}

.tab-switcher {
  display: inline-flex;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 30px;
  padding: 5px;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.tab-btn {
  padding: 0.8rem 2rem;
  border-radius: 25px;
  border: none;
  background: transparent;
  color: rgba(255, 255, 255, 0.7);
  font-size: 1.1rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-btn.active {
  background: #00f3ff;
  color: #000;
  font-weight: bold;
  box-shadow: 0 0 15px rgba(0, 243, 255, 0.4);
}

.content-container {
  background: rgba(20, 20, 40, 0.6);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(0, 243, 255, 0.2);
  border-radius: 16px;
  padding: 3rem;
  min-height: 400px;
}

.markdown-body {
  color: rgba(255, 255, 255, 0.9);
  line-height: 1.8;
}

/* Markdown 样式适配 */
:deep(.markdown-body h1),
:deep(.markdown-body h2),
:deep(.markdown-body h3) {
  color: #00f3ff;
  margin-top: 1.5rem;
  margin-bottom: 1rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 0.5rem;
}

:deep(.markdown-body p) {
  margin-bottom: 1rem;
}

:deep(.markdown-body ul),
:deep(.markdown-body ol) {
  padding-left: 1.5rem;
  margin-bottom: 1rem;
}

:deep(.markdown-body li) {
  margin-bottom: 0.5rem;
}

:deep(.markdown-body a) {
  color: #ff00de;
  text-decoration: none;
}

:deep(.markdown-body a:hover) {
  text-decoration: underline;
}

:deep(.markdown-body blockquote) {
  border-left: 4px solid #00f3ff;
  padding-left: 1rem;
  color: rgba(255, 255, 255, 0.6);
  margin: 1rem 0;
}

/* Admin Actions */
.admin-actions {
  margin-top: 2rem;
  text-align: center;
}

.edit-btn {
  padding: 0.8rem 2rem;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.edit-btn:hover {
  border-color: #00f3ff;
  color: #00f3ff;
}

/* Modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  backdrop-filter: blur(5px);
}

.modal-content {
  background: #1a1a2e;
  border: 1px solid #00f3ff;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 800px;
  box-shadow: 0 0 30px rgba(0, 243, 255, 0.2);
}

.modal-content h3 {
  color: #00f3ff;
  margin-bottom: 1.5rem;
  text-align: center;
}

textarea {
  width: 100%;
  padding: 1rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  color: white;
  font-family: monospace;
  font-size: 1rem;
  resize: vertical;
  outline: none;
}

textarea:focus {
  border-color: #00f3ff;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 1.5rem;
}

.cancel-btn,
.save-btn {
  padding: 0.6rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
}

.cancel-btn {
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: white;
}

.save-btn {
  background: #00f3ff;
  border: none;
  color: #000;
}

.save-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  padding: 4rem;
  color: rgba(255, 255, 255, 0.5);
}

/* Transitions */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
