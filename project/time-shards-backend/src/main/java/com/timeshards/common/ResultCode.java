package com.timeshards.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 统一返回状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 失败
    FAILED(500, "操作失败"),

    // 常见业务错误
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final int code;
    private final String message;
}