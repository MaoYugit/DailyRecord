package com.timeshards.service.impl;

import com.timeshards.entity.Article;
import com.timeshards.mapper.ArticleMapper;
import com.timeshards.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Map<String, Object> getArticleList(Long categoryId, String keyword, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Article> list = articleMapper.findList(categoryId, keyword, offset, limit);
        long total = articleMapper.count(categoryId, keyword);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("limit", limit);
        return result;
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleMapper.findById(id);
        if (article != null) {
            article.setTags(articleMapper.findTagsByArticleId(id));
            article.setMetas(articleMapper.findMetasByArticleId(id));
        }
        return article;
    }

    @Override
    public void createArticle(Article article) {
        if (article.getSlug() == null || article.getSlug().isEmpty()) {
            article.setSlug(UUID.randomUUID().toString());
        }
        articleMapper.insert(article);
        
        // Save Tags
        if (article.getTags() != null) {
            for (com.timeshards.entity.Tag tag : article.getTags()) {
                articleMapper.addTag(article.getId(), tag.getId());
            }
        }
        
        // Save Metas
        if (article.getMetas() != null) {
            for (com.timeshards.entity.ArticleMeta meta : article.getMetas()) {
                meta.setArticleId(article.getId());
                articleMapper.addMeta(meta);
            }
        }
    }
}
