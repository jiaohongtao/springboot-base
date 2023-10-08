package com.hong.util.common;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author jiaohongtao
 * @since 2019/9/26 10:21
 */

public class TestUtils {

	public static void main(String[] args) {

		// create();
		// execute();
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("执行停止");
//				stop();
//			}
//		}, 1000);

		// cancel();
		// kill();
		// find();
		// findLog();
		// findAllLog();

		sendTask();
		// status();
	}


	private final static String token = "Gg3000lFhb9z";
	private final static String time = (new Date()).getTime() / 1000 + "";
	private final static String appId = "deploy-server";


	public static void status() {

		String server = "http://zero.people.local";
		String url = "api/v1/client/status";
		System.out.println("当前时间：" + time);

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject one = new JSONObject();
		one.put("ip", "10.20.10.159");
		/*one.put("vpc", "");
		one.put("region", "");
		one.put("tenant", "");*/
		JSONObject two = new JSONObject();
		two.put("ip", "10.20.10.20");
		/*two.put("vpc", "");
		two.put("region", "");
		two.put("tenant", "");*/
		jsonArray.put(one);
		jsonArray.put(two);
		jsonObject.put("tree_host", jsonArray);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.post(server + "/" + url)
					.headers(header).body(jsonObject).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTask() {

		String server = "http://zero.people.local";
		String url = "api/v2/send";
		System.out.println("当前时间：" + time);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", 1);
		jsonObject.put("task_id", 302122351);
		jsonObject.put("target_index", 0);
		jsonObject.put("context", "QQQQQQQQQQQQQQQQQQQQQQQQQQQ");

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.post(server + "/" + url)
					.headers(header).body(jsonObject).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void findAllLog() {

		String server = "http://zero.people.local";
		String url = "api/v2/target/1/log";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.get(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void findLog() {

		String server = "http://zero.people.local";
		String url = "api/v2/task/308481/log-by-index/0";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.get(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void find() {

		String server = "http://zero.people.local";
		String url = "api/v2/task/308483";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.get(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void kill() {

		String server = "http://zero.people.local";
		String url = "api/v2/task/308473/kill";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.put(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cancel() {

		String server = "http://zero.people.local";
		String url = "api/v2/task/308479/cancel";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.put(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stop() {

		String server = "http://zero.people.local";
		String url = "api/v2/task/308479/pause";
		System.out.println("当前时间：" + time);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.put(server + "/" + url)
					.headers(header).asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void execute() {
		String server = "http://zero.people.local";
		String url = "api/v2/task/308549/start";
		System.out.println("当前时间：" + time);


		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.put(server + "/" + url)
					.headers(header)
					.asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void create() {
		String server = "http://zero.people.local";
		String url = "api/v2/task";
		System.out.println("当前时间：" + time);

		JSONObject jsonObject = new JSONObject();
		JSONObject metaJsonObject = new JSONObject();
		metaJsonObject.put("account", "root");
		metaJsonObject.put("concurrency", 1);
		metaJsonObject.put("tolerance", 1); // N
		metaJsonObject.put("timeout", 300);
		metaJsonObject.put("total_timeout", 300); // N
		metaJsonObject.put("callback", ""); // N
		metaJsonObject.put("pipe_callback", ""); // N
		// metaJsonObject.put("shell", "#!/bin/bash\npwd && ls;"); // shell: ok
		metaJsonObject.put("shell", "pwd"); // shell: ok
		metaJsonObject.put("create_user", "jht");
		jsonObject.put("meta", metaJsonObject);

		JSONArray target = new JSONArray();
		JSONObject targetJsonObject = new JSONObject();
		targetJsonObject.put("cluster", "beijing");
		targetJsonObject.put("ip", "10.20.10.11");
		targetJsonObject.put("is_pause_point", 0);
		targetJsonObject.put("instance", "jht_test");
		target.put(targetJsonObject);
		jsonObject.put("target", target);

		try {
			String sign = encodeHmacSHA256(token, url, time);
			System.out.println("密钥：" + sign);
			Map<String, String> header = new HashMap<>();
			header.put("Auth-App-Id", appId);
			header.put("Auth-Request-Time", time);
			header.put("Content-Type", "application/json");
			header.put("Auth-Sign", sign);

			HttpResponse<String> response = Unirest.post(server + "/" + url)
					.headers(header).body(jsonObject)
					.asString();
			System.out.println(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encodeHmacSHA256(String token, String url, String time) throws Exception {
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(token.getBytes("UTF-8"), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return Hex.encodeHexString(sha256_HMAC.doFinal((url + time).getBytes("UTF-8")));
	}
}
