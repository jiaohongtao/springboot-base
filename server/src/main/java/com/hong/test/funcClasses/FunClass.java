package com.hong.test.funcClasses;

/**
 * 普通函数式接口
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/12/13 15:44
 */
@FunctionalInterface
public interface FunClass {
    void getA();

    default String getB() {
        return "getB";
    }
}
