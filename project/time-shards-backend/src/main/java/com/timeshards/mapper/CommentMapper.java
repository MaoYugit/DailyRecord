package com.timeshards.mapper;

import com.timeshards.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 查询评论列表
     * @param articleId 文章ID (可选，不传则查所有评论，用于后台)
     * @param status 状态 (可选，1-通过，0-待审核。不传则查所有)
     */
    List<Comment> selectList(@Param("articleId") Long articleId, @Param("status") Integer status);

    // 查单个
    Comment selectById(@Param("id") Long id);

    // 写入
    int insert(Comment comment);

    // 审核状态修改
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    // 逻辑删除
    int deleteById(@Param("id") Long id);
}