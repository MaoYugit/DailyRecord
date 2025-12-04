package com.timeshards.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.timeshards.common.BusinessException;
import com.timeshards.entity.Article;
import com.timeshards.entity.ArticleMeta;
import com.timeshards.entity.Tag;
import com.timeshards.entity.User; // 引入 User 实体
import com.timeshards.mapper.ArticleMapper;
import com.timeshards.mapper.UserMapper; // 引入 UserMapper
import com.timeshards.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder; // 引入 Security上下文
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

    // --- 新增：注入 UserMapper ---
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getArticleList(Long categoryId, Long tagId, Integer status, Integer isTop, String keyword, int page, int limit) {
        // 构建查询条件
        Article condition = new Article();
        condition.setCategoryId(categoryId);
        condition.setStatus(status);
        condition.setIsTop(isTop);
        condition.setTitle(keyword);

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
        // ============================================================
        // 核心修复：自动设置当前登录用户的 ID
        // ============================================================

        // 1. 从 Spring Security 上下文中获取当前登录的用户名
        String currentUsername = null;
        try {
            currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            // 如果上下文为空，说明未经过 Token 认证
            throw new BusinessException("无法获取登录信息，请重新登录");
        }

        if (currentUsername == null || "anonymousUser".equals(currentUsername)) {
            throw new BusinessException("当前用户未登录");
        }

        // 2. 根据用户名查询数据库获取完整用户对象 (包含 ID)
        User currentUser = userMapper.selectByUsername(currentUsername);

        if (currentUser == null) {
            throw new BusinessException("当前登录用户数据异常(未找到用户)");
        }

        // 3. 将查到的 ID 赋值给文章对象
        // 这一步解决了 "Column 'user_id' cannot be null" 的报错
        article.setUserId(currentUser.getId());

        // ============================================================

        // 4. 处理 Slug
        if (article.getSlug() == null || article.getSlug().trim().isEmpty()) {
            article.setSlug(UUID.randomUUID().toString());
        }
        // 查重 Slug
        if (articleMapper.selectDetailBySlug(article.getSlug()) != null) {
            throw new BusinessException("文章别名(Slug)已存在，请更换");
        }

        // 5. 插入主体
        articleMapper.insert(article);

        // 6. 插入标签关联
        if (article.getTags() != null && !article.getTags().isEmpty()) {
            List<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toList());
            articleMapper.insertArticleTags(article.getId(), tagIds);
        }

        // 7. 插入 Meta 关联
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