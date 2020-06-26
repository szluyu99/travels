package com.yusael.travels.test;

import com.yusael.travels.TravelsApplication;
import com.yusael.travels.entity.User;
import com.yusael.travels.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TravelsApplication.class)
@RunWith(SpringRunner.class)
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("zhenyu");
        user.setPassword("123");
        user.setEmail("123@qq.com");
        userService.register(user);
    }

}
