package com.timeshards.service.impl;

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
        return categoryMapper.findAll();
    }
}
