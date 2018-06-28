package com.seecen.exam.day0809;

/**
 * 机动车实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public abstract class MotoVehicle {
	public static final String CAR_GL8 = "别克商务舱GL8";
	public static final String CAR_550i = "宝马550i";
	public static final String CAR_bieke = "别克林荫大道";
	
	// 车牌号，品牌，颜色，里程
	private String num;
	private String brand;
	private String color;
	private double mileage;

	public MotoVehicle() {
		super();
	}

	public MotoVehicle(String num, String brand, String color, double mileage) {
		super();
		this.num = num;
		this.brand = brand;
		this.color = color;
		this.mileage = mileage;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public abstract double calRent(int day);
}
