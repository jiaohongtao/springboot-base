package com.hong.test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiaohongtao
 * @since 2019/10/12 18:00
 */
public class LambdaTest {

	public static void main(String[] args) {

		//实现多线程程序
		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "新线程创建了");
			}
		};
		new Thread(r).start();

		//简化代码
		new Thread(new Runnable() {
			//使用匿名内部类创建多线程
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "新线程创建了");
			}
		}).start();

		//使用Lambda表达式创建多线程
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + "新线程创建了");
		}).start();

		//简化使用Lambda表达式创建多线程
		new Thread(() -> System.out.println(Thread.currentThread().getName() + "新线程创建了")).start();

		// 将字符串转换为大写，然后使用逗号串起来
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany",
				"Italy", "U.K.", "Canada");
		String G7Countries = G7.stream().map(String::toUpperCase)
				.collect(Collectors.joining(", "));
		// System.out.println(G7Countries);

		List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
		IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x)
				.summaryStatistics();
		/*System.out.println("Highest prime number in List : " + stats.getCount());
		System.out.println("Highest prime number in List : " + stats.getMax());
		System.out.println("Lowest prime number in List : " + stats.getMin());
		System.out.println("Sum of all prime numbers : " + stats.getSum());
		System.out.println("Average of all prime numbers : " + stats.getAverage());*/

	}
}


class Test2 {

	public static void main(String args[]) {
		Test2 tester = new Test2();

		// 有参数类型
		MathOperation addition = (int a, int b) -> a + b;

		// 无参数类型
		MathOperation subtraction = (a, b) -> a - b;

		// 有花括号，有return关键字
		MathOperation multiplication = (int a, int b) -> {
			return a * b;
		};

		// 无花括号，无return关键字，单一表达式情况
		MathOperation division = (int a, int b) -> a / b;

		// MathOperation调用示例
		System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
		System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
		System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
		System.out.println("10 / 5 = " + tester.operate(10, 5, division));

		// 有括号
		GreetingService greetService1 = message -> System.out.println("Hello " + message);

		// 无括号，单个参数情况
		GreetingService greetService2 = (message) -> System.out.println("Hello " + message);

		// GreetingService调用示例
		greetService1.sayMessage("Mahesh");
		greetService2.sayMessage("Suresh");

		//有括号， 无参情况
		Runnable runTest = () -> System.out.println("Running");
		//Runnable调用示例
		runTest.run();
	}

	// 内部接口
	interface MathOperation {
		int operation(int a, int b);
	}

	interface GreetingService {
		void sayMessage(String message);
	}

	interface Runnable {
		void run();
	}

	private int operate(int a, int b, MathOperation mathOperation) {
		return mathOperation.operation(a, b);
	}
}