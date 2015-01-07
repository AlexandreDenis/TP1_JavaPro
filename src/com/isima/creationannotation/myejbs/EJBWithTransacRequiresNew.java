package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;

@TransactionAttribute(type=TransactionAttributeType.REQUIRES_NEW)
public class EJBWithTransacRequiresNew implements IEJBWithTransacRequiresNew {
	@Override
	public void execSQL() {
		
	}
}
