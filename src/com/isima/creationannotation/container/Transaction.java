package com.isima.creationannotation.container;

public class Transaction {
	private static int currId = 0;
	private int _id;
	
	public int getId(){
		return _id;
	}
	
	public Transaction(){
		_id = currId++;
	}
}
