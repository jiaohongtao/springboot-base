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
public class UserDelParam extends IpaBaseParam {
    private boolean preserve;

    public static UserDelParam baseParam() {
        UserDelParam entity = builder().preserve(false).build();
        entity.setVersion(FreeipaConstant.VERSION);
        return entity;
    }
}
