<template>
  <div class="background-wrapper" ref="container"></div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue';
import * as THREE from 'three';

const container = ref(null);
let scene, camera, renderer, particles = [];
let time = 0;

const initThree = () => {
    scene = new THREE.Scene();
    scene.fog = new THREE.FogExp2(0x1a0033, 0.001);

    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 100;

    renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    container.value.appendChild(renderer.domElement);

    // 创建星云粒子系统
    const particleCount = 3000;
    const geometry = new THREE.BufferGeometry();
    const positions = new Float32Array(particleCount * 3);
    const colors = new Float32Array(particleCount * 3);
    const sizes = new Float32Array(particleCount);

    // 定义星云颜色
    const nebulaColors = [
        new THREE.Color(0xff00ff), // 紫色
        new THREE.Color(0xff1493), // 粉色
        new THREE.Color(0x9370db), // 中紫色
        new THREE.Color(0x4b0082), // 靛蓝
        new THREE.Color(0x00ffff), // 青色
    ];

    for (let i = 0; i < particleCount; i++) {
        // 创建螺旋星云形状
        const radius = Math.random() * 80;
        const angle = Math.random() * Math.PI * 2;
        const height = (Math.random() - 0.5) * 40;
        
        positions[i * 3] = Math.cos(angle) * radius;
        positions[i * 3 + 1] = height;
        positions[i * 3 + 2] = Math.sin(angle) * radius;

        // 随机颜色
        const color = nebulaColors[Math.floor(Math.random() * nebulaColors.length)];
        colors[i * 3] = color.r;
        colors[i * 3 + 1] = color.g;
        colors[i * 3 + 2] = color.b;

        sizes[i] = Math.random() * 3 + 1;
    }

    geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
    geometry.setAttribute('color', new THREE.BufferAttribute(colors, 3));
    geometry.setAttribute('size', new THREE.BufferAttribute(sizes, 1));

    const material = new THREE.PointsMaterial({
        size: 2,
        vertexColors: true,
        transparent: true,
        opacity: 0.8,
        blending: THREE.AdditiveBlending,
        sizeAttenuation: true
    });

    const particleSystem = new THREE.Points(geometry, material);
    scene.add(particleSystem);
    particles.push(particleSystem);

    // 添加发光效果
    const ambientLight = new THREE.AmbientLight(0x330033);
    scene.add(ambientLight);

    const pointLight1 = new THREE.PointLight(0xff00ff, 2, 200);
    pointLight1.position.set(50, 0, 0);
    scene.add(pointLight1);

    const pointLight2 = new THREE.PointLight(0x00ffff, 2, 200);
    pointLight2.position.set(-50, 0, 0);
    scene.add(pointLight2);

    animate();
};

const animate = () => {
    requestAnimationFrame(animate);
    time += 0.001;

    particles.forEach(particle => {
        particle.rotation.y += 0.0005;
        particle.rotation.x = Math.sin(time) * 0.1;
    });

    renderer.render(scene, camera);
};

const onWindowResize = () => {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
};

onMounted(() => {
    initThree();
    window.addEventListener('resize', onWindowResize);
});

onBeforeUnmount(() => {
    window.removeEventListener('resize', onWindowResize);
    if (renderer) {
        renderer.dispose();
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
    background: radial-gradient(circle at center, #2d1b4e 0%, #0a0015 100%);
}
</style>
