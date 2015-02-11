package com.isima.creationannotation.container;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.reflections.ReflectionUtils;

import com.isima.creationannotation.annotations.PersistenceContext;
import com.isima.creationannotation.annotations.PostConstruct;
import com.isima.creationannotation.annotations.PreDestroy;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;

public class InstanceManager {
	// singleton
	private static InstanceManager INSTANCE = new InstanceManager();
	
	// pools d'EJB
	private static HashMap<Class<?>, List<Object>> _pools = new HashMap<Class<?>, List<Object>>();
	
	// nombre d'EJB de base dans le pool d'une interface donnée
	private static int sizePool = 10;
	
	/**
	 * Constructeur privé
	 */
	private InstanceManager(){
		
	}
	
	/**
	 * Obtention de l'instance unique de TransactionManager
	 * @return singleton du TransactionManager
	 */
	public static InstanceManager getInstance(){
		return INSTANCE;
	}
	
	public static <T> T getEJBInstance(Class<T> class_to_instantiate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, EmptyPoolEJBException, IllegalArgumentException, InvocationTargetException {
		T instance_to_return;
		
		if(_pools.get(class_to_instantiate) == null){
			// on créé et initialise le pool
			// création d'une pool
			_pools.put(class_to_instantiate, new ArrayList<Object>());

			//System.out.println(class_to_instantiate.getName());
			
			for(int i = 0; i < sizePool; ++i){
				_pools.get(class_to_instantiate).add(Class.forName(class_to_instantiate.getName()).newInstance());
			}
		}
		
		//System.out.println("taille pool : " + _pools.get(class_to_instantiate).size());
		
		if(_pools.get(class_to_instantiate).size() > 0){
			instance_to_return = (T)_pools.get(class_to_instantiate).remove(0);
			
			// application des méthodes ayant l'annotation PostConstruct
			Set<Method> methods = ReflectionUtils.getAllMethods(class_to_instantiate, ReflectionUtils.withAnnotation(PostConstruct.class));
			for(Method method : methods){
				method.invoke(instance_to_return, null);
			}
			
			// on initialise tous les EntityManager lorsqu'il y a l'annotation PersistenceContext
			Set<Field> persistenceContexts = ReflectionUtils.getAllFields(class_to_instantiate, ReflectionUtils.withAnnotation(PersistenceContext.class));
			for(Field pc : persistenceContexts){
				boolean oldAccessibility = pc.isAccessible();
				
				pc.setAccessible(true);
				pc.set(instance_to_return, EntityManagerImpl.getInstance());
				pc.setAccessible(oldAccessibility);
			}
		} else {
			throw new EmptyPoolEJBException();
		}
		
		return instance_to_return;
	}
	
	/**
	 * Fonction qui réinitialise les pools
	 */
	public static void resetPools(int newSizePool){
		_pools.clear();
		
		sizePool = newSizePool;
	}
	
	/**
	 * Remet l'instance d'EJB dans la pool
	 * @param instance_of_ejb_to_release instance de l'EJB à remettre dans la pool
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> void release(Class<T> class_of_pool, T instance_of_ejb_to_release) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(instance_of_ejb_to_release != null){
			// on exécute toutes les méthodes annotées PreDestroy de l'instance courante
			Set<Method> methods = ReflectionUtils.getAllMethods(class_of_pool, ReflectionUtils.withAnnotation(PreDestroy.class));
			for(Method method : methods){
				method.invoke(instance_of_ejb_to_release, null);
			}
			
			// on remet l'instance d'EJB dans le pool
			_pools.get(class_of_pool).add(instance_of_ejb_to_release);
		}
	}
}
