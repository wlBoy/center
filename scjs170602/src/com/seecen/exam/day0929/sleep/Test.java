package com.seecen.exam.day0929.sleep;

public class Test {
	public static void main(String[] args) {
		Printer printer = new Printer();
		Thread t1 = new Thread(new MyThread(printer, 0));
		Thread t2 = new Thread(new MyThread(printer, 1));
		Thread t3 = new Thread(new MyThread(printer, 2));
		t1.start();
		t2.start();
		t3.start();
	}
}
