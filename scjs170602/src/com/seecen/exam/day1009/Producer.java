package com.seecen.exam.day1009;

public class Producer implements Runnable {

	private Warehouse wh;
	private String userName;
	public Producer(String userName,Warehouse wh) {
		this.userName = userName;
		this.wh = wh;
	}
	
	public void run() {
		while(true) {
			try {
				wh.production(userName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
