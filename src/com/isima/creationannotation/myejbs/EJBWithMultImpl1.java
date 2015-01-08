package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * 1ère classe implémentant l'interface IEJBWithMultImpl
 * @author alexandre.denis
 *
 */
public class EJBWithMultImpl1 implements IEJBWithMultImpl {
	
	/**
	 * Méthode simple exécutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	public int execSQL(){
		// Implicite : TransactionManager.getInstance().begin(); -> par le proxy
		
		// DO SOMETHING
		
		return TransactionManager.getInstance().getNbTransactions();
	}
}
