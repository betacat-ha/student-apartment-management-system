package cn.com.betacat;

import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestUserController {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUserList() {
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println("user = " + user);
        }
    }
}
