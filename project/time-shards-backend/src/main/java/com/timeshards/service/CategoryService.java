package com.timeshards.service;

import com.timeshards.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryBySlug(String slug);
    Category createCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
}
