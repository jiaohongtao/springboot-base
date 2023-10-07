package com.hong.test.sdp.freeipa.user.entity;

import java.lang.annotation.*;

/**
 * 忽略Freeipa的初始化
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IgnoreInit {
}
