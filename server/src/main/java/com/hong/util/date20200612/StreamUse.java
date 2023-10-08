package com.hong.util.date20200612;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年06月23日
 */
public class StreamUse {

    /**
     * 只取出为数字的元素，将该数组转换为Integer类型的数组
     */
    public static List<Integer> mapingToIntegers(List<String> list) {
        return list.stream().filter(a -> Pattern.matches("^[0-9]*$", a)).map(Integer::valueOf).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("123", "456", "789", "1101", "212121121", "asdaa", "3e3e3e", "2321eew");
        System.out.println(mapingToIntegers(list));
    }
}
