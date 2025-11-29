package com.timeshards.common;

import lombok.Getter;

/**
 * 自定义业务异常
 * 用于在 Service 层手动抛出逻辑错误（例如：用户名已存在、库存不足）
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    // 使用默认错误码 (500)
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.FAILED.getCode();
    }

    // 使用枚举构造
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    // 自定义错误码和消息
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}