package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章实体类
 * 对应数据库表: blog_article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogArticle {
    /**
     * 主键ID
     * 对应字段: id (bigint)
     */
    private Long id;

    /**
     * 作者ID
     * 对应字段: user_id (bigint)
     */
    private Long userId;

    /**
     * 分类ID
     * 对应字段: category_id (bigint)
     */
    private Long categoryId;

    /**
     * 文章标题
     * 对应字段: title (varchar)
     */
    private String title;

    /**
     * 文章别名(URL)
     * 对应字段: slug (varchar)
     */
    private String slug;

    /**
     * 文章摘要
     * 对应字段: summary (varchar)
     */
    private String summary;

    /**
     * 文章内容(Markdown)
     * 对应字段: content (longtext)
     */
    private String content;

    /**
     * 文章内容(HTML)
     * 对应字段: content_html (longtext)
     */
    private String contentHtml;

    /**
     * 封面图片URL
     * 对应字段: cover_image (varchar)
     */
    private String coverImage;

    /**
     * 状态: 0-草稿, 1-发布, 2-下架
     * 对应字段: status (tinyint)
     */
    private Integer status;

    /**
     * 置顶: 0-否, 1-是
     * 对应字段: is_top (tinyint)
     */
    private Integer isTop;

    /**
     * 浏览量
     * 对应字段: view_count (int)
     */
    private Integer viewCount;

    /**
     * 评论数
     * 对应字段: comment_count (int)
     */
    private Integer commentCount;

    /**
     * 创建时间
     * 对应字段: create_time (datetime)
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 对应字段: update_time (datetime)
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除: 0-未删除, 1-已删除
     * 对应字段: is_deleted (tinyint)
     */
    private Integer isDeleted;
}
