package com.yusael.travels.service;

import com.yusael.travels.entity.User;

public interface UserService {
    void register(User user);
    User login(User user);
}
