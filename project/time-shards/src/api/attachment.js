import request from './request';

export const uploadFile = (file, userId) => {
    const formData = new FormData();
    formData.append("file", file);
    if (userId) formData.append("userId", userId);
    return request.post("/attachments/upload", formData, {
        headers: { "Content-Type": "multipart/form-data" },
    });
};
