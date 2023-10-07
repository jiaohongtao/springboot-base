package com.hong.test.sdp.freeipa.user.entity;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/19
 */
public enum FreeipaErrorCode {

    USER_DEL_ERROR(4001, "删除失败"),
    USER_ENABLE_ERROR(4009, "启用失败"),
    USER_DISABLE_ERROR(4010, "禁用失败");

    private Integer code;
    private String msg;

    FreeipaErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
