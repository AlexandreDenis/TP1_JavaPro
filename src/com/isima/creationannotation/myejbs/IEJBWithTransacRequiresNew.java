package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Interface d'EJB qui a un TransactionAttribute REQUIRES_NEW
 * @author alexandre.denis
 *
 */
public interface IEJBWithTransacRequiresNew {
	int execSQL();
	int useMethodWhichNeedsTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException;
	int callMethodOfEJBTransacRequired() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException;
}
