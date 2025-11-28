import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
});

// Request interceptor
api.interceptors.request.use(
    (config) => {
        // You can add auth token here if needed
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor
api.interceptors.response.use(
    (response) => {
        const res = response.data;
        if (res.code !== 200) {
            console.error('API Error:', res.message);
            return Promise.reject(new Error(res.message || 'Error'));
        }
        return res.data;
    },
    (error) => {
        import axios from 'axios';

        const api = axios.create({
            baseURL: 'http://localhost:8080/api',
            timeout: 5000,
        });

        // Request interceptor
        api.interceptors.request.use(
            (config) => {
                // You can add auth token here if needed
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        // Response interceptor
        api.interceptors.response.use(
            (response) => {
                const res = response.data;
                if (res.code !== 200) {
                    console.error('API Error:', res.message);
                    return Promise.reject(new Error(res.message || 'Error'));
                }
                return res.data;
            },
            (error) => {
                console.error('Network Error:', error);
                return Promise.reject(error);
            }
        );

        export const getArticles = (params) => {
            return api.get('/articles', { params });
        };

        export const getArticleById = (id) => {
            return api.get(`/articles/${id}`);
        };

        export const createArticle = (data) => {
            return api.post('/articles', data);
        };

        export const getCategories = () => {
            return api.get('/categories');
        };

        export const searchArticles = (q) => {
            return api.get('/search', { params: { q } });
        };

        // User
        export const login = (data) => api.post('/login', data);
        export const register = (data) => api.post('/users', data);
        export const getUser = (id) => api.get(`/users/${id}`);

        // Tag
        export const getTags = () => api.get('/tags');

        // Comment
        export const getComments = (articleId) => api.get('/comments', { params: { articleId } });
        export const createComment = (data) => api.post('/comments', data);

        // Attachment
        export const uploadFile = (file, userId) => {
            const formData = new FormData();
            formData.append('file', file);
            formData.append('userId', userId);
            return api.post('/attachments/upload', formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
        };

        // Config
        export const getConfigs = () => api.get('/configs');

        export default api;
