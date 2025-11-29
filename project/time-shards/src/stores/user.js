import { defineStore } from 'pinia';
import { ref, computed } from 'vue';
import { login as loginApi, register as registerApi } from '../api/user';

export const useUserStore = defineStore('user', () => {
    const user = ref(JSON.parse(localStorage.getItem('user') || 'null'));

    const isLoggedIn = computed(() => !!user.value);

    const login = async (credentials) => {
        try {
            const res = await loginApi(credentials);
            // Assuming res contains user info and token
            // Adjust based on actual backend response structure
            // If backend returns { token: '...', user: { ... } } or just { ...user, token: '...' }
            user.value = res;
            localStorage.setItem('user', JSON.stringify(res));
            return res;
        } catch (error) {
            throw error;
        }
    };

    const register = async (userData) => {
        try {
            await registerApi(userData);
        } catch (error) {
            throw error;
        }
    };

    const logout = () => {
        user.value = null;
        localStorage.removeItem('user');
        // Optional: Redirect to login or home
        window.location.reload();
    };

    return {
        user,
        isLoggedIn,
        login,
        register,
        logout
    };
});
