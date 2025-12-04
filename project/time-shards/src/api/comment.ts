import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 评论结构 */
export interface Comment {
  id?: number; // 创建时可选
  articleId: number; // 文章ID (创建时必填)
  userId?: number; // 用户ID (如果是登录用户)
  parentId?: number; // 父评论ID (回复时使用，顶级为0)
  nickname?: string; // 昵称 (游客)
  email?: string; // 邮箱 (游客)
  website?: string; // 网站 (游客)
  content: string; // 评论内容
  status?: number; // 状态 (0:待审核, 1:通过, 2:拒绝)
  isAdmin?: number; // 是否管理员评论
  createTime?: string; // 创建时间
  isDeleted?: number; // 是否删除
  articleTitle?: string; // 文章标题 (仅响应返回)
  avatar?: string; // 用户头像 (仅响应返回)
}

/** 评论列表查询参数 */
export interface CommentQueryParams {
  articleId?: number | string; // 文章ID
  status?: number; // 状态 (0:待审核, 1:通过, 2:拒绝)
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取评论列表
 * 对应文档: GET /api/comments
 * 响应说明: 文档显示 data 为 Comment 数组
 */
export const getComments = (params: CommentQueryParams): Promise<Comment[]> => {
  return request.get("/comments", { params });
};

/**
 * 发表评论
 * 对应文档: POST /api/comments
 * 响应说明: 文档显示 data 为单个 Comment 对象
 */
export const createComment = (data: Comment): Promise<Comment> => {
  return request.post("/comments", data);
};

/**
 * 删除评论
 * 对应文档: DELETE /api/comments/{id}
 * 响应说明: 文档显示 data 为 string
 */
export const deleteComment = (id: number | string): Promise<string> => {
  return request.delete(`/comments/${id}`);
};

/**
 * 审核评论 / 修改评论状态
 * 对应文档: PUT /api/comments/{id}/status
 * 注意: 文档指出 status 是 query 参数
 * 响应说明: 文档显示 data 为 string
 * @param id 评论ID
 * @param status 新状态 (0:待审核, 1:通过, 2:拒绝)
 */
export const updateCommentStatus = (
  id: number | string,
  status: number
): Promise<string> => {
  // axios.put(url, body, config)
  // 因为 status 是 query 参数，body 传 null
  return request.put(`/comments/${id}/status`, null, {
    params: { status },
  });
};
