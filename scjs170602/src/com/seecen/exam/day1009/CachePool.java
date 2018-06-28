package com.seecen.exam.day1009;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池  
 * @author bigpig
 * 
 * 创建一个cache线程池
 * 1. 如果没有空闲的线程，那么就会创建一个
 * 2. 如果有的话，就直接使用之前的线程来处理
 */
public class CachePool {
	public static void main(String[] args) throws Exception {
		// 初始0创建，有就用，没有就创建，适合做短任务
		//ExecutorService es = Executors.newCachedThreadPool();
		// 初始创建固定数量，后面任务使用现有的线程，不够就等待
		//ExecutorService es = Executors.newFixedThreadPool(3);
		// 参考上面fix的，这里只创建1个
		//ExecutorService es = Executors.newSingleThreadExecutor();
		
		// 周期任务， 每过多久执行一次该线程
		ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
		
		es.scheduleAtFixedRate(new MyJob(), 0, 7, TimeUnit.DAYS);
	}
}
class MyJob implements Runnable {
	public void run() {
		System.out.println(Thread.currentThread().getId() + "do jobing...");
		System.out.println(Thread.currentThread().getId() + "end...");
	}
}