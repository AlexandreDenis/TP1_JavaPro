package com.isima.creationannotation.exceptions;

/**
 * FullPoolEJBException
 * Exception lanc�e lorsque l'utilisateur essaie de cr�er un nouvel
 * EJB alors que la pool d'EJB de l'EJBContainer est pleine
 * @author alexandre.denis
 *
 */
public class EmptyPoolEJBException extends Exception {
	/**
	 * Renvoie un message sp�cifique � l'exception
	 */
	@Override
	public String getMessage() {
		return "EmptyPoolEJBException : The pool of EJB is empty.";
	}
}
