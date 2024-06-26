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

import cn.com.betacat.constant.ErrorCodeConstant;
import cn.com.betacat.constant.MessageConstant;
import cn.com.betacat.constant.RedisMessageConstant;
import cn.com.betacat.dao.UserDao;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.Student;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.RedisService;
import cn.com.betacat.services.StudentService;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/mobile-login")
    public Result mobileLogin(@RequestBody() Map<String, Object> loginInfo) {
        Student student = new Student();
        student.setPhone((String) loginInfo.get("phone"));
        student.setPassword((String) loginInfo.get("password"));

        if (student.getPhone() == null || student.getPassword() == null) {
            return Result.error(400, "用户名或密码错误");
        }

        Student loginStudent = studentService.getStudentInfoByPhoneAndPwd(student);

        if (loginStudent == null) {
            return Result.error(403, "用户名或密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginStudent.getId());
        claims.put("phone", loginStudent.getEmail());
        claims.put("name", loginStudent.getName());

        String token = JwtUtils.generateJwt(claims);

        // 创建一个 Map 来存储生成的 token
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);

        return Result.success(resultMap);
    }


    @PostMapping("/login")
    public Result login(@RequestBody() Map<String, Object> loginInfo) {
        User user = new User();
        user.setEmail((String) loginInfo.get("email"));
        user.setPassword((String) loginInfo.get("password"));
        String verifyCode = (String) loginInfo.get("verifyCode");
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification((String) loginInfo.get("verifyCode"));

        if (user.getEmail() == null || user.getPassword() == null) {
            return Result.error(400, "用户名或密码错误");
        }

        User loginUser = userService.getUserInfoByEmailAndPwd(user);

        if (loginUser == null) {
            return Result.error(403, "用户名或密码错误");
        }

        // 验证码校验
        if (verifyCode == null || verifyCode.isEmpty() || !captchaService.verification(captchaVO).isSuccess()) {
            return Result.error(ErrorCodeConstant.NEED_VERIFY_CODE, MessageConstant.NEED_VERIFY_CODE);
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

    @PostMapping("/resetPwd")
    public Result resetPwd(@RequestBody() Map<String, Object> resetInfo) {
        String email = (String) resetInfo.get("email");
        String newPassword = (String) resetInfo.get("newPassword");
        String verifyCode = (String) resetInfo.get("verifyCode");

        if (email == null || verifyCode == null || verifyCode.isEmpty()) {
            return Result.error(400, "请求参数错误");
        }

        User user = userService.getUserInfoByEmail(email);

        if (user == null) {
            return Result.error(403, "登录已失效，请重新登录");
        }

        // 验证码校验
        if (!verifyCode.equals(redisService.get(user.getPhone() + RedisMessageConstant.SENDTYPE_RESETPWD))) {
            return Result.error(ErrorCodeConstant.VERIFY_CODE_ERROR, MessageConstant.VERIFY_CODE_ERROR);
        }

        user.setPassword(newPassword);

        userDao.updateById(user);

        return Result.success();
    }
}
