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

    /**
     * 更新用户管理的楼栋
     * @param id 用户id
     * @param buildingId 楼栋id
     * @return 是否更新成功
     */
    Boolean updateBuildingAdminBy(Integer id, Integer buildingId);
}
