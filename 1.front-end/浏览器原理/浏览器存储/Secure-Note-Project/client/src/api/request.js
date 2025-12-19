import axios from "axios";

const request = axios.create({
  baseURL: "http://localhost:3000", // 后端地址
  timeout: 5000,
  // 必须设置为 true，否则跨域请求不会带 Cookie，后端也写不进 Cookie
  withCredentials: true,
});

// 响应拦截器 (用于处理错误)
request.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error("请求出错:", error);
    return Promise.reject(error);
  }
);

export default request;
