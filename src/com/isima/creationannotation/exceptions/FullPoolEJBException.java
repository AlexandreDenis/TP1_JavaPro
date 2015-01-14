package com.isima.creationannotation.exceptions;

/**
 * FullPoolEJBException
 * Exception lanc�e lorsque l'utilisateur essaie de cr�er un nouvel
 * EJB alors que la pool d'EJB de l'EJBContainer est pleine
 * @author alexandre.denis
 *
 */
public class FullPoolEJBException extends Exception {
	/**
	 * Renvoie un message sp�cifique � l'exception
	 */
	@Override
	public String getMessage() {
		return "FullPoolEJBException : The pool of EJB is full.";
	}
}
