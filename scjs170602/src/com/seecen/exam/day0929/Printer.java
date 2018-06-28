package com.seecen.exam.day0929;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Printer {
	/*
	 * 解决方法三:姓名使用局部变量
	 * public void printName(String name) throws Exception {
			File file = new File("D:\\test\\" + name + ".txt");
			if (!file.exists())
				file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			for (int i = 0; i < name.length(); i++) {
				bw.write(name.charAt(i));
			}
			// 换行
			bw.newLine();
			bw.flush();
			bw.close();
		}
	 */
	private String name;
	
	public void printName() throws Exception {
		 File file = new File("D:\\test\\" + name + ".txt");
		 if (!file.exists())
			 file.createNewFile();
		 BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		 for (int i = 0; i < name.length(); i++) {
			 bw.write(name.charAt(i));
		 }
		 // 换行
		 bw.newLine();
		 bw.flush();
		 bw.close();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
