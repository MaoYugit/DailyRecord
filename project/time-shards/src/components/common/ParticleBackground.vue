<script setup>
import { loadSlim } from "tsparticles-slim";

const particlesInit = async (engine) => {
  await loadSlim(engine);
};

const particlesLoaded = async (container) => {
  console.log("Particles container loaded", container);
};

const options = {
  background: {
    color: {
      value: "transparent", // Let CSS handle the background color
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
      value: "#00f3ff", // Cyan/Glitch color
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
</script>

<template>
  <vue-particles
    id="tsparticles"
    :particlesInit="particlesInit"
    :particlesLoaded="particlesLoaded"
    :options="options"
    class="particles-container"
  />
</template>

<style scoped>
.particles-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1; /* Behind everything */
  pointer-events: none; /* Let clicks pass through unless interacting */
}
</style>
