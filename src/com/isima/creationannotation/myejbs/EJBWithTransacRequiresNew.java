package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * TransactionAttribute : REQUIRES_NEW
 * @author alexandre.denis
 *
 */
@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
public class EJBWithTransacRequiresNew implements IEJBWithTransacRequiresNew {
	/**
	 * Méthode simple exécutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int execSQL() {
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		// DO SOMETHING
		
		return TransactionManager.getInstance().getNbTransactions();
	}
	
	/**
	 * Méthode appelant à une autre méthode de l'EJB
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int useMethodWhichNeedsTransaction(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return execSQL();
	}
	
	/**
	 * Méthode faisant appel à une autre méthode d'un autre EJB
	 * dont le TransactionAttribute est REQUIRED
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int callMethodOfEJBTransacRequired(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return EJBContainer.create(IEJBWithTransacRequired.class)
				.execSQL();
	}
}
