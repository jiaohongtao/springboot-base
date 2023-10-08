package com.hong.study.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * aio 服务端
 */
public class AioServer {

    public AsynchronousServerSocketChannel serverChannel;

    /**
     * 监听客户端请求
     *
     * @throws Exception 异常
     */
    public void listen() throws Exception {
        //打开一个服务端通道
        serverChannel = AsynchronousServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(8080));//监听 8080 端口
        //服务监听
        serverChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AioServer>() {

            @Override
            public void completed(AsynchronousSocketChannel client, AioServer attachment) {
                try {
                    if (client.isOpen()) {
                        System.out.println("接收到新的客户端连接，地址：" + client.getRemoteAddress());
                        final ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取客户端发送的信息
                        client.read(buffer, client, new CompletionHandler<Integer, AsynchronousSocketChannel>() {

                            @Override
                            public void completed(Integer result, AsynchronousSocketChannel attachment) {
                                try {
                                    //读取请求，处理客户端发送的数据
                                    buffer.flip();
                                    String content = new String(buffer.array(), 0, buffer.limit());
                                    System.out.println("服务端收到客户端发送的信息：" + content);

                                    //向客户端发送数据
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put("server send".getBytes());
                                    writeBuffer.flip();
                                    attachment.write(writeBuffer).get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                                try {
                                    exc.printStackTrace();
                                    attachment.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //当有新客户端接入的时候，直接调用accept方法，递归执行下去，保证多个客户端都可以阻塞
                    attachment.serverChannel.accept(attachment, this);
                }
            }

            @Override
            public void failed(Throwable exc, AioServer attachment) {
                exc.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        //启动服务器，并监听客户端
        new AioServer().listen();
        //因为是异步IO执行，让主线程睡眠但不关闭
        Thread.sleep(Integer.MAX_VALUE);
    }
}