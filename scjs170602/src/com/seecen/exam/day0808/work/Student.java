package com.seecen.exam.day0808.work;

/**
 * 学生实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月8日
 */
public class Student {
	public static final String MAJOR_COMPUTER = "计算机科学与技术";
	public static final String MAJOR_SALES = "市场营销";
	public static final String MAJOR_ENGINEER = "土木工程";
	// 姓名，年龄，性别，专业
	private String name;
	private int age = 18;
	private String sex = "男";
	private String pro;

	public Student() {
		super();
	}

	public Student(String name, int age, String sex, String pro) {
		super();
		this.setName(name);
		this.setAge(age);
		this.setSex(sex);
		this.setPro(pro);
	}

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
		if (age > 0 && age < 25) {
			this.age = age;
		} else {
			System.out.println("学生年龄应该:0~25岁！(默认值为:18)");
		}
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		if (sex.equals("男") || sex.equals("女")) {
			this.sex = sex;
		} else {
			System.out.println("性别只能是男或女!(默认值为:男)");
		}
	}

	public String getPro() {
		return pro;
	}

	public void setPro(String pro) {
		this.pro = pro;
	}

	/**
	 * 自白函数
	 */
	public void show() {
		System.out.println("我的姓名是:" + getName() + ",年龄是:" + getAge() + ",性别是:"
				+ getSex() + ",专业是:" + getPro());
	}

	/**
	 * 自白函数
	 * 
	 * @param num
	 *            要自白的次数
	 */
	public void show(int num) {
		for (int i = 0; i < num; i++) {
			show();
		}
	}

	/**
	 * 自白函数
	 * 
	 * @param isShow
	 *            是否要自白,false表示不自白，否则自白一次
	 */
	public void show(boolean isShow) {
		if (isShow) {
			show();
		} else {
			System.out.println();
		}
	}
}
