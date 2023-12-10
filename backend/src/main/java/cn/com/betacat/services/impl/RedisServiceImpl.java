package cn.com.betacat.services.impl;

import cn.com.betacat.constant.RedisMessageConstant;
import cn.com.betacat.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void setResetPwdCode(String telephone, String authCode) {
        redisTemplate.opsForValue().set(telephone + RedisMessageConstant.SENDTYPE_RESETPWD, authCode, 5, TimeUnit.MINUTES);
    }

    public void getResetPwdCode(String telephone) {
        redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_RESETPWD);
    }
}
