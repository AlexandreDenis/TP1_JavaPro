package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.Stateless;

/**
 * Interface d'EJB
 * EJB contenant un autre EJB
 * @author alexandre.denis
 *
 */
public interface IEJBImbriques {
	int execSQL();
	ILecture getEJBImbrique();
}
