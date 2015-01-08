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
	 * D�marre une nouvelle transaction on r�utilise celle courante.
	 * Cela d�pend de la strat�gie utilis�e.
	 * Voir le TransactionAttribute de l'EJB n�cessitant une transaction.
	 */
	public void begin(){

	}
	
	public void commit(){
		
	}
	
	/**
	 * R�cup�re la transaction courante s'il y en a une
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
	 * Renvoie le nombre de transactions g�r�es par le TransactionManager 
	 * au moment de l'appel de la m�thode
	 * @return le nombre de transactions dans la pile du TransactionManager
	 */
	public int getNbTransactions(){
		return _transactions.size();
	}
}
