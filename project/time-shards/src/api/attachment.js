import request from "./request";

/**
 * 获取附件列表
 * 对应文档: GET /api/attachments
 * @param {number|string} userId - 用户ID (必填)
 */
export const getAttachments = (userId) => {
  return request.get("/attachments", {
    params: { userId },
  });
};

/**
 * 上传文件
 * 对应文档: POST /api/attachments/upload
 * @param {File} file - 文件对象
 * @param {number|string} userId - 用户ID
 */
export const uploadFile = (file, userId) => {
  const formData = new FormData();
  // 只把文件放入 FormData
  formData.append("file", file);

  return request.post("/attachments/upload", formData, {
    // 文档指出 userId 是 query 参数，所以用 params 传，axios 会把它拼接到 URL 后
    params: { userId },
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

/**
 * 删除附件
 * 对应文档: DELETE /api/attachments/{id}
 * @param {number|string} id - 附件ID
 */
export const deleteAttachment = (id) => {
  return request.delete(`/attachments/${id}`);
};
