package com.seecen.exam.day0929;
/**
 * 创建线程的第一种方法:继承Thread类
 * @author wanlei
 *
 */
public class MyThread extends Thread{
	//cpu启动新的线程后，会去执行run方法,自己主动调用run是没有用的
	public void run() {
		while(true) {
			System.out.println("myThread线程正在执行中，线程的ID为" + Thread.currentThread().getId());
		}
	}
}
