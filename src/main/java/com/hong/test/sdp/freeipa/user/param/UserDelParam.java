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
public class UserDelParam extends IpaBaseParam {
    private boolean preserve;

    public static IpaBaseParamBuilder<?, ?> baseParam() {
        return builder().preserve(false).version(FreeipaConstant.VERSION);
    }
}
