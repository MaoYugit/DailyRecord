<template>
  <div class="glitch-wrapper">
    <h1 class="glitch" :data-text="text">{{ text }}</h1>
  </div>
</template>

<script setup>
defineProps({
  text: {
    type: String,
    required: true
  }
});
</script>

<style scoped>
.glitch-wrapper {
  display: inline-block;
}

.glitch {
  position: relative;
  color: var(--color-text-main);
  font-size: 4rem;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.glitch::before,
.glitch::after {
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: var(--color-bg);
}

.glitch::before {
  left: 2px;
  text-shadow: -1px 0 var(--color-accent-rational);
  clip: rect(24px, 550px, 90px, 0);
  animation: glitch-anim-2 3s infinite linear alternate-reverse;
}

.glitch::after {
  left: -2px;
  text-shadow: -1px 0 var(--color-accent-mystic);
  clip: rect(85px, 550px, 140px, 0);
  animation: glitch-anim 2.5s infinite linear alternate-reverse;
}

@keyframes glitch-anim {
  0% { clip: rect(17px, 9999px, 94px, 0); }
  20% { clip: rect(39px, 9999px, 86px, 0); }
  40% { clip: rect(20px, 9999px, 20px, 0); }
  60% { clip: rect(69px, 9999px, 12px, 0); }
  80% { clip: rect(56px, 9999px, 77px, 0); }
  100% { clip: rect(12px, 9999px, 63px, 0); }
}

@keyframes glitch-anim-2 {
  0% { clip: rect(129px, 9999px, 36px, 0); }
  20% { clip: rect(36px, 9999px, 4px, 0); }
  40% { clip: rect(88px, 9999px, 92px, 0); }
  60% { clip: rect(12px, 9999px, 31px, 0); }
  80% { clip: rect(122px, 9999px, 2px, 0); }
  100% { clip: rect(34px, 9999px, 11px, 0); }
}
</style>
