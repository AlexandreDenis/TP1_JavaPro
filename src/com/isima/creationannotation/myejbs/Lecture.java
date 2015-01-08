package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.Stateless;

/**
 * Classe d'EJB simple
 * De type Stateless
 * @author alexandre.denis
 *
 */
@Stateless
public class Lecture implements ILecture{
	private int _integer;
	
	@PersistenceContext
	//EntityManager em;
	
	/**
	 * Constructeur par défaut
	 */
	public Lecture(){
		_integer = 0;
	}
	
	/**
	 * Méthode lisant la base de données
	 */
	public void readDB() {
		
	}
	
	/**
	 * Incrémentation de l'entier de l'instance
	 */
	public void incInteger(){
		++_integer;
	}
	
	/**
	 * Retourne l'entier de l'instance
	 */
	public int getInteger(){
		return _integer;
	}
}
