package com.timeshards.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.timeshards.common.BusinessException;
import com.timeshards.entity.Article;
import com.timeshards.entity.ArticleMeta;
import com.timeshards.entity.Tag;
import com.timeshards.mapper.ArticleMapper;
import com.timeshards.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Map<String, Object> getArticleList(Long categoryId, Long tagId, Integer status, Integer isTop, String keyword, int page, int limit) {
        // 构建查询条件
        Article condition = new Article();
        condition.setCategoryId(categoryId);
        condition.setStatus(status);
        condition.setIsTop(isTop);
        condition.setTitle(keyword); // Mapper XML 中 title 字段被复用为关键词搜索

        // 开启分页
        PageHelper.startPage(page, limit);
        List<Article> list = articleMapper.selectList(condition);
        PageInfo<Article> pageInfo = new PageInfo<>(list);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", pageInfo.getTotal());
        result.put("page", page);
        result.put("limit", limit);
        return result;
    }

    @Override
    public Article getArticleById(Long id) {
        Article article = articleMapper.selectDetailById(id);
        if (article == null) {
            throw new BusinessException("文章不存在");
        }
        return article;
    }

    @Override
    public Article getArticleBySlug(String slug) {
        Article article = articleMapper.selectDetailBySlug(slug);
        if (article == null) {
            throw new BusinessException("文章不存在");
        }
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createArticle(Article article) {
        // 1. 处理 Slug
        if (article.getSlug() == null || article.getSlug().trim().isEmpty()) {
            article.setSlug(UUID.randomUUID().toString());
        }
        // 查重 Slug
        if (articleMapper.selectDetailBySlug(article.getSlug()) != null) {
            throw new BusinessException("文章别名(Slug)已存在，请更换");
        }

        // 2. 插入主体
        articleMapper.insert(article);

        // 3. 插入标签关联
        if (article.getTags() != null && !article.getTags().isEmpty()) {
            List<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toList());
            articleMapper.insertArticleTags(article.getId(), tagIds);
        }

        // 4. 插入 Meta 关联
        if (article.getMetas() != null && !article.getMetas().isEmpty()) {
            for (ArticleMeta meta : article.getMetas()) {
                meta.setArticleId(article.getId());
            }
            articleMapper.insertArticleMetas(article.getMetas());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(Article article) {
        // 1. 更新主体
        articleMapper.updateById(article);

        // 2. 更新标签 (先删后加)
        articleMapper.deleteTagsByArticleId(article.getId());
        if (article.getTags() != null && !article.getTags().isEmpty()) {
            List<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toList());
            articleMapper.insertArticleTags(article.getId(), tagIds);
        }

        // 3. 更新 Meta (先删后加)
        articleMapper.deleteMetasByArticleId(article.getId());
        if (article.getMetas() != null && !article.getMetas().isEmpty()) {
            for (ArticleMeta meta : article.getMetas()) {
                meta.setArticleId(article.getId());
            }
            articleMapper.insertArticleMetas(article.getMetas());
        }
    }

    @Override
    public void deleteArticle(Long id) {
        articleMapper.deleteById(id);
    }

    @Override
    public void incrementViewCount(Long id) {
        articleMapper.incrementViewCount(id);
    }
}
