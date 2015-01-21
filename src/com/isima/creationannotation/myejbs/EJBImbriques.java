package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.container.EntityManager;
import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * Implémentation d'un EJB en contenant un autre
 * @author Alexandre
 *
 */
@Stateless
public class EJBImbriques implements IEJBImbriques{
	@EJB
	private ILecture l;
	
	@PersistenceContext
	private EntityManager em;
	
	public ILecture getEJBImbrique(){
		return l;
	}
	
	@Override
	public int execSQL() {
		em.persist(new Object());
		return TransactionManager.getInstance().getNbTransactions();
	}
}
