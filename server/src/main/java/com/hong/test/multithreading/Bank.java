package com.hong.test.multithreading;

/**
 * @author jiaohongtao
 * @description 银行存取钱问题
 * @since 2019/10/25 11:23
 */
public class Bank {

	private static int money;

	private int getMoney() {
		return money;
	}

	// 存钱
	public void saveMoney(int m) {
		synchronized (this) {
			System.out.println("存钱后的总金额：" + (money += m));
		}
	}

	// 取钱
	public void drawMoney(int m) {
		synchronized (this) {
			Bank bank = new Bank();
			if (bank.getMoney() <= 0) {
				System.out.println("没得钱，取个pi");
			} else {
				System.out.println("取钱后剩的总金额：" + (money -= m));
			}
		}
	}

	public static void main(String[] args) {
		Man man = new Man();
		Women women = new Women();
		Thread t1 = new Thread(man);
		Thread t2 = new Thread(man);
		Thread t3 = new Thread(man);
		Thread t4 = new Thread(women);
		Thread t5 = new Thread(women);
		Thread t6 = new Thread(women);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
}

class Man implements Runnable {
	private Bank bank = new Bank();

	public void run() {
		int m = 100;
		int i = 0;
		while (i < 5) {
			bank.saveMoney(m);
			i++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Women implements Runnable {
	private Bank bank = new Bank();

	public void run() {
		int m = 100;
		int i = 0;
		//bank.getMoney()> 0
		while (i < 5) {
			bank.drawMoney(m);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}