package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 博客标签实体类
 * 对应数据库表: blog_tag
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 标签名称
     * 对应字段: name (varchar)
     */
    private String name;

    /**
     * URL别名
     * 对应字段: slug (varchar)
     */
    private String slug;

    /**
     * 创建时间
     * 对应字段: create_time (datetime)
     */
    private LocalDateTime createTime;
}