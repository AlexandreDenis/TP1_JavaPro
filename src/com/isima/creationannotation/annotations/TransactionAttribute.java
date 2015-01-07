package com.isima.creationannotation.annotations;

public @interface TransactionAttribute {
	public TransactionAttributeType type() default TransactionAttributeType.REQUIRED;
}
