package com.seecen.exam.day0929.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Printer {
	//创建一个读写所
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	public void printerName() {
		//读所上锁，在此期间不能进行写操作
		rwLock.readLock().lock();
		for(int i = 1; i <= 50; i++) {
			System.out.println(Thread.currentThread().getId() + "正在执行读read操作" + i);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//读所解锁
		rwLock.readLock().unlock();
	}
	
	public  void writerName() {
		//写所上锁，在此期间不能进行读操作
		rwLock.writeLock().lock();
		for(int i = 0; i <= 50; i++) {
			System.out.println(Thread.currentThread().getId() + "正在执行写write操作" + i);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//写所解锁
		rwLock.writeLock().unlock();
	}
	
}
