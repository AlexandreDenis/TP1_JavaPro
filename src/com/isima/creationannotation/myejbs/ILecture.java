package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.container.EntityManager;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Interface d'EJB simple
 * @author alexandre.denis
 *
 */
public interface ILecture {
	void readDB() throws EmptyPoolEJBException;
	void methodUsingAnotherEJB() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException;
	int getValPostConstruct();
	int getValPreDestroy();
	int getValue();
	void setValue(int newValue);
	EntityManager getEntityManager();
}
