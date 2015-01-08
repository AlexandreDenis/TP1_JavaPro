package com.isima.creationannotation.container;

/**
 * Classe représentant une transaction
 * @author alexandre.denis
 *
 */
public class Transaction {
	// entier utilisé pour la génération d'identifiant unique
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
	 * Constructeur - génère un identifiant unique pour la nouvelle
	 * transaction
	 */
	public Transaction(){
		_id = currId++;
	}
}
