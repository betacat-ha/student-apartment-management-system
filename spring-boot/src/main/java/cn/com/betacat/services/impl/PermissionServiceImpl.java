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

package cn.com.betacat.services.impl;

import cn.com.betacat.dao.RoleDao;
import cn.com.betacat.pojo.Role;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.PermissionService;
import cn.com.betacat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @Override
    public String getPermissionBy(String token) {
        User user = userService.getUserInfoBy(token);
        if (user == null) {
            return null;
        }

        Role role = roleDao.selectById(user.getRoleId());
        if (role == null) {
            return null;
        }

        return role.getPermission();
    }

    @Override
    public Boolean checkPermission(String token, String permission) {
        String userPermission = getPermissionBy(token);
        if (userPermission == null) {
            return false;
        }

        return userPermission.contains(permission);
    }
}
