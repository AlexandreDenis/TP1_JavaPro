package com.isima.creationannotation.container;

public class TransactionManager {
	private Transaction _transaction;
	
	public void begin(){
		_transaction = new Transaction();
	}
	
	public Transaction getTransaction(){
		return _transaction;
	}
	
	public void end(){
		
	}
}
