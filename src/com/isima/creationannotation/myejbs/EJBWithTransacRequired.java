package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.EntityManager;
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
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * Méthode simple exécutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
	@Override
	public int execSQL() {
		em.persist(new Object());
		
		return TransactionManager.getInstance().getNbTransactions();
	}
	
	/**
	 * Méthode appelant une autre méthode de l'EJB
	 * @return le nombre de transactions ouvertes
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
	@Override
	public int useMethodWhichNeedsTransaction(){
		return execSQL();
	}
	
	/**
	 * Méthode faisant appel à une autre méthode d'un autre EJB
	 * dont le TransactionAttribute est REQUIRES_NEW
	 * @return le nombre de transactions ouvertes
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
	@Override
	public int callMethodOfEJBTransacRequiresNew() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		IEJBWithTransacRequiresNew ejb = EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class);
		int nbTransac = ejb.execSQL();
		
		return nbTransac;
	}
}
