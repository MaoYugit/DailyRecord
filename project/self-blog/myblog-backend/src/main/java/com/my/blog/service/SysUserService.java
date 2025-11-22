package com.my.blog.service;

import com.my.blog.entity.SysUser;

import java.util.List;

public interface SysUserService {
    /**
     * 1. 根据ID查询用户
     */
    SysUser getUserById(Long id);

    /**
     * 2. 根据用户名查询用户
     */
    SysUser selecByUserName(String username);

    /**
     * 3. 新增用户
     */
    void createUser(SysUser user);

    /**
     * 4. 修改用户
     */
    void updateUser(SysUser user);

    /**
     * 5. 删除用户
     */
    void deleteUser(Long id);

    /**
     * 6. 查询列表
     */
    List<SysUser> selectList();

}