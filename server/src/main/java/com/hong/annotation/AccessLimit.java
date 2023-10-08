package com.hong.annotation;

import java.lang.annotation.*;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/02/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AccessLimit {
    /** 指定的时间内 */
    long seconds();
    /** 请求的次数 */
    int maxCnt();
}
