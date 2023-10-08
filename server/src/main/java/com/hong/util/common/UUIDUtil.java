package com.hong.util.common;

import java.util.UUID;

/**
 * @author jiaohongtao
 * @since 2019/8/23 14:41
 */
public class UUIDUtil {
	/**
	 * 获取uuid
	 */
	public static String getUUID() {

		return UUID.randomUUID().toString().replace("-", "");
	}
}
