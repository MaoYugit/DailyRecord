import request from "./request.js";
import type { Tag } from "./tag.js";
// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 文章元数据结构 */
export interface ArticleMeta {
  id: number;
  articleId: number;
  metaKey: string;
  metaValue: string;
}

/** 文章核心结构 (对应 Article) */
export interface Article {
  id?: number; // 创建时可选，更新时必填
  userId?: number;
  categoryId?: number;
  title: string;
  slug?: string; // 别名
  summary?: string; // 摘要
  content: string; // Markdown内容
  contentHtml?: string; // HTML内容
  coverImage?: string; // 封面图
  status?: number; // 状态
  isTop?: number; // 是否置顶
  viewCount?: number;
  commentCount?: number;
  createTime?: string;
  updateTime?: string;
  isDeleted?: number;
  categoryName?: string;
  authorName?: string;
  tags?: Tag[];
  metas?: ArticleMeta[];
}

/** 文章列表查询参数 */
export interface ArticleQueryParams {
  page?: number; // 当前页码
  limit?: number; // 每页数量
  keyword?: string; // 搜索关键词
  categoryId?: number; // 分类ID
  tagId?: number; // 标签ID
  status?: number; // 状态
  isTop?: number; // 是否置顶
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取文章列表 (支持分页、搜索、筛选)
 * 对应文档: GET /api/articles
 * 响应说明: 文档显示返回类型为 ApiResponseMapStringObject，通常包含 list 和 total
 */
export const getArticles = (params: ArticleQueryParams): Promise<any> => {
  return request.get("/articles", { params });
};

/**
 * 根据 ID 获取文章详情
 * 对应文档: GET /api/articles/{id}
 */
export const getArticleById = (id: number | string): Promise<Article> => {
  return request.get(`/articles/${id}`);
};

/**
 * 根据 Slug (别名) 获取文章详情 (SEO 友好)
 * 对应文档: GET /api/articles/slug/{slug}
 */
export const getArticleBySlug = (slug: string): Promise<Article> => {
  return request.get(`/articles/slug/${slug}`);
};

/**
 * 创建文章
 * 对应文档: POST /api/articles
 * 响应说明: 文档显示 data 为 string (可能返回 "创建成功" 或 新ID字符串)
 */
export const createArticle = (data: Article): Promise<string> => {
  return request.post("/articles", data);
};

/**
 * 更新文章
 * 对应文档: PUT /api/articles
 * 注意: data 中必须包含 id
 */
export const updateArticle = (data: Article): Promise<string> => {
  return request.put("/articles", data);
};

/**
 * 删除文章
 * 对应文档: DELETE /api/articles/{id}
 */
export const deleteArticle = (id: number | string): Promise<string> => {
  return request.delete(`/articles/${id}`);
};

/**
 * 增加文章阅读量
 * 对应文档: POST /api/articles/{id}/view
 */
export const addArticleView = (id: number | string): Promise<void> => {
  return request.post(`/articles/${id}/view`);
};
