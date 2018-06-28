package com.seecen.exam.day0802;

public class Cell {
	String pinPai;
	
	public Cell(){
		super();
	}
	public Cell(String pinPai) {
		super();
		this.pinPai = pinPai;
	}

	public String xuDian() {
		return "正在为" + pinPai + "的电池蓄电";
	}
}
