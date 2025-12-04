import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/** 用户结构 */
export interface User {
  id?: number;
  username: string;
  password?: string; // 密码 (请求时必填，响应时通常不返回)
  email?: string;
  nickname?: string;
  avatar?: string;
  bio?: string; // 简介
  role?: number; // 角色
  createTime?: string;
  updateTime?: string;
  isDeleted?: number;
}

/** 登录返回结果结构 (对应 Map<String, Object>) */
export interface LoginResult {
  token?: string; // JWT Token
  user?: User; // 用户信息
  [key: string]: any; // 允许包含其他动态字段
}

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 用户登录
 * 对应文档: POST /api/login
 * 响应说明: 文档显示 data 为 Map，通常包含 token 和 user 信息
 */
export const login = (
  data: Pick<User, "username" | "password">
): Promise<LoginResult> => {
  return request.post("/login", data);
};

/**
 * 注册新用户
 * 对应文档: POST /api/users
 * 响应说明: 文档显示 data 为 User 对象
 */
export const register = (data: User): Promise<User> => {
  return request.post("/users", data);
};

/**
 * 根据ID获取用户信息
 * 对应文档: GET /api/users/{id}
 * 响应说明: 文档显示 data 为 User 对象
 */
export const getUserById = (id: number | string): Promise<User> => {
  return request.get(`/users/${id}`);
};

/**
 * 更新用户信息
 * 对应文档: PUT /api/users
 * 注意: data 中必须包含 id
 * 响应说明: 文档显示 data 为 string
 */
export const updateUser = (data: User): Promise<string> => {
  return request.put("/users", data);
};
