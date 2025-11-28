package com.timeshards.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private Long userId;
    private Long parentId;
    private String nickname;
    private String email;
    private String website;
    private String content;
    private Integer status; // 0-待审核, 1-通过, 2-垃圾评论
    private Integer isAdmin;
    private LocalDateTime createTime;
    private Integer isDeleted;
    
    // Transient
    private String articleTitle;
    private String avatar; // 评论人头像
}
