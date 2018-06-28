package com.seecen.exam.day0812;
/**
 * 测试人实体类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月12日
 */
public class TestPerson {
	public static void main(String[] args) {
		Son son = new Son("小明",20);
		son.eat();
		son.play();
		son.swim();
		son.show();
		Father t = new Father("小明他爸",37);
		t.show();
		Mother m = new Mother("小明他妈",35);
		m.show();
	}
}
