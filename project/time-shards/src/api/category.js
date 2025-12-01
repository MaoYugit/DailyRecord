import request from "./request";

/**
 * 获取所有分类列表
 * 对应文档: GET /api/categories
 */
export const getCategories = () => {
  return request.get("/categories");
};

/**
 * 创建新分类
 * 对应文档: POST /api/categories
 * @param {Object} data - 分类对象
 * @param {string} data.name - 分类名称
 * @param {string} data.slug - 分类别名 (URL友好)
 * @param {string} [data.description] - 描述
 * @param {number} [data.parentId] - 父分类ID
 * @param {number} [data.sort] - 排序值
 */
export const createCategory = (data) => {
  return request.post("/categories", data);
};

/**
 * 更新分类信息
 * 对应文档: PUT /api/categories
 * @param {Object} data - 分类对象 (必须包含 id)
 */
export const updateCategory = (data) => {
  return request.put("/categories", data);
};

/**
 * 删除分类
 * 对应文档: DELETE /api/categories/{id}
 * @param {number|string} id - 分类ID
 */
export const deleteCategory = (id) => {
  return request.delete(`/categories/${id}`);
};

/**
 * 根据 Slug 获取分类详情 (通常用于前台页面展示)
 * 对应文档: GET /api/categories/slug/{slug}
 * @param {string} slug - 分类别名
 */
export const getCategoryBySlug = (slug) => {
  return request.get(`/categories/slug/${slug}`);
};
