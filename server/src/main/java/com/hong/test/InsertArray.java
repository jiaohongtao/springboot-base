package com.hong.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jiaohongtao
 * @since 2019/10/12 17:25
 */
public class InsertArray {

	public static void main(String[] args) {

		List<Object> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");

		list.add(2, "ccc");

		list.add(2, "qqq");
		// System.out.println(list);

	}
}
