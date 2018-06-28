package com.seecen.exam.day0811;

/**
 * 教师实体类
 * 用来测试重写父类object中的常用方法
 * @scjs170602
 * @author 【万磊】
 * @2017年8月11日
 */
public class Teacher implements Cloneable{
	private String name;// 姓名
	private String major;// 专业
	private int teachAge;// 教龄

	public Teacher() {
		super();
	}

	public Teacher(String name, String major, int teachAge) {
		super();
		this.name = name;
		this.major = major;
		this.teachAge = teachAge;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getTeachAge() {
		return teachAge;
	}

	public void setTeachAge(int teachAge) {
		this.teachAge = teachAge;
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println(getName() + "这个对象快要被GC回收了!");
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Teacher)) {
			return false;
		}
		Teacher t = (Teacher) obj;
		if (t.getName().equals(this.getName())
				&& t.getMajor().equals(this.getMajor())
				&& t.getTeachAge() == this.getTeachAge()) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public String toString() {
		return "Teacher [name=" + name + ", major=" + major + ", teachAge="
				+ teachAge + "]";
	}

	@Override
	public int hashCode() {
		int suanfa = 10 * 20 * 30 - 12;
		return (this.getName().hashCode() + this.getMajor().hashCode() + this
				.getTeachAge()) * suanfa;
	}
}
