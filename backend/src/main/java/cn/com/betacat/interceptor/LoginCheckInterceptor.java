/*
 * Copyright 2023 BetaCat_HA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.betacat.interceptor;

import cn.com.betacat.pojo.Result;
import cn.com.betacat.util.JwtUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//自定义拦截器
@Component //当前拦截器对象由Spring创建和管理
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    //前置方式
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle .... ");
        //1.获取请求url
        String url = request.getRequestURL().toString();
        log.info("请求路径：{}", url);

        //2.判断请求url中是否包含无需登录的链接，如果包含，说明是登录操作，放行
        if(url.contains("/auth")){
            return true;//结束当前方法的执行
        }
        if (url.contains("/validate/Send4ResetPwd.do")) {
            return true;
        }
        // 放行验证码
        if (url.contains("/captcha")) {
            return true;
        }

        // 放行debug模式
        // if (request.getHeader("Token").equals("debug")) {
        //     return true;
        // }

        if (!request.getMethod().equals("OPTIONS")) {

            //3.获取请求头中的令牌（token）
            String token = request.getHeader("token");
            if (token == null) {
                token = request.getHeader("Token");
            }
            log.info("从请求头中获取的令牌：{}", token);

            //4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
            if (!StringUtils.hasLength(token)) {
                log.info("Token不存在");

                //创建响应结果对象
                Result responseResult = Result.error("NOT_LOGIN");
                //把Result对象转换为JSON格式字符串 (fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
                String json = JSONObject.toJSONString(responseResult);
                //设置响应头（告知浏览器：响应的数据类型为json、响应的数据编码表为utf-8）
                response.setContentType("application/json;charset=utf-8");
                //响应
                response.getWriter().write(json);

                return false;//不放行
            }

            //5.解析token，如果解析失败，返回错误结果（未登录）
            try {
                JwtUtils.parseJWT(token);
            } catch (Exception e) {
                log.info("令牌解析失败!");

                //创建响应结果对象
                Result responseResult = Result.error("NOT_LOGIN");
                //把Result对象转换为JSON格式字符串 (fastjson是阿里巴巴提供的用于实现对象和json的转换工具类)
                String json = JSONObject.toJSONString(responseResult);
                //设置响应头
                response.setContentType("application/json;charset=utf-8");
                //响应
                response.getWriter().write(json);

                return false;
            }
        }

        //6.放行
        return true;
    }
}