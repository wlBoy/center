package com.seecen.exam.day0731;

import java.util.Scanner;
/**
 * 往已有的歌曲数组中有序地插入一个新歌曲
 * compareToIgnoreCase 
 * 1)比较前后位置，忽略大小写
 * 2)在前面为负数， 在后面为正数
 * 3)按顺序比较，第一个相同，比第二个，以此类推
 * @author Administrator
 */
public class InsertMusic {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String answer = null;
		do{
			String[] musics = { "Island", "Ocean", "Pretty", "Sun" };
			System.out.print("请输入要插入的歌曲名称：");
			String music = sc.next();
			// 根据用户输入的歌曲名称,找出要插入的位置,index初始值为数组的最后一个位置
			int index = musics.length;
			for (int i = 0; i < musics.length; i++) {
				//当条件成立时,music应该是除了在数组最后一个位置之前的位置
				if (music.compareToIgnoreCase(musics[i]) < 0) {
					index = i;
					break;
				}
			}
			// 创建一个新数组,将旧数组的数据拷贝到新数组
			String[]  newMusics = new String[musics.length + 1];
			/*for (int i = 0; i < musics.length; i++) {
				newMusics[i] = musics[i];
			}*/
			System.arraycopy(musics, 0, newMusics, 0, musics.length);
			// 将新数组在要插入的位置index之后的内容全部往后移一个位置,从后面开始移
			for (int i = newMusics.length - 1; i > index; i--) {
				newMusics[i] = newMusics[i - 1];
			}
			// 将输入的音乐插入到新数组中的index位置去
			newMusics[index] = music;
			// 将新数组输出
			for (int i = 0; i < newMusics.length; i++) {
				System.out.print(newMusics[i] + " ");
			}
			System.out.println("");
			System.out.print("你是否要继续插入歌曲(y/n):");
			answer = sc.next();
		}while(answer.equalsIgnoreCase("y"));
		System.out.println("操作完毕，系统退出!");
		sc.close();
	}
}
