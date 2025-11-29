import request from './request';

export const login = (data) => {
    return request.post('/login', data);
};

export const register = (data) => {
    return request.post('/users', data);
};

export const getUser = (id) => {
    return request.get(`/users/${id}`);
};

export const updateUser = (data) => {
    return request.put('/users', data);
};
