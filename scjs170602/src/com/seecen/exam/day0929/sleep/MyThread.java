package com.seecen.exam.day0929.sleep;

public class MyThread implements Runnable {
	public static final String[] NAMES = { "liuxuande", "guanyunchang", "zhangyide" };
	private Printer printer;
	private int index;

	public MyThread(Printer printer, int index) {
		super();
		this.printer = printer;
		this.index = index;
	}

	public void run() {
		while (true) {
			printer.printerName(index);
		}
	}

}
