<!-- src/components/NavHeader.vue -->
<script setup>
import { ref, onMounted, onBeforeUpdate, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { gsap } from 'gsap'

const navigationLinks = [
  { to: '/', text: '首页' },
  { to: '/about', text: '关于' },
]

const navItemRefs = ref([])
const magicLineRef = ref(null)
const route = useRoute()

onBeforeUpdate(() => {
  navItemRefs.value = []
})

const moveMagicLine = (targetEl) => {
  if (!targetEl) return
  gsap.to(magicLineRef.value, {
    duration: 0.4,
    ease: 'power3.out',
    left: targetEl.offsetLeft,
    width: targetEl.offsetWidth,
    opacity: 1,
  })
}

watch(
  () => route.path,
  async () => {
    await nextTick()
    const activeLink = navItemRefs.value.find((el) => el.classList.contains('router-link-active'))
    moveMagicLine(activeLink)
  },
  { immediate: true },
)

onMounted(async () => {
  gsap.from(navItemRefs.value, {
    duration: 0.5,
    opacity: 0,
    y: 20,
    stagger: 0.1,
    ease: 'power3.out',
    delay: 0.2,
  })

  await nextTick()
  const activeLinkOnMount = navItemRefs.value.find((el) =>
    el.classList.contains('router-link-active'),
  )
  if (activeLinkOnMount) {
    gsap.set(magicLineRef.value, {
      left: activeLinkOnMount.offsetLeft,
      width: activeLinkOnMount.offsetWidth,
      opacity: 1,
    })
  }
})
</script>

<template>
  <header class="header-container">
    <nav class="nav-header">
      <router-link
        v-for="item in navigationLinks"
        :key="item.to"
        :to="item.to"
        class="nav-item"
        :ref="
          (el) => {
            if (el) navItemRefs.push(el.$el)
          }
        "
      >
        {{ item.text }}
      </router-link>

      <div ref="magicLineRef" class="magic-line"></div>
    </nav>
  </header>
</template>

<style scoped>
.header-container {
  padding: 1rem 2rem;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}
.nav-header {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: fit-content;
  margin: 0 auto;
}
.nav-item {
  position: relative;
  color: #333;
  padding: 0.8rem 1.5rem;
  margin: 0 0.5rem;
  text-decoration: none;
  font-size: 1.1rem;
  font-weight: 500;
  transition: color 0.3s ease;
  z-index: 1;
}
.nav-item:hover {
  color: #42b983;
}
.nav-item.router-link-active {
  color: #42b983;
}
.magic-line {
  position: absolute;
  bottom: -5px;
  left: 0;
  height: 4px;
  background-color: #42b983;
  border-radius: 2px;
  opacity: 0;
  z-index: 0;
}
</style>
