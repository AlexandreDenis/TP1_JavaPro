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
	 * M�thode simple ex�cutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int execSQL() {
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		// DO SOMETHING
		
		return TransactionManager.getInstance().getNbTransactions();
	}
	
	/**
	 * M�thode appelant une autre m�thode de l'EJB
	 * @return le nombre de transactions ouvertes
	 */
	@Override
	public int useMethodWhichNeedsTransaction(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return execSQL();
	}
	
	/**
	 * M�thode faisant appel � une autre m�thode d'un autre EJB
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
