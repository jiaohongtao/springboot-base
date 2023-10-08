package com.hong.study.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO 客户端
 */
public class NioClientTest {

    public static void main(String[] args) throws IOException {
        // 打开socket通道
        SocketChannel sc = SocketChannel.open();
        //设置为非阻塞
        sc.configureBlocking(false);
        //连接服务器地址和端口
        sc.connect(new InetSocketAddress("127.0.0.1", 8080));
        while (!sc.finishConnect()) {
            // 没连接上,则一直等待
            System.out.println("客户端正在连接中，请耐心等待");
        }

        // 发送内容
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("Hello，我是客户端".getBytes());
        writeBuffer.flip();
        sc.write(writeBuffer);

        // 读取响应
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (sc.isOpen() && sc.read(readBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (readBuffer.position() > 0) {
                break;
            }
        }
        readBuffer.flip();

        String result = new String(readBuffer.array(), 0, readBuffer.limit());
        System.out.println("客户端收到服务端：" + sc.socket().getRemoteSocketAddress() + "，返回的信息：" + result);

        // 关闭通道
        sc.close();
    }
}