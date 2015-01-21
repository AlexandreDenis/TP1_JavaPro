package com.isima.creationannotation.myejbs;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.PostConstruct;
import com.isima.creationannotation.annotations.PreDestroy;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.EntityManager;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Classe d'EJB simple
 * De type Stateless
 * @author alexandre.denis
 *
 */
@Stateless
public class Lecture implements ILecture{
	private int _valPostConstruct;
	private int _valPreDestroy;
	private int _value;
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * Constructeur par défaut
	 */
	public Lecture(){
		_valPostConstruct = -1;
		_valPreDestroy = 0;
	}
	
	/**
	 * Méthode lisant la base de données
	 */
	public void readDB() {
		
	}
	
	/**
	 * Méthode qui appelle un autre EJB ILecture
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws EmptyPoolEJBException 
	 */
	public void methodUsingAnotherEJB() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		EJBContainer.getInstance().create(ILecture.class).readDB();
	}
	
	/**
	 * Retourne la valeur de l'entier
	 * lié à l'annotation PostConstruct
	 */
	public int getValPostConstruct(){
		return _valPostConstruct;
	}
	
	/**
	 * Retourne la valeur de l'entier
	 * lié à l'annotation PreDestroy
	 */
	public int getValPreDestroy(){
		return _valPreDestroy;
	}
	
	/**
	 * Retourne l'EntityManager de la classe
	 */
	public EntityManager getEntityManager(){
		return em;
	}
	
	/**
	 * Getter de _value
	 * @return valeur de _value
	 */
	public int getValue(){
		return _value;
	}
	
	/**
	 * Setter de _value
	 */
	public void setValue(int newValue){
		_value = newValue;
	}
	
	@PostConstruct
	public void init(){
		_valPostConstruct = 0;
		_value = 0;	// on initialise _value à 0
	}
	
	@PreDestroy
	public void reset(){
		_valPreDestroy++;
	}
}
