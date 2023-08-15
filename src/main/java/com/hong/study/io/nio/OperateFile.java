package com.hong.study.io.nio;

import com.hong.util.common.FileOperate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

/**
 * nio 读写复制
 * https://blog.csdn.net/m0_75198698/article/details/131149815
 * https://blog.csdn.net/wanzijy/article/details/129805617
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/15
 */
public class OperateFile {

    public static void main(String[] args) {
        try {
            // read();
            // write();

            copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read() throws IOException {
        // 读取文件内容
        /*String content = Files.readString(Paths.get("path/to/file.txt"));
        System.out.println(content);*/

        // 逐行读取文件内容
        List<String> lines = Files.readAllLines(Paths.get(FileOperate.getDeskPath() + "\\TODO.txt"));
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void write() throws IOException {
        // 写入文件内容
        /*String content = "Hello, World!";
        Files.writeString(Paths.get("path/to/file.txt"), content);*/

        // 逐行写入文件内容
        List<String> lines = Arrays.asList("Line 1", "Line 2", "Line 3");
        Files.write(Paths.get(FileOperate.getDeskPath() + "/file.txt"), lines);
    }

    public static void copy() throws IOException {
        // 复制文件
        Path source = Paths.get(FileOperate.getDeskPath() + "/file.txt");
        Path target = Paths.get(FileOperate.getDeskPath() + "/file2.txt");
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
