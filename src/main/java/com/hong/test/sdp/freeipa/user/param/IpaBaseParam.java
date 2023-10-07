package com.hong.test.sdp.freeipa.user.param;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/18
 */
@Getter
@Setter
@SuperBuilder
public class IpaBaseParam {
    /* 版本 */
    private String version;
}
