import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import BlogView from "../views/BlogView.vue";
import DesignSystemView from "../views/DesignSystemView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/blog",
    name: "blog",
    component: BlogView,
  },
  {
    path: "/design",
    name: "Design",
    component: DesignSystemView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
