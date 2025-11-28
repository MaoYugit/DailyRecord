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
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
