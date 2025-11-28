package com.timeshards.mapper;

import com.timeshards.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("SELECT c.*, a.title as articleTitle, u.avatar " +
            "FROM blog_comment c " +
            "LEFT JOIN blog_article a ON c.article_id = a.id " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "WHERE c.article_id = #{articleId} AND c.is_deleted = 0 AND c.status = 1 " +
            "ORDER BY c.create_time ASC")
    List<Comment> findByArticleId(Long articleId);

    @Insert("INSERT INTO blog_comment(article_id, user_id, parent_id, nickname, email, website, content, status, is_admin, create_time) " +
            "VALUES(#{articleId}, #{userId}, #{parentId}, #{nickname}, #{email}, #{website}, #{content}, #{status}, #{isAdmin}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    @Update("UPDATE blog_comment SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
