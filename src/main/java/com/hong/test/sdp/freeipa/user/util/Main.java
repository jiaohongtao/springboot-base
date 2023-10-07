package com.hong.test.sdp.freeipa.user.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数为字符串的类
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Slf4j
public class Main {

    public static final String COOKIE = "ipa_session=MagBearerToken=bRQw3qaWQoDM5go4%2baIJ0%2bx6VMAvO2tyw1XQrFhQlngpboMXxXfZQpbrKCyEhJPRpVuxZmfTZON1SMgWw3uBcadQJ%2f6D6D0Mmhe%2fK4e7MuUfFVosqWJtqQ24ydiSZVAqORu%2fBgn6rMunoBmqhqSGR%2fnbgsYhbxrHv9dqCpl%2bjGTim8NNLAg4BX%2bNc0uty5rk; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3";

    public static void main(String[] args) throws IOException, URISyntaxException {

//        searchAll();
//        searchUser("jiao");
//        showUser("hjiao");
//
//        addUser("apiadd002");
//        modifyUser("apiadd002");
//        passwd("apiadd002");
//        // 不可用，参数等不共用
//        resetPasswd("apiadd002");
//        System.out.println(changePasswd("apiadd002"));
//
//        disableUser("apiadd002");
//        enableUser("apiadd002");
//        delUser("jiao006");
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
    public static JSONObject searchUser(String username) throws IOException, URISyntaxException {
        // {
        //  "pkey_only": true,
        //  "sizelimit": 0,
        //  "version": "2.237"
        //}
        // 返回信息太多
        // String requestBody = "{\"method\":\"user_find\",\"params\":[[\"" + username + "\"]," + "{\"all\":true,\"version\":\"2.237\"}]}";
        String requestBody = "{\"method\":\"user_find\"," + "\"params\":[[\"" + username + "\"]," + "{\"pkey_only\":true, \"sizelimit\":0, \"version\":\"2.237\"}]}";
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", COOKIE);
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
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
    public static JSONObject showUser(String username) throws IOException, URISyntaxException {
        // 根据 uid 精确查找用户
        String requestBody = "{\"method\":\"user_show/1\"," + "\"params\":[[\"" + username + "\"]," + "{\"all\":true,\"version\":\"2.237\"}]}";
        /*String requestBody = "{\"method\":\"user_show/1\"," +
                "\"params\":[[\"" + username + "\"]," +
                "{\"all\":false,\"version\":\"2.237\"}]}";*/
        // HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", "ipa_session=MagBearerToken=qumyqcuVzUf4l7fOyywnrljtiQPBv4doi6zzFYqfA7ivWUr8LOts4vtWrcGHKpHQeXEiWJOdy98KUJ5FqAGHOxMX9B2DEX%2fIP0l6BUDawmchhVxP9X2yq6Ks8Dnyvq3IVFoebAmwQcPOIehyKkclyY6PJF0yijkCdNhQ9PwFvHHiAEv07b2n9ILsuMi6c3sN; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
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
    public static JSONObject addUser(String username) throws IOException, URISyntaxException {
        // 根据 uid 精确查找用户
        // {
        //  "pkey_only": true,
        //  "sizelimit": 0,
        //  "version": "2.237"
        //}
        // givenname 名 sn 姓 userpassword 密码
        String requestBody = "{\"method\":\"user_add\"," + "\"params\":[[\"" + username + "\"]," + "{\"givenname\":\"jiao\", \"sn\":\"ao\", \"userpassword\":\"mima1234\", \"version\":\"2.237\"}]}";
        // HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", "ipa_session=MagBearerToken=qumyqcuVzUf4l7fOyywnrljtiQPBv4doi6zzFYqfA7ivWUr8LOts4vtWrcGHKpHQeXEiWJOdy98KUJ5FqAGHOxMX9B2DEX%2fIP0l6BUDawmchhVxP9X2yq6Ks8Dnyvq3IVFoebAmwQcPOIehyKkclyY6PJF0yijkCdNhQ9PwFvHHiAEv07b2n9ILsuMi6c3sN; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
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
    public static JSONObject modifyUser(String username) throws IOException, URISyntaxException {
        // 根据 uid 精确查找用户
        // {
        //  "pkey_only": true,
        //  "sizelimit": 0,
        //  "version": "2.237"
        //}
        // givenname 名 sn 姓 userpassword 密码
        String requestBody = "{\"method\":\"user_mod\"," + "\"params\":[[\"" + username + "\"]," + "{" + "\"all\":true, " + "\"rights\":true, " + "\"givenname\":\"jiao3\", " + "\"sn\":\"ao3\", \"version\":\"2.237\"}]}";
        // HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", "ipa_session=MagBearerToken=qumyqcuVzUf4l7fOyywnrljtiQPBv4doi6zzFYqfA7ivWUr8LOts4vtWrcGHKpHQeXEiWJOdy98KUJ5FqAGHOxMX9B2DEX%2fIP0l6BUDawmchhVxP9X2yq6Ks8Dnyvq3IVFoebAmwQcPOIehyKkclyY6PJF0yijkCdNhQ9PwFvHHiAEv07b2n9ILsuMi6c3sN; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 修改自己的密码
     * 自己修改的密码，重新登录时，不需要再重新设置新密码
     *
     * @param username 用户登录名
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject passwd(String username) throws IOException, URISyntaxException {
        // 根据 uid 精确查找用户
        // {
        //  "pkey_only": true,
        //  "sizelimit": 0,
        //  "version": "2.237"
        //}
        // givenname 名 sn 姓 userpassword 密码
        String requestBody = "{\"method\":\"passwd\"," + "\"params\":[[\"" + username + "\"]," + "{" + "\"current_password\":\"1234\", " + "\"password\":\"123456\", " + "\"version\":\"2.237\"}]}";
        // HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", "ipa_session=MagBearerToken=1CIJ7772LwMPbVTNGfCPo3JpV3RbaFGUK5Olt36pa0mBgRfS9ceBNYXSWiC0pwafOH9w%2bN8hfYRufjv8kp6C%2bKHJmmKBIoxfcTTHpmX6eqe3Zxbkx8f1yCwUVOmSPv2IZDSTWvsffvBdifcjdxlqM%2b3Cjs1LCifG4pQ1YI3CfaqLfXrEVYcdVfgTbV%2bLffNksbIDL5oNjYucDuTWI2oeog%3d%3d; grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
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
    public static JSONObject resetPasswd(String username) throws IOException, URISyntaxException {
        String requestBody = "{\"method\":\"passwd\"," + "\"params\":[[\"" + username + "\"]," + "{" + "\"password\":\"123456\", " + "\"version\":\"2.237\"}]}";
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", COOKIE);
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);
        return result;
    }

    /**
     * 重新登录时的修改密码，密码独立性
     *
     * @param username
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static boolean changePasswd(String username) throws IOException, URISyntaxException {
        String url = "https://sdp246.hadoop.com/ipa/session/change_password";
        String requestBody = "user=" + username + "&old_password=123456&new_password=1234567890";

        Map<String, String> headers = new HashMap<>();
        // headers.put("Cookie", "grafana_session=ec66fe648eef13e6094cb4d8fcc92be3");
        headers.put(HttpHeaders.CONTENT_TYPE, HttpUtil.CONTENT_TYPE_FORM_DATA);

        HttpPost httpPost = HttpUtil.createHttpPost(url, requestBody, headers);
        String responseBody = HttpUtil.executeHttpPost(httpPost);
        System.out.println(responseBody);
        if (responseBody.contains("Password change successfulresponseBody") && requestBody.contains("Password was changed.")) {
            return true;
        } else if (responseBody.contains("400 Bad Request")) {
            log.error("请检查账号密码是否正确!");
            return false;
        } else if (responseBody.contains("Password change rejected") && requestBody.contains("The old password or username is not correct.")) {
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
    public static JSONObject delUser(String username) throws IOException, URISyntaxException {
        String requestBody = "{\"method\":\"user_del\"," + "\"params\":[[\"" + username + "\"]," + "{\"preserve\":\"false\", \"version\":\"2.237\"}]}";
        // HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody);
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", COOKIE);
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);

        JSONObject errorData = result.getJSONObject("error");
        if (errorData != null && errorData.size() > 0) {
            if (errorData.getInteger("code").equals(4001)) {
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
    public static JSONObject disableUser(String username) throws IOException, URISyntaxException {
        String requestBody = "{\"method\":\"user_disable\"," + "\"params\":[[\"" + username + "\"],{\"version\":\"2.237\"}]}";
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", COOKIE);
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
        String responseBody = HttpUtil.executeHttpPost(httpPost);

        JSONObject result = JSONObject.parseObject(responseBody);
        System.out.println(result);

        JSONObject errorData = result.getJSONObject("error");
        if (errorData != null && errorData.size() > 0) {
            if (errorData.getInteger("code").equals(4010)) {
                log.info("name: 【{}】, message: 【{}】", errorData.getString("name"), errorData.getString("message"));
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
    public static JSONObject enableUser(String username) throws IOException, URISyntaxException {
        String requestBody = "{\"method\":\"user_enable\"," + "\"params\":[[\"" + username + "\"],{\"version\":\"2.237\"}]}";
        Map<String, String> headers = new HashMap<>();
        headers.put("cookie", COOKIE);
        HttpPost httpPost = HttpUtil.createHttpPost("https://sdp246.hadoop.com/ipa/session/json", requestBody, headers);
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

            JSONObject errorData = result.getJSONObject("error");
            if (errorData != null && errorData.size() > 0) {
                if (errorData.getInteger("code").equals(4010)) {
                    log.info("name: 【{}】, message: 【{}】", errorData.getString("name"), errorData.getString("message"));
                } else {
                    log.error("禁用失败");
                }
            }
        }

        return result;
    }

}
