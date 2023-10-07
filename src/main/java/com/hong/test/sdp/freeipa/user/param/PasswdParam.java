package com.hong.test.sdp.freeipa.user.param;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Getter
@Setter
@SuperBuilder
public class PasswdParam extends IpaBaseParam {

    @NotNull
    private String current_password;
    @NotNull
    private String password;

    // {" + "\"current_password\":\"1234\", " + "\"password\":\"123456\", " + "\"version\":\"2.237\"}
    public static IpaBaseParamBuilder<?, ?> baseParam(PasswdParam passwdParam) {
        return builder()
                .current_password(passwdParam.getCurrent_password())
                .password(passwdParam.getPassword())
                .version(FreeipaConstant.VERSION);
    }
}
