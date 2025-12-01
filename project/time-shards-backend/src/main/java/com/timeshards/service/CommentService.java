package com.timeshards.service;

import com.timeshards.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getComments(Long articleId, Integer status);
    Comment createComment(Comment comment);
    void auditComment(Long id, Integer status);
    void deleteComment(Long id);
}
