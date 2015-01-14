package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.exceptions.FullPoolEJBException;

/**
 * Interface d'EJB qui a un TransactionAttribute REQUIRED
 * @author alexandre.denis
 *
 */
public interface IEJBWithTransacRequired {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequiresNew() throws FullPoolEJBException;
}
