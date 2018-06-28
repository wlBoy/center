package com.seecen.exam.day1009;

public class Customer implements Runnable {

	private Warehouse wh;
	private String userName;
	public Customer(String userName,Warehouse wh) {
		this.userName = userName;
		this.wh = wh;
	}
	public void run() {
		while(true) {
			try {
				wh.consumption(userName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
