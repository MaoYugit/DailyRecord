package com.maoyu.blogbackend.controller;

import com.maoyu.blogbackend.common.Result;
import com.maoyu.blogbackend.entity.User;
import com.maoyu.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    // 1. 注入 Service
    @Autowired
    private UserService userService;

    /**
     * 新增用户
     * POST http://localhost:8080/users
     * Body: { "username": "maoyu", "age": 18 }
     */
    @PostMapping
    public Result CreateUser(@RequestBody User user){
        userService.save(user);
        return Result.success("用户创建成功，ID为：" + user.getId());
    }

    /**
     * 根据 ID 查询用户
     * GET http://localhost:8080/users/1
     */
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Integer id) {
        // 直接调用 MP 提供的 getById 方法
        User user = userService.getById(id);

        return Result.success(user);
    }

    /**
     * 查询所有用户 (测试用)
     * GET http://localhost:8080/users
     */
    @GetMapping
    public Result<?> getAllUsers() {
        return Result.success(userService.list());
    }
}
