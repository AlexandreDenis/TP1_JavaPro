package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.Transaction;

@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
public class EJBWithTransacRequired implements IEJBWithTransacRequired{
	@Override
	public int execSQL() {
		// TransactionManager.begin();
		return -1;
	}
	
	@Override
	public int useMethodWhichNeedsTransaction(){
		// TransactionManager.begin();
		return execSQL();
	}
	
	@Override
	public int callMethodOfEJBTransacRequiresNew(){
		// TransactionManager.begin()
		return EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.execSQL();
	}
}
