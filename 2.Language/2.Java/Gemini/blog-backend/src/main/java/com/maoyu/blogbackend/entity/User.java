package com.maoyu.blogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 对应数据库里的 tb_user 表
@Data // 自动生成 Getter/Setter
@Builder
@NoArgsConstructor // 生成无参构造
@AllArgsConstructor // 生成全参构造
@TableName("users")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private Integer age;
    private LocalDateTime createdAt;
}
