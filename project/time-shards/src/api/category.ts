import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 分类结构 */
export interface Category {
  id?: number; // 创建时可选，更新/删除/查询时存在
  name: string; // 分类名称
  slug: string; // 分类别名 (URL友好)
  description?: string; // 描述
  parentId?: number; // 父分类ID (0表示顶级)
  sort?: number; // 排序值
  createTime?: string; // 创建时间
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取所有分类列表
 * 对应文档: GET /api/categories
 * 响应说明: 文档显示 data 为 Category 数组
 */
export const getCategories = (): Promise<Category[]> => {
  return request.get("/categories");
};

/**
 * 创建新分类
 * 对应文档: POST /api/categories
 * 响应说明: 文档显示 data 为新创建的 Category 对象
 */
export const createCategory = (data: Category): Promise<Category> => {
  return request.post("/categories", data);
};

/**
 * 更新分类信息
 * 对应文档: PUT /api/categories
 * 注意: data 中必须包含 id
 * 响应说明: 文档显示 data 为 string (例如 "更新成功")
 */
export const updateCategory = (data: Category): Promise<string> => {
  return request.put("/categories", data);
};

/**
 * 删除分类
 * 对应文档: DELETE /api/categories/{id}
 * 响应说明: 文档显示 data 为 string
 */
export const deleteCategory = (id: number | string): Promise<string> => {
  return request.delete(`/categories/${id}`);
};

/**
 * 根据 Slug 获取分类详情 (通常用于前台页面展示)
 * 对应文档: GET /api/categories/slug/{slug}
 * 响应说明: 文档显示 data 为 Category 对象
 */
export const getCategoryBySlug = (slug: string): Promise<Category> => {
  return request.get(`/categories/slug/${slug}`);
};
