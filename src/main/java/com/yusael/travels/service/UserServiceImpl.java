package com.yusael.travels.service;

import com.yusael.travels.dao.UserDAO;
import com.yusael.travels.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void register(User user) {
        if (userDAO.findByUsername(user.getUsername()) == null) {
            userDAO.save(user);
        } else {
            throw new RuntimeException("用户名已存在！");
        }
    }

    @Override
    public User login(User user) {
        User userDB = userDAO.findByUsername(user.getUsername());
        if (userDB != null) {
            if (userDB.getPassword().equals(user.getPassword())) {
                return userDB;
            }
            throw new RuntimeException("密码输入错误！");
        } else {
            throw new RuntimeException("用户名输入错误！");
        }
    }

}
