package com.seecen.exam.day0929;
/**
 * 创建线程的第二种方法:实现Runnable接口
 * @author wanlei
 *
 */
public class MyRunnable implements Runnable{
	@Override
	public void run() {
		while(true) {
			System.out.println("mythread线程名称为：" + Thread.currentThread().getName() + ", id为：" + Thread.currentThread().getId());
		}
	}
}
