package com.timeshards.common;

import lombok.Data;

@Data
public class ApiResponse<T> {

    // 状态码
    private int code;

    // 提示信息
    private String message;

    // 数据载体
    private T data;

    // 接口耗时/时间戳 (用于排查问题)
    private long timestamp;

    // 构造函数私有化，强制使用静态方法创建
    private ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功返回结果
     */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /**
     * 成功返回结果 (带数据)
     * @param data 获取的数据
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * 成功返回结果 (带数据和自定义消息)
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 失败返回结果 (默认 500)
     */
    public static <T> ApiResponse<T> error() {
        return error(ResultCode.FAILED);
    }

    /**
     * 失败返回结果 (使用常用枚举)
     */
    public static <T> ApiResponse<T> error(ResultCode errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return response;
    }

    /**
     * 失败返回结果 (自定义消息)
     */
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(ResultCode.FAILED.getCode());
        response.setMessage(message);
        return response;
    }

    /**
     * 失败返回结果 (完全自定义)
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}