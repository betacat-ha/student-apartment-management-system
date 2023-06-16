package cn.com.betacat.services.impl;

import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.entity.User;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginByEmailAndPwd(User user) {
        return userMapper.getByEmailAndPassword(user);
    }

    @Override
    public User getUserInfoBy(String token) {
        Integer id = (Integer) JwtUtils.parseJWT(token).get("id");
        return userMapper.selectById(id);
    }
}
