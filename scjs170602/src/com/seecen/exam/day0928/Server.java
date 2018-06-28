package com.seecen.exam.day0928;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
public class Server {
	public static void main(String[] args) throws Exception {
		// 创建一个socket服务绑定在本机的9999端口号上
		ServerSocket server = new ServerSocket(9999);
		// 等待客户端的连接
		System.out.println("...waiting client...");
		Socket socket = server.accept();
		System.out.println("client connectivity..." + socket);
		while(true) {
			// 服务器输入的地方
			String str = input();
			if("88".equals(str)) break;
			// 服务器往客户端发送数据
			socket.getOutputStream()
				.write(str.getBytes());
			// 输入完毕,告诉别人我输入完了,换行.
			socket.getOutputStream()
				.write("\r".getBytes());
			// 接收客户端发过来的数据
			InputStream is = socket.getInputStream();
			// 发过来的有可能是中文,所以转换为字符流
			InputStreamReader isr = 
					new InputStreamReader(is);
			BufferedReader br = 
					new BufferedReader(isr);
			// 读取一行发过来的数据
			String msg = br.readLine();
			System.out.println("client发送的数据为:" + msg);
		}	
		server.close();
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
