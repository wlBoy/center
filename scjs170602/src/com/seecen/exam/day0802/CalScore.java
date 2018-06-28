package com.seecen.exam.day0802;

public class CalScore {
	double javaScore;
	double CShapeScore;
	double DBScore;
	
	public double sumScore(){
		return javaScore+CShapeScore+DBScore;
	}
	public double avgScore(){
		return sumScore()/3;
	}
}
