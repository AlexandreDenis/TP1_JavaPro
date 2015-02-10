package com.isima.creationannotation.container;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.adapters.MetadataAdapter;
import org.reflections.scanners.Scanner;
import org.reflections.serializers.Serializer;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.annotations.Stateless;
import com.isima.creationannotation.annotations.TransactionAttribute;
import com.isima.creationannotation.annotations.TransactionAttributeType;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;

/**
 * Conteneur EJB
 * @author alexandre.denis
 *
 */
public class EJBContainer {
	
	// singleton
	private static EJBContainer INSTANCE = null;
	
	// classe qui gère la réflexion
	private static Reflections reflection = null;
	
	// mapping entre interface d'EJB et implémentations
	private static HashMap<Class<?>, List<Class<?>>> _implementations = new HashMap<Class<?>, List<Class<?>>>();
	
	// résultat de la réflection
	private static List<Class> classes;
	private static List<Class> interfaces;
	
	/**
	 * Création et initialisation d'un nouvel EJBContainer
	 * @return un nouvel EJBContainer
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static EJBContainer createEJBContainer() throws InstantiationException, IllegalAccessException{
		return createEJBContainer(null);
	}
	
	public static EJBContainer createEJBContainer(final ClassLoader cl) throws InstantiationException, IllegalAccessException{
		// création de l'EJBContainer
		INSTANCE = new EJBContainer();

		if(cl != null){
//			List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
//			classLoadersList.add(cl);
			//classLoadersList.add(INSTANCE.getClass().getClassLoader());
			//System.out.println(cl.toString());
			
			reflection = new Reflections();
//			reflection = new Reflections(new ConfigurationBuilder()
//				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
//			reflection = new Reflections(new ConfigurationBuilder()
//				.addClassLoader(EJBContainer.class.getClassLoader())
//				.addClassLoader(cl));
		}
		
		// initialisation de l'EJBContainer
		try {
			initializePools();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Retourne la classe gérant la réflection
	 * @return classe gérant la réflection
	 */
	private static Reflections getReflections(){
		if(reflection == null){
			reflection = new Reflections();
		}
		
		return reflection;
	}
	
