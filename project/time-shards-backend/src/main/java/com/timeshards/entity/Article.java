package com.timeshards.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Article {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String contentHtml;
    private String coverImage;
    private Integer status; // 0-Draft, 1-Published, 2-Offline
    private Integer isTop;
    private Integer viewCount;
    private Integer commentCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
    
    // Transient fields for joins
    private String categoryName;
    private String authorName;
    
    private java.util.List<Tag> tags;
    private java.util.List<ArticleMeta> metas;
}
