import request from './request';

export const getConfigs = () => {
    return request.get('/configs');
};
