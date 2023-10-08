package com.hong.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author jiaohongtao
 * @since 2019/10/25 17:21
 */
public class TestLambda {

    public static void main(String[] args) {
        /* 测试list_lambda */

//		List<String> proNames = Arrays.asList("Ni", "Hao", "Lambda");
//		List<String> uppercaseNames = proNames.stream().map(String::toUpperCase).collect(Collectors.toList());
//		System.out.println(uppercaseNames);
//        Runnable runnable = () -> System.out.println("abc");
//        runnable.run();
//
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("demo-pool-%d").build();
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//
//        singleThreadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
//        singleThreadPool.shutdown();
//
//        new Thread(() -> System.out.println("1")).start();
//        System.out.println("2");
//        System.out.println("3");


        System.out.println(getFilter("txt").accept(new File("C:\\Users\\jiaohongtao\\Desktop\\TODO.txt")));
    }


    static FileFilter getFilter(String ext) {
        return (pathname) -> pathname.toString().endsWith(ext);
    }



}
