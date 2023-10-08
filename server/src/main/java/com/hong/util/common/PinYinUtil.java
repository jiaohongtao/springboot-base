package com.hong.util.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 获取中文串拼音或拼音首字母
 *
 * @author jiaohongtao 杨红杰
 * @since 2019/11/6 17:15
 */
public class PinYinUtil {

	private PinYinUtil() {
	}

	/**
	 * 获取中文拼音首字母，其他字符不变
	 *
	 * @param str
	 * @return String
	 */
	private static String getShortPinyin(String str) {
		return getShortPinyin(str, true);
	}

	/**
	 * 获取中文拼音首字母
	 *
	 * @param str
	 * @param retain 为true保留其他字符
	 * @return String
	 */
	private static String getShortPinyin(String str, boolean retain) {
		return getPinyin(str, true, retain);
	}

	/**
	 * 获取中文拼音，其他字符不变
	 *
	 * @param str
	 * @return String
	 */
	private static String getFullPinyin(String str) {
		return getFullPinyin(str, true);
	}

	/**
	 * 获取中文拼音
	 *
	 * @param str
	 * @param retain 为true保留其他字符
	 * @return String
	 */
	private static String getFullPinyin(String str, boolean retain) {
		return getPinyin(str, false, retain);
	}

	/**
	 * 获取中文拼音
	 *
	 * @param str
	 * @param shortPinyin 为true获取中文拼音首字母
	 * @param retain      为true保留其他字符
	 * @return String
	 */
	private static String getPinyin(String str, boolean shortPinyin, boolean retain) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		StringBuilder pinyinBuffer = new StringBuilder();
		char[] arr = str.toCharArray();
		for (char c : arr) {
			String[] temp = PinyinHelper.toHanyuPinyinStringArray(c);
			if (ArrayUtils.isNotEmpty(temp)) {
				if (StringUtils.isNotBlank(temp[0])) {
					if (shortPinyin) {
						pinyinBuffer.append(temp[0].charAt(0));
					} else {
						pinyinBuffer.append(temp[0].replaceAll("\\d", ""));
					}
				}
			} else {
				if (retain) {
					pinyinBuffer.append(c);
				}
			}
		}
		return pinyinBuffer.toString();
	}


	private static String getChar(int a) {
		return a + "";
	}

	public static void main(String[] args) {
		/*String str = "中华人民共和国万岁！";
		System.out.println("例一：获取中文拼音首字母");
		System.out.println(str);
		System.out.println(getShortPinyin(str));
		str = "我爱你中国！";
		System.out.println("例二：获取中文拼音");
		System.out.println(str);
		System.out.println(getFullPinyin(str));*/

		/*int i = 99;
		char a = (char) i;
		System.out.println(i);
		System.out.println(a);
		System.out.println(getChar('c'));*/

		/*System.out.println(-2111111111L >>> 2);
		System.out.println(2 << 2);*/
	}
}