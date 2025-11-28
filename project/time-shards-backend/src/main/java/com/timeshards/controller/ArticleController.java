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
@RequestMapping("/api")
@Tag(name = "Article", description = "Article Management APIs")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "Get Article List", description = "Get list of articles with pagination and filters")
    @GetMapping("/articles")
    public ApiResponse<Map<String, Object>> getArticles(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(articleService.getArticleList(categoryId, null, page, limit));
    }

    @Operation(summary = "Search Articles", description = "Search articles by title or content")
    @GetMapping("/search")
    public ApiResponse<Map<String, Object>> searchArticles(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(articleService.getArticleList(null, q, page, limit));
    }

    @Operation(summary = "Get Article Detail", description = "Get article detail by ID")
    @GetMapping("/articles/{id}")
    public ApiResponse<Article> getArticle(@PathVariable Long id) {
        return ApiResponse.success(articleService.getArticleById(id));
    }

    @Operation(summary = "Create Article", description = "Create a new article")
    @PostMapping("/articles")
    public ApiResponse<String> createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ApiResponse.success("Article created successfully");
    }
}
