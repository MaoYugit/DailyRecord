import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 附件/文件结构 */
export interface Attachment {
  id: number;
  userId: number;
  originalName: string; // 原始文件名
  filePath: string; // 文件存储路径
  fileUrl: string; // 文件访问URL
  fileType: string; // 文件类型
  fileSize: number; // 文件大小
  storageLocation: number; // 存储位置标识
  createTime: string; // 上传时间
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取附件列表
 * 对应文档: GET /api/attachments
 * 响应说明: 文档显示 data 为 Attachment 数组
 */
export const getAttachments = (
  userId: number | string
): Promise<Attachment[]> => {
  return request.get("/attachments", {
    params: { userId },
  });
};

/**
 * 上传文件
 * 对应文档: POST /api/attachments/upload
 * 响应说明: 文档显示 data 为单个 Attachment 对象
 * @param file - 文件对象 (通常来自 <input type="file">)
 * @param userId - 用户ID
 */
export const uploadFile = (
  file: File,
  userId: number | string
): Promise<Attachment> => {
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
 * 响应说明: 文档显示 data 为 string
 */
export const deleteAttachment = (id: number | string): Promise<string> => {
  return request.delete(`/attachments/${id}`);
};
