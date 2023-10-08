package com.hong.test.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jiaohongtao
 * @decription 基于TCP的BIO通信, 服务器创建服务并监听端口，与客户端建立连接，并向客户端转发消息，客户端输入quit命令即可退出。
 * @since 2019/10/31 15:14
 */
public class Server {
	public static void main(String[] args) {
		final String QUIT = "quit";
		final int DEFAULT_PORT = 8888;
		ServerSocket serverSocket = null;

		//绑定监听端口
		try {
			serverSocket = new ServerSocket(DEFAULT_PORT);
			System.out.println("启动服务器，监听端口" + DEFAULT_PORT);

			// while (true) { // 可以不加，因为serverSocket.accept会阻塞，等待接收，直到结束
			// 等待客户端连接
			Socket socket = serverSocket.accept();
			System.out.println("客户端[" + socket.getPort() + "]已连接");
			//创建IO流
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream())
			);
			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())
			);

			String msg = null;
			while ((msg = bufferedReader.readLine()) != null) {
				// 读取客户端发送的消息,当对方关闭时返回null

				if (!"".equals(msg)) {

					System.out.println("客户端[" + socket.getPort() + "]：" + msg);
					//回复客户发送的消息
					bufferedWriter.write("服务器：" + msg + "\n");
					bufferedWriter.flush(); //保证缓冲区的数据发送出去
					//查看客户端是否退出
					if (QUIT.equals(msg)) {
						System.out.println("客户端[" + socket.getPort() + "]已退出");
						break;
					}
				} else {
					bufferedWriter.write("服务器：请正确输入！\n");
					bufferedWriter.flush(); //保证缓冲区的数据发送出去
				}
			}
			// }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
					System.out.println("关闭ServerSocket");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}