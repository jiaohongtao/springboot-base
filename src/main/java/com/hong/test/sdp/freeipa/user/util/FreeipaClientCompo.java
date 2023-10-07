package com.hong.test.sdp.freeipa.user.util;

import com.alibaba.fastjson.JSONObject;
import com.hong.bean.Result;
import com.hong.exception.BusinessException;
import com.hong.test.sdp.freeipa.user.comp.TestComponent;
import com.hong.test.sdp.freeipa.user.entity.CommonConstant;
import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import com.hong.test.sdp.freeipa.user.entity.FreeipaMethod;
import com.hong.test.sdp.freeipa.user.entity.IgnoreInit;
import com.hong.test.sdp.freeipa.user.param.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

/**
 * Freeipa客户端工具类代理
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Slf4j
@Component
public class FreeipaClientCompo {

    @Resource
    private TestComponent testComponent;

    private static String COOKIE;

    private static String BASE_JSON_URL;

    /**
     * Freeipa客户端初始化
     */
    @IgnoreInit
    public void init() throws BusinessException, IOException, URISyntaxException {
        BASE_JSON_URL = okAddress(FreeipaConstant.BASE_JSON_URL);

        // TODO 通过 集群ID && Freeipa类型 确定一条 Freeipa 的参数，参数包括 域名、（端口）、账号、密码

        // TODO 获取数据库存储值
        String username = "admin";
        String password = "mima123456";
        init(username, password);
    }

    /**
     * @param username 账号
     * @param password 密码
     */
    @IgnoreInit
    private void init(@NotBlank String username, @NotBlank String password) throws BusinessException, IOException, URISyntaxException {
        Result<String> loginResult = login(LoginParam.builder().user(username).password(password).build());
        if (loginResult.isFailed()) {
            log.error(loginResult.getMessage());
            throw new BusinessException(loginResult.getMessage());
        }
        COOKIE = loginResult.getData();
    }

    /**
     * 获取完整地址
     *
     * @param preUrl 预地址
     * @return 完整地址
     */
    @IgnoreInit
    private String okAddress(String preUrl) {
        String domain = testComponent.get();
        return String.format(preUrl, domain);
    }

    /**
     * 登录
     *
     * @param loginParam 用户登录信息
     */
    @IgnoreInit
    public Result<String> login(@Valid LoginParam loginParam) throws IOException, URISyntaxException {
        HttpPost httpPost = HttpUtil.createHttpPost(
                okAddress(FreeipaConstant.LOGIN_URL),
                loginParam.baseParam(),
                Collections.singletonMap(HttpHeaders.CONTENT_TYPE, HttpUtil.CONTENT_TYPE_FORM_DATA)
        );
        String cookie = HttpUtil.loginGetCookie(httpPost);

        if (StringUtils.isBlank(cookie)) {
            return Result.failed("登录失败!");
        }
        return Result.success(cookie);
    }

    /**
     * 查询所有用户
     */
    public Result<JSONObject> searchAll() throws IOException, URISyntaxException {
        return searchUser("");
    }

    /**
     * 列表查询，可通过 用户登录名 模糊匹配
     */
    public Result<JSONObject> searchUser(@NotNull String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_FIND)
                .params(new Object[]{
                        new String[]{username},
                        UserFindParam.baseParam().build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 获取用户
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> showUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_SHOW)
                .params(new Object[]{
                        new String[]{username},
                        UserShowParam.baseParam().build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE));
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 添加用户
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> addUser(@NotBlank String username, @Valid UserAddParam user) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_ADD)
                .params(new Object[]{
                        new String[]{username},
                        UserAddParam.baseParam(user).build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        // 3007
        return handleResult(responseBody);
    }


    /**
     * 修改用户
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> modifyUser(@NotBlank String username, UserModParam userModParam) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_MOD)
                .params(new Object[]{
                        new String[]{username},
                        UserModParam.baseParam(userModParam).build()
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 修改自己的密码
     * 自己修改的密码，重新登录时，不需要再重新设置新密码，**需要使用自己的cookie**
     * 如果使用管理员的cookie，需要再重新登录时，需要重新设置新密码
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> passwd(@NotBlank String username, @Valid PasswdParam passwdParam) throws Exception {
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
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 管理员重置用户密码
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> resetPasswd(@NotBlank String username, @Valid ResetPasswdParam resetPasswdParam) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.PASSWD)
                .params(new Object[]{
                        new String[]{username},
                        ResetPasswdParam.baseParam(resetPasswdParam).build()
                })
                .build();

        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE));
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 重新登录时的修改密码，密码独立性
     *
     * @param changePasswordParam 修改用户信息
     */
    @IgnoreInit
    public boolean changePassword(@Valid ChangePasswordParam changePasswordParam) throws IOException, URISyntaxException {
        HttpPost httpPost = HttpUtil.createHttpPost(
                okAddress(FreeipaConstant.CHANGE_PASSWORD_URL),
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
     */
    public Result<JSONObject> delUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_DEL)
                .params(new Object[]{
                        new String[]{username},
                        UserDelParam.baseParam().build()
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 用户禁用
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> disableUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_DISABLE)
                .params(new Object[]{
                        new String[]{username},
                        Collections.singletonMap(CommonConstant.VERSION, FreeipaConstant.VERSION)
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 用户启用
     *
     * @param username 用户登录名
     */
    public Result<JSONObject> enableUser(@NotBlank String username) throws IOException, URISyntaxException {
        IpaParamReq ipaParamReq = IpaParamReq.builder()
                .method(FreeipaMethod.USER_ENABLE)
                .params(new Object[]{
                        new String[]{username},
                        Collections.singletonMap(CommonConstant.VERSION, FreeipaConstant.VERSION)
                })
                .build();
        HttpPost httpPost = HttpUtil.createHttpPost(
                BASE_JSON_URL,
                JSONObject.toJSONString(ipaParamReq),
                Collections.singletonMap(CommonConstant.COOKIE, COOKIE)
        );
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        return handleResult(responseBody);
    }

    /**
     * 处理返回结果
     *
     * @param responseBody 返回Body
     * @return 封装后的结果
     */
    private Result<JSONObject> handleResult(String responseBody) {
        JSONObject result = JSONObject.parseObject(responseBody);
        JSONObject errorData = result.getJSONObject(CommonConstant.ERROR);
        if (errorData != null && errorData.size() > 0) {
            return Result.failed(new JSONObject(), errorData.getString(CommonConstant.MESSAGE));
        }
        return Result.success(result);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        /*init();
        // searchAll();
        searchUser("jiao");*/
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
