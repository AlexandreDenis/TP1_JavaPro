package com.isima.creationannotation.container;

import java.util.Stack;

/**
 * TransactionManager
 * Gestionnaire de transactions utilisé par les proxys
 * @author alexandre.denis
 *
 */
public class TransactionManager {
	
	// singleton
	private static TransactionManager INSTANCE = new TransactionManager();
	
	// pile de transactions
	private Stack<Transaction> _transactions;
	
	/**
	 * Constructeur privé
	 */
	private TransactionManager(){
		_transactions = new Stack<Transaction>();	// initialisation de la pile de transactions
	}
	
	/**
	 * Obtention de l'instance unique de TransactionManager
	 * @return singleton du TransactionManager
	 */
	public static TransactionManager getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Démarre une nouvelle transaction ou réutilise celle courante
	 * si il n'y en a pas en court.
	 */
	public void begin(){
		if(_transactions.size() < 1){
			_transactions.push(new Transaction());
		}
	}
	
	/**
	 * Démarre une nouvelle transaction.
	 */
	public void beginNewTransaction(){
		_transactions.push(new Transaction());
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
			_transactions.pop();	// on supprime la transaction courante de la pile
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
