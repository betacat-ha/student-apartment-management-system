package cn.com.betacat.controller;

import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.User;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public Result login(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return Result.error(400, "用户名或密码错误");
        }

        User loginUser = userService.loginByEmailAndPwd(user);

        if (loginUser == null) {
            return Result.error(403, "用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("email", loginUser.getEmail());
        claims.put("name", loginUser.getName());

        String token = JwtUtils.generateJwt(claims);

        return Result.success(token);
    }
}
