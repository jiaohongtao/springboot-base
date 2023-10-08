package com.hong.util.common;

public class Int2ChineseNum {

    public static String int2chineseNum(int src) {
        final String[] num = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String[] unit = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        StringBuilder dst = new StringBuilder();
        int count = 0;
        while (src > 0) {
            dst.insert(0, (num[src % 10] + unit[count]));
            src = src / 10;
            count++;
        }
        return dst.toString().replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }

    public static void main(String[] args) {
        int abc = 123;

        System.out.println(int2chineseNum(abc));
    }
}