	/**
	 * Initialise les pools en fonction de ce qui se trouve 
	 * dans le classloader
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static void initializePools() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		classes = getClassesEJB();			// récupération des classes d'implémentations d'EJB
		interfaces = getInterfacesEJB();	// récupération des interfaces d'EJB
		
		// on vide la map
		_implementations.clear();
		
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
			/*List<Class<?>> values = _implementations.get(anInterface);
			if(values != null){
				if(values.size() != 1){
					_implementations.remove(anInterface);
				}
			}*/
		}
		
		// on initialise les pools avec sizePool instances d'EJB
		/*Set keys = _implementations.keySet();
		Iterator<Class> it = keys.iterator();
		while(it.hasNext()){
			Class key = it.next();
			Class impl = _implementations.get(key).get(0);

			if(_implementations.get(key).size() == 1){
				// création d'une pool
				_pools.put(key, new ArrayList<Object>());
				
				for(int i = 0; i < sizePool; ++i){
					_pools.get(key).add(Class.forName(impl.getName()).newInstance());
				}
			}
		}*/
		
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
			
			System.out.println(key.getName() + " = " + values.size());
		}*/
	}
	
	/**
	 * Récupère la liste des classes d'EJB présentes
	 * dans le package appropriée
	 * @return liste des classes d'EJB présentes dans le package appropriée
	 * @throws ClassNotFoundException
	 */
	private static List<Class> getClassesEJB() throws ClassNotFoundException {
		ArrayList<Class> result = new ArrayList<Class>();

		Reflections refl = getReflections();
		Set<Class<?>> set_classes = refl.getTypesAnnotatedWith(Stateless.class);
		for (Class clazz : set_classes) {
			if(!Modifier.isInterface(Class.forName(clazz.getName()).getModifiers())){
				//System.out.println(clazz.getName());
				result.add(Class.forName(clazz.getName()));
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
		ArrayList<Class> result = new ArrayList<Class>();
		
		if(classes == null){
			classes = getClassesEJB();
		}
		
		for(Class clazz : classes){
			Class[] set_interfaces = clazz.getInterfaces();
			for(Class inter : set_interfaces){
				if(!result.contains(inter)){
					result.add(inter);
					//System.out.println(inter.getName());
				}
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
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	public <T> T create (Class<T> class_to_instantiate) throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
	
		// pas d'implémentation pour l'interface d'EJB
		if(_implementations.get(class_to_instantiate) == null){
			throw new NoImplementationEJBException();
		}
		
		// plusieurs implémentations pour l'interface d'EJB
		if(_implementations.get(class_to_instantiate).size() > 1){
			throw new AmbiguousEJBException();
		}
		
		// cas où l'interface d'EJB a une seule implémentation
		final Class class_implementation = _implementations.get(class_to_instantiate).get(0);
		@SuppressWarnings("unchecked")
		T proxy = (T)Proxy.newProxyInstance(
						class_implementation.getClassLoader(),
						new Class[] {class_to_instantiate},
						new InvocationHandler() {
							
							@Override
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								Object res = null;

								// on récupère la méthode de la classe
								// pour obtenir le TransactionAttribute
								Method[] implMethods = class_implementation.getMethods();
								Method implMethod = null;
								for(Method met : implMethods){
									if(met.getName() == method.getName()){
										implMethod = met;
									}
								}
								
								// récupération d'un EJB du pool
								T ejb = (T)InstanceManager.getEJBInstance(class_implementation);
								
								// gestion des EJB imbriqués
								EJBContainer.getInstance().manage(ejb);
								
								// récupération d'une transaction
								if(implMethod.isAnnotationPresent(TransactionAttribute.class)){
									if(implMethod.getAnnotation(TransactionAttribute.class).type() == TransactionAttributeType.REQUIRED){
										TransactionManager.getInstance().begin();
									} else if(implMethod.getAnnotation(TransactionAttribute.class).type() == TransactionAttributeType.REQUIRES_NEW){
										TransactionManager.getInstance().beginNewTransaction();
									}
								}
								
								try{
									res = method.invoke(ejb, args);
								} catch(InvocationTargetException e){
									throw e.getCause();
								}
								
								// on ferme la transaction
								TransactionManager.getInstance().end();
								
								// on remet l'instance d'EJB dans le pool
								InstanceManager.release(class_implementation, ejb);
								
								return res;
							}
						});
		
		return proxy;
	}
	
	/**
	 * Injecte des EJBs aux champs de l'Object o annotés par @EJB
	 * @param o Object pour lequel on veut injecter des EJBs
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void manage(Object o) throws NoImplementationEJBException, AmbiguousEJBException, IllegalArgumentException, IllegalAccessException {
		// récupère la liste des champs de la classe de l'objet o
		// annoté par l'annotation EJB
		Set<Field> fields = getAllFields(o.getClass(), withAnnotation(EJB.class));

		// pour chacun des champs ainsi récupéré
		for(Field field : fields){
			//System.out.println(field.getType().getName());
			// pas d'implémentation pour l'interface d'EJB
			if(_implementations.get(field.getType()) == null){
				throw new NoImplementationEJBException();
			}
			
			// plusieurs implémentations pour l'interface d'EJB
			if(_implementations.get(field.getType()).size() > 1){
				throw new AmbiguousEJBException();
			}
			
			// cas où l'interface d'EJB a une seule implémentation
			final Class class_implementation = _implementations.get(field.getType()).get(0);
			
			boolean oldAccessibility = field.isAccessible();
			field.setAccessible(true);
			
			field.set(o,Proxy.newProxyInstance(
							class_implementation.getClassLoader(),
							new Class[] {field.getType()},
							new InvocationHandler() {
								
								@Override
								public Object invoke(Object proxy, Method method, Object[] args)
										throws Throwable {
									Object res = null;
									
									// on récupère la méthode de la classe
									// pour obtenir le TransactionAttribute
									Method[] implMethods = class_implementation.getMethods();
									Method implMethod = null;
									for(Method met : implMethods){
										if(met.getName() == method.getName()){
											implMethod = met;
										}
									}
									
									// récupération d'un EJB du pool
									Object ejb = InstanceManager.getEJBInstance(class_implementation);
									
									// gestion des EJB imbriqués
									EJBContainer.getInstance().manage(ejb);
									
									// récupération d'une transaction
									if(implMethod.isAnnotationPresent(TransactionAttribute.class)){
										if(implMethod.getAnnotation(TransactionAttribute.class).type() == TransactionAttributeType.REQUIRED){
											TransactionManager.getInstance().begin();
										} else if(implMethod.getAnnotation(TransactionAttribute.class).type() == TransactionAttributeType.REQUIRES_NEW){
											TransactionManager.getInstance().beginNewTransaction();
										}
									}
									try{
										res = method.invoke(ejb, args);
									} catch(InvocationTargetException e){
										throw e.getCause();
									}
									
									// on ferme la transaction
									TransactionManager.getInstance().end();
									
									// on remet l'instance d'EJB dans le pool
									InstanceManager.release(class_implementation, ejb);
									
									return res;
								}
							}));
			field.setAccessible(oldAccessibility);
		}
	}
}
