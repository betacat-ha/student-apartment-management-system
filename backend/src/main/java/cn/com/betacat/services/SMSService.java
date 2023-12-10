package cn.com.betacat.services;

public interface SMSService {
    boolean sendValidateCode(String telephone, String sendType);
    boolean sendRestPwdCode(String telephone);
}
