<template>
  <div class="mouse-effect-container" ref="container"></div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import gsap from 'gsap';

const props = defineProps({
  effectType: {
    type: String,
    default: 'flower', // 'flower', 'firework', 'star'
    validator: (value) => ['flower', 'firework', 'star'].includes(value)
  },
  enabled: {
    type: Boolean,
    default: true
  }
});

const container = ref(null);
let mouseX = 0;
let mouseY = 0;
let lastEmitTime = 0;
const emitInterval = 50; // ms

// 花朵绽放效果
const createFlower = (x, y) => {
  const petalCount = 8;
  const colors = ['#ff6b9d', '#c44569', '#f8b500', '#ffa502', '#ff6348'];
  
  for (let i = 0; i < petalCount; i++) {
    const petal = document.createElement('div');
    petal.className = 'petal';
    const color = colors[Math.floor(Math.random() * colors.length)];
    petal.style.background = `radial-gradient(circle, ${color}, transparent)`;
    petal.style.left = `${x}px`;
    petal.style.top = `${y}px`;
    container.value.appendChild(petal);
    
    const angle = (Math.PI * 2 * i) / petalCount;
    const distance = 50 + Math.random() * 30;
    const endX = x + Math.cos(angle) * distance;
    const endY = y + Math.sin(angle) * distance;
    
    gsap.to(petal, {
      x: endX - x,
      y: endY - y,
      scale: Math.random() * 0.5 + 0.5,
      opacity: 0,
      duration: 1 + Math.random() * 0.5,
      ease: 'power2.out',
      onComplete: () => {
        petal.remove();
      }
    });
  }
  
  // 中心点
  const center = document.createElement('div');
  center.className = 'flower-center';
  center.style.left = `${x}px`;
  center.style.top = `${y}px`;
  container.value.appendChild(center);
  
  gsap.to(center, {
    scale: 0,
    opacity: 0,
    duration: 0.8,
    ease: 'power2.out',
    onComplete: () => {
      center.remove();
    }
  });
};

// 烟花效果
const createFirework = (x, y) => {
  const particleCount = 30;
  const colors = ['#ff0844', '#ffb199', '#ffd23f', '#00f2ea', '#7b68ee'];
  const baseColor = colors[Math.floor(Math.random() * colors.length)];
  
  for (let i = 0; i < particleCount; i++) {
    const particle = document.createElement('div');
    particle.className = 'firework-particle';
    particle.style.background = baseColor;
    particle.style.left = `${x}px`;
    particle.style.top = `${y}px`;
    particle.style.boxShadow = `0 0 10px ${baseColor}`;
    container.value.appendChild(particle);
    
    const angle = (Math.PI * 2 * i) / particleCount;
    const velocity = 100 + Math.random() * 100;
    const endX = x + Math.cos(angle) * velocity;
    const endY = y + Math.sin(angle) * velocity;
    
    gsap.to(particle, {
      x: endX - x,
      y: endY - y + 50, // 重力效果
      opacity: 0,
      scale: 0,
      duration: 1 + Math.random() * 0.5,
      ease: 'power2.out',
      onComplete: () => {
        particle.remove();
      }
    });
  }
};

// 星光轨迹效果
const createStar = (x, y) => {
  const star = document.createElement('div');
  star.className = 'star-particle';
  star.style.left = `${x}px`;
  star.style.top = `${y}px`;
  
  // 随机星星形状
  const shapes = ['✦', '✧', '★', '✨', '⭐'];
  star.textContent = shapes[Math.floor(Math.random() * shapes.length)];
  
  const colors = ['#ffd700', '#ffed4e', '#00f3ff', '#a8e6cf', '#ff8b94'];
  const color = colors[Math.floor(Math.random() * colors.length)];
  star.style.color = color;
  star.style.textShadow = `0 0 10px ${color}, 0 0 20px ${color}`;
  
  container.value.appendChild(star);
  
  gsap.to(star, {
    y: '+=30',
    opacity: 0,
    scale: 0,
    rotation: Math.random() * 360,
    duration: 1.5,
    ease: 'power1.out',
    onComplete: () => {
      star.remove();
    }
  });
};

const handleMouseMove = (e) => {
  if (!props.enabled) return;
  
  mouseX = e.clientX;
  mouseY = e.clientY;
  
  const now = Date.now();
  if (now - lastEmitTime < emitInterval) return;
  lastEmitTime = now;
  
  switch (props.effectType) {
    case 'flower':
      createFlower(mouseX, mouseY);
      break;
    case 'star':
      createStar(mouseX, mouseY);
      break;
  }
};

const handleClick = (e) => {
  if (!props.enabled) return;
  
  if (props.effectType === 'firework') {
    createFirework(e.clientX, e.clientY);
  }
};

onMounted(() => {
  window.addEventListener('mousemove', handleMouseMove);
  window.addEventListener('click', handleClick);
});

onBeforeUnmount(() => {
  window.removeEventListener('mousemove', handleMouseMove);
  window.removeEventListener('click', handleClick);
});

// 清理所有粒子当效果类型改变时
watch(() => props.effectType, () => {
  if (container.value) {
    container.value.innerHTML = '';
  }
});
</script>

<style scoped>
.mouse-effect-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 9999;
  overflow: hidden;
}

:deep(.petal) {
  position: absolute;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  pointer-events: none;
}

:deep(.flower-center) {
  position: absolute;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: radial-gradient(circle, #ffd700, #ff6b9d);
  transform: translate(-50%, -50%);
  pointer-events: none;
}

:deep(.firework-particle) {
  position: absolute;
  width: 4px;
  height: 4px;
  border-radius: 50%;
  pointer-events: none;
}

:deep(.star-particle) {
  position: absolute;
  font-size: 20px;
  pointer-events: none;
  transform: translate(-50%, -50%);
  user-select: none;
}
</style>
