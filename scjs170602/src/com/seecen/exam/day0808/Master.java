package com.seecen.exam.day0808;

/**
 * 主人实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class Master {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * 治疗宠物的方法
	 * 健康值小于50就治疗
	 * @param pet
	 *            宠物对象
	 */
	public void Cure(Pet pet) {
		if (pet.getHealthy() < 50) {
			pet.toHospital();
		}
	}

	/**
	 * 喂养宠物的方法
	 * 
	 * @param pet
	 *            宠物对象
	 */
	public void feed(Pet pet) {
		pet.eat();
	}

	/**
	 * 和宠物玩的方法 
	 * 根据传进来的父类对象Pet判断其类型，是Dog就调用狗的方法,是Penguin就调用企鹅的方法
	 * @param pet
	 *            宠物对象
	 */
	public void play(Pet pet) {
		if (pet instanceof Dog) {
			Dog dog = (Dog) pet;
			dog.catchFlyDisc();
		}
		if (pet instanceof Penguin) {
			Penguin penguin = (Penguin) pet;
			penguin.swimming();
		}
	}
}
