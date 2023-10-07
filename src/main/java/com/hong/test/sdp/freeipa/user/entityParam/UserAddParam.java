package com.hong.test.sdp.freeipa.user.entityParam;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Setter
@Getter
@Builder
public class UserAddParam extends IpaBaseParam {
    // givenname 名 sn 姓 userpassword 密码
    @NotBlank
    private String givenname;
    @NotBlank
    private String userpassword;
    @NotBlank
    private String sn;

    public static UserAddParam baseParam(@Valid UserAddParam user) {
        UserAddParam entity = builder()
                .givenname(user.getGivenname())
                .userpassword(user.getUserpassword())
                .sn(user.getSn())
                .build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }
}
