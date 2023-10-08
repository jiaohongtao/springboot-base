package com.hong.test.multithreading;

/**
 * @author jiaohongtao
 * @since 2019/10/25 16:19
 */
public class RabbitTortoise {
	/*示例三：龟兔赛跑问题
	龟兔赛跑：20米     // 只要为了看到效果，所有距离缩短了
	要求：
		1.兔子每秒0.5米的速度，每跑2米休息10秒，
		2.乌龟每秒跑0.1米，不休息
		3.其中一个跑到终点后另一个不跑了！

	程序设计思路：
		1.创建一个Animal动物类，继承Thread，编写一个running抽象方法，重写run方法，把running方法在run方法里面调用。
		2.创建Rabbit兔子类和Tortoise乌龟类，继承动物类
		3.两个子类重写running方法
		4.本题的第3个要求涉及到线程回调。需要在动物类创建一个回调接口，创建一个回调对象*/

	public static void main(String[] args) {
		//实例化乌龟和兔子
		Tortoise tortoise = new Tortoise();
		Rabbit rabbit = new Rabbit();
		//回调方法的使用，谁先调用calltoback方法，另一个就不跑了
		rabbit.calltoback = new LetOneStop(tortoise);//让兔子的回调方法里面存在乌龟对象的值，可以把乌龟stop
		tortoise.calltoback = new LetOneStop(rabbit);//让乌龟的回调方法里面存在兔子对象的值，可以把兔子stop
		//开始跑
		tortoise.start();
		rabbit.start();

		/*可以看到结果兔子赢了。
		一般来说兔子获得了胜利是在最后输出的，
		但是，由于线程一直在执行所以会出现：
		“兔子跑了0.5米，距离终点还有0米”还没来得及输出完，
		而“兔子获得了胜利”已经输出完毕了。*/
	}
}

abstract class Animal extends Thread {

	double length = 20;//比赛的长度

	public abstract void running();//抽象方法需要子类实现

	//在父类重写run方法，在子类只要重写running方法就可以了
	@Override
	public void run() {
		super.run();
		while (length > 0) {
			running();
		}
	}

	//在需要回调数据的地方（两个子类需要），声明一个接口
	public interface Calltoback {
		void win();
	}

	//2.创建接口对象
	Calltoback calltoback;
}

class Rabbit extends Animal {

	Rabbit() {
		setName("兔子");// Thread的方法，给线程赋值名字
	}

	// 重写running方法，编写兔子的奔跑操作
	@Override
	public void running() {
		// 跑的距离
		double dis = 1;
		length -= dis;//跑完后距离减少
		if (length <= 0) {
			length = 0;
			System.out.println("兔子获得了胜利");
			//给回调对象赋值，让乌龟不要再跑了
			if (calltoback != null) {
				calltoback.win();
			}
		}
		System.out.println("兔子跑了" + dis + "米，距离终点还有" + (int) length + "米");

		if (length % 2 == 0) {// 两米休息一次
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Tortoise extends Animal {

	Tortoise() {
		setName("乌龟");// Thread的方法，给线程赋值名字
	}

	// 重写running方法，编写乌龟的奔跑操作
	@Override
	public void running() {
		// 跑的距离
		double dis = 0.2;
		length -= dis;
		if (length <= 0) {
			length = 0;
			System.out.println("乌龟获得了胜利");
			// 让兔子不要在跑了
			if (calltoback != null) {
				calltoback.win();
			}
		}
		System.out.println("乌龟跑了" + dis + "米，距离终点还有" + (int) length + "米");
		try {
			sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class LetOneStop implements Animal.Calltoback {

	// 动物对象
	private Animal an;

	// 获取动物对象，可以传入兔子或乌龟的实例
	LetOneStop(Animal an) {
		this.an = an;
	}

	//让动物的线程停止
	@Override
	public void win() {
		// 线程停止
		an.stop();
	}
}