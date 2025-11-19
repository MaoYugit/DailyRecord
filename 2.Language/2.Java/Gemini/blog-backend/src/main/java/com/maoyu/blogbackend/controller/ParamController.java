package com.maoyu.blogbackend.controller;

import com.maoyu.blogbackend.entity.UserParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/param")
public class ParamController {
    @GetMapping("/articles/{id}")
    public String getArticleDetail(@PathVariable Integer id) {
        return "你请求的文章 ID 是: " + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword") String keyword, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        return "你正在搜索关键词: " + keyword + ", 第 " + page + " 页";
    }

    @PostMapping("/users")
    public String createUser(@RequestBody UserParam userParam) {
        return "用户名: " + userParam.getUsername() + ", 密码: " + userParam.getPassword();
    }
}
