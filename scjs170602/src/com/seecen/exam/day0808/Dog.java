package com.seecen.exam.day0808;

/**
 * 狗实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Dog extends Pet {
	private String type;// 类型

	public Dog() {
		super();
	}

	public Dog(String name, int healthy, int lover) {
		super(name, healthy, lover);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 狗玩飞盘的方法(自己的方法)
	 */
	public void catchFlyDisc() {
		this.setHealthy(this.getHealthy() - 10);
		this.setLover(this.getLover() + 5);
		System.out.println(getName() + "玩飞盘游戏!");
	}

	/**
	 * 狗自白方法(复写父类的抽象方法)
	 */
	@Override
	public void show() {
		System.out.println("我的名字叫:\"" + this.getName() + "\",我的健康值是:"
				+ this.getHealthy() + ",我和我主人的亲密程度是:" + this.getLover()
				+ ",我的类型是:" + this.getType());
	}

	/**
	 * 狗去医院方法(复写父类的抽象方法)
	 */
	@Override
	public void toHospital() {
		this.setHealthy(this.getHealthy() + 10);
		System.out.println(getName() + "打针,吃药!");
	}

	/**
	 * 狗吃饭方法(复写父类的抽象方法)
	 */
	@Override
	public void eat() {
		System.out.println(getName() + "爱好吃排骨!");
	}
}
