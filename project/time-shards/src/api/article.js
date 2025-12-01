import request from "./request"; // 假设你的 axios 实例文件名为 request.js

/**
 * 获取文章列表 (支持分页、搜索、筛选)
 * 对应文档: GET /api/articles
 * @param {Object} params
 * @param {number} [params.page] - 当前页码
 * @param {number} [params.limit] - 每页数量
 * @param {string} [params.keyword] - 搜索关键词
 * @param {number} [params.categoryId] - 分类ID
 * @param {number} [params.tagId] - 标签ID
 * @param {number} [params.status] - 状态
 * @param {number} [params.isTop] - 是否置顶
 */
export const getArticles = (params) => {
  return request.get("/articles", { params });
};

/**
 * 根据 ID 获取文章详情
 * 对应文档: GET /api/articles/{id}
 * @param {number|string} id
 */
export const getArticleById = (id) => {
  return request.get(`/articles/${id}`);
};

/**
 * 根据 Slug (别名) 获取文章详情 (通常用于前台 SEO 友好的 URL)
 * 对应文档: GET /api/articles/slug/{slug}
 * @param {string} slug
 */
export const getArticleBySlug = (slug) => {
  return request.get(`/articles/slug/${slug}`);
};

/**
 * 创建文章
 * 对应文档: POST /api/articles
 * @param {Object} data - 文章对象
 */
export const createArticle = (data) => {
  return request.post("/articles", data);
};

/**
 * 更新文章
 * 对应文档: PUT /api/articles
 * @param {Object} data - 文章对象 (必须包含 id)
 */
export const updateArticle = (data) => {
  return request.put("/articles", data);
};

/**
 * 删除文章
 * 对应文档: DELETE /api/articles/{id}
 * @param {number|string} id
 */
export const deleteArticle = (id) => {
  return request.delete(`/articles/${id}`);
};

/**
 * 增加文章阅读量
 * 对应文档: POST /api/articles/{id}/view
 * @param {number|string} id
 */
export const addArticleView = (id) => {
  return request.post(`/articles/${id}/view`);
};
