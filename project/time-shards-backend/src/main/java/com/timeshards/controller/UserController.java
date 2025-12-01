package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.User;
import com.timeshards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "用户管理接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录", description = "通过用户名和密码登录")
    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody User loginReq) {
        User user = userService.login(loginReq.getUsername(), loginReq.getPassword());
        user.setPassword(null); // 不返回密码
        return ApiResponse.success(user);
    }

    @Operation(summary = "用户注册", description = "注册新用户")
    @PostMapping("/users")
    public ApiResponse<User> register(@RequestBody User user) {
        User newUser = userService.register(user);
        newUser.setPassword(null);
        return ApiResponse.success(newUser);
    }

    @Operation(summary = "获取用户信息", description = "根据ID获取用户信息")
    @GetMapping("/users/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return ApiResponse.success(user);
    }

    @Operation(summary = "更新用户信息", description = "更新用户资料")
    @PutMapping("/users")
    public ApiResponse<String> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ApiResponse.success("更新成功");
    }
}
