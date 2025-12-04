import axios from "axios";
import type { InternalAxiosRequestConfig, AxiosResponse } from "axios";

// 创建 axios 实例
const service = axios.create({
  baseURL: "/api", // 后端接口的基础路径
  timeout: 10000, // 请求超时时间 (10秒)
});

// =================================================
// 请求拦截器 (Request Interceptor)
// =================================================
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 1. 从 localStorage 获取用户信息 (包含 token)
    const user = JSON.parse(localStorage.getItem("user") || "{}");

    // 2. 如果存在 Token，则添加到请求头中
    if (user.token && config.headers) {
      // 【关键修改】后端 JwtAuthenticationFilter 要求格式必须为 "Bearer <token>"
      // 注意：Bearer 后面的空格不能少
      config.headers["Authorization"] = `Bearer ${user.token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// =================================================
// 响应拦截器 (Response Interceptor)
// =================================================
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;

    // 后端 ApiResponse 结构: { code: 200, message: "...", data: ... }

    // 1. 如果 code 不是 200，说明业务逻辑出错 (如参数错误、操作失败)
    if (res.code !== undefined && res.code !== 200) {
      // 特殊处理: 401 未授权 (Token 过期或无效)
      if (res.code === 401) {
        // 清除本地过期信息
        localStorage.removeItem("user");
        // 强制跳转回登录页
        window.location.href = "/login";
      }

      // 抛出错误，让前端组件捕获 (例如弹出错误提示)
      return Promise.reject(new Error(res.message || "系统错误"));
    }

    // 2. 成功: 直接返回 data 数据部分 (这样前端调用时不用再写 .data.data)
    // 如果 data 是 null (例如 void 接口)，也返回 res 以防万一
    return (res.data !== undefined ? res.data : res) as any;
  },
  (error) => {
    // 处理网络层面的错误 (如 404, 500, 网络断开)
    console.error("Network Error:", error);

    // 如果后端返回了 401 状态码 (有时 Spring Security 会直接返回 HTTP 401)
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("user");
      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);

export default service;
