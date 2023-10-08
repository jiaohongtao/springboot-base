package com.hong.util.date20200612;

import java.util.concurrent.TimeUnit;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月15日
 */
public class TreadUse {

    public static void main(String[] args) {
        long period = 1L * 10 * 60;
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    // 间隔一段时间轮询一次
                    TimeUnit.MILLISECONDS.sleep(period);
                    // 得到result后进行处理(比如将结果插入到数据库)
                    System.out.println("===线程执行中");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        thread.start();
    }
}