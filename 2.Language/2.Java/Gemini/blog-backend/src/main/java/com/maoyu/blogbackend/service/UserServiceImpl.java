package com.maoyu.blogbackend.service;

import com.maoyu.blogbackend.dao.UserDao;
import com.maoyu.blogbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public  User findUser(Integer id) {
        // 业务逻辑：可以在这里加判断，比如 id < 0 抛出异常等
        System.out.println("Service层：正在处理业务逻辑...");
        return userDao.getUserById(id);
    }
}
