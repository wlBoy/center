package com.seecen.exam.day0803;

public class GuessDemo {
	public static void main(String[] args) {
		Guess gs = new Guess();
		gs.init();
		if(gs.guessPrice()){
			System.out.println("恭喜你猜对啦!");
		}else{
			System.out.println("对不起，你4次都没猜对,下次再努力吧!");
		}
	}
}
