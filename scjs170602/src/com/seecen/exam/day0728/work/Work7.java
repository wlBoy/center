package com.seecen.exam.day0728.work;

import java.util.Scanner;
/**
 * 输出一批整数中的最大值和最小值
 * @author Administrator
 */
public class Work7 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = 0,max = 0,min = 0;
		do{
			System.out.print("请输入一个整数(输入0结束):");
			num = sc.nextInt();
			if(num>max){
				max=num;
			}
			if(num>0){
				//将后面一个值和前面一个值进行最小值比较
				if(num<min||min==0){
					min=num;
				}
			}
		}while(num!=0);		
		System.out.print("最大值为:" + max +"\t最小值为:" + min);
		sc.close();
	}
}
