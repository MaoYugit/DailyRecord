package com.timeshards.mapper;

import com.timeshards.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章分类数据访问层
 * 对应表: blog_category
 */
@Mapper
public interface CategoryMapper {

    /**
     * 根据主键查询
     */
    Category selectById(@Param("id") Long id);

    /**
     * 根据别名查询 (用于前台 URL 路由)
     * 例如: /category/java -> slug = "java"
     */
    Category selectBySlug(@Param("slug") String slug);

    /**
     * 查询所有分类
     * 通常用于构建前台导航菜单或后台下拉列表
     * 结果按 sort 升序排列
     */
    List<Category> selectAll();

    /**
     * 根据名称查询 (用于防止重名)
     */
    Category selectByName(@Param("name") String name);

    /**
     * 新增分类
     */
    int insert(Category category);

    /**
     * 动态更新分类信息
     */
    int updateById(Category category);

    /**
     * 物理删除分类
     * 注意：Service 层需先检查该分类下是否有文章，否则不允许删除
     */
    int deleteById(@Param("id") Long id);
}