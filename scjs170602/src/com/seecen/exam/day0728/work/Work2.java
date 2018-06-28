package com.seecen.exam.day0728.work;
/**
 * 使用do..while循环,输出摄氏度(0-250)与相应的华氏度的对照表
 * @author Administrator
 */
public class Work2 {
	public static void main(String[] args) {
		int tem = 0 ;
		double oF = 0 ;
		do{
			System.out.println("摄氏温度\t\t" + tem + " ℃");
			oF = tem * 9 / 5.0 +32  ;
			System.out.println("对应的华氏温度\t" + oF + " ℉");
			tem += 20;
		}while(tem<=250);
	}
}
