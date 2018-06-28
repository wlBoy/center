package com.seecen.exam.day0928;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
public class Client {
	public static void main(String[] args) throws Exception {
		// 创建和服务器的连接
		Socket socket = new Socket("127.0.0.1",9999);
		System.out.println("客户端已连上");
		while(true) {
			// 用输入流获取服务器发送过来的数据
			InputStream is = socket.getInputStream();
			// 发过来的有可能是中文,所以转换为字符流
			InputStreamReader isr = 
					new InputStreamReader(is);
			BufferedReader br = 
					new BufferedReader(isr);
			// 读取一行发过来的数据
			String msg = br.readLine();
			System.out.println("server发送的数据为:" + msg);
			
			// 客户端往服务器发送数据
			String str = input();
			if("88".equals(str)) break;
			socket.getOutputStream().write(str.getBytes());
			socket.getOutputStream().write("\r".getBytes());
		}
		
		
		socket.close();
	}
	
	public static String input() throws Exception {
		/**
		 * System.in : 
		 * 将用户控制台输入的数据转为输入流
		 * InputStreamReader:
		 * 将输入流转为字符流,可以兼容中文
		 * BufferedReader:
		 * 在字符流的基础上,套上缓冲区*/
		InputStreamReader reader = 
				new InputStreamReader(System.in);
		BufferedReader br = 
				new BufferedReader(reader);
		System.out.print("请输入:");
		// readLine读取一行的意思
		// 如果没有这一行,会一直等待这一行的到来
		// 用户敲了回车,代表换行,程序也就读到这一行了
		String str = br.readLine();
		// 将用户输入的数据返回给调用方法者
		return str;
	}
}
