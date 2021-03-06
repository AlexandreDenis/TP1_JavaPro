package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.container.EntityManager;
import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * 2�me classe impl�mentant l'interface IEJBWithMultImpl
 * @author alexandre.denis
 *
 */
@Stateless
public class EJBWithMultImpl2 implements IEJBWithMultImpl {
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * M�thode simple ex�cutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	public int execSQL(){
		em.persist(new Object());
		
		return TransactionManager.getInstance().getNbTransactions();
	}
}
