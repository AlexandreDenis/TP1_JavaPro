package com.isima.creationannotation.myejbs;

public interface IEJBWithTransacRequired {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequiresNew();
}
