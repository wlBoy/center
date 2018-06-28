package com.seecen.exam.day0929;

public class NameThread implements Runnable {
	private Printer printer;
	private String name;

	public NameThread(Printer printer, String name) {
		this.printer = printer;
		this.name = name;
	}
	/*
	 * 解决方法二:牺牲效率，保证安全，加同步代码(加锁)
	 * public void run() {
			try {
				synchronized (printer) {
					for (int i = 0; i < 1000; i++) {
						printer.setName(name);
						printer.printName();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 */
	/*
	 * 解决方法三:姓名使用局部变量
	 * public void run() {
			try {
				for (int i = 0; i < 1000; i++) {
					printer.printName(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 */
	public void run() {
		try {
			for (int i = 0; i < 1000; i++) {
				printer.setName(name);
				printer.printName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
