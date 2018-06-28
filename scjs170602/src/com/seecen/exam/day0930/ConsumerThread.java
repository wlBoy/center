package com.seecen.exam.day0930;

import java.util.List;

/**
 * 消费者线程
 * 
 * @author wanlei
 *
 */
public class ConsumerThread implements Runnable {
	private List<Forage> list;
	private String name;

	public ConsumerThread(List<Forage> list, String name) {
		super();
		this.list = list;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted())
					break;
				Forage f = null;
				//购买粮草时加锁，此时不能添加粮草，线程安全
				Test.lock.lock();
				if (list.size() == 0) {
					Test.full.signalAll();
					Test.empty.await();
				}
				Thread.sleep(500);
				//购买粮草,list默认移除第一个
				f = list.remove(0);
				Test.lock.unlock();
				System.out.println(name + "购买了粮草" + f.getId() + ",仓库剩余量为:" + list.size());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
