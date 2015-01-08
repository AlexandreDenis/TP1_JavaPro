package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.TransactionManager;

@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
public class EJBWithTransacRequiresNew implements IEJBWithTransacRequiresNew {
	@Override
	public int execSQL() {
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		// DO SOMETHING
		
		return TransactionManager.getInstance().getNbTransactions();
	}
	
	@Override
	public int useMethodWhichNeedsTransaction(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return execSQL();
	}
	
	@Override
	public int callMethodOfEJBTransacRequired(){
		// Implicite : TransactionManager.begin(); -> par le proxy
		
		return EJBContainer.create(IEJBWithTransacRequired.class)
				.execSQL();
	}
}
