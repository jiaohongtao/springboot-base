package com.hong.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/06/14
 */
@Controller
public class DemoController {
    //进入系统首页方法，如果没有登录，会跳转到CAS统一登录页面，登录成功后会回调该方法。
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    //登出
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        // http://<CAS Server的实际IP地址>:<端口>/cas/logout?service=http://<SSM的IP地址>:<SSM的IP端口>/ssm/sso/login.do
        // 与本地 cas 服务器联调成功
        return "redirect:http://www.cainiao.com:8080/cas/logout";
    }

}
