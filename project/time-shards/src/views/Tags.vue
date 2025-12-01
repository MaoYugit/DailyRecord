<template>
  <div class="tags-page">
    <div class="page-header">
      <h1 class="page-title">标签云</h1>
      <p class="page-subtitle">在三维空间中探索标签</p>
    </div>

    <!-- 3D标签云容器 -->
    <div class="tag-cloud-3d" ref="cloudContainer"></div>

    <!-- 热门标签列表 -->
    <div class="hot-tags-section" v-if="hotTags.length > 0">
      <h2 class="section-title">🔥 热门标签</h2>
      <div class="hot-tags-list">
        <button 
          v-for="tag in hotTags" 
          :key="tag.id"
          class="hot-tag-btn"
          @click="goToTag(tag)"
        >
          <span class="tag-name">{{ tag.name }}</span>
          <span class="tag-count">{{ tag.articleCount || 0 }}</span>
        </button>
      </div>
    </div>

    <!-- 所有标签网格 -->
    <div class="all-tags-section" v-if="allTags.length > 0">
      <h2 class="section-title">📌 所有标签</h2>
      <div class="tags-grid">
        <button 
          v-for="tag in allTags" 
          :key="tag.id"
          class="tag-item"
          :style="getTagStyle(tag)"
          @click="goToTag(tag)"
        >
          {{ tag.name }}
        </button>
      </div>
    </div>

    <div v-if="loading" class="loading">加载中...</div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router';
import { getTags, getHotTags } from '@/api/tag';
import * as THREE from 'three';
import gsap from 'gsap';

const router = useRouter();
const cloudContainer = ref(null);
const allTags = ref([]);
const hotTags = ref([]);
const loading = ref(true);

let scene, camera, renderer, tagMeshes = [];
let mouseX = 0, mouseY = 0;

const loadTags = async () => {
  try {
    const [allRes, hotRes] = await Promise.all([
      getTags(),
      getHotTags(10)
    ]);
    
    allTags.value = allRes || [];
    hotTags.value = hotRes || [];
    
    if (allTags.value.length > 0) {
      initTagCloud3D();
    }
    
    // 动画
    setTimeout(() => {
      gsap.from('.hot-tag-btn', {
        opacity: 0,
        scale: 0.8,
        duration: 0.5,
        stagger: 0.1,
        ease: 'back.out(1.7)'
      });
    }, 100);
  } catch (error) {
    console.error('Failed to load tags:', error);
  } finally {
    loading.value = false;
  }
};

const initTagCloud3D = () => {
  if (!cloudContainer.value) return;

  scene = new THREE.Scene();
  camera = new THREE.PerspectiveCamera(75, cloudContainer.value.clientWidth / 400, 0.1, 1000);
  camera.position.z = 300;

  renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
  renderer.setSize(cloudContainer.value.clientWidth, 400);
  renderer.setClearColor(0x000000, 0);
  cloudContainer.value.appendChild(renderer.domElement);

  // 创建标签球
  const radius = 150;
  const tagCount = Math.min(allTags.value.length, 30);
  
  for (let i = 0; i < tagCount; i++) {
    const tag = allTags.value[i];
    
    // 使用球面坐标均匀分布
    const phi = Math.acos(-1 + (2 * i) / tagCount);
    const theta = Math.sqrt(tagCount * Math.PI) * phi;
    
    const x = radius * Math.cos(theta) * Math.sin(phi);
    const y = radius * Math.sin(theta) * Math.sin(phi);
    const z = radius * Math.cos(phi);

    // 创建文字精灵
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    canvas.width = 256;
    canvas.height = 64;
    
    context.fillStyle = '#00f3ff';
    context.font = 'bold 24px Arial';
    context.textAlign = 'center';
    context.textBaseline = 'middle';
    context.fillText(tag.name, 128, 32);
    
    const texture = new THREE.CanvasTexture(canvas);
    const material = new THREE.SpriteMaterial({ map: texture, transparent: true });
    const sprite = new THREE.Sprite(material);
    
    sprite.position.set(x, y, z);
    sprite.scale.set(50, 12.5, 1);
    sprite.userData = { tag };
    
    scene.add(sprite);
    tagMeshes.push(sprite);
  }

  animate3D();
};

const animate3D = () => {
  requestAnimationFrame(animate3D);
  
  // 自动旋转
  tagMeshes.forEach(mesh => {
    mesh.position.applyAxisAngle(new THREE.Vector3(0, 1, 0), 0.001);
  });
  
  // 鼠标交互
  camera.position.x += (mouseX * 0.1 - camera.position.x) * 0.05;
  camera.position.y += (-mouseY * 0.1 - camera.position.y) * 0.05;
  camera.lookAt(scene.position);
  
  renderer.render(scene, camera);
};

const onMouseMove = (event) => {
  mouseX = (event.clientX / window.innerWidth) * 2 - 1;
  mouseY = (event.clientY / window.innerHeight) * 2 - 1;
};

const getTagStyle = (tag) => {
  const count = tag.articleCount || 0;
  const fontSize = Math.min(0.8 + count * 0.1, 2);
  return {
    fontSize: `${fontSize}rem`
  };
};

const goToTag = (tag) => {
  router.push(`/tag/${tag.slug || tag.id}`);
};

onMounted(() => {
  loadTags();
  window.addEventListener('mousemove', onMouseMove);
});

onBeforeUnmount(() => {
  window.removeEventListener('mousemove', onMouseMove);
  if (renderer) {
    renderer.dispose();
  }
});
</script>

<style scoped>
.tags-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 4rem 2rem;
  min-height: 100vh;
}

.page-header {
  text-align: center;
  margin-bottom: 3rem;
}

.page-title {
  font-size: 3.5rem;
  font-weight: bold;
  background: linear-gradient(135deg, #00f3ff, #ff00de);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 1rem;
}

.page-subtitle {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.7);
}

.tag-cloud-3d {
  width: 100%;
  height: 400px;
  margin-bottom: 4rem;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(20, 20, 40, 0.3);
  border-radius: 16px;
  border: 1px solid rgba(0, 243, 255, 0.2);
  overflow: hidden;
}

.hot-tags-section,
.all-tags-section {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.8rem;
  color: #00f3ff;
  margin-bottom: 1.5rem;
  text-shadow: 0 0 10px rgba(0, 243, 255, 0.5);
}

.hot-tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.hot-tag-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, rgba(0, 243, 255, 0.2), rgba(255, 0, 222, 0.2));
  border: 1px solid rgba(0, 243, 255, 0.5);
  border-radius: 24px;
  color: white;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 1rem;
}

.hot-tag-btn:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 243, 255, 0.4);
  border-color: #00f3ff;
}

.tag-name {
  font-weight: 600;
}

.tag-count {
  background: rgba(255, 255, 255, 0.2);
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.85rem;
}

.tags-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  align-items: center;
}

.tag-item {
  padding: 0.5rem 1.25rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  color: rgba(255, 255, 255, 0.9);
  cursor: pointer;
  transition: all 0.3s ease;
}

.tag-item:hover {
  background: rgba(0, 243, 255, 0.2);
  border-color: #00f3ff;
  transform: scale(1.1);
}

.loading {
  text-align: center;
  padding: 4rem;
  color: rgba(255, 255, 255, 0.5);
  font-size: 1.2rem;
}

@media (max-width: 768px) {
  .page-title {
    font-size: 2.5rem;
  }
  
  .tag-cloud-3d {
    height: 300px;
  }
}
</style>
