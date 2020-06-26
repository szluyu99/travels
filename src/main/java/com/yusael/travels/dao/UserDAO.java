package com.yusael.travels.dao;

import com.yusael.travels.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    // 注册用户
    void save(User user);
    // 根据用户名查询用户
    User findByUsername(String username);
}
