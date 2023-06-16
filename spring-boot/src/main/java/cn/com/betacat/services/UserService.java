package cn.com.betacat.services;

import cn.com.betacat.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param user 用户信息
     * @return user 用户信息
     */
    User loginByEmailAndPwd(User user);

    /**
     * 获取用户信息
     * @param token 用户token
     */
    User getUserInfoBy(String token);
}
