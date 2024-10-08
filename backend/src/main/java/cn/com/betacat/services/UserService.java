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

package cn.com.betacat.services;

import cn.com.betacat.pojo.User;

public interface UserService {
    /**
     * 使用邮箱和密码获取用户信息
     * @param user 用户信息
     * @return user 用户信息
     */
    User getUserInfoByEmailAndPwd(User user);

    /**
     * 使用邮箱获取用户信息
     * @param email 用户邮箱
     * @return user 用户信息
     */
    User getUserInfoByEmail(String email);

    /**
     * 获取用户信息
     * @param token 用户token
     */
    User getUserInfoBy(String token);

    /**
     * 更新用户管理的楼栋
     * @param id 用户id
     * @param buildingId 楼栋id
     * @return 是否更新成功
     */
    Boolean updateBuildingAdminBy(Integer id, Integer buildingId);
}
