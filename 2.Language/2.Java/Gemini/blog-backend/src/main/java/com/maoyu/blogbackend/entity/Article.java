package com.maoyu.blogbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("posts")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long id;             // 文章ID (用 Long 防止文章太多 int 不够)
    private String title;        // 标题
    private String author;       // 作者
    private String content;      // 内容
    private LocalDateTime createTime; // 创建时间 (Java 8 推荐的时间类型)
}
