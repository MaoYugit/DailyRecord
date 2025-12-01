import request from "./request";

/**
 * 获取评论列表
 * 对应文档: GET /api/comments
 * @param {Object} params - 查询参数
 * @param {number|string} [params.articleId] - 文章ID (可选)
 * @param {number} [params.status] - 状态 (0:待审核, 1:通过, 2:拒绝) (可选)
 */
export const getComments = (params) => {
  return request.get("/comments", { params });
};

/**
 * 发表评论
 * 对应文档: POST /api/comments
 * @param {Object} data - 评论对象
 * @param {number|string} data.articleId - 文章ID
 * @param {string} data.content - 评论内容
 * @param {number|string} [data.parentId] - 父评论ID (回复时使用)
 * @param {string} [data.nickname] - 昵称 (游客)
 * @param {string} [data.email] - 邮箱 (游客)
 */
export const createComment = (data) => {
  return request.post("/comments", data);
};

/**
 * 删除评论
 * 对应文档: DELETE /api/comments/{id}
 * @param {number|string} id - 评论ID
 */
export const deleteComment = (id) => {
  return request.delete(`/comments/${id}`);
};

/**
 * 审核评论 / 修改评论状态
 * 对应文档: PUT /api/comments/{id}/status
 * @param {number|string} id - 评论ID
 * @param {number} status - 新状态 (0:待审核, 1:通过, 2:拒绝)
 */
export const updateCommentStatus = (id, status) => {
  // 注意：文档显示 status 是 query 参数，而不是 body 参数
  // 所以 axios.put 的第二个参数(body)传 null，第三个参数传 params
  return request.put(`/comments/${id}/status`, null, {
    params: { status },
  });
};
