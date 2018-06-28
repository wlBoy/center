package com.seecen.exam.day0810.work3;

/**
 * 宠物抽象类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public abstract class Pet {
	private String name = "无名氏";// 昵称
	private int health = 100;// 健康值
	private int love = 20;// 亲密度

	public Pet() {
		super();
	}

	public Pet(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public abstract void show();
}
