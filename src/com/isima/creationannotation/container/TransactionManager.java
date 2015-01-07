package com.isima.creationannotation.container;

import java.util.Stack;

public class TransactionManager {
	
	// singleton
	private static TransactionManager INSTANCE = new TransactionManager();
	
	private Stack<Transaction> _transactions;
	
	private TransactionManager(){
		_transactions = new Stack<Transaction>();	// initialisation de la pile de transaction
	}
	
	public static TransactionManager getInstance(){
		return INSTANCE;
	}
	
	public void begin(){

	}
	
	public void commit(){
		
	}
	
	public Transaction getTransaction(){
		return null;
	}
	
	public void end(){
		
	}
}
