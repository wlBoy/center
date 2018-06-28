package com.seecen.exam.day0804;

public class VIPBiz {
	/**
	 * 显示所有会员信息
	 */
	public void showAll(VIP[] vips) {
		System.out.println("***会员列表***");
		System.out.println("编号\t积分");
		for (VIP v : vips) {
			System.out.println(v.num + "\t" + v.score);
		}
	}

	/**
	 * 根据会员编号查找会员
	 * 
	 * @param num
	 *            会员编号
	 * @param vips
	 *            要查找的VIP对象数组
	 * @return 找到返回该VIP，否则返回null
	 */
	public VIP findByNum(VIP[] vips, String num) {
		//两种循环都可以
		/*for (int i = 0; i < vips.length; i++) {
			if (num.equals(vips[i].num)) {
				return vips[i];
			}
		}*/
		for (VIP vip : vips) {
			if(vip.num!=null&&vip.num.equals(num)){
				return vip;
			}
		}
		return null;
	}
}
