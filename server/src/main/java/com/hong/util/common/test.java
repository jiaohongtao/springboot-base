package com.hong.util.common;

import com.hong.bean.HostBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaohongtao
 * @since 2019/9/27 14:37
 */
public class test {

	public static void main(String[] args) {
//		List<Object> strings = new ArrayList<>();
//		String a = "a";
//		String b = "b";
//		String c = "c";
//		String d = "d";
//		int aa = 1;
//		strings.add(a);
//		strings.add(b);
//		strings.add(c);
//		strings.add(d);
//		strings.add(aa);
//
//		System.out.println(strings);
//
//		JSONArray jsonArray = new JSONArray(strings);
//		System.out.println(jsonArray);
//		JSONObject jsonObject = new JSONObject();
//		// jsonObject.put("sss", jsonArray);
//		jsonObject.put("bbbb", strings);
//		System.out.println(jsonObject);

		HostBean hostBean = new HostBean();
		hostBean.setIp("10.20.10.20");
		HostBean hostBean1 = new HostBean();
		hostBean1.setIp("10.20.10.10");
		List<HostBean> hostBeans = new ArrayList<>();
		hostBeans.add(hostBean);
		hostBeans.add(hostBean1);

		String token = "Gg3000lFhb9z";
		String appId = "deploy-server";
		String server = "http://zero.people.local";
		TestUtil testUtil = new TestUtil(appId, token, server);
		testUtil.status(hostBeans);
	}
}
