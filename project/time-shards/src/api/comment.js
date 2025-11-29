import request from './request';

export const getComments = (articleId) => {
    return request.get('/comments', { params: { articleId } });
};

export const createComment = (data) => {
    return request.post('/comments', data);
};
