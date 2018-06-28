package com.seecen.exam.day0808;

/**
 * 企鹅实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Penguin extends Pet {
	final String SEX_MEN = "雄";
	final String SEX_FEMAL = "雌";
	private String sex;// 性别

	public Penguin() {
		super();
	}

	public Penguin(String name, int healthy, int lover) {
		super(name, healthy, lover);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 企鹅游泳方法(自己的方法)
	 */
	public void swimming() {
		this.setHealthy(this.getHealthy() - 5);
		this.setLover(this.getLover() + 8);
		System.out.println(getName() + "玩游泳游戏!");
	}

	/**
	 * 企鹅自白方法(复写父类的抽象方法)
	 */
	@Override
	public void show() {
		System.out.println("我的名字叫:\"" + this.getName() + "\",我的健康值是:"
				+ this.getHealthy() + ",我和我主人的亲密程度是:" + this.getLover()
				+ ",我的性别是:" + this.getSex());
	}

	/**
	 * 企鹅去医院方法(复写父类的抽象方法)
	 */
	@Override
	public void toHospital() {
		this.setHealthy(this.getHealthy() + 20);
		System.out.println(getName() + "吃药,疗养!");
	}

	/**
	 * 企鹅吃饭方法(复写父类的抽象方法)
	 */
	@Override
	public void eat() {
		System.out.println(getName() + "喜爱吃大米饭!");
	}
}
