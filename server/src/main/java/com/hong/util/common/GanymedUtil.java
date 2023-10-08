package com.hong.util.common;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;

/**
 * @author jiaohongtao
 * @since 2019/9/20 16:06
 */
public class GanymedUtil {

	private static Connection login(String ip, int port, String username, String password) {
		boolean flag;
		Connection connection = null;
		try {
			connection = new Connection(ip, port);
			connection.connect();// 连接
			flag = connection.authenticateWithPassword(username, password);// 认证
			if (flag) {
				System.out.println("================登录成功==================");
				return connection;
			}
		} catch (IOException e) {
			System.out.println("登录失败,请检查主机IP是否有误：" + e);
			connection.close();
		}
		return connection;
	}

	/**
	 * 远程执行shell脚本或者命令
	 *
	 * @param command 即将执行的命令
	 * @return 命令执行完后返回的结果值
	 */
	private static String execCommand(Connection connection, String command) {
		String result = "";
		try {
			if (connection != null) {
				Session session = null;// 打开一个会话
				try {
					session = connection.openSession();
				} catch (ConnectException e) {
					e.printStackTrace();
				}
				try {
					session.execCommand(command);// 执行命令
				}/* catch (IllegalArgumentException e) {
					System.out.println("参数？");
				}*/ catch (IOException e) {
					e.printStackTrace();
				}
				String DEFAULT_CHART = "UTF-8";
				result = processStdout(session.getStdout(), DEFAULT_CHART);
				if ("".equals(result)) {
					System.out.println("请检查脚本内容是否有误");
					System.exit(1);
				}
				connection.close();
				session.close();
			}
		} catch (IOException e) {
			System.out.println("执行命令失败,链接conn:" + connection + ",执行的命令：" + command + "   " + e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解析脚本执行返回的结果集
	 *
	 * @param in      输入流对象
	 * @param charset 编码
	 * @return 以纯文本的格式返回
	 */
	private static String processStdout(InputStream in, String charset) {
		InputStream stdout = new StreamGobbler(in);
		StringBuilder buffer = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
			String line;
			while ((line = br.readLine()) != null) {
				buffer.append(line).append("\n");
				// System.out.println(line);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("解析脚本出错：" + e.getMessage());
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static void ganymedExecCommand(String host, int port, String username, String password, String command) {

		Connection connection = login(host, port, username, password);
		String execCommand = execCommand(connection, command);

		System.out.println(execCommand);
	}

	public static void main(String[] args) {
		String ip = "10.20.10.20";
		String username = "root";
		String password = "123456";
		int port = 22;
		// String path = "/data/";

		String masterIp = "10.20.10.154";
		String masterPort = "8080";

		String install_url = "http://10.20.10.7:8088/nginxdata/ifrit-agent-people-v0.01.466.9defcf6.20190719190311.bin";
		String getArgsExecScript = " wget -c " + install_url + " -O installer; sh installer -- -m " + masterIp + ":" + masterPort + " /export/servers/ifrit;\n";

		String rmInstaller = " rm -rf installer;\n";
		String getAndRm = getArgsExecScript + rmInstaller;
		String command = "cd /tmp;\n" +
				"if [ ! -f 'installer' ];then\n" +
				getAndRm +
				"else\n" +
				rmInstaller + getAndRm +
				"fi";

		// String url = "https://raw.githubusercontent.com/jiaoht/LinuxUse/master/CatDisk";
		// String getExecScript = " wget -c " + url + " -O installer; sh installer;";

		ganymedExecCommand(ip, port, username, password, command);
	}
}
