package com.seecen.exam.day0930;

import java.util.List;
import java.util.Random;

/**
 * 生产者线程
 * 
 * @author wanlei
 *
 */
public class ProducerThread implements Runnable {
	private List<Forage> list;
	private int length;
	private String name;

	public ProducerThread(List<Forage> list, int length, String name) {
		super();
		this.list = list;
		this.length = length;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted())
					break;
				//生产随机粮草id
				Random r = new Random();
				Forage f = new Forage();
				f.setId(r.nextInt(100));
				//添加粮草时加锁，此时不能购买粮草，线程安全
				Test.lock.lock();
				if (list.size() >= length) {
					Test.empty.signalAll();
					Test.full.await();
				}
				Thread.sleep(500);
				//将粮草添加到list中去
				list.add(f);
				Test.lock.unlock();
				System.out.println(name + "生产了粮草" + f.getId() + ",仓库剩余量为:" + list.size());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
