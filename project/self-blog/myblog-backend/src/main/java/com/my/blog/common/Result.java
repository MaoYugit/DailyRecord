package com.my.blog.common;

import lombok.Data;

/**
 * 统一响应结果封装类
 * @param <T> 数据载体的类型 (比如 User, List<Article>, String 等)
 */
@Data // Lombok注解：自动生成 Getter, Setter, toString 等
public class Result<T> {

    // 状态码：200成功，其他失败
    private Integer code;

    // 提示信息
    private String msg;

    // 返回的数据
    private T data;

    // 构造方法私有化，强制使用静态方法创建对象
    private Result() {}

    // 全参构造
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回 (带数据)
     * 用法：return Result.success(user);
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功返回 (不带数据，比如删除成功)
     * 用法：return Result.success();
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 失败返回
     * 用法：return Result.error("用户名已存在");
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 失败返回 (自定义状态码)
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}