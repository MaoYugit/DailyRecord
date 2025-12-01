import request from "./request";

/**
 * 获取所有标签列表
 * 对应文档: GET /api/tags
 */
export const getTags = () => {
  return request.get("/tags");
};

/**
 * 创建新标签
 * 对应文档: POST /api/tags
 * @param {Object} data - 标签对象
 * @param {string} data.name - 标签名称
 * @param {string} data.slug - 标签别名 (URL友好)
 */
export const createTag = (data) => {
  return request.post("/tags", data);
};

/**
 * 更新标签信息
 * 对应文档: PUT /api/tags
 * @param {Object} data - 标签对象 (必须包含 id)
 */
export const updateTag = (data) => {
  return request.put("/tags", data);
};

/**
 * 根据 ID 删除标签
 * 对应文档: DELETE /api/tags/{id}
 * @param {number|string} id - 标签ID
 */
export const deleteTag = (id) => {
  return request.delete(`/tags/${id}`);
};

/**
 * 获取热门标签 (按文章数排序)
 * 对应文档: GET /api/tags/hot
 * @param {number} [limit] - 获取数量限制 (可选)
 */
export const getHotTags = (limit) => {
  return request.get("/tags/hot", {
    params: { limit },
  });
};
