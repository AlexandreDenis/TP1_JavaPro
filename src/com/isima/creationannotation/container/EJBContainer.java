package com.isima.creationannotation.container;

import java.lang.reflect.Constructor;

/**
 * Conteneur EJB
 * @author alexandre.denis
 *
 */
public class EJBContainer {
	/**
	 * Création d'un EJB à partir de l'interface passée en paramètre
	 * @param class_to_instantiate Interface de l'EJB à instancier
	 * @return Instance d'une implémentation de l'interface passée en paramètre
	 */
	static public <T> T create (Class<T> class_to_instantiate){		
		return null;
	}
	
	/**
	 * Injecte des EJBs aux champs de l'Object o annotés par @EJB
	 * @param o Object pour lequel on veut injecter des EJBs
	 */
	static public void manage(Object o) {
		
	}
}
