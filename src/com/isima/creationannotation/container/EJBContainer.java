package com.isima.creationannotation.container;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.ClassPath;
import com.isima.creationannotation.exceptions.FullPoolEJBException;

/**
 * Conteneur EJB
 * @author alexandre.denis
 *
 */
public class EJBContainer {
	
	// singleton
	private static EJBContainer INSTANCE = null;
	
	// pool d'EJB
	private static HashMap<Class<?>, List<Class<?>>> _implementations = new HashMap<Class<?>, List<Class<?>>>();
	private static HashMap<Class<?>, List<Object>> _pools = new HashMap<Class<?>, List<Object>>(); 
	
	// nombre d'EJB de base dans le pool d'une interface donnée
	private static int sizePool = 10;
	
	/**
	 * Création et initialisation d'un nouvel EJBContainer
	 * @return un nouvel EJBContainer
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static EJBContainer createEJBContainer() throws InstantiationException, IllegalAccessException{
		// création de l'EJBContainer
		INSTANCE = new EJBContainer();
		
		// initialisation de l'EJBContainer
		try {
			initializePools();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Initialise les pools en fonction de ce qui se trouve 
	 * dans le classloader
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static void initializePools() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		List<Class> classes = getClassesEJB();			// récupération des classes d'implémentations d'EJB
		List<Class> interfaces = getInterfacesEJB();	// récupération des interfaces d'EJB
		
		// on vide les maps
		_implementations.clear();
		_pools.clear();
		
		/*System.out.println("*****Classes*****");
		for(Class aclass : classes){
			System.out.println(aclass.getName());
		}
		
		System.out.println("*****Interfaces*****");
		for(Class aclass : interfaces){
			System.out.println(aclass.getName());
		}*/
		
		// on fait le mapping entre interface d'EJB et implémentations
		for(Class anInterface : interfaces){
			for(Class aClass : classes){
				if(Arrays.asList(aClass.getInterfaces()).contains(anInterface)){
					if(!_implementations.containsKey(anInterface)){
						_implementations.put(anInterface, new ArrayList<Class<?>>());
					}
					_implementations.get(anInterface).add(aClass);
				}
			}
			
			// si plusieurs implémentations, on supprime l'interface de la map des
			// pools à initialiser
			List<Class<?>> values = _implementations.get(anInterface);
			if(values != null){
				if(values.size() != 1){
					_implementations.remove(anInterface);
				}
			}
		}
		
		// on initialise les pools avec sizePool instances d'EJB
		Set keys = _implementations.keySet();
		Iterator<Class> it = keys.iterator();
		while(it.hasNext()){
			Class key = it.next();
			Class impl = _implementations.get(key).get(0);

			// création d'une pool
			_pools.put(key, new ArrayList<Object>());
			
			for(int i = 0; i < sizePool; ++i){
				_pools.get(key).add(Class.forName(impl.getName()).newInstance());
			}
		}
		
		/*Set keys2 = _pools.keySet();
		Iterator<Class> it2 = keys2.iterator();
		while(it2.hasNext()){
			Class key2 = it2.next();
			
			System.out.println(key2.getName() + " = " +_pools.get(key2).size());
		}*/
		
		/*Set keys = _implementations.keySet();
		Iterator<Class> it = keys.iterator();
		while(it.hasNext()){
			Class key = it.next();
			ArrayList<?> values = new ArrayList(_implementations.get(key));
			String strValues = "";
			for(Object aClass : values){
				Class theClass = (Class)aClass;
				strValues += theClass.getName() + " || ";
			}
			
			System.out.println(key.getName() + " = " + strValues);
		}*/
	}
	
	/**
	 * Récupère la liste des classes d'EJB présentes
	 * dans le package appropriée
	 * @return liste des classes d'EJB présentes dans le package appropriée
	 * @throws ClassNotFoundException
	 */
	private static List<Class> getClassesEJB() throws ClassNotFoundException {
		ClassPath classpath = null;
		ArrayList<Class> result = new ArrayList<Class>();
		
		try {
			classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
		} catch (IOException e) {
			System.out.println("Error when trying to retrieve the classloader");
			e.printStackTrace();
		} 
		
		// scans the class path used by classloader
		for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("com.isima.creationannotation.myejbs")) {
			if(!Modifier.isInterface(Class.forName(classInfo.getName()).getModifiers())){
				result.add(Class.forName(classInfo.getName()));
			}
		}
		
		return result;
	}
	
	/**
	 * Récupère la liste des interfaces d'EJB présentes
	 * dans le package appropriée
	 * @return liste des interfaces d'EJB présentes dans le package appropriée
	 * @throws ClassNotFoundException
	 */
	private static List<Class> getInterfacesEJB() throws ClassNotFoundException {
		ClassPath classpath = null;
		ArrayList<Class> result = new ArrayList<Class>();
		
		try {
			classpath = ClassPath.from(ClassLoader.getSystemClassLoader());
		} catch (IOException e) {
			System.out.println("Error when trying to retrieve the classloader");
			e.printStackTrace();
		} 
		
		// scans the class path used by classloader
		for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("com.isima.creationannotation.myejbs")) {
			if(Modifier.isInterface(Class.forName(classInfo.getName()).getModifiers())){
				result.add(Class.forName(classInfo.getName()));
			}
		}
		
		return result;
	}
	
	/**
	 * Retourne l'instance de l'EJBContainer
	 * Le créé si l'instance n'a pas été créée
	 * @return
	 */
	public static EJBContainer getInstance(){
		if(INSTANCE == null){
			try {
				return createEJBContainer();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return INSTANCE;
	}
	
	/**
	 * Création d'un EJB à partir de l'interface passée en paramètre
	 * @param class_to_instantiate Interface de l'EJB à instancier
	 * @return Instance d'une implémentation de l'interface passée en paramètre
	 * @throws FullPoolEJBException 
	 */
	public <T> T create (Class<T> class_to_instantiate) throws FullPoolEJBException{	
		List<Object> pool = _pools.get(class_to_instantiate);
		T result = null;
		
		if(pool == null){
			// on essaie d'instancier une implémentation de l'EJB
			
		} else {
			if(pool.size() > 0){
				result = (T)pool.remove(0);
			} else {
				throw new FullPoolEJBException();
			}
		}
		
		return result;
	}
	
	/**
	 * Remet l'instance d'EJB dans la pool
	 * @param instance_of_ejb_to_release instance de l'EJB à remettre dans la pool
	 */
	public <T> void release(Class<T> class_of_pool, T instance_of_ejb_to_release){
		if(instance_of_ejb_to_release != null){
			_pools.get(class_of_pool).add(instance_of_ejb_to_release);
		}
	}
	
	/**
	 * Injecte des EJBs aux champs de l'Object o annotés par @EJB
	 * @param o Object pour lequel on veut injecter des EJBs
	 */
	public void manage(Object o) {
		
	}
}
