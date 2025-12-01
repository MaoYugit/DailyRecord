<template>
  <div class="background-wrapper" ref="container"></div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, ref } from 'vue';
import * as THREE from 'three';

const container = ref(null);
let scene, camera, renderer;
let spiralParticles = [];
let time = 0;

const initThree = () => {
    scene = new THREE.Scene();
    scene.fog = new THREE.FogExp2(0x000510, 0.0008);

    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 150;

    renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    container.value.appendChild(renderer.domElement);

    // 创建螺旋星系
    const spiralArms = 5;
    const particlesPerArm = 1000;

    for (let arm = 0; arm < spiralArms; arm++) {
        const geometry = new THREE.BufferGeometry();
        const positions = new Float32Array(particlesPerArm * 3);
        const colors = new Float32Array(particlesPerArm * 3);
        const sizes = new Float32Array(particlesPerArm);

        for (let i = 0; i < particlesPerArm; i++) {
            const radius = (i / particlesPerArm) * 100;
            const angle = (i / particlesPerArm) * Math.PI * 4 + (arm * Math.PI * 2) / spiralArms;
            
            positions[i * 3] = Math.cos(angle) * radius;
            positions[i * 3 + 1] = (Math.random() - 0.5) * 10;
            positions[i * 3 + 2] = Math.sin(angle) * radius;

            // 渐变颜色：从深蓝到金色
            const t = i / particlesPerArm;
            const color = new THREE.Color();
            color.setHSL(0.6 - t * 0.5, 1, 0.5 + t * 0.3);
            
            colors[i * 3] = color.r;
            colors[i * 3 + 1] = color.g;
            colors[i * 3 + 2] = color.b;

            sizes[i] = (1 - t) * 3 + 1;
        }

        geometry.setAttribute('position', new THREE.BufferAttribute(positions, 3));
        geometry.setAttribute('color', new THREE.BufferAttribute(colors, 3));
        geometry.setAttribute('size', new THREE.BufferAttribute(sizes, 1));

        const material = new THREE.PointsMaterial({
            size: 2,
            vertexColors: true,
            transparent: true,
            opacity: 0.9,
            blending: THREE.AdditiveBlending,
            sizeAttenuation: true
        });

        const spiral = new THREE.Points(geometry, material);
        scene.add(spiral);
        spiralParticles.push(spiral);
    }

    // 添加中心发光核心
    const coreGeometry = new THREE.SphereGeometry(5, 32, 32);
    const coreMaterial = new THREE.MeshBasicMaterial({
        color: 0xffd700,
        transparent: true,
        opacity: 0.8
    });
    const core = new THREE.Mesh(coreGeometry, coreMaterial);
    scene.add(core);

    // 光照
    const ambientLight = new THREE.AmbientLight(0x1a1a3e);
    scene.add(ambientLight);

    const pointLight = new THREE.PointLight(0xffd700, 2, 300);
    pointLight.position.set(0, 0, 0);
    scene.add(pointLight);

    animate();
};

const animate = () => {
    requestAnimationFrame(animate);
    time += 0.0005;

    spiralParticles.forEach((spiral, index) => {
        spiral.rotation.y = time + (index * Math.PI * 2) / spiralParticles.length;
    });

    camera.position.x = Math.sin(time * 0.5) * 20;
    camera.position.y = Math.cos(time * 0.3) * 10;
    camera.lookAt(0, 0, 0);

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
    background: radial-gradient(circle at center, #0a1628 0%, #000510 100%);
}
</style>
