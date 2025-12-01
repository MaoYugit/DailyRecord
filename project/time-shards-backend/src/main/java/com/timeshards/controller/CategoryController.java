package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Category;
import com.timeshards.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取所有分类", description = "获取所有分类列表")
    @GetMapping
    public ApiResponse<List<Category>> getCategories() {
        return ApiResponse.success(categoryService.getAllCategories());
    }

    @Operation(summary = "获取分类详情(Slug)", description = "根据Slug获取分类")
    @GetMapping("/slug/{slug}")
    public ApiResponse<Category> getCategoryBySlug(@PathVariable String slug) {
        return ApiResponse.success(categoryService.getCategoryBySlug(slug));
    }

    @Operation(summary = "创建分类", description = "创建新分类")
    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        return ApiResponse.success(categoryService.createCategory(category));
    }

    @Operation(summary = "更新分类", description = "更新分类信息")
    @PutMapping
    public ApiResponse<String> updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return ApiResponse.success("分类更新成功");
    }

    @Operation(summary = "删除分类", description = "删除分类")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("分类删除成功");
    }
}
