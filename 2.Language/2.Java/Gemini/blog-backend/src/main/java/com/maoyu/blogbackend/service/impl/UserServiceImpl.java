package com.maoyu.blogbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maoyu.blogbackend.dao.UserDao;
import com.maoyu.blogbackend.entity.User;
import com.maoyu.blogbackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
