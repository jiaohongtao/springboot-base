package com.hong.test.sdp.freeipa.user.param;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Setter
@Getter
@SuperBuilder
public class UserAddParam extends IpaBaseParam {
    // givenname 名 sn 姓 userpassword 密码
    @NotBlank
    private String givenname;
    @NotBlank
    private String userpassword;
    @NotBlank
    private String sn;

    public static IpaBaseParamBuilder<?, ?> baseParam(@Valid UserAddParam user) {
        return builder()
                .givenname(user.getGivenname())
                .userpassword(user.getUserpassword())
                .sn(user.getSn())
                .version(FreeipaConstant.VERSION);
    }
}
