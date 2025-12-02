import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import ArticleDetail from '../views/ArticleDetail.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/article/:id',
        name: 'ArticleDetail',
        component: ArticleDetail
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/categories',
        name: 'Categories',
        component: () => import('../views/Categories.vue')
    },
    {
        path: '/category/:slug',
        name: 'CategoryDetail',
        component: () => import('../views/Home.vue') // 复用Home组件，通过参数筛选
    },
    {
        path: '/tags',
        name: 'Tags',
        component: () => import('../views/Tags.vue')
    },
    {
        path: '/tag/:slug',
        name: 'TagDetail',
        component: () => import('../views/Home.vue') // 复用Home组件，通过参数筛选
    },
    {
        path: '/archive',
        name: 'Archive',
        component: () => import('../views/Archive.vue')
    },
    {
        path: '/search',
        name: 'Search',
        component: () => import('../views/Search.vue')
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('../views/Profile.vue')
    },
    {
        path: '/attachments',
        name: 'Attachments',
        component: () => import('../views/Attachments.vue')
    },
    {
        path: '/settings',
        name: 'Settings',
        component: () => import('../views/Settings.vue')
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    },
    {
        path: '/editor',
        name: 'ArticleCreate',
        component: () => import('../views/ArticleEditor.vue')
    },
    {
        path: '/editor/:id',
        name: 'ArticleEdit',
        component: () => import('../views/ArticleEditor.vue')
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
