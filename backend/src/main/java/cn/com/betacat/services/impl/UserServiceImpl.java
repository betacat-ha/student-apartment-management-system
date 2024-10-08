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

import cn.com.betacat.dao.UserDao;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserInfoByEmailAndPwd(User user) {
        return userDao.getByEmailAndPassword(user);
    }

    @Override
    public User getUserInfoByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    @Override
    public User getUserInfoBy(String token) {
        Integer id = (Integer) JwtUtils.parseJWT(token).get("id");
        return userDao.selectById(id);
    }

    @Override
    public Boolean updateBuildingAdminBy(Integer id, Integer buildingId) {
        User user = userDao.selectById(id);
        if (user == null) {
            return false;
        }
        user.setBuildingId(buildingId);
        userDao.updateById(user);
        return true;
    }
}
