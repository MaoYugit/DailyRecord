package com.my.blog.controller;

import com.my.blog.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {
    @GetMapping("/hello")
    public Result<String> hello(){
        return Result.success("hello");
    }
}
