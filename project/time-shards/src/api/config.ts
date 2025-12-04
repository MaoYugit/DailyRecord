import request from "./request.js";

// ==========================================
// 数据类型定义 (Type Definitions)
// ==========================================

/**
 * 配置项映射表
 * 对应文档中的 Map<String, String> 结构
 * key 为配置名，value 为配置值
 */
export type ConfigMap = Record<string, string>;

// ==========================================
// 接口方法 (API Methods)
// ==========================================

/**
 * 获取所有配置
 * 对应文档: GET /api/configs
 * 响应说明: 文档显示 data 为 Map<String, String> 对象
 */
export const getConfigs = (): Promise<ConfigMap> => {
  return request.get("/configs");
};

/**
 * 更新或添加配置
 * 对应文档: POST /api/configs
 * 响应说明: 文档显示 data 为 string
 * @param data - 配置键值对 (例如 { site_name: "My Blog", allow_comment: "1" })
 */
export const updateConfigs = (data: ConfigMap): Promise<string> => {
  return request.post("/configs", data);
};
