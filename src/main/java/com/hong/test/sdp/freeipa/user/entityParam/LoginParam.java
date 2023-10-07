package com.hong.test.sdp.freeipa.user.entityParam;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Builder
@Data
public class LoginParam {
    @NotNull
    String user;
    @NotNull
    String password;

    public String baseParam() {
        return "user=" + user + "&password=" + password;
    }
}
