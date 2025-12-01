package com.timeshards.service;

import com.timeshards.entity.User;

public interface UserService {
    User login(String username, String password);
    User register(User user);
    User getUserById(Long id);
    void updateUser(User user);
}
