package com.seecen.exam.day0929;
/**
 * 创建线程的方式
 * 1. 继承Thread类
 * 2. 实现Runnable接口
 * 3. 线程池的方式
 * @author wanlei
 *
 */
public class TestThread {
	public static void main(String[] args) {
		/*
		  	//第一种方法的调用
			Thread t = new MyThread();
			//告诉jvm启动一个新的线程,发送指令可能只需有0.00001ms,但是这个线程真正跑起来，也就是执行run方法,不确定的，取决于cpu的调度。
			t.start();
			// main方法:Java进程中的一个主线程
			while(true) {
				System.out.println("main线程正在执行中，线程的ID为" + Thread.currentThread().getId());
			}
			System.out.println("-----------------------------------------------------");
		*/
		//第二种方法的调用
		Runnable r1 = new MyRunnable();
		Runnable r2 = new MyRunnable();
		Runnable r3 = new MyRunnable();
		Thread t1 = new Thread(r1, "线程1");
		Thread t2 = new Thread(r2, "线程2");
		Thread t3 = new Thread(r3, "线程3");
		//调用start()方法必须得有Thread实例对象
		t1.start();
		t2.start();
		t3.start();
	}
}
