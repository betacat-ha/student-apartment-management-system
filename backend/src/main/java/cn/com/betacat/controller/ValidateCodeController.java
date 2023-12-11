package cn.com.betacat.controller;

import cn.com.betacat.constant.MessageConstant;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.SMSService;
import cn.com.betacat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ValidateCodeController {
    @Autowired
    private UserService userService;

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
     * @param email 邮箱
     * @return 统一结果
     */
    @RequestMapping("/validate/Send4ResetPwd.do")
    public Result Send4ResetPwd(@RequestParam String email) {
        User user = userService.getUserInfoByEmail(email);

        if (user == null) {
            return Result.error(MessageConstant.USER_NOT_EXIST);
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            return Result.error(MessageConstant.REST_PWD_PHONE_NOT_EXIST);
        }

        return smsService.sendRestPwdCode(user.getPhone()) ?
                Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS) :
                Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
