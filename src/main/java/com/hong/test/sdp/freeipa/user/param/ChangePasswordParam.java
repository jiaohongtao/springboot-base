package com.hong.test.sdp.freeipa.user.param;

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
public class ChangePasswordParam {
    @NotNull
    String user;
    @NotNull
    String old_password;
    @NotNull
    String new_password;

    public String baseParam() {
        return "user=" + user + "&old_password=" + old_password + "&new_password=" + new_password;
    }
}
