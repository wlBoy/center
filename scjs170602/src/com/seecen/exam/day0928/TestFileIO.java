package com.seecen.exam.day0928;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * IO流: 按反向： 输入流，输出流 下载 上传 按类型： 字节流，字符流
 * 字节输入流：InputStream->FileInputStream(BufferedInputStream)
 * 字节输出流：OutputStream->FileOutputStream(BufferedOutputStream) (操作字节)
 * 字符输入流：Reader->FileReader(BufferedReader)
 * 字符输出流：Writer->FileWriter(BufferedWriter) (操作字符，为读取中文而生)
 * 使用时，都建议加上缓冲区(上面4个都有其对应的缓冲区)，减少对硬盘的伤害
 */
public class TestFileIO {
	public static void main(String[] args) throws IOException {
		/*
		 * readFile();
		 * wirteFile();
		 * copyFile();
		 * File file = new File("D:\\Executable-software\\Virtual Machines");
		 * printFile(file, 0);
		 * readFile1(); 
		 * wirteFile1(); 
		 * copyFile1();
		 */
		readConvert();
	}

	/**
	 * 字节流FileInputStream读文件(用字节输入流从硬盘中读文件)
	 * 
	 * @throws IOException
	 */
	public static void readFile() throws IOException {
		File file = new File("d:" + File.separator + "test.txt");
		InputStream is = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(is, 1024 * 1024);
		byte[] b = new byte[1024];
		int len = 0;
		// 一次读2个字节,当len为-1时，表示已经读完
		while ((len = bis.read(b)) != -1) {
			// 将读完的字节数组变成字符串输出
			String str = new String(b, 0, len);
			System.out.print(str);
		}
		bis.close();
	}

	/**
	 * 字符流FileReader读文件(用字符输入流从硬盘中读文件)
	 * 
	 * @throws IOException
	 */
	public static void readFile1() throws IOException {
		File file = new File("d:" + File.separator + "test.txt");
		Reader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader,1024 * 1024);
		char[] c = new char[1024];
		int len = 0;
		// 一次读3个字符,当len为-1时，表示已经读完
		while ((len = br.read(c)) != -1) {
			// 将读完的字节数组变成字符串输出
			String str = new String(c, 0, len);
			System.out.print(str);
		}
		br.close();
	}

	/**
	 * 字节流FileOutputStream写文件(用字节输出流从硬盘中写文件)
	 * 
	 * @throws IOException
	 */
	public static void wirteFile() throws IOException {
		File source = new File("d:" + File.separator + "test.txt");
		File file = new File("d:" + File.separator + "test");
		// 如果该目录不存在,就创建该目录
		if (!file.exists()) {
			file.mkdir();
		}
		File file1 = new File(file, "my.txt");
		// 如果该文件不存在,就创建该文件
		if (!file1.exists()) {
			file1.createNewFile();
		}
		InputStream is = new FileInputStream(source);
		BufferedInputStream bis = new BufferedInputStream(is,1024 * 1024);
		// true代表可以在后面追加
		OutputStream out = new FileOutputStream(file1, true);
		BufferedOutputStream bos = new BufferedOutputStream(out,1024 * 1024);

		// out.write("小样".getBytes());
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		bis.close();
		//数据缓冲区剩余的数据
		bos.flush();
		bos.close();
	}

	/**
	 * 字符流FileWriter写文件(用字符输出流从硬盘中写文件)
	 * 
	 * @throws IOException
	 */
	public static void wirteFile1() throws IOException {
		File source = new File("d:" + File.separator + "test.txt");
		File file = new File("d:" + File.separator + "test");
		// 如果该目录不存在,就创建该目录
		if (!file.exists()) {
			file.mkdir();
		}
		File file1 = new File(file, "my.txt");
		// 如果该文件不存在,就创建该文件
		if (!file1.exists()) {
			file1.createNewFile();
		}
		Reader reader = new FileReader(source);
		BufferedReader br = new BufferedReader(reader,1024 * 1024);
		// true代表可以在后面追加
		Writer write = new FileWriter(file1, true);
		BufferedWriter bw = new BufferedWriter(write,1024 * 1024);
		char[] c = new char[1024];
		int len = 0;
		while ((len = br.read(c)) != -1) {
			bw.write(c, 0, len);
		}
		br.close();
		//数据缓冲区剩余的数据
		bw.flush();
		bw.close();
	}

	/**
	 * 将D盘中的test.txt文件复制到E盘的根目录下(用字节流FileInputStream和BufferedOutputStream)
	 * 
	 * @throws IOException
	 */
	public static void copyFile() throws IOException {
		File source = new File("d:" + File.separator + "test.txt");
		File desc = new File("e:" + File.separator + "test.txt");
		// 如果该文件不存在,就创建该文件
		if (!desc.exists())
			desc.createNewFile();
		// 构建源文件的输入流并为输入流加上缓冲区
		InputStream is = new FileInputStream(source);
		BufferedInputStream bis = new BufferedInputStream(is,1024 * 1024);
		// 构建目的文件的输出流并为输出流加上缓冲区
		OutputStream out = new FileOutputStream(desc);
		BufferedOutputStream bos = new BufferedOutputStream(out,1024 * 1024);

		byte[] b = new byte[1024];
		int len = 0;
		while ((len = bis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		// 释放资源
		is.close();
		//数据缓冲区剩余的数据
		bos.flush();
		bos.close();
	}

	/**
	 * 将D盘中的test.txt文件复制到E盘的根目录下(用字符流FileReader和FileWriter)
	 * 
	 * @throws IOException
	 */
	public static void copyFile1() throws IOException {
		File source = new File("d:" + File.separator + "test.txt");
		File desc = new File("e:" + File.separator + "test.txt");
		// 如果该文件不存在,就创建该文件
		if (!desc.exists())
			desc.createNewFile();
		// 构建源文件的输入流并为输入流加上缓冲区
		Reader reader = new FileReader(source);
		BufferedReader br = new BufferedReader(reader,1024 * 1024);
		// 构建目的文件的输出流并为输出流加上缓冲区
		Writer write = new FileWriter(desc);
		BufferedWriter bw = new BufferedWriter(write,1024 * 1024);

		char[] c = new char[1024];
		int len = 0;
		while ((len = br.read(c)) != -1) {
			bw.write(c, 0, len);
		}
		// 释放资源
		br.close();
		//数据缓冲区剩余的数据
		bw.flush();
		bw.close();
	}
	/**
	 * 用InputStreamReader将字节流转换为字符流
	 * @throws IOException
	 */
	public static void readConvert() throws IOException {
		InputStream is = new FileInputStream(new File("d:" + File.separator + "test.txt"));
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		while (true) {
			String s = br.readLine();
			if (s == null) {
				break;
			}
			System.out.println(s);
		}
		br.close();
	}
	
	/**
	 * 打印文件树(给定一个文件,打印出该文件下的所有文件结构)
	 * 
	 * @param file
	 *            要打印的文件
	 * @param level
	 *            几级目录，默认从0开始(指定文件为一级目录)
	 * @throws IOException
	 */
	public static void printFile(File file, int level) throws IOException {
		// 几级目录就打印几个-
		for (int i = 0; i < level; i++) {
			System.out.print("-");
		}
		// 输出当前文件名
		System.out.println(file.getName());
		// 判断是否为目录，是就列出该目录下的所有文件，再遍历递归调用该方法
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				// 递归调用
				printFile(f, level + 1);
			}
		}
	}
}
