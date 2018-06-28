package com.seecen.exam.day0729;

//import java.util.Scanner;
/**
 * 测试循环，一些循环的经典案例
 * @author Administrator
 */
public class TestCycle2 {
	public static void main(String[] args) {
		/*	1.整数反转:
			1)String str=""+value1%10+value1/10%10+value1/100%10+value1/1000%10+value1/10000%10;
			  System.out.println("反转之后的整数是:"+ str);
			2)int value1 = 123456789;
			  int value2 ;
			  while(value1/1!=0){
				  value2 =  value1 % 10;//取到末尾一位
				  System.out.print(value2);
				  value1 = value1 / 10 ; //得到除末尾的前n位
			  }
			2.等于 一个数字的所有表达式
			Scanner sc = new Scanner(System.in);
			System.out.print("请输入一个数:");
			int num = sc.nextInt();
			System.out.println("等于"+num+"的表达式有:");
			for (int i = 0 ; i <= num; i++) {
				//(num-i)+i=num,循环i+1次
				System.out.println(i+"+"+(num-i)+"="+num);
			}
			3.计算n位顾客，年龄大于或小于30的各比例
			Scanner sc = new Scanner(System.in);
			int age , count1 = 0, count2 = 0;
			System.out.print("请输入顾客的位数:");
			double num = sc.nextDouble();
			for (int i = 0; i < num; i++) {
				System.out.print("请输入第"+(i+1)+"位顾客的年龄:");
				age = sc.nextInt();
				//用两个计数器分别计算人数个数
				if(age>30)	count1++;
				if(age<30)	count2++;
			}
			//num必须是double类型，否则不会产生小数，会一直是0
			String par1 = (count1/num*100)+"%";
			String par2= (count2/num*100)+"%";
			System.out.println("30岁以上的比例是:"+ par1);
			System.out.println("30岁以下的比例是:"+ par2);
			4.模拟添加客户信息
			Scanner sc = new Scanner(System.in);
			System.out.println("Myshopping管理系统>客户信息管理系统>添加客户信息");
			System.out.print("请输入你需录入信息的客户人数:");
			int num = sc.nextInt();
			for (int i = 0; i < num; i++) {
				System.out.println("第" + (i + 1) + "位客户信息正在录入:");
				System.out.println("请输入会员号(<4位整数>):");
				int IdNum = sc.nextInt();
				System.out.println("请输入会员生日(月/日<用两位整数表示>):");
				String birth = sc.next();
				System.out.println("请输入会员积分:");
				int score = sc.nextInt();
				// 判断会员号是否为4位整数
				if (IdNum >= 1000 && IdNum <= 9999) {
					System.out.println("已录入的会员信息是:");
					System.out.println("会员号\t会员生日\t会员积分");
					System.out.println(IdNum + "\t" + birth + "\t" + score);
				} else {
					System.out.println("会员号" + IdNum + "是无效的会员号!");
					System.out.println("录入信息失败，请重新录入!");
				}
			}
			System.out.println("录入客户信息完毕，程序结束!");
			sc.close();
			5.分别在不同店中买衣服，最后统计总共购买衣服的数量
			Scanner sc = new Scanner(System.in);
			int shopCount = 5;
			String answer = null;
			int sum = 0;
			for (int i = 1; i <= shopCount; i++) {
				int count = 0;
				System.out.println("欢迎光临第" + i + "家店:");
				do {
					System.out.print("要离开吗(y/n):");
					answer = sc.next();
					if (answer.equals("n")) {
						sum++;
						count++;
						System.out.println("您在第" + i + "家店买了" + count + "件衣服啦！");
					}
				} while (answer.equals("n"));
			}
			System.out.println("您在"+shopCount+"家店中共购买了" + sum + "件衣服！");
			sc.close();
			*/
		//打印九九乘法表
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(j + "*" + i + "=" + (i * j) + "\t");
			}
			System.out.println();
		}
	}
}
