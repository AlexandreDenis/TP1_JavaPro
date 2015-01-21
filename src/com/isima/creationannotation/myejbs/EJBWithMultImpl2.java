package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.container.TransactionManager;

/**
 * Classe d'EJB
 * 2�me classe impl�mentant l'interface IEJBWithMultImpl
 * @author alexandre.denis
 *
 */
@Stateless
public class EJBWithMultImpl2 implements IEJBWithMultImpl {
	
	/**
	 * M�thode simple ex�cutant une action de persistance
	 * @return le nombre de transactions ouvertes
	 */
	public int execSQL(){
		// Implicite : TransactionManager.getInstance().begin(); -> par le proxy
		
		// DO SOMETHING
		
		return TransactionManager.getInstance().getNbTransactions();
	}
}
