package com.isima.creationannotation.container;

import java.util.List;

public interface EntityManager {
	void persist(Object entity);
	void persist(int filiere, Object entity);
	List<Object> get(int filiere);
}
