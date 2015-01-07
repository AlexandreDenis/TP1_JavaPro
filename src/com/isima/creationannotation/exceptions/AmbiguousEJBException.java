package com.isima.creationannotation.exceptions;

public class AmbiguousEJBException extends Exception {
	@Override
	public String getMessage() {
		return "AmbiguousEJBException : More than one implementation found.";
	}
}
