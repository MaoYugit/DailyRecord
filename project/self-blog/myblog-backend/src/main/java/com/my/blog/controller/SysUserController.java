package com.my.blog.controller;

import com.my.blog.common.Result;
import com.my.blog.entity.SysUser;
import com.my.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController // = @Controller + @ResponseBody (返回JSON数据)
@RequestMapping("/user") // 所有接口统一前缀: localhost:8080/user
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取用户信息接口
     * GET /user/{id}
     */
    @GetMapping("/{id}")
    public Result<SysUser> getUser(@PathVariable("id") Long id) {
        SysUser user = sysUserService.getUserById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 处于安全考虑，密码通常不返回给前端，设为null（仅内存中修改，不影响数据库）
        user.setPassword(null);
        return Result.success(user);
    }

    /**
     * 新增用户接口 (测试用)
     * POST /user/add
     */
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody SysUser user) {
        // 补全一些后端生成的字段
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);

        sysUserService.createUser(user);
        return Result.success("用户创建成功");
    }
}