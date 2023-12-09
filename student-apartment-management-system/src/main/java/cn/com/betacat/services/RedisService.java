package cn.com.betacat.services;

public interface RedisService {
    void set(String key, Object value);

    Object get(String key);

    void delete(String key);

    void expire(String key, long timeout);

    /**
     * 设置重置密码的验证码
     * @param telephone 手机号
     * @param authCode 验证码
     */
    void setResetPwdCode(String telephone, String authCode);

    /**
     * 获取重置密码的验证码
     * @param telephone 手机号
     */
    void getResetPwdCode(String telephone);
}
