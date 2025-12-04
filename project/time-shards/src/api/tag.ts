import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 标签结构 */
export interface Tag {
  id?: number; // 创建时可选，更新/读取时存在
  name: string; // 标签名称
  slug: string; // 标签别名 (URL友好)
  createTime?: string; // 创建时间
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取所有标签列表
 * 对应文档: GET /api/tags
 * 响应说明: 文档显示 data 为 Tag 数组
 */
export const getTags = (): Promise<Tag[]> => {
  return request.get("/tags");
};

/**
 * 创建新标签
 * 对应文档: POST /api/tags
 * 响应说明: 文档显示 data 为新创建的 Tag 对象
 */
export const createTag = (data: Tag): Promise<Tag> => {
  return request.post("/tags", data);
};

/**
 * 更新标签信息
 * 对应文档: PUT /api/tags
 * 注意: data 中必须包含 id
 * 响应说明: 文档显示 data 为 string
 */
export const updateTag = (data: Tag): Promise<string> => {
  return request.put("/tags", data);
};

/**
 * 根据 ID 删除标签
 * 对应文档: DELETE /api/tags/{id}
 * 响应说明: 文档显示 data 为 string
 */
export const deleteTag = (id: number | string): Promise<string> => {
  return request.delete(`/tags/${id}`);
};

/**
 * 获取热门标签 (按文章数排序)
 * 对应文档: GET /api/tags/hot
 * 响应说明: 文档显示 data 为 Tag 数组
 * @param limit - 获取数量限制 (可选)
 */
export const getHotTags = (limit?: number): Promise<Tag[]> => {
  return request.get("/tags/hot", {
    params: { limit },
  });
};
