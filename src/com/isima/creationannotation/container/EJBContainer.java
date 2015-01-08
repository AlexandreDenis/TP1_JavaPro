package com.isima.creationannotation.container;

import java.lang.reflect.Constructor;

/**
 * Conteneur EJB
 * @author alexandre.denis
 *
 */
public class EJBContainer {
	/**
	 * Cr�ation d'un EJB � partir de l'interface pass�e en param�tre
	 * @param class_to_instantiate Interface de l'EJB � instancier
	 * @return Instance d'une impl�mentation de l'interface pass�e en param�tre
	 */
	static public <T> T create (Class<T> class_to_instantiate){		
		return null;
	}
	
	/**
	 * Injecte des EJBs aux champs de l'Object o annot�s par @EJB
	 * @param o Object pour lequel on veut injecter des EJBs
	 */
	static public void manage(Object o) {
		
	}
}
