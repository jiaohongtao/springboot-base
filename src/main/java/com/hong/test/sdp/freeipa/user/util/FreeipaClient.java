package com.hong.test.sdp.freeipa.user.util;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
import com.hong.exception.BusinessException;
import com.hong.test.sdp.freeipa.user.entity.CommonConstant;
import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import com.hong.test.sdp.freeipa.user.entity.FreeipaErrorCode;
import com.hong.test.sdp.freeipa.user.entity.FreeipaMethod;
import com.hong.test.sdp.freeipa.user.param.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.HttpHeaders;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * 参数为对象的方法
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Slf4j
public class FreeipaClient {

    // public static String COOKIE = "ipa_session=MagBearerToken=YErjT8YhS%2f1LfgAlYBNWX3%2f4kS4MuJPYoq9qfUHfyDgpUlMnxO8%2bawJhygIpRAXennbeeVr0CC9%2fMPzvT9aBiKLDoMALh7z3QVjfMCd1aTKpCCP%2fZ6TmaaqUe00kw%2bbSB9nYt%2fqancMiIzwuATP%2bOhC0esB63bJvhNc9l%2bUo6aLQYWfaaZ3%2bEzWRFMlFy2fw; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";
    public static String COOKIE;

    /**
     * Freeipa客户端初始化
     */
    public static void init() throws BusinessException, IOException, URISyntaxException {
        // TODO 获取数据库存储值
        String username = "admin";
        String password = "mima123456";
        init(username, password);
    }

    /**
     * @param username 账号
     * @param password 密码
     */
    public static void init(@NotBlank String username, @NotBlank String password) throws BusinessException, IOException, URISyntaxException {
        Result loginResult = login(LoginParam.builder().user(username).password(password).build());
        if (loginResult.isFailed()) {
            log.error(loginResult.getMessage());
            throw new BusinessException(loginResult.getMessage());
        }
        COOKIE = (String) loginResult.getData();
    }

