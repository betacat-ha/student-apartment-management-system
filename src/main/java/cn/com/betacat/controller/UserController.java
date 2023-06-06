package cn.com.betacat.controller;

import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public Result query() {
        List<User> list = userMapper.selectList(null);
        return new Result(200, "ok", list);
    }

    @PostMapping("/user")
    public Result save(User user) {
        int i = userMapper.insert(user);
        if (i > 0) {
            return new Result(200, "ok", user);
        } else {
            return new Result(200, "查询失败", null);
        }
    }
}
