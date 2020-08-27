package com.example.springboothtml.test.multithreading;

/**
 * @author jiaohongtao
 * @description sleep()实现对象存取值
 * @since 2019/10/25 15:07
 */
public class ThreadCom {
	public static void main(String[] args) {
		Person person = new Person();
		new Thread(new Producer(person)).start();
		new Thread(new Consumer(person)).start();
	}
}

class Person {
	private String name = "张杰";
	private String sex = "男";

	public synchronized void put(String name, String sex) {
		this.name = name;
		this.sex = sex;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//方法加锁
	public synchronized void get() {
		System.out.println(name + "----->" + sex);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Consumer implements Runnable {
	private Person person;

	public Consumer(Person person) {
		this.person = person;
	}

	public void run() {
		while (true) {
			person.get();
		}
	}
}

class Producer implements Runnable {
	private Person person;

	public Producer(Person person) {
		this.person = person;
	}

	public void run() {
		int i = 0;
		while (true) {
			if (i == 0) {
				person.put("谢娜", "女");
			} else {
				person.put("张杰", "男");
			}
			i = (i + 1) % 2;//奇数和偶数
		}
	}
}
