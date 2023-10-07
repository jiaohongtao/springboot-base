package com.hong.test.sdp.freeipa.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
import com.hong.test.sdp.freeipa.user.param.ChangePasswordParam;
import com.hong.test.sdp.freeipa.user.param.LoginParam;
import com.hong.test.sdp.freeipa.user.param.UserAddParam;
import com.hong.test.sdp.freeipa.user.util.FreeipaClientCompo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/19
 */
@Slf4j
@RestController
@RequestMapping("/ipa/user")
public class FreeipaController {

    @Resource
    private FreeipaClientCompo freeipaClient;

    @RequestMapping("/login")
    public Result<String> login() throws IOException, URISyntaxException {
        return freeipaClient.login(LoginParam.builder().user("admin").password("mima123456").build());
    }

    @RequestMapping("/all")
    public Result<JSONObject> all() throws IOException, URISyntaxException {
        // freeipaClient.init();
        return freeipaClient.searchAll();
    }

    /**
     * 添加的用户，需要登录重新设置新密码后才能使用
     */
    @RequestMapping("add")
    public Result<JSONObject> add() throws IOException, URISyntaxException {
        // freeipaClient.init();
        return freeipaClient.addUser("apic001",
                //UserAddParam.builder().givenname("apic").sn("cc").userpassword("123456").build());
                UserAddParam.builder().givenname("apic").userpassword("123456").build());
    }

    /**
     * 添加用户，该用户可直接登录使用
     *
     * @return 用户
     */
    @RequestMapping("add-user")
    public Result<JSONObject> addUser() throws IOException, URISyntaxException {
        String username = "apic002";
        String tmpPasswd = "12345678";
        String passwd = "123456";
        // freeipaClient.init();
        Result<JSONObject> userResult = freeipaClient.addUser(username,
                UserAddParam.builder().givenname("apic").sn("cc").userpassword(tmpPasswd).build());

        if (userResult.isFailed()) {
            return userResult;
        }
        log.info("添加成功: 【{}】", userResult);

        boolean result = freeipaClient.changePassword(ChangePasswordParam.builder()
                .user(username).old_password(tmpPasswd).new_password(passwd).build());
        if (!result) {
            return Result.failed("添加失败");
        }
        return userResult;
    }

    @RequestMapping
    public Result<JSONObject> get() throws IOException, URISyntaxException {
        // freeipaClient.init();
        return freeipaClient.showUser("apic001");
    }
}
