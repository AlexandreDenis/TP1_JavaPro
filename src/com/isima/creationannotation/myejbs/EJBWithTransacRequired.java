package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.TransactionManager;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Classe d'EJB
 * TransactionAttribute : REQUIRED
 * @author alexandre.denis
 *
 */
@Stateless
public class EJBWithTransacRequired implements IEJBWithTransacRequired{
	/**
	 * M�thode simple ex�cutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
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
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
	@Override
	public int useMethodWhichNeedsTransaction(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return execSQL();
	}
	
	/**
	 * M�thode faisant appel � une autre m�thode d'un autre EJB
	 * dont le TransactionAttribute est REQUIRES_NEW
	 * @return le nombre de transactions ouvertes
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
	@Override
	public int callMethodOfEJBTransacRequiresNew() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		// Implicite : TransactionManager.begin(); -> par le proxy
		IEJBWithTransacRequiresNew ejb = EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class);
		int nbTransac = ejb.execSQL();
		
		return nbTransac;
	}
}
