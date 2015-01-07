package com.isima.creationannotation.exceptions;

public class NoImplementationEJBException extends Exception {
	@Override
	public String getMessage() {
		return "NoImplementationEJBException : No implementation found for the EJB.";
	}
}
