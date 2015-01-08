package com.isima.creationannotation.myejbs;

public interface IEJBWithTransacRequiresNew {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequired();
}
