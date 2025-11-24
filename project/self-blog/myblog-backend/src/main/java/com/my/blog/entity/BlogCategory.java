package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 博客分类实体类
 * 对应数据库表: blog_category
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogCategory {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 分类名称
     * 对应字段: name (varchar)
     */
    private String name;

    /**
     * URL别名
     * 对应字段: slug (varchar)
     */
    private String slug;

    /**
     * 描述
     * 对应字段: description (varchar)
     */
    private String description;

    /**
     * 父分类ID (0代表顶级分类)
     * 对应字段: parent_id (bigint)
     */
    private Long parentId;

    /**
     * 排序 (数字越小越前)
     * 对应字段: sort (int)
     */
    private Integer sort;

    /**
     * 创建时间
     * 对应字段: create_time (datetime)
     */
    private LocalDateTime createTime;
}