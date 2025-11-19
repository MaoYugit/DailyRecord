package com.maoyu.blogbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 1. 标记这是一个控制器，并且返回 JSON/字符串 数据
/**
 * 是一个组合注解。
 * 等同于 @Controller + @ResponseBody。
 * 意味着这个类的方法返回的数据直接写入 HTTP 响应体（JSON/字符串），而不是跳转页面。
 */
@RestController
public class HelloController {
    // 2. 定义映射路径：当浏览器访问 /hello 时触发
    /**
     * 路由映射：当浏览器发送 GET 请求访问 /hello 路径时，触发此方法。
     */
    @GetMapping("/hello")
    public String hello(){
        // 3. 返回的内容直接显示在浏览器上
        return "Hello World";
    }
}
