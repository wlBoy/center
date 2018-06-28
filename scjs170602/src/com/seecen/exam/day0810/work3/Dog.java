package com.seecen.exam.day0810.work3;

/**
 * 狗类,继承抽象父类宠物
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class Dog extends Pet implements IFlyingDisc,IEat{
	private String strain = "雪瑞娜";

	public Dog() {
		super();
	}

	public Dog(String name, String strain) {
		super(name);
		this.strain = strain;
	}

	public String getStrain() {
		return strain;
	}

	public void setStrain(String strain) {
		this.strain = strain;
	}

	@Override
	public void show() {
		System.out.println("宠物的自白：\n我的名字叫" + getName() + "，我的健康值是"
				+ getHealth() + "，我和主人的亲密程度是" + getLove() + "。");
	}
	@Override
	public void eat() {
		super.setHealth(super.getHealth() + 3);
		System.out.println("狗狗" + super.getName() + "吃饱啦！健康值增加3。");
	}
	@Override
	public void catchingFlyDisc() {
		System.out.println("狗狗" + super.getName() + "正在接飞盘。");
		super.setHealth(super.getHealth() - 10);
		super.setLove(super.getLove() + 5);
	}
}
