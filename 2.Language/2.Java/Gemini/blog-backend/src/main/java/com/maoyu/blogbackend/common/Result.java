package com.maoyu.blogbackend.common;

import lombok.Data;

/**
 * 统一响应类
 * <T> 是泛型，代表 data 里的数据类型不确定，可能是 User，可能是 List<Article>，也可能没有。
 */
@Data // Lombok 注解：自动生成 Getter/Setter/toString
public class Result<T> {
    private Integer code; // 状态码：200成功，其他失败
    private String msg; // 提示信息
    private T data; // 返回的数据

    // 私有化构造方法，禁止外部直接 new，强制使用静态方法
    private Result() {
    }

    // 1. 成功的静态方法（没数据，比如删除成功）
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "success";
        return result;
    }

    // 2. 成功的静态方法（有数据，比如查询成功）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = "success";
        result.data = data;
        return result;
    }

    // 3. 失败的静态方法
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 500; // 暂时统一定义为 500，后期可以定义枚举
        result.msg = msg;
        return result;
    }
}
