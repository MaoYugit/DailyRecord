package com.timeshards.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j // 1. 加入日志注解 (依赖 Lombok)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. 处理自定义业务异常
     * 场景：Service 层手动 throw new BusinessException("余额不足");
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException e) {
        // 业务异常通常不需要打印堆栈，因为这是预期的逻辑错误
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 2. 处理参数校验异常 (Spring Validation)
     * 场景：前端传参为空，被 @NotNull 拦截
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        // 提取校验失败的字段和错误信息
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        String msg = sb.toString();
        log.warn("参数校验失败: {}", msg);
        return ApiResponse.error(ResultCode.VALIDATE_FAILED.getCode(), msg);
    }

    /**
     * 3. 处理所有未知的系统异常 (兜底方案)
     * 场景：空指针 (NPE)、数组越界、数据库连接失败
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        // 打印完整的堆栈信息，方便后端排查 Bug
        log.error("系统内部异常: ", e);

        // ⚠️ 给前端返回模糊信息，不要把 e.getMessage() 直接暴露出去
        // 除非是在开发环境 (可以通过配置文件判断)，否则生产环境建议统一返回 "系统繁忙"
        return ApiResponse.error(ResultCode.FAILED.getCode(), "系统繁忙，请稍后再试");
    }
}