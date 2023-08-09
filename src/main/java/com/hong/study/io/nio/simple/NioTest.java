package com.hong.study.io.nio.simple;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/08
 */
public class NioTest {

    public static void main(String[] args) {
        // write();
        zeroCopy();
    }

    static void test() {
        // 创建新的buffer，大小是1024个字节

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 创建之后是写模式，因此可以直接写数据
        for (int i = 0; i < 10; i++) {
            buffer.put((byte) i);
        }
        // 调用flip方法切换到读模式
        buffer.flip();
        // 循环读取
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }

    static void write() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("E:/tmp/nio.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel channel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("测试nio往文件写数据".getBytes());
        // 需要切换为读模式，因为下面调用write方法时相当于从buffer里面读数据
        buffer.flip();
        // 向channel写数据
        int len = 0;
        try {
            len = channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("字节数：" + len);

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void read() {
        FileInputStream in;
        try {
            in = new FileInputStream("E:/tmp/nio.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        FileChannel channel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(8);

        int len = -1;
        List<Byte> list = new ArrayList<>();
        byte[] bytes = new byte[8];
        // 循环读取数据
        while (true) {
            try {
                if ((len = channel.read(buffer)) == -1) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 下面要从buffer中读数据，因此切换为读模式
            buffer.flip();
            buffer.get(bytes, 0, len);
            for (int i = 0; i < len; i++) {
                list.add(bytes[i]);
            }
            // 下一个循环需要先向buffer写数据，因此切换为写模式
            buffer.clear();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转为byte数组
        byte[] resBytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            resBytes[i] = list.get(i);
        }
        // 以字符串形式打印
        System.out.println(new String(resBytes));
    }

    static void zeroCopy() {
        try (
                FileChannel from = new FileInputStream("E:\\tmp\\nio.txt").getChannel();
                FileChannel to = new FileOutputStream("E:\\tmp\\nioTo.txt").getChannel()
        ) {
            //效率高，底层利用操作系统的零拷贝
            //size(),FileChannel独有的方法，获取文件大小
            from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
