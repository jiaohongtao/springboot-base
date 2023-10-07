package com.hong.test.sdp.freeipa.user.param;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Setter
@Getter
@SuperBuilder
public class UserModParam extends IpaBaseParam {
    private boolean all;
    private boolean rights;
    private String givenname;
    private String sn;

    // {"all":true, "rights":true, "givenname":"jiao3", "sn":"ao3", "version":"2.237"}
    public static IpaBaseParamBuilder<?, ?> baseParam(UserModParam userModParam) {
        return builder()
                .givenname(userModParam.getGivenname())
                .sn(userModParam.getSn())
                .all(true).rights(true).version(FreeipaConstant.VERSION);
    }
}
