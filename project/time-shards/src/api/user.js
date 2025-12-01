import request from "./request";

/**
 * 用户登录
 * 对应文档: POST /api/login
 * @param {Object} data - 登录信息
 * @param {string} data.username - 用户名
 * @param {string} data.password - 密码 (虽然文档示例是User对象，但通常登录只需要传账号密码)
 */
export const login = (data) => {
  return request.post("/login", data);
};

/**
 * 注册新用户
 * 对应文档: POST /api/users
 * @param {Object} data - 注册信息
 * @param {string} data.username - 用户名
 * @param {string} data.password - 密码
 * @param {string} [data.email] - 邮箱
 * @param {string} [data.nickname] - 昵称
 */
export const register = (data) => {
  return request.post("/users", data);
};

/**
 * 根据ID获取用户信息
 * 对应文档: GET /api/users/{id}
 * @param {number|string} id - 用户ID
 */
export const getUserById = (id) => {
  return request.get(`/users/${id}`);
};

/**
 * 更新用户信息
 * 对应文档: PUT /api/users
 * @param {Object} data - 用户对象 (必须包含 id)
 * @param {number} data.id - 用户ID
 * @param {string} [data.nickname] - 昵称
 * @param {string} [data.avatar] - 头像地址
 * @param {string} [data.bio] - 简介
 * @param {string} [data.email] - 邮箱
 */
export const updateUser = (data) => {
  return request.put("/users", data);
};
