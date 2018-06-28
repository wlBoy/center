package com.seecen.exam.day0811;

/**
 * 测试教师类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月11日
 */
public class TeacherDemo {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * 父类object类中的==和equals()方法都是比内存地址的,要使==比地址,equals()方法比内容,所以一般要重写父类的equals()方法和hashCode()方法(String类就是重写了)
		 */
		Teacher t1 = new Teacher("李四", "计算机", 20);
		Teacher t2 = new Teacher("张", "计算机", 20);
		Teacher t3 = new Teacher();
		try {
			t3 = (Teacher) t1.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} 
		System.out.println(t3.toString());
		Thread.sleep(3000);
		t3 = null;
		System.gc();
		System.out.println(t1 == t2);
		System.out.println(t1.equals(t2));
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
	}

}
