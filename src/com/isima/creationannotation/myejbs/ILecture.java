package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.container.EntityManager;

/**
 * Interface d'EJB simple
 * @author alexandre.denis
 *
 */
public interface ILecture {
	void readDB();
	void incInteger();
	int getInteger();
	EntityManager getEntityManager();
}
