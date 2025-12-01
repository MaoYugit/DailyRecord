package com.timeshards.service.impl;

import com.timeshards.common.BusinessException;
import com.timeshards.entity.Category;
import com.timeshards.mapper.CategoryMapper;
import com.timeshards.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        return categoryMapper.selectBySlug(slug);
    }

    @Override
    public Category createCategory(Category category) {
        if (categoryMapper.selectByName(category.getName()) != null) {
            throw new BusinessException("分类名称已存在");
        }
        if (categoryMapper.selectBySlug(category.getSlug()) != null) {
            throw new BusinessException("分类别名已存在");
        }
        categoryMapper.insert(category);
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // TODO: 检查该分类下是否有文章，如果有则禁止删除或转移文章
        categoryMapper.deleteById(id);
    }
}
