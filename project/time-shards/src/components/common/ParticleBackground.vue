<script setup>
import { loadSlim } from "tsparticles-slim";
import { onMounted, onBeforeUnmount, ref } from 'vue';
import * as THREE from 'three';

const particlesInit = async (engine) => {
  await loadSlim(engine);
};

const particlesLoaded = async (container) => {
  console.log("Particles container loaded", container);
};

const options = {
  background: {
    color: {
      value: "transparent",
    },
  },
  fpsLimit: 120,
  interactivity: {
    events: {
      onClick: {
        enable: true,
        mode: "push",
      },
      onHover: {
        enable: true,
        mode: "repulse",
      },
      resize: true,
    },
    modes: {
      push: {
        quantity: 4,
      },
      repulse: {
        distance: 200,
        duration: 0.4,
      },
    },
  },
  particles: {
    color: {
      value: "#00f3ff",
    },
    links: {
      color: "#00f3ff",
      distance: 150,
      enable: true,
      opacity: 0.2,
      width: 1,
    },
    move: {
      direction: "none",
      enable: true,
      outModes: {
        default: "bounce",
      },
      random: false,
      speed: 1,
      straight: false,
    },
    number: {
      density: {
        enable: true,
        area: 800,
      },
      value: 80,
    },
    opacity: {
      value: 0.3,
    },
    shape: {
      type: "circle",
    },
    size: {
      value: { min: 1, max: 3 },
    },
  },
  detectRetina: true,
};

// Three.js Logic
const threeContainer = ref(null);
let scene, camera, renderer, shards = [];

const initThree = () => {
    scene = new THREE.Scene();
    // Fog for depth
    scene.fog = new THREE.FogExp2(0x000000, 0.002);

    camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
    camera.position.z = 50;

    renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(window.devicePixelRatio);
    threeContainer.value.appendChild(renderer.domElement);

    // Lights
    const ambientLight = new THREE.AmbientLight(0x404040);
    scene.add(ambientLight);
    const pointLight = new THREE.PointLight(0x00f3ff, 1, 100);
    pointLight.position.set(10, 10, 10);
    scene.add(pointLight);

    // Create Shards (Tetrahedrons)
    const geometry = new THREE.TetrahedronGeometry(1, 0);
    const material = new THREE.MeshPhongMaterial({
        color: 0x00f3ff,
        flatShading: true,
        transparent: true,
        opacity: 0.6,
        shininess: 100
    });

    for (let i = 0; i < 50; i++) {
        const shard = new THREE.Mesh(geometry, material);
        shard.position.x = (Math.random() - 0.5) * 100;
        shard.position.y = (Math.random() - 0.5) * 100;
        shard.position.z = (Math.random() - 0.5) * 50;
        shard.rotation.x = Math.random() * Math.PI;
        shard.rotation.y = Math.random() * Math.PI;
        shard.scale.setScalar(Math.random() * 2 + 0.5);
        
        // Custom properties for animation
        shard.userData = {
            rotationSpeed: {
                x: (Math.random() - 0.5) * 0.02,
                y: (Math.random() - 0.5) * 0.02
            },
            floatSpeed: (Math.random() - 0.5) * 0.05
        };

        scene.add(shard);
        shards.push(shard);
    }

    animate();
};

const animate = () => {
    requestAnimationFrame(animate);

    shards.forEach(shard => {
        shard.rotation.x += shard.userData.rotationSpeed.x;
        shard.rotation.y += shard.userData.rotationSpeed.y;
        shard.position.y += shard.userData.floatSpeed;

        // Reset position if out of bounds (looping)
        if (shard.position.y > 50) shard.position.y = -50;
        if (shard.position.y < -50) shard.position.y = 50;
    });

    // Mouse interaction (optional, simple parallax)
    // camera.position.x += (mouseX - camera.position.x) * 0.05;
    // camera.position.y += (-mouseY - camera.position.y) * 0.05;

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
    // Cleanup Three.js
    if (renderer) {
        renderer.dispose();
    }
});
</script>

<template>
  <div class="background-wrapper">
      <div ref="threeContainer" class="three-container"></div>
      <vue-particles
        id="tsparticles"
        :particlesInit="particlesInit"
        :particlesLoaded="particlesLoaded"
        :options="options"
        class="particles-container"
      />
  </div>
</template>

<style scoped>
.background-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    overflow: hidden;
    background: radial-gradient(circle at center, #1a1a2e 0%, #000000 100%);
}

.three-container {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 0;
}

.particles-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1; /* Particles on top of Three.js */
  pointer-events: none;
}
</style>
