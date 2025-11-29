import request from './request';

export const getArticles = (params) => {
    return request.get('/articles', { params });
};

export const getArticleById = (id) => {
    return request.get(`/articles/${id}`);
};

export const createArticle = (data) => {
    return request.post('/articles', data);
};

export const updateArticle = (data) => {
    return request.put('/articles', data);
};

export const searchArticles = (q) => {
    return request.get('/articles/search', { params: { q } });
};
