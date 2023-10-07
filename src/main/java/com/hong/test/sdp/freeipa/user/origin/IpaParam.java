package com.hong.test.sdp.freeipa.user.origin;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Ipa 请求参数
 */
@Builder
@Data
public class IpaParam {

    // {
    // "method":"user_show/1",
    //  "params":[
    //      ["admin"],
    //      {"all":true,"version":"2.237"}
    //  ]
    // }
    private String method;
    private List<Object> params;
}