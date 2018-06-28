package com.seecen.exam.day0929.lock;

public class MyThread1 implements Runnable {
	
	private Printer printer;
	public MyThread1(Printer printer) {
		this.printer = printer;
	}
	
	public void run() {
		while(true) {
			printer.writerName();
		}
	}
}
