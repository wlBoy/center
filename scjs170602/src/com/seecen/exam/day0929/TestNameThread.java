package com.seecen.exam.day0929;
/**
 * 实例变量，还不加锁，会造成线程安全的问题,解决方法如下:
 * 1. 创建多个printer实例，可以解决，但会增加内存负担，不推荐使用
 * 2. 牺牲时间效率，保证安全，加同步代码块synchronized(加锁)
 * 3. 使用局部变量，不存在线程安全问题，推荐使用
 * @author wanlei
 * 关键字synchronized的用法:
 * 1.修饰实例方法，代表该实例方法的全部内容都同步，加锁对象为this(当前对象)
 * 2.作为同步代码块，加锁对象为任意唯一的对象即可，代码块内的内容同步
 * 3.修饰静态static方法，代表该静态方法的全部内容都同步，加锁对象为当前类的字节码文件，即当前类名.class，这个是一个类唯一的标识
 */
public class TestNameThread {
	/*
	 * 解决方法一:创建多个printer实例
	 * public static void main(String[] args) {
			Printer printer1 = new Printer();
			Printer printer2 = new Printer();
			Runnable r1 = new NameThread(printer1, "xizhiying");
			Runnable r2 = new NameThread(printer2, "yanjunwei");
			Thread t1 = new Thread(r1);
			Thread t2 = new Thread(r2);
			t1.start();
			t2.start();
		}
	 */
	public static void main(String[] args) {
		Printer printer = new Printer();
		Runnable r1 = new NameThread(printer, "xizhiying");
		Runnable r2 = new NameThread(printer, "yanjunwei");
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
	}
}
