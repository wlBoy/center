package com.seecen.exam.day0810.work3;

/**
 * 企鹅类,继承抽象父类宠物
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class Penguin extends Pet implements IEat, ISwimming {
	private String sex = "Q仔";

	public Penguin() {
		super();
	}

	public Penguin(String name, String sex) {
		super(name);
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public void show() {
		System.out.println("宠物的自白：\n我的名字叫" + getName() + "，我的健康值是"
				+ getHealth() + "，我和主人的亲密程度是" + getLove() + ",我的性别是" + getSex()
				+ "。");
	}

	@Override
	public void eat() {
		super.setHealth(super.getHealth() + 5);
		System.out.println("企鹅" + super.getName() + "吃饱啦！健康值增加5。");
	}

	@Override
	public void swim() {
		System.out.println("企鹅" + super.getName() + "正在游泳。");
		super.setHealth(super.getHealth() - 10);
		super.setLove(super.getLove() + 5);
	}
}
