package com.hong.study.io.nio.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO读取文件
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/08/09
 */
public class NioRead {

    public static void main(String[] args) {

        String path = "C:\\Users\\jx\\Desktop\\tmp\\settings.xml";

        try (FileChannel channel = new FileInputStream(path).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, buffer.limit());
                // System.out.println("读取到：" + content);
                System.out.print(content);

                // 对每一次读取的buffer进行处理
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
