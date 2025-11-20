package com.maoyu.blogbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maoyu.blogbackend.dao.ArticleDao;
import com.maoyu.blogbackend.entity.Article;
import com.maoyu.blogbackend.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {
    // 这里面什么都不用写！
    // 你已经拥有了 ArticleDao 的全部能力，而且被封装得更好用了。
    // 比如：this.save(article); // 自动调用 dao.insert(article)
}
