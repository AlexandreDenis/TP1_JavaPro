package com.isima.creationannotation.container;

/**
 * Classe repr�sentant une transaction
 * @author alexandre.denis
 *
 */
public class Transaction {
	// entier utilis� pour la g�n�ration d'identifiant unique
	private static int currId = 0;
	
	// identifiant unique de la transaction
	private int _id;
	
	/**
	 * Obtention de l'identifiant de la transaction
	 * @return l'identifiant de la transaction
	 */
	public int getId(){
		return _id;
	}
	
	/**
	 * Constructeur - g�n�re un identifiant unique pour la nouvelle
	 * transaction
	 */
	public Transaction(){
		_id = currId++;
	}
}
