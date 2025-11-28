import { defineStore } from 'pinia';
import { ref } from 'vue';
import { getArticles, getArticleById } from '@/api';

export const useArticleStore = defineStore('article', () => {
    const articles = ref([]);
    const currentArticle = ref(null);
    const loading = ref(false);
    const error = ref(null);

    const fetchArticles = async (params) => {
        loading.value = true;
        try {
            const res = await getArticles(params);
            // Assuming res is { list: [], total: ... } or just []
            if (res.list) {
                articles.value = params.page === 1 ? res.list : [...articles.value, ...res.list];
                return res;
            } else {
                articles.value = res;
                return { list: res, total: res.length };
            }
        } catch (err) {
            error.value = err;
        } finally {
            loading.value = false;
        }
    };

    const fetchArticleById = async (id) => {
        loading.value = true;
        try {
            const data = await getArticleById(id);
            currentArticle.value = data;
            return data;
        } catch (err) {
            error.value = err;
        } finally {
            loading.value = false;
        }
    };

    return {
        articles,
        currentArticle,
        loading,
        error,
        fetchArticles,
        fetchArticleById
    };
});
