package com.timeshards.service.impl;

import com.timeshards.common.BusinessException;
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
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return user;
    }

    @Override
    public User register(User user) {
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 默认头像
        if (user.getAvatar() == null) {
            user.setAvatar("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        }
        user.setRole(0); // 默认普通用户
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.updateById(user);
    }
}
