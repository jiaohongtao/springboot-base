package com.hong.util.common;

import java.util.Arrays;

/**
 * @author jiaohongtao
 * @since 2019/10/10 18:01
 */
public class ArraySort {

	public static void main(String[] args) {
		int[] nums = new int[30];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = (int) (Math.random() * 667);
		}
		//计算排序时间
		long stratTime = System.currentTimeMillis();
		//java包里自带的函数
		Arrays.sort(nums);
		long endTime = System.currentTimeMillis();

		System.out.printf("耗时 %d 毫秒\n", endTime - stratTime);

		for (int num : nums) {
			System.out.printf("%d  ", num);
		}
	}
}
