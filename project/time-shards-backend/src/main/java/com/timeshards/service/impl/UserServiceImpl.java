package com.timeshards.service.impl;

import com.timeshards.entity.User;
import com.timeshards.mapper.UserMapper;
import com.timeshards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    @Override
    public User register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(0); // 默认普通用户
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }
}
