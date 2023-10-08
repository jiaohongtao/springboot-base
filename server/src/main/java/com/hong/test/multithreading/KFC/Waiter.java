package com.hong.test.multithreading.KFC;

/**
 * @author jiaohongtao
 * @since 2019/10/25 16:37
 */
public class Waiter extends Thread {
	private KFC kfc;

	//KFC要传入，保证每一个服务员和用户在同一个KFC对象内
	public Waiter(KFC kfc) {
		this.kfc = kfc;
	}

	private double random = Math.random();

	@Override
	public void run() {
		//每次生产的数量
		int size = (int) (random * 5) + 4;
		while (true) {
			//传入每次生产的数量
			kfc.product(size);
		}

	}
}
