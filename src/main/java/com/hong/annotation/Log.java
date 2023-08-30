package com.hong.annotation;

import com.hong.enums.JhtService;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {

    JhtService module();

    String target();

    String operation();
}
