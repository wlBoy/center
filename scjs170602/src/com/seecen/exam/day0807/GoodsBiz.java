package com.seecen.exam.day0807;

import java.text.DecimalFormat;

/**
 * 商品业务类
 * 
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class GoodsBiz {
	/**
	 * 显示所有的商品信息
	 * 
	 * @param gds
	 *            商品对象数组
	 */
	public void showAll(Goods[] gds) {
		System.out.println("编号\t商品\t价格");
		DecimalFormat df = new DecimalFormat("#,###.####");
		for (int i = 0; i < gds.length; i++) {
			System.out.println(gds[i].getId() + "\t" + gds[i].getName() + "\t"
					+ df.format(gds[i].getPrice()));
		}
	}

	/**
	 * 根据id查找商品
	 * 
	 * @param gds
	 *            商品对象数组
	 * @param id
	 *            商品id
	 * @return 商品对象
	 */
	public Goods findGoodById(Goods[] gds, int id) {
		for (int i = 0; i < gds.length; i++) {
			if (id == gds[i].getId()) {
				return gds[i];
			}
		}
		return null;
	}

	/**
	 * 根据商品编号购买该商品
	 * 
	 * @param gds
	 *            商品对象数组
	 * @param id
	 *            商品id
	 * @param num
	 *            购买数量
	 * @return 购买总金额
	 */
	public double buyGoods(Goods[] gds, int id, int num) {
		double sum = 0;
		Goods gd = findGoodById(gds, id);
		sum += gd.getPrice() * num;
		return sum;
	}
}