    /**
     * 登录
     *
     * @param loginParam 用户登录信息
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Result login(@Valid LoginParam loginParam) throws IOException, URISyntaxException {
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.LOGIN_URL,
                loginParam.baseParam(),
                Collections.singletonMap(HttpHeaders.CONTENT_TYPE, HttpUtil.CONTENT_TYPE_FORM_DATA)
        );
        // ipa_session=MagBearerToken=cssXK1%2brRYIfH3uUrlamuy7N1oQYfli2t%2bY9UFmfrqKVdvJwev9NWqVlMYtUVbOgIJ2ogq2YJ1wnBe0wtVdl6IqIsa6go66EAr3cc31XRanJF3WFXMeMRUa2Ix99Yqx10b35d2mPlGm7QiYZ4EzpcMfRKtZmwqrc24wfHPht%2bpJ6kzCdKOWmPjSFPIndoQv4;path=/ipa;httponly;secure;
        String cookie = HttpUtil.loginGetCookie(httpPost);
        // System.out.println(cookie);

        if (StringUtils.isBlank(cookie)) {
            return Result.failed("登录失败!");
        }
        return Result.success(cookie);
    }

    /**
     * 查询所有用户
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject searchAll() throws IOException, URISyntaxException {
        return searchUser("");
    }

    /**
     * 列表查询，可通过 用户登录名 模糊匹配
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject searchUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_FIND)
                .params(new Object[]{
                        new String[]{username},
                        UserFindParam.baseParam().build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 获取用户
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject showUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_SHOW)
                .params(new Object[]{
                        new String[]{username},
                        UserShowParam.baseParam().build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE));
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 添加用户
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject addUser(@NotBlank String username, @Valid UserAddParam user) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_ADD)
                .params(new Object[]{
                        new String[]{username},
                        UserAddParam.baseParam(user).build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 修改用户
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject modifyUser(@NotBlank String username, UserModParam userModParam) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_MOD)
                .params(new Object[]{
                        new String[]{username},
                        UserModParam.baseParam(userModParam).build()
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 修改自己的密码
     * 自己修改的密码，重新登录时，不需要再重新设置新密码，**需要使用自己的cookie**
     * 如果使用管理员的cookie，需要再重新登录时，需要重新设置新密码
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject passwd(@NotBlank String username, @Valid PasswdParam passwdParam) throws Exception {
        // 修改自己的密码，需用用自己的账号密码登录的Cookie
        init(username, passwdParam.getCurrent_password());

        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.PASSWD)
                .params(new Object[]{
                        new String[]{username},
                        PasswdParam.baseParam(passwdParam).build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 管理员重置用户密码
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject resetPasswd(@NotBlank String username, @Valid ResetPasswdParam resetPasswdParam) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.PASSWD)
                .params(new Object[]{
                        new String[]{username},
                        ResetPasswdParam.baseParam(resetPasswdParam).build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE));
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 重新登录时的修改密码，密码独立性
     *
     * @param changePasswordParam 修改用户信息
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static boolean changePassword(@Valid ChangePasswordParam changePasswordParam) throws IOException, URISyntaxException {
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.CHANGE_PASSWORD_URL,
                changePasswordParam.baseParam(),
                Collections.singletonMap(HttpHeaders.CONTENT_TYPE, HttpUtil.CONTENT_TYPE_FORM_DATA)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        System.out.println(responseBody);
        if (responseBody.contains("Password change successful") && responseBody.contains("Password was changed.")) {
            log.info("修改成功");
            return true;
        } else if (responseBody.contains("400 Bad Request")) {
            log.error("请检查账号密码是否正确!");
            return false;
        } else if (responseBody.contains("Password change rejected") && responseBody.contains("The old password or username is not correct.")) {
            log.error("旧密码或用户名不正确!");
            return false;
        }

        return false;

    }

    /**
     * 用户删除
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject delUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_DEL)
                .params(new Object[]{
                        new String[]{username},
                        UserDelParam.baseParam().build()
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);

        JSONObject errorData = result.getJSONObject(CommonConstant.ERROR);
        if (errorData != null && errorData.size() > 0) {
            if (FreeipaErrorCode.USER_DEL_ERROR.getCode().equals(errorData.getInteger(CommonConstant.CODE))) {
                log.info(errorData.getString("message"));
            }
        } else {
            // {"result":{"result":{"failed":[]},"summary":"已删除用户\"jiao006\"","value":["jiao006"]},"principal":"admin@HADOOP.COM","version":"4.6.8"}
            JSONObject resultData = result.getJSONObject("result");
            log.info(resultData.getString("summary"));
        }

        return result;
    }

    /**
     * 用户禁用
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject disableUser(@NotBlank String username) throws IOException, URISyntaxException {
        // String requestBody = "{\"method\":\"user_disable\"," + "\"params\":[[\"" + username + "\"],{\"version\":\"2.237\"}]}";
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_DISABLE)
                .params(new Object[]{
                        new String[]{username},
                        Collections.singletonMap(CommonConstant.VERSION, FreeipaConstant.VERSION)
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);

        JSONObject errorData = result.getJSONObject(CommonConstant.ERROR);
        if (errorData != null && errorData.size() > 0) {
            if (FreeipaErrorCode.USER_ENABLE_ERROR.getCode().equals(errorData.getInteger(CommonConstant.CODE))) {
                log.info("name: 【{}】, message: 【{}】",
                        errorData.getString(CommonConstant.NAME), errorData.getString(CommonConstant.MESSAGE));
            } else {
                log.error("禁用失败");
            }
        }
        return result;
    }

    /**
     * 用户启用
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject enableUser(@NotBlank String username) throws IOException, URISyntaxException {
        // String requestBody = "{\"method\":\"user_enable\"," + "\"params\":[[\"" + username + "\"],{\"version\":\"2.237\"}]}";
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_ENABLE)
                .params(new Object[]{
                        new String[]{username},
                        Collections.singletonMap(CommonConstant.VERSION, FreeipaConstant.VERSION)
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                FreeipaConstant.BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);

        JSONObject resultData = result.getJSONObject("result");
        if (resultData != null && resultData.size() > 0) {
            if (resultData.getBoolean("result")) {
                log.info("启用成功");
                log.info(resultData.getString("summary"));
            }
        } else {

            JSONObject errorData = result.getJSONObject(CommonConstant.ERROR);
            if (errorData != null && errorData.size() > 0) {
                if (FreeipaErrorCode.USER_ENABLE_ERROR.getCode().equals(errorData.getInteger(CommonConstant.CODE))) {
                    log.info("name: 【{}】, message: 【{}】",
                            errorData.getString(CommonConstant.NAME), errorData.getString(CommonConstant.MESSAGE));
                } else {
                    log.error("启用失败");
                }
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        init();
        // searchAll();
        searchUser("jiao");
//        searchUser("jiao");
//        showUser("hjiao");
//        addUser("apiadd003", UserAddParam.builder().givenname("api").sn("add").userpassword("1234").build());
//        modifyUser("apiadd003", UserModParam.builder().givenname("apii").sn("addd").build());
//        passwd("apiadd003", PasswdParam.builder().current_password("123456").password("1234").build());

//        resetPasswd("apiadd003", ResetPasswdParam.builder().password("1234567890").build());

//        System.out.println(
//                changePassword(ChangePasswordParam.builder()
//                        .user("apiadd003").old_password("1234567890").new_password("12345678").build()
//                )
//        );
//
//        disableUser("apiadd003");
//        enableUser("apiadd003");
//        delUser("apiadd003");
//        System.out.println(login(LoginParam.builder().user("admin").password("mima123456").build()));
    }

}
