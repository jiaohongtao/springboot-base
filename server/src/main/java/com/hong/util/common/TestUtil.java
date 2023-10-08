package com.hong.util.common;

import com.hong.bean.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiaohongtao
 * @description agent操作类
 * @since 2019/9/26 10:21
 */
public class TestUtil {

	private String appId; // deploy-server
	private String token; // Gg3000lFhb9z
	private String server; // http://zero.people.local

	public TestUtil(String appId, String token, String server) {
		this.appId = appId;
		this.token = token;
		this.server = server;
	}

	public void status(List<HostBean> hostBeans) {
		String url = "api/v1/client/status";
		String time = System.currentTimeMillis() / 1000 + "";

		postReq(url, time, server, new JSONObject().put("tree_host", hostBeans));
	}

	public void sendTask(TaskBean taskBean) {
		String url = "api/v2/send";
		String time = System.currentTimeMillis() / 1000 + "";

		postReq(url, time, server, taskBean);
	}

	public void findAllLog(String id) {
		String url = "api/v2/target/" + id + "/log";
		String time = System.currentTimeMillis() / 1000 + "";

		getReq(url, time);
	}

	public void findLog(String id, int index) {
		String url = "api/v2/task/" + id + "/log-by-index/" + index;
		String time = System.currentTimeMillis() / 1000 + "";

		getReq(url, time);
	}

	public void find(String id) {
		String url = "api/v2/task/" + id;
		String time = System.currentTimeMillis() / 1000 + "";

		getReq(url, time);
	}

	public void kill(String id) {
		String url = "api/v2/task/" + id + "/kill";
		String time = System.currentTimeMillis() / 1000 + "";

		putReq(url, time);
	}

	public void cancel(String id) {
		String url = "api/v2/task/" + id + "/cancel";
		String time = System.currentTimeMillis() / 1000 + "";

		putReq(url, time);
	}

	public void stop(String id) {
		String url = "api/v2/task/" + id + "/pause";
		String time = System.currentTimeMillis() / 1000 + "";

		putReq(url, time);
	}

	public void execute(String id) {
		String url = "api/v2/task/" + id + "/start";
		String time = System.currentTimeMillis() / 1000 + "";

		putReq(url, time);
	}

	public void create(MetaBean metaBean, List<TargetBean> targetBeans, PluginBean pluginBean) {
		String url = "api/v2/task";
		String time = System.currentTimeMillis() / 1000 + "";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("meta", metaBean);
		jsonObject.put("target", new JSONArray(targetBeans));
		jsonObject.put("plugin", pluginBean);

		postReq(url, time, server, jsonObject);
	}

	private void postReq(String url, String time, String server, Object object) {
		try {
			System.out.println("当前时间：" + time);
			Map<String, String> header = new HashMap<>();
			signHeader(url, time, header);

			HttpResponse<String> response = Unirest.post(server + "/" + url)
					.headers(header).body((JSONObject) object).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getReq(String url, String time) {
		try {
			System.out.println("当前时间：" + time);
			Map<String, String> header = new HashMap<>();
			signHeader(url, time, header);

			System.out.println(server + "/" + url);
			HttpResponse<String> response = Unirest.get(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void putReq(String url, String time) {
		try {
			System.out.println("当前时间：" + time);
			Map<String, String> header = new HashMap<>();
			signHeader(url, time, header);

			HttpResponse<String> response = Unirest.put(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void signHeader(String url, String time, Map<String, String> header) throws Exception {
		String sign = encodeHmacSHA256(token, url, time);
		System.out.println("密钥：" + sign);
		header.put("Auth-App-Id", appId);
		header.put("Auth-Request-Time", time);
		header.put("Content-Type", "application/json");
		header.put("Auth-Sign", sign);
	}

	public static String encodeHmacSHA256(String token, String url, String time) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(token.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return Hex.encodeHexString(sha256_HMAC.doFinal((url + time).getBytes("UTF-8")));
	}
}