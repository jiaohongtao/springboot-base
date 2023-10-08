package com.hong.test.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiaohongtao
 * @since 2019/10/25 15:46
 */
public class SmokeAlcohol {
	/*故事起源：老爸有俩孩子：小红和小明。老爸想喝酒了，他让小红去买酒，小红出去了。然后老爸突然想吸烟了，于是老爸让小明去买烟。在面对对象的思想中，一般会把买东西，然后买回来这件事作为一个方法，如果按照顺序结构或者使用多线程同步的话，小明想去买烟就必须等小红这个买东西的操作进行完。这样无疑增加了时间的开销。异步就是为了解决这样的问题。你可以分别给小红小明下达指令，让他们去买东西，然后你就可以自己做自己的事，等他们买回来的时候接收结果就可以了。（Java8编写）*/

	public static void main(String[] args) {
		//两个线程的线程池
		ExecutorService executor = Executors.newFixedThreadPool(2);
		//小红买酒任务，这里的future2代表的是小红未来发生的操作，返回小红买东西这个操作的结果
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			System.out.println("爸：小红你去买瓶酒！");
			try {
				System.out.println("小红出去买酒了，女孩子跑的比较慢，估计5s后才会回来...");
				Thread.sleep(5000);
				return "我买回来了！";
			} catch (InterruptedException e) {
				System.err.println("小红路上遭遇了不测");
				return "来世再见！";
			}
		}, executor);

		//小明买烟任务，这里的future1代表的是小明未来买东西会发生的事，返回值是小明买东西的结果

		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			System.out.println("爸：小明你去买包烟！");
			try {
				System.out.println("小明出去买烟了，可能要3s后回来...");
				Thread.sleep(3000);
				return "我买回来了!";
			} catch (InterruptedException e) {
				System.out.println("小明路上遭遇了不测！");
				return "这是我托人带来的口信，我已经不在了。";
			}
		}, executor);

		//获取小红买酒结果，从小红的操作中获取结果，把结果打印
		future2.thenAccept((e) -> {
			System.out.println("小红说：" + e);
		});
		//获取小明买烟的结果
		future1.thenAccept((e) -> {
			System.out.println("小明说：" + e);
		});

		System.out.println("爸：loading......");
		System.out.println("爸:我觉得无聊甚至去了趟厕所。");
		System.out.println("爸：loading......");
	}
}
