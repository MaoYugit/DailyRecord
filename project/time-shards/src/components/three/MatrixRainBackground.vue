<template>
  <div class="background-wrapper" ref="container">
    <canvas ref="canvas" class="matrix-canvas"></canvas>
  </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue';

const container = ref(null);
const canvas = ref(null);
let ctx;
let animationId;
let columns;
let drops = [];

const matrixChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%^&*()_+-=[]{}|;:,.<>?/~`';

const initMatrix = () => {
    ctx = canvas.value.getContext('2d');
    canvas.value.width = window.innerWidth;
    canvas.value.height = window.innerHeight;

    columns = Math.floor(canvas.value.width / 20);
    drops = Array(columns).fill(1);

    animate();
};

const animate = () => {
    // 半透明黑色背景，创建拖尾效果
    ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
    ctx.fillRect(0, 0, canvas.value.width, canvas.value.height);

    // 设置文字样式
    ctx.fillStyle = '#0f0'; // 绿色
    ctx.font = '16px monospace';

    for (let i = 0; i < drops.length; i++) {
        // 随机字符
        const char = matrixChars[Math.floor(Math.random() * matrixChars.length)];
        
        // 绘制字符
        const x = i * 20;
        const y = drops[i] * 20;
        
        // 添加发光效果
        ctx.shadowBlur = 10;
        ctx.shadowColor = '#0f0';
        ctx.fillText(char, x, y);

        // 随机重置某些列
        if (y > canvas.value.height && Math.random() > 0.975) {
            drops[i] = 0;
        }

        drops[i]++;
    }

    animationId = requestAnimationFrame(animate);
};

const onWindowResize = () => {
    canvas.value.width = window.innerWidth;
    canvas.value.height = window.innerHeight;
    columns = Math.floor(canvas.value.width / 20);
    drops = Array(columns).fill(1);
};

onMounted(() => {
    initMatrix();
    window.addEventListener('resize', onWindowResize);
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', onWindowResize);
    if (animationId) {
        cancelAnimationFrame(animationId);
    }
});
</script>

<style scoped>
.background-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    overflow: hidden;
    background: #000000;
}

.matrix-canvas {
    width: 100%;
    height: 100%;
    display: block;
}
</style>
