package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户实体类
 * 对应数据库表: sys_user
 */
@Data                 // Lombok: 自动生成 Getter, Setter, toString
@NoArgsConstructor    // Lombok: 自动生成无参构造器
@AllArgsConstructor   // Lombok: 自动生成全参构造器
public class SysUser {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 用户名
     * 对应字段: username (varchar)
     */
    private String username;

    /**
     * 密码 (BCrypt加密)
     * 对应字段: password (varchar)
     */
    private String password;

    /**
     * 邮箱
     * 对应字段: email (varchar)
     */
    private String email;

    /**
     * 昵称
     * 对应字段: nickname (varchar)
     */
    private String nickname;

    /**
     * 头像URL
     * 对应字段: avatar (varchar)
     */
    private String avatar;

    /**
     * 简介
     * 对应字段: bio (varchar)
     */
    private String bio;

    /**
     * 角色: 0-普通用户, 1-管理员
     * 对应字段: role (tinyint)
     */
    private Integer role;

    /**
     * 创建时间
     * 对应字段: create_time (datetime)
     * 注意：我们开启了 map-underscore-to-camel-case，所以自动对应 create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * 对应字段: update_time (datetime)
     */
    private Date updateTime;

    /**
     * 逻辑删除: 0-未删除, 1-已删除
     * 对应字段: is_deleted (tinyint)
     */
    private Integer isDeleted;
}