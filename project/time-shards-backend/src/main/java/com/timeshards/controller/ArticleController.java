package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Article;
import com.timeshards.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Article", description = "文章管理接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "获取文章列表", description = "支持分页、分类、标签、状态、置顶、关键词筛选")
    @GetMapping
    public ApiResponse<Map<String, Object>> getArticles(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer isTop,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(articleService.getArticleList(categoryId, tagId, status, isTop, keyword, page, limit));
    }

    @Operation(summary = "获取文章详情(ID)", description = "根据ID获取文章详情")
    @GetMapping("/{id}")
    public ApiResponse<Article> getArticle(@PathVariable Long id) {
        return ApiResponse.success(articleService.getArticleById(id));
    }

    @Operation(summary = "获取文章详情(Slug)", description = "根据Slug获取文章详情(SEO)")
    @GetMapping("/slug/{slug}")
    public ApiResponse<Article> getArticleBySlug(@PathVariable String slug) {
        return ApiResponse.success(articleService.getArticleBySlug(slug));
    }

    @Operation(summary = "创建文章", description = "发布新文章")
    @PostMapping
    public ApiResponse<String> createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ApiResponse.success("文章发布成功");
    }

    @Operation(summary = "更新文章", description = "更新文章内容及关联")
    @PutMapping
    public ApiResponse<String> updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);
        return ApiResponse.success("文章更新成功");
    }

    @Operation(summary = "删除文章", description = "逻辑删除文章")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ApiResponse.success("文章删除成功");
    }

    @Operation(summary = "增加阅读量", description = "文章阅读量+1")
    @PostMapping("/{id}/view")
    public ApiResponse<Void> incrementViewCount(@PathVariable Long id) {
        articleService.incrementViewCount(id);
        return ApiResponse.success();
    }
}
