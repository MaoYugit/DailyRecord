<template>
  <div class="attachments-page">
    <div class="page-header">
      <h1 class="page-title">附件管理</h1>
      <p class="page-subtitle">管理所有上传的文件资源</p>
    </div>

    <div class="upload-section">
      <div 
        class="drop-zone" 
        @dragover.prevent="dragOver = true" 
        @dragleave.prevent="dragOver = false"
        @drop.prevent="handleDrop"
        :class="{ 'is-dragover': dragOver }"
        @click="triggerFileInput"
      >
        <div class="upload-icon">☁️</div>
        <p>点击或拖拽文件到此处上传</p>
        <input 
          type="file" 
          ref="fileInput" 
          class="hidden-input" 
          @change="handleFileSelect" 
          multiple
        />
      </div>
    </div>

    <div class="attachments-grid" v-if="attachments.length > 0">
      <div 
        v-for="(file, index) in attachments" 
        :key="file.id" 
        class="attachment-card"
      >
        <div class="file-preview">
          <img v-if="isImage(file)" :src="file.url" :alt="file.name" loading="lazy" />
          <div v-else class="file-icon">{{ getFileIcon(file) }}</div>
        </div>
        
        <div class="file-info">
          <h3 class="file-name" :title="file.name">{{ file.name }}</h3>
          <div class="file-meta">
            <span>{{ formatSize(file.size) }}</span>
            <span>{{ formatDate(file.createTime) }}</span>
          </div>
        </div>

        <div class="file-actions">
          <button class="action-btn copy" @click="copyUrl(file.url)" title="复制链接">📋</button>
          <button class="action-btn delete" @click="handleDelete(file.id)" title="删除文件">🗑️</button>
        </div>
      </div>
    </div>

    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="!loading && attachments.length === 0" class="empty">暂无附件</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAttachments, uploadFile, deleteAttachment } from '@/api/attachment';
import { useUserStore } from '@/stores/user';
import gsap from 'gsap';

const userStore = useUserStore();
const attachments = ref([]);
const loading = ref(true);
const dragOver = ref(false);
const fileInput = ref(null);

const loadAttachments = async () => {
  if (!userStore.user?.id) return;
  
  try {
    const res = await getAttachments(userStore.user.id);
    attachments.value = res || [];
    
    // 动画
    if (attachments.value.length > 0) {
      setTimeout(() => {
        gsap.from('.attachment-card', {
          opacity: 0,
          scale: 0.9,
          duration: 0.5,
          stagger: 0.05,
          ease: 'back.out(1.2)'
        });
      }, 100);
    }
  } catch (error) {
    console.error('Failed to load attachments:', error);
  } finally {
    loading.value = false;
  }
};

const handleUpload = async (file) => {
  if (!userStore.user?.id) return;
  
  try {
    const res = await uploadFile(file, userStore.user.id);
    // 假设返回的是新上传的文件对象
    attachments.value.unshift(res);
    alert('上传成功');
  } catch (error) {
    console.error('Upload failed:', error);
    alert('上传失败');
  }
};

const handleFileSelect = (e) => {
  const files = e.target.files;
  if (files.length > 0) {
    Array.from(files).forEach(file => handleUpload(file));
  }
};

const handleDrop = (e) => {
  dragOver.value = false;
  const files = e.dataTransfer.files;
  if (files.length > 0) {
    Array.from(files).forEach(file => handleUpload(file));
  }
};

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleDelete = async (id) => {
  if (!confirm('确定要删除这个文件吗？')) return;
  
  try {
    await deleteAttachment(id);
    attachments.value = attachments.value.filter(f => f.id !== id);
  } catch (error) {
    console.error('Delete failed:', error);
    alert('删除失败');
  }
};

const copyUrl = (url) => {
  navigator.clipboard.writeText(url).then(() => {
    alert('链接已复制');
  });
};

const isImage = (file) => {
  return file.type?.startsWith('image/') || /\.(jpg|jpeg|png|gif|webp)$/i.test(file.name);
};

const getFileIcon = (file) => {
  if (file.type?.includes('pdf')) return '📄';
  if (file.type?.includes('video')) return '🎬';
  if (file.type?.includes('audio')) return '🎵';
  return '📦';
};

const formatSize = (bytes) => {
  if (bytes === 0) return '0 B';
  const k = 1024;
  const sizes = ['B', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString();
};

onMounted(() => {
  loadAttachments();
});
</script>

<style scoped>
.attachments-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 3rem;
}

.page-title {
  font-size: 2.5rem;
  color: #00f3ff;
  margin-bottom: 0.5rem;
}

.page-subtitle {
  color: rgba(255, 255, 255, 0.6);
}

.upload-section {
  margin-bottom: 3rem;
}

.drop-zone {
  border: 2px dashed rgba(0, 243, 255, 0.3);
  border-radius: 16px;
  padding: 3rem;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  background: rgba(20, 20, 40, 0.3);
}

.drop-zone:hover,
.drop-zone.is-dragover {
  border-color: #00f3ff;
  background: rgba(0, 243, 255, 0.1);
}

.upload-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.hidden-input {
  display: none;
}

.attachments-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1.5rem;
}

.attachment-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  position: relative;
}

.attachment-card:hover {
  transform: translateY(-5px);
  border-color: #00f3ff;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.file-preview {
  height: 150px;
  background: rgba(0, 0, 0, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.file-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.file-icon {
  font-size: 3rem;
}

.file-info {
  padding: 1rem;
}

.file-name {
  font-size: 0.95rem;
  color: white;
  margin-bottom: 0.5rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.file-meta {
  display: flex;
  justify-content: space-between;
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.5);
}

.file-actions {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  gap: 0.5rem;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.attachment-card:hover .file-actions {
  opacity: 1;
}

.action-btn {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: none;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  transition: all 0.2s ease;
}

.action-btn:hover {
  transform: scale(1.1);
}

.action-btn.copy:hover {
  background: #00f3ff;
  color: black;
}

.action-btn.delete:hover {
  background: #ff4d4d;
}

.loading,
.empty {
  text-align: center;
  padding: 3rem;
  color: rgba(255, 255, 255, 0.5);
}
</style>
