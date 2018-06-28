package com.seecen.exam.day0802;

public class PhoneDemo {

	public static void main(String[] args) {
		Phone iphone = new Phone();
		/*
		    Cell samsungCell = new Cell();
			samsungCell.pinPai = "三星";
			//赋值给iphone中的cell属性，否则为空
			iphone.cell = samsungCell;
		 */
		iphone.cell = new Cell("三星");
		System.out.println(iphone.downloadMusic());
		System.out.println(iphone.playMusic());
		System.out.println(iphone.chongDian());
	}

}
