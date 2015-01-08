package com.isima.creationannotation.myejbs;

/**
 * Interface d'EJB qui a un TransactionAttribute REQUIRES_NEW
 * @author alexandre.denis
 *
 */
public interface IEJBWithTransacRequiresNew {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequired();
}
