package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.exceptions.FullPoolEJBException;

/**
 * Interface d'EJB qui a un TransactionAttribute REQUIRES_NEW
 * @author alexandre.denis
 *
 */
public interface IEJBWithTransacRequiresNew {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequired() throws FullPoolEJBException;
}
