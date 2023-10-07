package com.hong.bean.dfjx;

import lombok.Builder;
import lombok.Data;

/**
 * 日报-周报-登录实体
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/28
 */
@Data
@Builder
public class LoginReq {
    private String username;
    private String password;
    private String code;
    private String uuid;
}
