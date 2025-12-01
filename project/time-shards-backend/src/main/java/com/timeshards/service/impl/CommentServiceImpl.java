package com.timeshards.service.impl;

import com.timeshards.entity.Comment;
import com.timeshards.entity.User;
import com.timeshards.mapper.CommentMapper;
import com.timeshards.mapper.UserMapper;
import com.timeshards.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Comment> getComments(Long articleId, Integer status) {
        return commentMapper.selectList(articleId, status);
    }

    @Override
    public Comment createComment(Comment comment) {
        // 1. 设置默认状态 (待审核)
        if (comment.getStatus() == null) {
            comment.setStatus(0); 
        }

        // 2. 自动填充用户信息
        if (comment.getUserId() != null) {
            User user = userMapper.selectById(comment.getUserId());
            if (user != null) {
                // 如果前端没传昵称，用数据库的
                if (comment.getNickname() == null || comment.getNickname().trim().isEmpty()) {
                    comment.setNickname(user.getNickname() != null ? user.getNickname() : user.getUsername());
                }
                // 如果前端没传邮箱，用数据库的
                if (comment.getEmail() == null || comment.getEmail().trim().isEmpty()) {
                    comment.setEmail(user.getEmail());
                }
                // 如果是管理员发评，标记一下
                if (user.getRole() != null && user.getRole() == 1) {
                    comment.setIsAdmin(1);
                    comment.setStatus(1); // 管理员评论直接通过
                }
            }
        }

        // 3. 游客兜底
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

    @Override
    public void deleteComment(Long id) {
        commentMapper.deleteById(id);
    }
}