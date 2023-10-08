package com.hong.test.funcClasses;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月23日
 */
public class FuncTest {
    public static void main(String[] args) {
        // 普通
        FunClass funClass = () -> System.out.println("普通型：ABCD");
        funClass.getA();

        // 供给型接口
        FunClassB<String> funClassB = () -> {
            String string = 1 + 2 + "ab";
            String string2 = "ab" + 1 + 2;
            return string + "-" + string2;
        };
        System.out.println("供给型：" + funClassB.getT());

        // 消费型接口
        FunClassD<String> funClassD = a -> System.out.println("消费型：" + a);
        funClassD.consume("abcd");

        // 消费供给型接口
        FunClassC<String, Boolean> funClassC = a -> a ? "ok" : "no";
        System.out.println("消费供给型：" + funClassC.getTR(false));
    }
}
