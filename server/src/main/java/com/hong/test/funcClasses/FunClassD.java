package com.hong.test.funcClasses;

/**
 * 消费型接口
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/12/13 16:06
 */
@FunctionalInterface
public interface FunClassD<R> {
    void consume(R r);
}
