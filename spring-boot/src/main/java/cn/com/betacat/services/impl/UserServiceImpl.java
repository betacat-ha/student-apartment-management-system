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

import cn.com.betacat.dao.UserMapper;
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

    @Override
    public Boolean updateBuildingAdminBy(Integer id, Integer buildingId) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        user.setBuildingId(buildingId);
        userMapper.updateById(user);
        return true;
    }
}
