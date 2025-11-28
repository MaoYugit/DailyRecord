package com.timeshards.controller;

import com.timeshards.common.ApiResponse;
import com.timeshards.entity.Category;
import com.timeshards.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category Management APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get All Categories", description = "Get all categories")
    @GetMapping
    public ApiResponse<List<Category>> getCategories() {
        return ApiResponse.success(categoryService.getAllCategories());
    }
}
