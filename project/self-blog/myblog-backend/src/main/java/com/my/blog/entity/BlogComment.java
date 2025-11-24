package com.my.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 博客评论实体类
 * 对应数据库表: blog_comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment {

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
     * 评论人ID (空代表游客)
     * 对应字段: user_id (bigint)
     */
    private Long userId;

    /**
     * 父评论ID (0代表顶级评论)
     * 对应字段: parent_id (bigint)
     */
    private Long parentId;

    /**
     * 评论人昵称
     * 对应字段: nickname (varchar)
     */
    private String nickname;

    /**
     * 评论人邮箱
     * 对应字段: email (varchar)
     */
    private String email;

    /**
     * 评论人网站
     * 对应字段: website (varchar)
     */
    private String website;

    /**
     * 评论内容
     * 对应字段: content (varchar)
     */
    private String content;

    /**
     * 状态: 0-待审核, 1-通过, 2-垃圾评论
     * 对应字段: status (tinyint)
     */
    private Integer status;

    /**
     * 是否博主回复: 0-否, 1-是
     * 对应字段: is_admin (tinyint)
     */
    private Integer isAdmin;

    /**
     * 评论时间
     * 对应字段: create_time (datetime)
     */
    private LocalDateTime createTime;

    /**
     * 逻辑删除: 0-未删除, 1-已删除
     * 对应字段: is_deleted (tinyint)
     */
    private Integer isDeleted;
}