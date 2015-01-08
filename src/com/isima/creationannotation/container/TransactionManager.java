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
	
	/**
	 * Démarre une nouvelle transaction on réutilise celle courante.
	 * Cela dépend de la stratégie utilisée.
	 * Voir le TransactionAttribute de l'EJB nécessitant une transaction.
	 */
	public void begin(){

	}
	
	public void commit(){
		
	}
	
	/**
	 * Récupère la transaction courante s'il y en a une
	 * @return la transaction courante
	 */
	public Transaction getTransaction(){
		Transaction currTx = null;
		
		if(_transactions != null && _transactions.size() > 0){
			currTx = _transactions.firstElement();
		}
		
		return currTx;
	}
	
	/**
	 * Termine la transaction courante
	 */
	public void end(){
		if(_transactions != null && _transactions.size() > 0){
			_transactions.pop();	//
		}
	}
	
	/**
	 * Renvoie le nombre de transactions gérées par le TransactionManager 
	 * au moment de l'appel de la méthode
	 * @return le nombre de transactions dans la pile du TransactionManager
	 */
	public int getNbTransactions(){
		return _transactions.size();
	}
}
