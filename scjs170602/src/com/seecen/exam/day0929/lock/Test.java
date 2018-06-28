package com.seecen.exam.day0929.lock;

public class Test {
	
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread t1 = new Thread(new MyThread(printer));
		Thread t2 = new Thread(new MyThread(printer));
		Thread t3 = new Thread(new MyThread1(printer));
	
		t1.start();
		t2.start();
		t3.start();
	}
}
