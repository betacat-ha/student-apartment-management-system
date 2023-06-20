package cn.com.betacat.services.impl;

import cn.com.betacat.dao.RoleMapper;
import cn.com.betacat.entity.Role;
import cn.com.betacat.entity.User;
import cn.com.betacat.services.PermissionService;
import cn.com.betacat.services.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String getPermissionBy(String token) {
        User user = userService.getUserInfoBy(token);
        if (user == null) {
            return null;
        }

        Role role = roleMapper.selectById(user.getRoleId());
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
