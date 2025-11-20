package com.maoyu.blogbackend.controller;

import com.maoyu.blogbackend.common.Result;
import com.maoyu.blogbackend.entity.Article;
import com.maoyu.blogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 1. 发布文章 (Create)
    @PostMapping
    public Result<Article> publish(@RequestBody Article article) {
        // save 方法会自动把数据库生成的 ID 回填到 article 对象中
        articleService.save(article);
        return Result.success(article);
    }

    // 2. 查询所有文章 (Read List)
    @GetMapping
    public Result<List<Article>> getList() {
        // list() 是 IService 提供的查询所有方法
        return Result.success(articleService.list());
    }

    // 3. 根据 ID 查询文章详情 (Read Detail)
    @GetMapping("/{id}")
    public Result<Article> getDetail(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    // 4. 修改文章 (Update)
    // PUT http://localhost:8080/articles
    // Body: { "id": 1, "title": "新标题", "content": "新内容" }
    @PutMapping
    public Result<String> update(@RequestBody Article article) {
        // updateById 会根据传入的 id 修改其他字段
        boolean success = articleService.updateById(article);
        return success ? Result.success("修改成功") : Result.error("修改失败，ID不存在");
    }

    // 5. 删除文章 (Delete)
    // DELETE http://localhost:8080/articles/1
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = articleService.removeById(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}