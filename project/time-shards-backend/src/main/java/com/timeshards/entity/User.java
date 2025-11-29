package com.timeshards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String nickname;
    private String avatar;
    private String bio;
    private Integer role; // 0-普通用户, 1-管理员
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
