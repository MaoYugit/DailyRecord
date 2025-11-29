package com.timeshards.mapper;

import com.timeshards.entity.Article;
import com.timeshards.entity.ArticleMeta;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章核心 Mapper
 * 融合了文章主体管理 + 标签关联管理 + Meta扩展属性管理
 */
@Mapper
public interface ArticleMapper {

    // ==================== 查询 (Read) ====================

    /**
     * 【详情】根据ID查询 (高性能 5表联查)
     * 一次性带出：文章本体、作者、分类、标签列表、Meta列表
     */
    Article selectDetailById(@Param("id") Long id);

    /**
     * 【详情】根据Slug查询 (SEO路由用)
     */
    Article selectDetailBySlug(@Param("slug") String slug);

    /**
     * 【列表】条件查询
     * 支持：分类、标签(暂未实现复杂SQL)、用户、状态、关键词(标题+内容)
     * 自动处理分页需要配合 PageHelper 或手动 Limit
     */
    List<Article> selectList(Article article);

    /**
     * 【统计】根据条件统计总数 (用于分页计算)
     */
    long selectCount(Article article);

    // ==================== 写入 (Write) ====================

    /**
     * 1. 新增文章主体 (返回 ID)
     */
    int insert(Article article);

    /**
     * 2. 更新文章主体
     */
    int updateById(Article article);

    /**
     * 3. 逻辑删除文章
     */
    int deleteById(@Param("id") Long id);

    /**
     * 4. 阅读量 +1
     */
    int incrementViewCount(@Param("id") Long id);

    // ==================== 关联管理 (Relations) ====================
    // 你的代码里这些逻辑非常棒，我把它们转为 XML 实现以保持风格统一

    /**
     * 批量插入文章-标签关联
     * Service 层保存文章后，把 List<Long> tagIds 传进来
     */
    int insertArticleTags(@Param("articleId") Long articleId, @Param("tagIds") List<Long> tagIds);

    /**
     * 删除该文章的所有标签关联 (用于更新文章时，先删后加)
     */
    int deleteTagsByArticleId(@Param("articleId") Long articleId);

    /**
     * 批量插入扩展属性
     */
    int insertArticleMetas(@Param("metas") List<ArticleMeta> metas);

    /**
     * 删除该文章的所有扩展属性
     */
    int deleteMetasByArticleId(@Param("articleId") Long articleId);
}