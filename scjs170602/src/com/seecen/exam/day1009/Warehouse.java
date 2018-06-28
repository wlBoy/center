package com.seecen.exam.day1009;

public class Warehouse {
	
	private int length = 5; // 仓库总大小
	private String[] goods = new String[length];
	private int size = 0;	// 当前仓库的数量
	private int index = 0; // 默认从第0个开始放置物品
	
	/**
	 * 生产方法
	 * notifyAll
	 * @throws InterruptedException 
	 */
	public synchronized void production(String userName) throws InterruptedException {
		// 只要仓库有，就去唤醒消费者消费
		if(size > 0) {
			this.notifyAll();
		}
		// 如果满了就不能去生产
		while(size == length) {
			this.wait();
		}
		// 生产一个物品
		String str = "粮草" + (int)(Math.random() * 100);
		goods[index++] = str;
		size++;
		System.out.println(userName + "生产了" + str + ",仓库数量为：" + index);
		Thread.sleep(500);
	}
	
	/**
	 * 消费方法
	 * @throws InterruptedException 
	 */
	public synchronized void consumption(String userName) throws InterruptedException {
		// 只要仓库没满，就通知生产者去生产
		if(size < length) {
			this.notifyAll();
		}
		// 如果没了就不能去消费
		while(size == 0) {
			this.wait();
		}
		String str = goods[0];
		System.arraycopy(goods, 1, goods, 0, goods.length - 1);
		size --;
		index --;
		System.out.println(userName + "消费了" + str + ",仓库数量为：" + index);
		Thread.sleep(500);
	}
}
