package com.hong.util.common;

/**
 * @author jiaohongtao
 * @since 2019/11/6 18:07
 */
public class UpperFirstChar {

	public static void main(String[] args) {
		System.out.println(upperCaseFirst("barer"));
	}

	private static String upperCaseFirst(String str) {
		return upperCase(str.substring(0, 1)) + str.substring(1);
	}


	private static String upperCase(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
}
