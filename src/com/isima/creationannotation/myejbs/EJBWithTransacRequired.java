package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.Transaction;
import com.isima.creationannotation.container.TransactionManager;
import com.isima.creationannotation.exceptions.FullPoolEJBException;

/**
 * Classe d'EJB
 * TransactionAttribute : REQUIRED
 * @author alexandre.denis
 *
 */
@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
public class EJBWithTransacRequired implements IEJBWithTransacRequired{
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
	 * Méthode appelant une autre méthode de l'EJB
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int useMethodWhichNeedsTransaction(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return execSQL();
	}
	
	/**
	 * Méthode faisant appel à une autre méthode d'un autre EJB
	 * dont le TransactionAttribute est REQUIRES_NEW
	 * @return le nombre de transactions ouvertes
	 * @throws FullPoolEJBException 
	 */
	@Override
	public int callMethodOfEJBTransacRequiresNew() throws FullPoolEJBException{
		// Implicite : TransactionManager.begin(); -> par le proxy
		IEJBWithTransacRequiresNew ejb = EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class);
		int nbTransac = ejb.execSQL();
		
		EJBContainer.getInstance().release(IEJBWithTransacRequiresNew.class,
				ejb);
		
		return nbTransac;
	}
}
