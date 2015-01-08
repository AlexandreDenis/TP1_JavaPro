package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.container.Transaction;

public interface IEJBWithTransacRequired {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequiresNew();
}
