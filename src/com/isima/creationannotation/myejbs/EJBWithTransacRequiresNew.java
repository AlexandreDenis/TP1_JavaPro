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
 * TransactionAttribute : REQUIRES_NEW
 * @author alexandre.denis
 *
 */
@Stateless
public class EJBWithTransacRequiresNew implements IEJBWithTransacRequiresNew {
	/**
	 * Méthode simple exécutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
	@Override
	public int execSQL(){
		return TransactionManager.getInstance().getNbTransactions();
	}
	
	/**
	 * Méthode appelant à une autre méthode de l'EJB
	 * @return le nombre de transactions ouvertes
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws EmptyPoolEJBException 
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
	@Override
	public int useMethodWhichNeedsTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		return EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class).execSQL();
	}
	
	/**
	 * Méthode faisant appel à une autre méthode d'un autre EJB
	 * dont le TransactionAttribute est REQUIRED
	 * @return le nombre de transactions ouvertes
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
	@Override
	public int callMethodOfEJBTransacRequired() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return EJBContainer.getInstance()
				.create(IEJBWithTransacRequired.class)
				.execSQL();
	}
}
