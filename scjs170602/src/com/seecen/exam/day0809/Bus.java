package com.seecen.exam.day0809;

/**
 * 公交实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public final class Bus extends MotoVehicle {
	/**
	 * 租金(小号:RENT_MONEY_SMALL)
	 */
	public static final double RENT_MONEY_SMALL = 800;
	/**
	 * 租金(大号:RENT_MONEY_LARGE)
	 */
	public static final double RENT_MONEY_LARGE = 1500;
	private int seatCount;// 座位数

	public Bus() {
		super();
	}

	public Bus(String num, String brand, String color, double mileage,
			int seatCount) {
		super(num, brand, color, mileage);
		this.seatCount = seatCount;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	@Override
	public double calRent(int day) {
		if (getSeatCount() <= 16) {
			return day * Bus.RENT_MONEY_SMALL;
		} else {
			return day * Bus.RENT_MONEY_LARGE;
		}
	}

}
