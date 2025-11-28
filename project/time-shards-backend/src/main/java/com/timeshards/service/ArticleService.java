package com.timeshards.service;

import com.timeshards.entity.Article;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String, Object> getArticleList(Long categoryId, String keyword, int page, int limit);
    Article getArticleById(Long id);
    void createArticle(Article article);
}
