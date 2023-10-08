package com.hong.test.multithreading.KFC;

import lombok.Data;

/**
 * @author jiaohongtao
 * @since 2019/10/25 16:34
 */
@Data
public class Food {
	private String name = "";

	//通过构造方法传入食物的名字
	public Food(String name) {
		this.name = name;
	}
}
