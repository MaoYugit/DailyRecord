import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LessonView from '../views/LessonView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue'),
  },
  {
    path: '/lessons/:lessonId',
    name: 'lesson',
    component: LessonView,
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
