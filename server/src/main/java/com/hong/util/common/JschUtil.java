package com.hong.util.common;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jiaohongtao
 * @since 2019/9/20 14:01
 */
public class JschUtil {

	public static void main(String[] args) {

		String host = "192.168.112.130";
		int port = 22;
		String username = "root";
		String password = "root";
		String command = "uname -a";

		/*String url = "https://raw.githubusercontent.com/jiaoht/LinuxUse/master/CatDisk";
		String getScript = " wget -c " + url + " -O installer\n" + " sh installer\n";
		String rmInstaller = " rm -rf installer\n";
		String command = "cd /data\n" +
				"if [ ! -f 'installer' ];then\n" +
				getScript +	rmInstaller +
				"else\n" +
				rmInstaller + getScript + rmInstaller +
				"fi";*/

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			jschExecCommand(host, port, username, password, command);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Jsch方式：" + (endTime - startTime));
	}

	public static void jschExecCommand(String host, int port, String username, String password, String command) {

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect(60 * 1000);
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0) break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0) continue;
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (JSchException | IOException e) {
			e.printStackTrace();
		}
	}
}
