package com.xn.hk.common.utils.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title: MyEclipseGen
 * @Package: com.xn.ad.common.utils
 * @Description: MyEclipse破解程序,运行改程序,输入注册名,会生成该用户名的破解序列号,点击
 *               MyEclipse菜单中Window→Preferences
 *               →MyEclipse→Subscription,在右侧点击“Enter
 *               Subscription”,在弹出框中Subscriber中输入刚破解的用户名,在Subscription
 *               Code中输入刚破解生成的序列号,完成破解
 * @Author: wanlei
 * @Date: 2017-12-18 上午09:26:04
 */
public class MyEclipseGen {
	private static final Logger logger = LoggerFactory.getLogger(MyEclipseGen.class);
	private static final String LL = "Decompiling this copyrighted software is a violation of both your license agreement and the Digital Millenium Copyright Act of 1998 (http://www.loc.gov/copyright/legislation/dmca.pdf). Under section 1204 of the DMCA, penalties range up to a $500,000 fine or up to five years imprisonment for a first offense. Think about it; pay for a license, avoid prosecution, and feel better about yourself.";

	/**
	 * 获取破解序列号
	 * 
	 * @param userId
	 * @param licenseNum
	 * @return
	 */
	public static String getSerial(String userId, String licenseNum) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.add(1, 3);
		cal.add(6, -1);
		NumberFormat nf = new DecimalFormat("000");
		licenseNum = nf.format(Integer.valueOf(licenseNum));
		String verTime = new StringBuilder("-").append(new SimpleDateFormat("yyMMdd").format(cal.getTime())).append("0")
				.toString();
		String type = "YE3MP-";
		String need = new StringBuilder(userId.substring(0, 1)).append(type).append("300").append(licenseNum)
				.append(verTime).toString();
		String dx = new StringBuilder(need).append(LL).append(userId).toString();
		int suf = decode(dx);
		String code = new StringBuilder(need).append(String.valueOf(suf)).toString();
		return change(code);
	}

	private static int decode(String s) {
		int i;
		char[] ac;
		int j;
		int k;
		i = 0;
		ac = s.toCharArray();
		j = 0;
		k = ac.length;
		while (j < k) {
			i = (31 * i) + ac[j];
			j++;
		}
		return Math.abs(i);
	}

	private static String change(String s) {
		byte[] abyte0;
		char[] ac;
		int i;
		int k;
		int j;
		abyte0 = s.getBytes();
		ac = new char[s.length()];
		i = 0;
		k = abyte0.length;
		while (i < k) {
			j = abyte0[i];
			if ((j >= 48) && (j <= 57)) {
				j = (((j - 48) + 5) % 10) + 48;
			} else if ((j >= 65) && (j <= 90)) {
				j = (((j - 65) + 13) % 26) + 65;
			} else if ((j >= 97) && (j <= 122)) {
				j = (((j - 97) + 13) % 26) + 97;
			}
			ac[i] = (char) j;
			i++;
		}
		return String.valueOf(ac);
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.print("请输入注册用户名(英文):");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String userId = reader.readLine();
			System.out.println("破解序列号为:" + MyEclipseGen.getSerial(userId, "5"));
			reader.readLine();
		} catch (IOException ex) {
			logger.error("获取破解序列号失败，原因为:{}", ex);
		}
	}
}