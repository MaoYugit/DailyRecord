package com.timeshards.service;

import com.timeshards.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByArticleId(Long articleId);
    Comment createComment(Comment comment);
    void auditComment(Long id, Integer status);
}
