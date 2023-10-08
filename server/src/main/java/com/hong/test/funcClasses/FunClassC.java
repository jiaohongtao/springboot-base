package com.hong.test.funcClasses;

/**
 * 消费供给型接口
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/12/13 16:05
 */
@FunctionalInterface
public interface FunClassC<T, R> {

    T getTR(R r);
}
