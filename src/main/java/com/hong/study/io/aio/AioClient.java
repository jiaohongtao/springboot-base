package com.hong.study.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * aio 客户端
 */
public class AioClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        //打开一个客户端通道
        AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
        //与服务器建立连接
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));

        //睡眠1s，等待与服务器建立连接
        Thread.sleep(1000);
        try {
            //向服务器发送数据
            channel.write(ByteBuffer.wrap("Hello，我是客户端".getBytes())).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //从服务器读取数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer).get();//将通道中的数据写入缓冲buffer
            byteBuffer.flip();
            String result = new String(byteBuffer.array(), 0, byteBuffer.limit());
            System.out.println("客户端收到服务器返回的内容：" + result);//输出返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}