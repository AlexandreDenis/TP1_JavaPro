package com.isima.creationannotation.exceptions;

/**
 * NoImplementationEJBException
 * Exception lanc�e lorsqu'il n'existe pas d'impl�mentation possible
 * pour une interface d'EJB qu'on essaie d'injecter
 * @author alexandre.denis
 *
 */
public class NoImplementationEJBException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Renvoie un message sp�cifique � l'exception
	 */
	@Override
	public String getMessage() {
		return "NoImplementationEJBException : No implementation found for the EJB.";
	}
}
