package com.seecen.exam.day0801.work;
/**
 *  百钱买百鸡  100块钱几种买法可以买到100只鸡
 *  1只公鸡5块  1只母鸡3块  3只小鸡1块
 * @author Administrator
 */
public class HundredHen {
	public static void main(String[] args) {
		System.out.println("100块钱买到100只鸡的买法如下:");
		// 公鸡数量(最多20，循环20次)
		for (int cock = 0; cock < 20; cock++) {
			// 母鸡数量(最多33)
			for (int hen = 0; hen < 33; hen++) {
				// 小鸡数量(一定是3的倍数)
				int chicks = 100 - cock - hen;
				//所有鸡的价格总和
				int price = cock * 5 + hen * 3 + chicks / 3;
				if (price == 100 && chicks % 3 == 0) {
					System.out.println("公鸡的只数：" + cock + "," + "母鸡的只数：" + hen
							+ "," + "小鸡的只数：" + chicks);
				}
			}
		}
	}
}
