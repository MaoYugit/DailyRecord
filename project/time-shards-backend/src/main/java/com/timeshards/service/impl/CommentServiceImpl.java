package com.timeshards.service.impl;

import com.timeshards.entity.Comment;
import com.timeshards.entity.User; // 引入 User 实体
import com.timeshards.mapper.CommentMapper;
import com.timeshards.mapper.UserMapper; // 引入 UserMapper
import com.timeshards.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper; // 【新增】我们需要查用户信息

    @Override
    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    public Comment createComment(Comment comment) {
        // 1. 设置默认状态
        if (comment.getStatus() == null) {
            comment.setStatus(1);
        }

        // 2. 【关键修复】处理 nickname 为空的情况
        if (comment.getNickname() == null || comment.getNickname().trim().isEmpty()) {
            if (comment.getUserId() != null) {
                // 根据 userId 查询用户信息
                User user = userMapper.findById(comment.getUserId());
                if (user != null) {
                    // 优先使用昵称，没有则用用户名
                    String name = (user.getNickname() != null && !user.getNickname().isEmpty())
                            ? user.getNickname()
                            : user.getUsername();
                    comment.setNickname(name);
                }
            }
        }

        // 3. 最后的兜底：如果查不到用户，或者没传userId，给个默认值防止报错
        if (comment.getNickname() == null || comment.getNickname().trim().isEmpty()) {
            comment.setNickname("Anonymous");
        }

        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public void auditComment(Long id, Integer status) {
        commentMapper.updateStatus(id, status);
    }
}