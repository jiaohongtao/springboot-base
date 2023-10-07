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
public class UserFindParam extends IpaBaseParam {
    private boolean pkey_only;
    private int sizelimit;

    public static UserFindParam baseParam() {
        UserFindParam entity = builder().pkey_only(true).sizelimit(0).build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }
}
