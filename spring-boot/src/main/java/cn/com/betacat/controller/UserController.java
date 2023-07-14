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

import cn.com.betacat.dao.UserMapper;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.PermissionService;
import cn.com.betacat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/api/user/current")
    public Result queryCurrent(@RequestHeader String token) {
        User user = userService.getUserInfoBy(token);
        if (user == null) {
            return new Result(403, "用户授权已失效", null);
        }

        return Result.success(user);
    }

    @GetMapping("/api/user")
    public Result query(@RequestHeader String token) {
        if (!permissionService.checkPermission(token, "USER_QUERY")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        List<User> list = userMapper.selectList(null);
        return new Result(200, "ok", list);
    }

    @PostMapping("/api/user")
    public Result save(User user, @RequestHeader String token) {
        if (!permissionService.checkPermission(token, "USER_UPDATE")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        int i = userMapper.insert(user);
        if (i > 0) {
            return new Result(200, "ok", user);
        } else {
            return new Result(200, "查询失败", null);
        }
    }

    @DeleteMapping("/api/user")
    public Result delete(Integer id, @RequestHeader String token) {
        if (!permissionService.checkPermission(token, "USER_UPDATE")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        int i = userMapper.deleteById(id);
        if (i > 0) {
            return Result.success(null);
        } else {
            return Result.error(404, "删除失败");
        }
    }
}
