package cn.com.betacat.controller;

import cn.com.betacat.constant.MessageConstant;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateCodeController {


    @Autowired
    private SMSService smsService;

    // /**
    //  * 后台手机快速登录时发送手机验证码 <br/><br/>
    //  * 请求路径: /validate/backendSend4Login.do <br/>
    //  * 请求方式: GET <br/>
    //  * 请求参数: String telephone <br/>
    //  * @param telephone 手机号
    //  * @return 统一结果
    //  */
    // @RequestMapping("/validate/backendSend4Login.do")
    // public Result backendSend4Login(String telephone) {
    //     return smsService.sendLoginCode(telephone) ?
    //             Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS) :
    //             Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
    // }

    /**
     * 修改密码时发送手机验证码 <br/><br/>
     * 请求路径: /validate/Send4ResetPwd.do <br/>
     * 请求方式: GET <br/>
     * 请求参数: String telephone <br/>
     * @param telephone 手机号
     * @return 统一结果
     */
    @RequestMapping("/validate/Send4ResetPwd.do")
    public Result Send4ResetPwd(String telephone) {
        return smsService.sendRestPwdCode(telephone) ?
                Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS) :
                Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
