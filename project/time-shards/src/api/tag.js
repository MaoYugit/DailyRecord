import request from './request';

export const getTags = () => {
    return request.get('/tags');
};
