package com.hong.test.funcClasses;

/**
 * 供给型接口
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/12/13 15:59
 */
@FunctionalInterface
public interface FunClassB<T> {
    T getT();
}
