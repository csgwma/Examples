package me.gwma.java.basic.concurrency.producerconsumer;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain {

    public static void main(String[] args) {
        int val = 0;
        System.out.println("please test method :");
        System.out.println("1: testThread            2: testByExecutorService");
        Scanner scanner = new Scanner(System.in);
        val = scanner.nextInt();
        switch (val) {
            case 1:
                testThread();
                break;
            case 2:
                testByExecutorService();
                break;
            default:
                System.out.println("input the error number");
        }
        scanner.close();
        System.out.println("The Main Thread Done !");
    }

    public static void testThread() {
        // 1.准备堆栈数据结构
        ServerAlarmQueue stack = new ServerAlarmQueue();

        // 2.准备生产者线程
        Producer producer1 = new Producer(stack);
        Thread t1 = new Thread(producer1);
        t1.setName("1号生产者");

        Producer producer2 = new Producer(stack);
        Thread t2 = new Thread(producer2);
        t2.setName("2号生产者");

        // 3.准备消费者线程
        Consumer consumer1 = new Consumer(stack);
        Thread t3 = new Thread(consumer1);
        t3.setName("1号消费者");

        Consumer consumer2 = new Consumer(stack);
        Thread t4 = new Thread(consumer2);
        t4.setName("2号消费者");

        t3.start();
        t4.start();

        t1.start();
        t2.start();
    }

    public static void testByExecutorService() {
        // 1.准备堆栈数据结构
        ServerAlarmQueue stack = new ServerAlarmQueue();

        // 2.准备生产者线程
        Producer producer1 = new Producer(stack);
        Thread t1 = new Thread(producer1);
        t1.setName("1号生产者");

        Producer producer2 = new Producer(stack);
        Thread t2 = new Thread(producer2);
        t2.setName("2号生产者");
        // 3.准备消费者线程
        Consumer consumer1 = new Consumer(stack);
        Thread t3 = new Thread(consumer1);
        t3.setName("1号消费者");

        Consumer consumer2 = new Consumer(stack);
        Thread t4 = new Thread(consumer2);
        t4.setName("2号消费者");

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);
        executorService.submit(t4);
        // 虽然submit的是线程，但是线程名 不一样啦~~ ^_^
        // ExecutorService只是仅仅调用了Runnable接口的run方法
        executorService.shutdown();
    }
}
