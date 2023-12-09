package cn.com.betacat.constant;

public class RedisMessageConstant {
    public static final String SENDTYPE_LOGIN = "_002"; // 用于缓存登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "_003"; // 用于缓存找回密码时发送的验证码
    public static final String SENDTYPE_RESETPWD = "_004"; // 用于缓存重置密码时发送的验证码
}