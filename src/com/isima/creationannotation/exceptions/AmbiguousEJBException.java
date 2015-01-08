package com.isima.creationannotation.exceptions;

/**
 * AmbiguousEJBException
 * Exception lanc�e lorsqu'on tente d'injecter un EJB qui a plusieurs
 * impl�mentations possibles
 * @author alexandre.denis
 *
 */
public class AmbiguousEJBException extends Exception {

	/**
	 * Renvoie un message sp�cifique � l'exception
	 */
	@Override
	public String getMessage() {
		return "AmbiguousEJBException : More than one implementation found.";
	}
}
