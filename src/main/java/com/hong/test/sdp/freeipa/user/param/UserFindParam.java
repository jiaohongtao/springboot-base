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
public class UserFindParam extends IpaBaseParam {
    private boolean pkey_only;
    private int sizelimit;

    public static IpaBaseParamBuilder<?, ?> baseParam() {
        return builder().pkey_only(true).sizelimit(0).version(FreeipaConstant.VERSION);
    }

    /*public static UserFindParam baseEntity() {
        UserFindParam entity = builder().pkey_only(true).sizelimit(0).build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }*/
}
