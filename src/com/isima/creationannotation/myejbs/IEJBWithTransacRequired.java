package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Interface d'EJB qui a un TransactionAttribute REQUIRED
 * @author alexandre.denis
 *
 */
public interface IEJBWithTransacRequired {
	int execSQL();
	int useMethodWhichNeedsTransaction();
	int callMethodOfEJBTransacRequiresNew() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException;
}
