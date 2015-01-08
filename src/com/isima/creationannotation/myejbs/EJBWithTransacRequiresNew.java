package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.container.EJBContainer;

@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
public class EJBWithTransacRequiresNew implements IEJBWithTransacRequiresNew {
	@Override
	public int execSQL() {
		return -1;
	}
	
	@Override
	public int useMethodWhichNeedsTransaction(){
		return execSQL();
	}
	
	@Override
	public int callMethodOfEJBTransacRequired(){
		// TransactionManager.begin()
		return EJBContainer.create(IEJBWithTransacRequired.class)
				.execSQL();
	}
}
