package com.maoyu.blogbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maoyu.blogbackend.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseMapper<Article> {
    // 此时，这里面虽然是空的，但已经拥有了单表 CRUD 的所有能力！
}
