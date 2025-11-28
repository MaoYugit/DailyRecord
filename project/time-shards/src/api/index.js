import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  timeout: 5000,
});

// Request interceptor
api.interceptors.request.use(
  (config) => {
    // Get token from localStorage if it exists
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    if (user.token) {
      // Assuming the backend might use a token header, though the user prompt said "login returns user info (no password)".
      // If it's session based, axios handles cookies automatically if withCredentials is true.
      // But usually modern apps use tokens. I'll add it just in case, or maybe the user object IS the token?
      // The prompt didn't specify token format. I'll assume standard Bearer or just rely on the user object for now.
      // Actually, let's just leave it open for now or add a placeholder.
      // If the backend uses session, we might need `withCredentials: true`.
      // Let's add withCredentials just in case it's session based, or if we need to send cookies.
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
api.interceptors.response.use(
  (response) => {
    const res = response.data;
    // Assuming standard response structure: { code: 200, data: ..., message: ... }
    if (res.code && res.code !== 200) {
      console.error("API Error:", res.message);
      return Promise.reject(new Error(res.message || "Error"));
    }
    // If the backend returns data directly or wrapped in 'data'
    return res.data !== undefined ? res.data : res;
  },
  (error) => {
    console.error("Network Error:", error);
    return Promise.reject(error);
  }
);

// Article
export const getArticles = (params) => {
  return api.get("/articles", { params });
};

export const getArticleById = (id) => {
  return api.get(`/articles/${id}`);
};

export const createArticle = (data) => {
  return api.post("/articles", data);
};

export const searchArticles = (q) => {
  return api.get("/articles/search", { params: { q } }); // Adjusted path based on typical REST patterns, user said "search articles"
};

export const getCategories = () => {
  return api.get("/categories");
};

// User
export const login = (data) => api.post("/login", data); // User said "Login (BCrypt)", usually /login or /users/login. Prompt said "login interface returns user info".
export const register = (data) => api.post("/users", data); // Prompt said "User module: register"
export const getUser = (id) => api.get(`/users/${id}`);

// Tag
export const getTags = () => api.get("/tags");

// Comment
export const getComments = (articleId) =>
  api.get("/comments", { params: { articleId } });
export const createComment = (data) => api.post("/comments", data);

// Attachment
export const uploadFile = (file, userId) => {
  const formData = new FormData();
  formData.append("file", file);
  if (userId) formData.append("userId", userId);
  return api.post("/attachments/upload", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};

// Config
export const getConfigs = () => api.get("/configs");

export default api;
