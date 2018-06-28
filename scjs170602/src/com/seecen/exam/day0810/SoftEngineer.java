package com.seecen.exam.day0810;

/**
 * 软件工程师类,实现业务代理接口和编程接口
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class SoftEngineer implements IBizAgent, IProgrammer {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void writeProgrammer() {
		System.out.println(getName() + "会写代码!");
	}

	@Override
	public void giveBizSpeech() {
		System.out.println(getName() + "会讲业务!");
	}

	public void show() {
		System.out.println("我是一名软件工程师,我的名字叫" + getName());
	}
}
