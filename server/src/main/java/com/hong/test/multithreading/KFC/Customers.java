package com.hong.test.multithreading.KFC;

/**
 * @author jiaohongtao
 * @since 2019/10/25 16:36
 */
public class Customers extends Thread {
	private KFC kfc;

	/**KFC要传入，保证每一个服务员和用户在同一个KFC对象内*/
	public Customers(KFC kfc) {
		this.kfc = kfc;
	}

	private double random = Math.random();

	@Override
	public void run() {
		//每次要消费的食物的数量
		int size = (int) (random * 5);
		while (true) {
			//在消费的方法里面传入参数
			kfc.consume(size);
		}
	}
}
