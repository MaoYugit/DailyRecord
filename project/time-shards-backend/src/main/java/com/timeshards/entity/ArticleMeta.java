package com.timeshards.entity;

import lombok.Data;

@Data
public class ArticleMeta {
    private Long id;
    private Long articleId;
    private String metaKey;
    private String metaValue;
}
