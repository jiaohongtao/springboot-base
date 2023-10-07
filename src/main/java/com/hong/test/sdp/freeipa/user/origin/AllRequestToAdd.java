package com.hong.test.sdp.freeipa.user.origin;

import com.hong.test.sdp.freeipa.login.LoginClient;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 新建并重置密码，新建之前获取Cookie
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/13
 */
public class AllRequestToAdd {

    public static void main(String[] args) throws UnsupportedEncodingException {

        // 获取 cookie
        // 新建用户
        // 修改密码

        String url = "https://sdp246.hadoop.com/ipa/session/login_password";
        String payload = "user=admin&password=mima123456";
        String cookie = LoginClient.getCookie(url, payload);

        if (StringUtils.isBlank(cookie)) {
            System.out.println("登录失败");
            System.exit(1);
        }

        System.out.println("登录通过，开始新增用户");

        String username = "hong8";
        String sn = "jiao8";
        String loginName = sn + username;
        String passwd = "12345678";

        url = "https://sdp246.hadoop.com/ipa/session/json";
        payload = "{\"method\":\"user_add\",\"params\":[[\"" + loginName + "\"],{" +
                "\"givenname\":\"" + username + "\"," +
                "\"sn\":\"" + sn + "\"," +
                "\"userpassword\": \"" + passwd + "\"," +
                "\"version\":\"2.237\"}]}";
        // cookie = "ipa_session=MagBearerToken=%2f07hG%2fp8jlfum%2bbkj7WlabqwU3qdKl9kAW%2br7LtBRk%2fHN1V5GJed4djiY7neMCITG2KmBLYh7Vmns7a2Rk1CDTdPMlLAyxS9CDveYLfKV6GqvT3zwrkZxYPV1XeYrZ18f0OJhiDpqhFlmXxwlJ9gX%2bq%2fneJR7pVh0aRwdpCOLxcpnMaA%2fecqEaYy0RrKBjEi; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

        String addResult = UserAdd.addUser(url, payload, cookie);

        System.out.println("用户新增结果：");
        System.out.println(addResult);

        System.out.println("新增成功，开始重置密码：");

        url = "https://sdp246.hadoop.com/ipa/session/change_password";
        String newPassword = "mima123456";
        payload = "user=" + URLEncoder.encode(loginName, "UTF-8") +
                "&old_password=" + URLEncoder.encode(passwd, "UTF-8") +
                "&new_password=" + URLEncoder.encode(newPassword, "UTF-8");

        String changeResult = ChangePasswd.changePasswd(url, payload, cookie);
        System.out.println("重置密码结果：");
        if (changeResult.contains("The old password or username is not correct.")) {
            System.out.println("请检查用户名是否填写正确");
        } else if (changeResult.contains("Password was changed.")) {
            System.out.println("修改成功");
        } else {
            System.out.println("都不属于");
        }
        System.out.println(changeResult);
    }
}
