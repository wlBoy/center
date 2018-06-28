package com.seecen.exam.day0808;

/**
 * 宠物实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public abstract class Pet {
	private String name;// 名字
	private int healthy = 60;// 健康值
	private int lover = 20;// 亲密值

	public Pet() {
		super();
	}

	public Pet(String name, int healthy, int lover) {
		super();
		this.name = name;
		this.healthy = healthy;
		this.lover = lover;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealthy() {
		return healthy;
	}

	public void setHealthy(int healthy) {
		if (healthy >= 0 && healthy <= 100) {
			this.healthy = healthy;
		} else {
			System.out.println("健康值应该在0-100之间，默认值是60");
		}
	}

	public int getLover() {
		return lover;
	}

	public void setLover(int lover) {
		this.lover = lover;
	}
	/**
	 * 自白的抽象方法，让子类去具体实现
	 */
	public abstract void show();
	/**
	 * 去医院的抽象方法，让子类去具体实现
	 */
	public abstract void toHospital();
	/**
	 * 吃饭的抽象方法，让子类去具体实现
	 */
	public abstract void eat();
}
