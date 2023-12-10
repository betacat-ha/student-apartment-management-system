package cn.com.betacat.services.impl;


import cn.com.betacat.constant.RedisMessageConstant;
import cn.com.betacat.services.SMSService;
import cn.com.betacat.util.SMSUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SMSServiceImpl implements SMSService {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean sendValidateCode(String telephone, String sendType) {
        // 生成4位数字验证码
        String authCode = RandomStringUtils.randomNumeric(4);

        // 发送短信
        // String isOk = SMSUtils.send(telephone, authCode);

        // 测试时，不发送短信，直接输出验证码
        String isOk = "OK";
        System.out.println("后台手机快速登录发送手机验证码: " + isOk);

        // 判断短信是否发送成功? 是否为OK
        if ("OK".equals(isOk)) {
            // 短信发送成功，把验证码放到Redis中,存活时间5分钟
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(telephone + sendType, authCode, 5, TimeUnit.MINUTES);

            System.out.println("验证码为: " + authCode);

            return true;
        } else {
            return false;
        }
    }

    public boolean sendRestPwdCode(String telephone) {
        return sendValidateCode(telephone, RedisMessageConstant.SENDTYPE_RESETPWD);
    }
}
