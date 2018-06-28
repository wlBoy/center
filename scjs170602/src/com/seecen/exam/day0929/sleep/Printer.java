package com.seecen.exam.day0929.sleep;

/**
 * sleep 属于Thread类的方法，不会释放锁，可以写在非同步块中 sleep必须传入睡眠时间 wait
 * 属于Obect类的方法，会释放锁，需要在同步块中，否则会抛出异常 wait可以传入时间，也可以不传，
 * 不传会永久睡眠（阻塞状态）,需其他线程notify唤醒。
 * 
 * @author bigpig
 *
 */
public class Printer {
	private int flag;

	public Printer() {
		flag = 0;
	}

	public synchronized void printerName(int index) {
		try {

			if (flag == index) {
				for (int j = 0; j < 5; j++) {
					for (int i = 0; i < MyThread.NAMES[index].length(); i++) {
						System.out.print(MyThread.NAMES[index].charAt(i));
					}
					System.out.println();
					Thread.sleep(300);
				}
				flag++;
			}
			if (flag == MyThread.NAMES.length) {
				flag = 0;
			}
			this.notifyAll();
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
