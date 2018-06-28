package com.seecen.exam.day0930;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition empty = lock.newCondition();
	public static Condition full = lock.newCondition();

	public static void main(String[] args) {
		List<Forage> list = new ArrayList<Forage>();
		int length = 10;

		Runnable r1 = new ConsumerThread(list, "¹ØÓğ");
		Runnable r2 = new ConsumerThread(list, "ÕÅ·É");
		Runnable r3 = new ConsumerThread(list, "ÕÔÔÆ");

		Runnable r4 = new ProducerThread(list, length, "Öî¸ğÁÁ");
		Runnable r5 = new ProducerThread(list, length, "Ë¾ÂíÜ²");
		Runnable r6 = new ProducerThread(list, length, "ÖÜè¤");

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		Thread t4 = new Thread(r4);
		Thread t5 = new Thread(r5);
		Thread t6 = new Thread(r6);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}

}
