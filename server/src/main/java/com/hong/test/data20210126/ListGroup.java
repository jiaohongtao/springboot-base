package com.hong.test.data20210126;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月12日
 */
public class ListGroup {

    public static void main(String[] args) throws Exception {
        List<Object> list = new ArrayList<>();
        for (int i = 1; i <= 50000; i++) {
            list.add(String.valueOf(i));
        }
        batchHandle(list);
        batchHandle(list, 20000);
    }

    /**
     * 批量处理，可适用于数据库插入，最大量默认10000
     *
     * @param list 需要处理的数据
     * @throws Exception 插入异常
     */
    private static void batchHandle(List<Object> list) throws Exception {
        batchHandle(list, 10000);
    }

    /**
     * 批量处理，可适用于数据库插入
     *
     * @param list   需要处理的数据
     * @param maxNum 插入最大量
     * @throws Exception 插入异常
     */
    private static void batchHandle(List<Object> list, int maxNum) throws Exception {
        if (list.size() > maxNum) {
            List<Object> useList = new ArrayList<>();
            for (Object s : list) {
                useList.add(s);
                if (useList.size() >= maxNum) {
                    System.out.println(useList);
                    useList.clear();
                }
            }
        } else {
            System.out.println(list);
        }
    }
}
