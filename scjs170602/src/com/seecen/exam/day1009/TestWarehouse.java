package com.seecen.exam.day1009;

public class TestWarehouse {
	public static void main(String[] args) {
		Warehouse wh = new Warehouse();

		Producer zhuge = new Producer("诸葛亮", wh);
		Producer sima = new Producer("司马老贼", wh);
		Producer zhouyu = new Producer("周小瑜", wh);
		Customer zhaoyun = new Customer("赵云", wh);
		Customer zhangfei = new Customer("张飞", wh);
		Customer guanyu = new Customer("关羽", wh);

		Thread t1 = new Thread(zhuge);
		Thread t2 = new Thread(sima);
		Thread t3 = new Thread(zhouyu);
		Thread t4 = new Thread(zhaoyun);
		Thread t5 = new Thread(zhangfei);
		Thread t6 = new Thread(guanyu);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
}
