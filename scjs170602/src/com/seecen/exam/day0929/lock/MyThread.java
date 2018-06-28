package com.seecen.exam.day0929.lock;

public class MyThread implements Runnable {
	
	private Printer printer;
	public MyThread(Printer printer) {
		this.printer = printer;
	}
	
	public void run() {
		while(true) {
			printer.printerName();
		}
	}
}
