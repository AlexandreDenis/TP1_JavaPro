package com.isima.creationannotation.exceptions;

/**
 * AmbiguousEJBException
 * Exception lancée lorsqu'on tente d'injecter un EJB qui a plusieurs
 * implémentations possibles
 * @author alexandre.denis
 *
 */
public class AmbiguousEJBException extends Exception {

	/**
	 * Renvoie un message spécifique à l'exception
	 */
	@Override
	public String getMessage() {
		return "AmbiguousEJBException : More than one implementation found.";
	}
}
