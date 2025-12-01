import request from "./request";

/**
 * 获取所有配置
 * 对应文档: GET /api/configs
 * 返回值通常是一个对象: { "site_name": "...", "site_desc": "..." }
 */
export const getConfigs = () => {
  return request.get("/configs");
};

/**
 * 更新或添加配置
 * 对应文档: POST /api/configs
 * @param {Object} data - 配置对象 (键值对)
 * 例如: { "site_name": "我的博客", "allow_comment": "1" }
 */
export const updateConfigs = (data) => {
  return request.post("/configs", data);
};
