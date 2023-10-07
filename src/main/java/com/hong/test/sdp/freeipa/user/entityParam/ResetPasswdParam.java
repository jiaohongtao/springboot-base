package com.hong.test.sdp.freeipa.user.entityParam;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Getter
@Setter
@Builder
public class ResetPasswdParam extends IpaBaseParam {

    @NotNull
    private String password;

    public static ResetPasswdParam baseParam(ResetPasswdParam passwdParam) {
        ResetPasswdParam entity = builder()
                .password(passwdParam.getPassword())
                .build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }
}
