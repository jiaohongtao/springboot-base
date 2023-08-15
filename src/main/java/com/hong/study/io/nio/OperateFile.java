package com.hong.study.io.nio;

import com.hong.util.common.FileOperate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

    public static void copy2() throws IOException {
        FileInputStream inputStream = new FileInputStream(FileOperate.getDeskPath() + "/file.txt");
        FileChannel fileChannel1 = inputStream.getChannel();

        // 不存在则创建
        FileOutputStream outputStream = new FileOutputStream(FileOperate.getDeskPath() + "/file3.txt");
        FileChannel fileChannel2 = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        while (true) {
            byteBuffer.clear(); // 清空buffer
            int read = fileChannel1.read(byteBuffer); // 返回值表示读取的数据是多少
            if (read == -1) { // 表示读完
                break;
            }
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }

        inputStream.close();
        outputStream.close();
    }
}
