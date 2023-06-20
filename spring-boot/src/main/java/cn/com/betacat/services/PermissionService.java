package cn.com.betacat.services;

public interface PermissionService {
    /**
     * 根据用户id获取权限
     * @param token 用户id
     * @return 权限字符串
     */
    String getPermissionBy(String token);

    Boolean checkPermission(String token, String permission);
}
