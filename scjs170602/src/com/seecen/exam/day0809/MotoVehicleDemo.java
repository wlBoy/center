package com.seecen.exam.day0809;

/**
 * 测试机动车类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月9日
 */
public class MotoVehicleDemo {

	public static void main(String[] args) {
		MotoVehicle c = new Car("1113", "大众", "黑", 120.23, "别克林荫大道");
		System.out.println("汽车租3天的租金为:" + c.calRent(5));
		MotoVehicle b = new Bus("2413", "南昌公交", "白", 154.3, 16);
		System.out.println("公交租2天的租金为:" + b.calRent(3));
		System.out.println("-----------------------------------");
		MotoVehicle[] motos = new MotoVehicle[4];
		motos[0] = new Car("京NNN328","宝马","黑",135.8,"宝马550i");//2500
		motos[1] = new Car("京NY28728","别克","白",189.2,"别克林荫大道");//1500
		motos[2] = new Bus("京龙NY522","京龙","黄",110.5,34);//7500
		motos[3] = new Truck("京龙NY872","京龙","黑",100.23,800);//2500
		
		System.out.println("这些车租车5天的总租金为:" + calTotalRent(motos)+"元!");//14000
	}
	public static double calTotalRent(MotoVehicle[] motos){
		double totalPrice = 0;
		for (int i = 0; i < motos.length; i++) {
			totalPrice += motos[i].calRent(5);
		}
		return totalPrice;
	}
}
