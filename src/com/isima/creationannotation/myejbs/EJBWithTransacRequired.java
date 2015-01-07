package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;

@TransactionAttribute(type=TransactionAttributeType.REQUIRED)
public class EJBWithTransacRequired implements IEJBWithTransacRequired{
	@Override
	public void execSQL() {
		
	}
}
