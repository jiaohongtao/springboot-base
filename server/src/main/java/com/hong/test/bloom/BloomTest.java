package com.hong.test.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/07/27
 */
public class BloomTest {

    public static void main(String[] args) throws InterruptedException {

        try {
            bloomFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bloomFilter() throws Exception {
        //0.0000001d为错误率， 9000000 为预估元素的个数， 我第一次测试用了大概9000000行字符串的文本
        final BloomFilter<String> dealIdBloomFilter = BloomFilter.create(
                (Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8), 500, 0.0000001d);
        FileInputStream fileInputStream = new FileInputStream("D:\\Github\\springboot-use\\pom_.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
        String line;

        long start = System.currentTimeMillis();
        // 设置循环构建 StringBuilder 多少次，再写（IO）一次到文件中
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            final boolean put = dealIdBloomFilter.put(line);
            if (put) {
                sb.append(line).append("\n");
                i++;
            }
            // 设置 true 表示将读取的文件追加到文件中，可设置循环构建 StringBuilder 多少次，再写（IO）一次到文件中
            if (i % 100 == 0) {
                //保存虑重后的文本。
                FileUtils.write(new File("D:\\Github\\springboot-use\\_pom_.xml"),
                        sb.toString(), String.valueOf(StandardCharsets.UTF_8), true);
                // 清空stringBuilder的内容
                sb.setLength(0);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
