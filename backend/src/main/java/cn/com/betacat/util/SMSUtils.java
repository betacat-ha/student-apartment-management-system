package cn.com.betacat.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * 短信发送工具类
 */
public class SMSUtils {

    @Value("${aliyun.sms.signName}")
    public static String signName; // 签名

    @Value("${aliyun.sms.templateCode}")
    public static String templateCode; // 模板编号

    // 阿里云AccessKey ID
    @Value("${aliyun.sms.accessKeyId}")
    private static String accessKeyId;
    // 阿里云AccessKey Secret
    @Value("${aliyun.sms.accessKeySecret}")
    private static String accessKeySecret;

    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     * 发送短信
     *
     * @param telephone    电话号码
     * @param signName     签名
     * @param templateCode 模板编号
     * @param code         验证码
     * @return 返回的状态码为OK，则发送成功
     */
    public static String send(String telephone, String signName, String templateCode, String code) {
        try {
            Client client = SMSUtils.createClient(accessKeyId, accessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(telephone)
                    .setSignName(signName)  // 短信签名
                    .setTemplateCode(templateCode)  // 模板编号
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            // 发送短信，获取发送结果
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            // 将结果转成Map对象
            Map<String, Object> map = sendSmsResponse.toMap();
            // 获取主体部分
            Map<String, String> body = (Map<String, String>) map.get("body");
            System.out.println(body);
            return body.get("Code");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 失败返回空
        return null;
    }

    /**
     * 发送发送短信验证码
     * @param telephone
     * @param code
     * @return
     */
    public static String send(String telephone, String code) {
        return send(telephone, signName, templateCode, code);
    }
}