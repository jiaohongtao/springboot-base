package com.hong.test;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;
import org.apache.http.client.config.AuthSchemes;

/**
 * @author jiaohongtao
 * @since 2019/10/21 16:46
 */
public class RemoteWinrm4J {

	public static void main(String[] args) {
		WinRmClientContext context = WinRmClientContext.newInstance();
		WinRmTool.Builder builder = WinRmTool.Builder.builder("10.20.50.229", "dajiaotao", "6469");
/*
		WinRmTool.Builder builder = WinRmTool.Builder.builder("10.20.50.229", "administrator", "6469");
*/
		builder.setAuthenticationScheme(AuthSchemes.NTLM);
		builder.port(15985);
		builder.useHttps(false);

		builder.context(context);
		WinRmTool tool = builder.build();
		tool.setOperationTimeout(5000L);
		System.out.println("========");
		String command = "dir";
		WinRmToolResponse resp = tool.executeCommand(command);
		System.out.println(resp.getStatusCode());
		String out = resp.getStdOut();
		System.out.println(out);
		context.shutdown();
	}
}
