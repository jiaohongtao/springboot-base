package com.hong.test.sdp.freeipa.user.entityParam;

import lombok.Builder;
import lombok.Data;

/**
 * Ipa 请求参数
 */
@Builder
@Data
public class IpaParamReq {

    /* ipa 的方法 */
    private String method;
    /* ipa 参数 */
    private Object[] params;

    /*public static IpaParamReq buildParams(String method, Object... params) {
        Object[] objects = Arrays.stream(params).toArray();
        return IpaParamReq.builder().method(method).params(objects).build();
    }*/

    /*public IpaParamReq params(Object... objects) {
        this.params = objects;
        return this;
    }*/

}