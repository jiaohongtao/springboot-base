package com.hong.test.sdp.freeipa.user.entity;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
public interface FreeipaMethod {

    /* 模糊搜索用户，根据 uid */
    String USER_FIND = "user_find";
    /* 获取用户，根据 uid */
    String USER_SHOW = "user_show/1";
    /* 用户添加 */
    String USER_ADD = "user_add";
    String CHANGE_PASSWORD = "change_password";
    /* 用户重置自己的密码、管理员重置密码 */
    String PASSWD = "passwd";
    /* 用户修改 */
    String USER_MOD = "user_mod";
    /* 禁用 */
    String USER_DISABLE = "user_disable";
    /* 启用 */
    String USER_ENABLE = "user_enable";
    /* 删除 */
    String USER_DEL = "user_del";
}
