package com.timeshards.service;

import com.timeshards.entity.Article;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    /**
     * 获取文章列表 (分页 + 筛选)
     */
    Map<String, Object> getArticleList(Long categoryId, Long tagId, Integer status, Integer isTop, String keyword, int page, int limit);

    /**
     * 根据ID获取详情 (包含关联数据)
     */
    Article getArticleById(Long id);

    /**
     * 根据Slug获取详情 (SEO路由)
     */
    Article getArticleBySlug(String slug);

    /**
     * 创建文章
     */
    void createArticle(Article article);

    /**
     * 更新文章
     */
    void updateArticle(Article article);

    /**
     * 删除文章 (逻辑删除)
     */
    void deleteArticle(Long id);

    /**
     * 增加阅读量
     */
    void incrementViewCount(Long id);
}
