package com.maoyu.blogbackend.controller;

import com.maoyu.blogbackend.entity.User;
import com.maoyu.blogbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        System.out.println("Controller层：接收到请求，ID=" + id);
        // 调用 Service
        return userService.findUser(id);
    }
}
