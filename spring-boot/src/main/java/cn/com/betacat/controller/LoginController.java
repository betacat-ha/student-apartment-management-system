/*
 * Copyright 2023 BetaCat_HA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.betacat.controller;

import cn.com.betacat.dao.UserDao;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return Result.error(400, "用户名或密码错误");
        }

        User loginUser = userService.loginByEmailAndPwd(user);

        if (loginUser == null) {
            return Result.error(403, "用户名或密码错误");
        }

        // 更新最后登录时间
        loginUser.setLastLoginTime(LocalDateTime.now());

        userDao.updateById(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("email", loginUser.getEmail());
        claims.put("name", loginUser.getName());

        String token = JwtUtils.generateJwt(claims);

        return Result.success(token);
    }
}
