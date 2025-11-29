import axios from "axios";

const service = axios.create({
    baseURL: "/api",
    timeout: 10000,
});

// Request interceptor
service.interceptors.request.use(
    (config) => {
        const user = JSON.parse(localStorage.getItem("user") || "{}");
        if (user.token) {
            // Assuming Bearer token, adjust if needed based on backend
            // config.headers['Authorization'] = `Bearer ${user.token}`;
            // For now, let's assume the backend might use a custom header or just relies on session/cookie if not specified.
            // But usually it's Authorization. I'll add it if the user object has a token.
            // Given the previous code didn't have it, I'll leave it commented out but ready.
            // actually, let's add it, it's standard.
            config.headers['Authorization'] = user.token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor
service.interceptors.response.use(
    (response) => {
        const res = response.data;
        // Assuming standard response structure: { code: 200, data: ..., message: ... }
        // If code is not 200, it's an error (unless it's a blob or something)
        if (res.code !== undefined && res.code !== 200) {
            // You might want to handle specific codes here (e.g., 401 logout)
            if (res.code === 401) {
                // Handle unauthorized
                localStorage.removeItem("user");
                window.location.href = "/login";
            }
            return Promise.reject(new Error(res.message || "Error"));
        }
        // Return the data part directly if it exists, or the whole response if structure is different
        return res.data !== undefined ? res.data : res;
    },
    (error) => {
        console.error("Network Error:", error);
        return Promise.reject(error);
    }
);

export default service;
