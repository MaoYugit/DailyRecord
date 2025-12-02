<template>
  <div class="profile-page">
    <div v-if="loading" class="loading">加载中...</div>
    
    <div v-else-if="user" class="profile-container">
      <!-- 个人信息卡片 -->
      <div class="profile-card">
        <div class="avatar-section">
          <div class="avatar-wrapper">
            <img :src="user.avatar || defaultAvatar" alt="Avatar" class="avatar-img" />
            <div class="avatar-glow"></div>
          </div>
          <h2 class="username">{{ user.nickname || user.username }}</h2>
          <p class="user-role">{{ user.role === 'admin' ? '管理员' : '普通用户' }}</p>
        </div>

        <div class="info-section">
          <div class="info-group">
            <label>用户名</label>
            <div class="info-value">{{ user.username }}</div>
          </div>
          <div class="info-group">
            <label>邮箱</label>
            <div class="info-value">{{ user.email || '未设置' }}</div>
          </div>
          <div class="info-group">
            <label>注册时间</label>
            <div class="info-value">{{ formatDate(user.createTime) }}</div>
          </div>
          <div class="info-group">
            <label>个人简介</label>
            <div class="info-value bio">{{ user.bio || '这个人很懒，什么都没写...' }}</div>
          </div>
          
          <div class="action-buttons">
            <button class="edit-btn" @click="showEditModal = true">编辑资料</button>
            <button class="attach-btn" @click="router.push('/attachments')">管理附件</button>
          </div>
        </div>
      </div>

      <!-- 编辑模态框 -->
      <div v-if="showEditModal" class="modal-overlay" @click.self="showEditModal = false">
        <div class="modal-content">
          <h3>编辑个人资料</h3>
          <form @submit.prevent="handleUpdate">
            <div class="form-group">
              <label>昵称</label>
              <input v-model="editForm.nickname" type="text" />
            </div>
            <div class="form-group">
              <label>邮箱</label>
              <input v-model="editForm.email" type="email" />
            </div>
            <div class="form-group">
              <label>头像URL</label>
              <input v-model="editForm.avatar" type="text" placeholder="输入图片地址" />
            </div>
            <div class="form-group">
              <label>个人简介</label>
              <textarea v-model="editForm.bio" rows="4"></textarea>
            </div>
            
            <div class="modal-actions">
              <button type="button" class="cancel-btn" @click="showEditModal = false">取消</button>
              <button type="submit" class="save-btn" :disabled="updating">
                {{ updating ? '保存中...' : '保存修改' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <div v-else class="error-state">
      <p>未登录或无法获取用户信息</p>
      <button @click="router.push('/login')">去登录</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { getUserById, updateUser } from '@/api/user';
import gsap from 'gsap';

const router = useRouter();
const userStore = useUserStore();

const user = ref(null);
const loading = ref(true);
const showEditModal = ref(false);
const updating = ref(false);
const defaultAvatar = 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix';

const editForm = reactive({
  nickname: '',
  email: '',
  avatar: '',
  bio: ''
});

const loadUserProfile = async () => {
  // 假设当前登录用户的ID存储在store中，或者通过token获取
  // 这里简化处理，如果store中有user信息则直接使用，否则尝试获取
  if (!userStore.token) {
    router.push('/login');
    return;
  }

  try {
    // 这里假设有一个获取当前用户信息的接口，或者我们知道当前用户的ID
    // 暂时用 store 中的信息，如果 store 中只有基本信息，可以调用 getUserById
    // 由于 api/user.js 中 getUserById 需要 id，我们假设 store 中有 id
    if (userStore.user && userStore.user.id) {
      const res = await getUserById(userStore.user.id);
      user.value = res.data || res; // 根据实际返回结构调整
    } else {
      // 如果没有 ID，可能需要先调用一个 /me 接口，或者直接用 store 的信息
      user.value = userStore.user;
    }

    // 初始化编辑表单
    if (user.value) {
      editForm.nickname = user.value.nickname || '';
      editForm.email = user.value.email || '';
      editForm.avatar = user.value.avatar || '';
      editForm.bio = user.value.bio || '';
      
      // 动画
      setTimeout(() => {
        gsap.from('.profile-card', {
          y: 50,
          opacity: 0,
          duration: 0.8,
          ease: 'power3.out'
        });
      }, 100);
    }
  } catch (error) {
    console.error('Failed to load profile:', error);
  } finally {
    loading.value = false;
  }
};

const handleUpdate = async () => {
  if (!user.value) return;
  
  updating.value = true;
  try {
    const updateData = {
      id: user.value.id,
      ...editForm
    };
    
    await updateUser(updateData);
    
    // 更新本地显示
    user.value = { ...user.value, ...updateData };
    // 更新 store
    userStore.user = user.value;
    
    showEditModal.value = false;
    alert('修改成功！');
  } catch (error) {
    console.error('Update failed:', error);
    alert('修改失败，请重试');
  } finally {
    updating.value = false;
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return '未知';
  return new Date(dateStr).toLocaleDateString();
};

onMounted(() => {
  loadUserProfile();
});
</script>

<style scoped>
.profile-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.profile-card {
  background: rgba(20, 20, 40, 0.6);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(0, 243, 255, 0.3);
  border-radius: 24px;
  padding: 3rem;
  display: flex;
  gap: 3rem;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.5);
  position: relative;
  overflow: hidden;
}

.profile-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 5px;
  background: linear-gradient(90deg, #00f3ff, #ff00de);
}

.avatar-section {
  text-align: center;
  flex-shrink: 0;
}

.avatar-wrapper {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin: 0 auto 1.5rem;
  position: relative;
  padding: 5px;
  background: linear-gradient(135deg, #00f3ff, #ff00de);
}

.avatar-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 4px solid #1a1a2e;
  background: #1a1a2e;
}

.username {
  font-size: 1.8rem;
  color: white;
  margin-bottom: 0.5rem;
  font-weight: bold;
}

.user-role {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  background: rgba(0, 243, 255, 0.2);
  color: #00f3ff;
  border-radius: 12px;
  font-size: 0.9rem;
}

.info-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.info-group label {
  display: block;
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.info-value {
  font-size: 1.1rem;
  color: white;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.info-value.bio {
  line-height: 1.6;
  border-bottom: none;
  background: rgba(255, 255, 255, 0.05);
  padding: 1rem;
  border-radius: 8px;
}

.action-buttons {
  margin-top: auto;
  display: flex;
  gap: 1rem;
}

.edit-btn,
.attach-btn {
  padding: 0.75rem 2rem;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
}

.edit-btn {
  background: linear-gradient(90deg, #00f3ff, #00a8ff);
  color: #000;
}

.attach-btn {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: white;
}

.edit-btn:hover,
.attach-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 243, 255, 0.4);
}

.attach-btn:hover {
  border-color: #00f3ff;
  color: #00f3ff;
}

/* Modal Styles */
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
  max-width: 500px;
  box-shadow: 0 0 30px rgba(0, 243, 255, 0.2);
}

.modal-content h3 {
  color: #00f3ff;
  margin-bottom: 1.5rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1.2rem;
}

.form-group label {
  display: block;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 0.5rem;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.8rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  color: white;
  outline: none;
}

.form-group input:focus,
.form-group textarea:focus {
  border-color: #00f3ff;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

.cancel-btn {
  padding: 0.6rem 1.5rem;
  background: transparent;
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: white;
  border-radius: 6px;
  cursor: pointer;
}

.save-btn {
  padding: 0.6rem 1.5rem;
  background: #00f3ff;
  border: none;
  color: #000;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
}

.save-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .profile-card {
    flex-direction: column;
    padding: 2rem;
    gap: 2rem;
  }
}
</style>
