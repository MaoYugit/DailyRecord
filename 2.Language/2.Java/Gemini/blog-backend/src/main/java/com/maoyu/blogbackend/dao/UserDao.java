package com.maoyu.blogbackend.dao;

import com.maoyu.blogbackend.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    // 模拟根据 ID 查询数据库
    public User getUserById(Integer id) {
        // 假装这是从 MySQL 查出来的数据
        if (id == 1) {
            return new User(1, "Admin", 18);
        }
        return new User(id, "Unknown", 0);
    }
}
