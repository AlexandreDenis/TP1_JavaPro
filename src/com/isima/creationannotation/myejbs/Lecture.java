package com.isima.creationannotation.myejbs;

public class Lecture implements ILecture{
	private int _integer;
	
	public Lecture(){
		_integer = 0;
	}
	
	public void readDB() {
		
	}
	
	public void incInteger(){
		++_integer;
	}
	
	public int getInteger(){
		return _integer;
	}
}
