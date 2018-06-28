package com.seecen.exam.day0809;

/**
 * 汽车实体类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public final class Car extends MotoVehicle {
	/**
	 * 租金(型号GL8:RENT_MONEY_GL8)
	 */
	public static final double RENT_MONEY_GL8 = 600;
	/**
	 * 租金(型号550i:RENT_MONEY_550i)
	 */
	public static final double RENT_MONEY_550i = 500;
	/**
	 * 租金(型号bieke:RENT_MONEY_bieke)
	 */
	public static final double RENT_MONEY_bieke = 300;
	private String type;// 型号

	public Car() {
		super();
	}

	public Car(String num, String brand, String color, double mileage,
			String type) {
		super(num, brand, color, mileage);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public double calRent(int day) {
		if (getType().equals(MotoVehicle.CAR_GL8)) {
			return day * Car.RENT_MONEY_GL8;
		} else if (getType().equals(MotoVehicle.CAR_550i)) {
			return day * Car.RENT_MONEY_550i;
		} else if (getType().equals(MotoVehicle.CAR_bieke)) {
			return day * Car.RENT_MONEY_bieke;
		} else{
			System.out.println("错误的汽车型号!");
			return 0;
		}
	}

}
