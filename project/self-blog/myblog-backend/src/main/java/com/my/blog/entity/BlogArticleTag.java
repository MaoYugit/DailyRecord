package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章-标签关联实体类
 * 对应数据库表: blog_article_tag
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticleTag {

    /**
     * 文章ID
     * 对应字段: article_id (bigint)
     */
    private Long articleId;

    /**
     * 标签ID
     * 对应字段: tag_id (bigint)
     */
    private Long tagId;
}