package com.timeshards.mapper;

import com.timeshards.entity.Article;
import com.timeshards.entity.ArticleMeta;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("<script>" +
            "SELECT a.*, c.name as categoryName, u.nickname as authorName " +
            "FROM blog_article a " +
            "LEFT JOIN blog_category c ON a.category_id = c.id " +
            "LEFT JOIN sys_user u ON a.user_id = u.id " +
            "WHERE a.is_deleted = 0 AND a.status = 1 " +
            "<if test='categoryId != null'> AND a.category_id = #{categoryId} </if>" +
            "<if test='keyword != null and keyword != \"\"'> AND (a.title LIKE CONCAT('%', #{keyword}, '%') OR a.content LIKE CONCAT('%', #{keyword}, '%')) </if>" +
            "ORDER BY a.is_top DESC, a.create_time DESC " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Article> findList(@Param("categoryId") Long categoryId, 
                           @Param("keyword") String keyword, 
                           @Param("offset") int offset, 
                           @Param("limit") int limit);

    @Select("<script>" +
            "SELECT COUNT(*) FROM blog_article a " +
            "WHERE a.is_deleted = 0 AND a.status = 1 " +
            "<if test='categoryId != null'> AND a.category_id = #{categoryId} </if>" +
            "<if test='keyword != null and keyword != \"\"'> AND (a.title LIKE CONCAT('%', #{keyword}, '%') OR a.content LIKE CONCAT('%', #{keyword}, '%')) </if>" +
            "</script>")
    long count(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);

    @Select("SELECT a.*, c.name as categoryName, u.nickname as authorName " +
            "FROM blog_article a " +
            "LEFT JOIN blog_category c ON a.category_id = c.id " +
            "LEFT JOIN sys_user u ON a.user_id = u.id " +
            "WHERE a.id = #{id} AND a.is_deleted = 0")
    Article findById(Long id);

    @Insert("INSERT INTO blog_article(user_id, category_id, title, slug, summary, content, cover_image, status, create_time) " +
            "VALUES(#{userId}, #{categoryId}, #{title}, #{slug}, #{summary}, #{content}, #{coverImage}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Article article);

    // Tags
    @Insert("INSERT INTO blog_article_tag(article_id, tag_id) VALUES(#{articleId}, #{tagId})")
    void addTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    @Delete("DELETE FROM blog_article_tag WHERE article_id = #{articleId}")
    void deleteTagsByArticleId(Long articleId);

    @Select("SELECT t.* FROM blog_tag t JOIN blog_article_tag at ON t.id = at.tag_id WHERE at.article_id = #{articleId}")
    List<com.timeshards.entity.Tag> findTagsByArticleId(Long articleId);

    // Meta
    @Insert("INSERT INTO blog_article_meta(article_id, meta_key, meta_value) VALUES(#{articleId}, #{metaKey}, #{metaValue})")
    void addMeta(ArticleMeta meta);

    @Delete("DELETE FROM blog_article_meta WHERE article_id = #{articleId}")
    void deleteMetasByArticleId(Long articleId);

    @Select("SELECT * FROM blog_article_meta WHERE article_id = #{articleId}")
    List<com.timeshards.entity.ArticleMeta> findMetasByArticleId(Long articleId);
}
