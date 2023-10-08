package com.hong.util.common;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年12月24日
 */
public class ChineseSort {

    public static void sort() {
        ClassPathResource classPathResource = new ClassPathResource("static/city.txt");
        // StringBuilder buffer = new StringBuilder();
        try {
            InputStream inputStream = classPathResource.getInputStream();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            List<String> list = new ArrayList<>();
            while (bufferedReader.ready()) {
                // list.add(bufferedReader.readLine());
                String content = bufferedReader.readLine();
                content = content.trim();
                if ("".equals(content)) {
                    continue;
                    // System.out.println("===" + content);
                }
                System.out.println(content);
            }

            /*String[] objects = list.toArray(new String[0]);
            System.out.println(Arrays.toString(objects));
            Arrays.sort(objects, Collator.getInstance(Locale.CHINA));
            System.out.println("==========");
            Arrays.stream(objects).forEach(System.out::println);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sort();
    }
}
