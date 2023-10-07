package com.hong.test.sdp.freeipa.user.entityParam;

import com.hong.test.sdp.freeipa.user.entity.FreeipaConstant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Setter
@Getter
@Builder
public class UserModParam extends IpaBaseParam {
    private boolean all;
    private boolean rights;
    private String givenname;
    private String sn;

    // {"all":true, "rights":true, "givenname":"jiao3", "sn":"ao3", "version":"2.237"}
    public static UserModParam baseParam(UserModParam userModParam) {
        UserModParam entity = builder()
                .givenname(userModParam.getGivenname())
                .sn(userModParam.getSn())
                .all(true).rights(true).build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }
}
