package com.hong.test.sdp.freeipa.user.entity;

/**
 * FreeIPA常量
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
public interface FreeipaConstant {
    // freeipa 版本
    String VERSION = "2.237";

    String NAME = "FREEIPA";

    // https://sdp246.hadoop.com/ipa/session/json
    String FREEIPA_BASE_PROTOCOL = "https";
    // String FREEIPA_BASE_URL = "sdp246.hadoop.com";
    String FREEIPA_BASE_URL = "%s";
    String FREEIPA_API_PRE = "/ipa/session";

    /**
     * 通用地址前缀
     */
    String API_URL_PRE = FREEIPA_BASE_PROTOCOL + "://" + FREEIPA_BASE_URL + FREEIPA_API_PRE;
    /**
     * 基础通用接口地址
     */
    String BASE_JSON_URL = API_URL_PRE + "/json";
    /**
     * 重新登录时的修改密码地址
     */
    String CHANGE_PASSWORD_URL = API_URL_PRE + "/change_password";
    String LOGIN_URL = API_URL_PRE + "/login_password";
}
