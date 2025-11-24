package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章扩展属性实体类
 * 对应数据库表: blog_article_meta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticleMeta {

    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 文章ID
     * 对应字段: article_id (bigint)
     */
    private Long articleId;

    /**
     * 属性名
     * 对应字段: meta_key (varchar)
     */
    private String metaKey;

    /**
     * 属性值 (大文本)
     * 对应字段: meta_value (longtext)
     */
    private String metaValue;
}