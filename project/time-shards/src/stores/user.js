import { defineStore } from 'pinia';
import { ref } from 'vue';
import { login as loginApi, register as registerApi } from '@/api';

export const useUserStore = defineStore('user', () => {
    const user = ref(JSON.parse(localStorage.getItem('user') || 'null'));
    const isAuthenticated = ref(!!user.value);

    const login = async (credentials) => {
        try {
            const data = await loginApi(credentials);
            user.value = data;
            isAuthenticated.value = true;
            localStorage.setItem('user', JSON.stringify(data));
            return data;
        } catch (error) {
            throw error;
        }
    };

    const register = async (userData) => {
        try {
            const data = await registerApi(userData);
            // Assuming register automatically logs in or returns user data, 
            // otherwise we might need to redirect to login.
            // For now, let's assume it returns user data like login.
            if (data && data.id) {
                user.value = data;
                isAuthenticated.value = true;
                localStorage.setItem('user', JSON.stringify(data));
            }
            return data;
        } catch (error) {
            throw error;
        }
    };

    const logout = () => {
        user.value = null;
        isAuthenticated.value = false;
        localStorage.removeItem('user');
    };

    return {
        user,
        isAuthenticated,
        login,
        register,
        logout
    };
});
