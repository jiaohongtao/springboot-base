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
public class ResetPasswdParam extends IpaBaseParam {

    @NotNull
    private String password;

    public static IpaBaseParamBuilder<?, ?> baseParam(ResetPasswdParam passwdParam) {
        return builder()
                .password(passwdParam.getPassword())
                .version(FreeipaConstant.VERSION);
    }
}
