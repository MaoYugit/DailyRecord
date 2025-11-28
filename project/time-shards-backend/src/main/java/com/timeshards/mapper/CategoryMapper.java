package com.timeshards.mapper;

import com.timeshards.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM blog_category ORDER BY sort ASC, create_time DESC")
    List<Category> findAll();
}
