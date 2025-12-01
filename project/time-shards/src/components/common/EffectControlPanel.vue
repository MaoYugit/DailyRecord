<template>
  <div class="effect-control-panel" :class="{ 'panel-collapsed': collapsed }">
    <!-- 展开/收起按钮 -->
    <button class="toggle-btn" @click="collapsed = !collapsed" :title="collapsed ? '展开控制面板' : '收起控制面板'">
      <span v-if="collapsed">⚙️</span>
      <span v-else>✕</span>
    </button>

    <!-- 控制面板内容 -->
    <div class="panel-content" v-show="!collapsed">
      <h3 class="panel-title">✨ 特效控制中心</h3>
      
      <!-- 鼠标特效控制 -->
      <div class="control-section">
        <h4 class="section-title">🖱️ 鼠标特效</h4>
        <div class="toggle-switch">
          <label class="switch">
            <input type="checkbox" v-model="mouseEffectEnabled" @change="onMouseEffectToggle">
            <span class="slider"></span>
          </label>
          <span class="label-text">{{ mouseEffectEnabled ? '已开启' : '已关闭' }}</span>
        </div>
        
        <div v-if="mouseEffectEnabled" class="effect-types">
          <button 
            v-for="effect in mouseEffects" 
            :key="effect.type"
            class="effect-btn"
            :class="{ active: currentMouseEffect === effect.type }"
            @click="selectMouseEffect(effect.type)"
            :title="effect.description"
          >
            <span class="effect-icon">{{ effect.icon }}</span>
            <span class="effect-name">{{ effect.name }}</span>
          </button>
        </div>
      </div>

      <!-- 背景风格控制 -->
      <div class="control-section">
        <h4 class="section-title">🌌 背景风格</h4>
        <div class="background-types">
          <button 
            v-for="bg in backgrounds" 
            :key="bg.type"
            class="bg-btn"
            :class="{ active: currentBackground === bg.type }"
            @click="selectBackground(bg.type)"
            :title="bg.description"
          >
            <span class="bg-icon">{{ bg.icon }}</span>
            <span class="bg-name">{{ bg.name }}</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';

const emit = defineEmits(['update:mouseEffect', 'update:mouseEffectEnabled', 'update:background']);

const collapsed = ref(false);
const mouseEffectEnabled = ref(true);
const currentMouseEffect = ref('flower');
const currentBackground = ref('shards');

const mouseEffects = [
  { type: 'flower', name: '花朵', icon: '🌸', description: '鼠标移动时绽放花朵' },
  { type: 'firework', name: '烟花', icon: '🎆', description: '点击时爆发烟花' },
  { type: 'star', name: '星光', icon: '✨', description: '鼠标留下星光轨迹' }
];

const backgrounds = [
  { type: 'shards', name: 'Time Shards', icon: '💎', description: '时间碎片 - 科技青色' },
  { type: 'nebula', name: 'Cosmic Nebula', icon: '🌌', description: '宇宙星云 - 紫粉渐变' },
  { type: 'matrix', name: 'Matrix Rain', icon: '💚', description: '矩阵代码雨 - 赛博朋克' },
  { type: 'galaxy', name: 'Galaxy Spiral', icon: '🌀', description: '星系螺旋 - 深蓝金色' }
];

const onMouseEffectToggle = () => {
  emit('update:mouseEffectEnabled', mouseEffectEnabled.value);
};

const selectMouseEffect = (type) => {
  currentMouseEffect.value = type;
  emit('update:mouseEffect', type);
};

const selectBackground = (type) => {
  currentBackground.value = type;
  emit('update:background', type);
};

// 初始化发送当前状态
watch(() => {}, () => {
  emit('update:mouseEffect', currentMouseEffect.value);
  emit('update:mouseEffectEnabled', mouseEffectEnabled.value);
  emit('update:background', currentBackground.value);
}, { immediate: true });
</script>

<style scoped>
.effect-control-panel {
  position: fixed;
  top: 80px;
  right: 20px;
  background: rgba(20, 20, 40, 0.95);
  backdrop-filter: blur(10px);
  border: 2px solid rgba(0, 243, 255, 0.3);
  border-radius: 16px;
  padding: 20px;
  z-index: 10000;
  box-shadow: 0 8px 32px rgba(0, 243, 255, 0.2);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  max-width: 280px;
}

.panel-collapsed {
  padding: 0;
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.toggle-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: rgba(0, 243, 255, 0.2);
  border: 1px solid rgba(0, 243, 255, 0.5);
  border-radius: 50%;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  font-size: 16px;
  transition: all 0.3s ease;
  z-index: 1;
}

.panel-collapsed .toggle-btn {
  position: static;
  width: 100%;
  height: 100%;
  font-size: 24px;
}

.toggle-btn:hover {
  background: rgba(0, 243, 255, 0.4);
  transform: rotate(90deg);
}

.panel-content {
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

.panel-title {
  margin: 0 0 20px 0;
  font-size: 18px;
  font-weight: bold;
  color: #00f3ff;
  text-align: center;
  text-shadow: 0 0 10px rgba(0, 243, 255, 0.5);
}

.control-section {
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid rgba(0, 243, 255, 0.2);
}

.control-section:last-child {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.section-title {
  margin: 0 0 12px 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  font-weight: 600;
}

.toggle-switch {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.switch {
  position: relative;
  display: inline-block;
  width: 50px;
  height: 24px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.1);
  transition: 0.4s;
  border-radius: 24px;
  border: 1px solid rgba(0, 243, 255, 0.3);
}

.slider:before {
  position: absolute;
  content: "";
  height: 16px;
  width: 16px;
  left: 4px;
  bottom: 3px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: rgba(0, 243, 255, 0.5);
  border-color: #00f3ff;
}

input:checked + .slider:before {
  transform: translateX(26px);
  background-color: #00f3ff;
  box-shadow: 0 0 10px #00f3ff;
}

.label-text {
  color: rgba(255, 255, 255, 0.9);
  font-size: 13px;
}

.effect-types,
.background-types {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.effect-btn,
.bg-btn {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  color: rgba(255, 255, 255, 0.8);
  font-size: 13px;
}

.effect-btn:hover,
.bg-btn:hover {
  background: rgba(0, 243, 255, 0.1);
  border-color: rgba(0, 243, 255, 0.5);
  transform: translateX(4px);
}

.effect-btn.active,
.bg-btn.active {
  background: rgba(0, 243, 255, 0.2);
  border-color: #00f3ff;
  box-shadow: 0 0 15px rgba(0, 243, 255, 0.3);
}

.effect-icon,
.bg-icon {
  font-size: 18px;
  flex-shrink: 0;
}

.effect-name,
.bg-name {
  flex: 1;
  text-align: left;
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .effect-control-panel {
    top: auto;
    bottom: 20px;
    right: 20px;
    max-width: 240px;
  }
}
</style>
