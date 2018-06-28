package com.seecen.exam.day0809;

/**
 * 卡车实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月10日
 */
public class Truck extends MotoVehicle {
	/**
	 * 租金(轻:RENT_MONEY_SMALL)
	 */
	public static final double RENT_MONEY_LIGHT = 500;
	/**
	 * 租金(重:RENT_MONEY_LARGE)
	 */
	public static final double RENT_MONEY_WEIGHT = 1000;
	private int loadCapacity;// 承载量

	public Truck() {
		super();
	}

	public Truck(String num, String brand, String color, double mileage,
			int loadCapacity) {
		super(num, brand, color, mileage);
		this.loadCapacity = loadCapacity;
	}

	public int getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(int loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	@Override
	public double calRent(int day) {
		if (getLoadCapacity() > 1000) {
			return day * Truck.RENT_MONEY_WEIGHT;
		} else {
			return day * Truck.RENT_MONEY_LIGHT;
		}
	}

}
