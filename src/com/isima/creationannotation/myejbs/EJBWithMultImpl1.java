package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.container.EntityManager;
import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * 1ère classe implémentant l'interface IEJBWithMultImpl
 * @author alexandre.denis
 *
 */
@Stateless
public class EJBWithMultImpl1 implements IEJBWithMultImpl {
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * Méthode simple exécutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	public int execSQL(){
		em.persist(new Object());
		
		return TransactionManager.getInstance().getNbTransactions();
	}
}
