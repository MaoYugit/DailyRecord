package com.maoyu.blogbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maoyu.blogbackend.entity.Article;

/**
 * Service 接口
 * 继承 IService<Article> 后，自动拥有了：
 * save, removeById, updateById, getById, list, page, saveBatch 等方法
 */
public interface ArticleService extends IService<Article> {

    // 如果以后有复杂的自定义业务逻辑，可以在这里定义方法
    // 比如： void publishArticle(Article article);
}