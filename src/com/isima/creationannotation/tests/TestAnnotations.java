package com.isima.creationannotation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.container.InstanceManager;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.EmptyPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;
import com.isima.creationannotation.myejbs.IEJBImbriques;
import com.isima.creationannotation.myejbs.IEJBWithMultImpl;
import com.isima.creationannotation.myejbs.IEJBWithTransacRequired;
import com.isima.creationannotation.myejbs.IEJBWithTransacRequiresNew;
import com.isima.creationannotation.myejbs.IEJBWithoutImpl;
import com.isima.creationannotation.myejbs.ILecture;

/**
 * Classe de test du TP
 * @author alexandre.denis
 *
 */
public class TestAnnotations {
	
	@EJB
	private ILecture l = null;
	
	/**
	 * Teste que l'aspect sans état de l'EJB Stateless est bien respecté
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testEJBStateless() throws IllegalArgumentException, IllegalAccessException, NoImplementationEJBException, AmbiguousEJBException {
		InstanceManager.resetPools(1);
		
		EJBContainer.getInstance().manage(this);
		
		l.setValue(2);
		
		assertEquals(l.getValue(), 0);
		
		InstanceManager.resetPools(10);
	}

	/**
	 * Vérifie que la méthode manage() de l'EJBContainer injecte bien des instances 
	 * aux EJBs de la classe de test
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws EmptyPoolEJBException 
	 */
	@Test
	public void testManaged() throws NoImplementationEJBException, AmbiguousEJBException, IllegalArgumentException, IllegalAccessException, EmptyPoolEJBException {
		EJBContainer.getInstance().manage(this);
		l.readDB();
	}

	/**
	 * Vérifie que la création d'un EJB renvoie bien une instance et donc 
	 * une valeur non nulle
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testNonManaged() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException {
		EJBContainer
			.getInstance()
			.create(ILecture.class)
			.readDB();
	}

	/**
	 * Vérifie que l'injection d'une classe injecte en fait un proxy
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void isProxyReturned() throws NoImplementationEJBException, AmbiguousEJBException, IllegalArgumentException, IllegalAccessException{
		EJBContainer.getInstance().manage(this);
		assertTrue(Proxy.isProxyClass(l.getClass()));
	}
	
	/**
	 * Teste l'injection d'un EJB qui n'a pas d'implémentation
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test(expected=NoImplementationEJBException.class)
	public void testInjectionEJBWithoutImplementation() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException {
		EJBContainer.getInstance().create(IEJBWithoutImpl.class);
	}
	
	/**
	 * Teste l'injection d'un EJB qui possède plusieurs implémentations
	 * possibles
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test(expected=AmbiguousEJBException.class)
	public void testInjectionEBJWithMultipleImplementation() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		EJBContainer.getInstance().create(IEJBWithMultImpl.class);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Transaction à la base
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiredWithTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.useMethodWhichNeedsTransaction(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Pas de transaction à la base
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiredWithoutTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Transaction à la base
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiresNewWithTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.useMethodWhichNeedsTransaction(), 2);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Pas de transaction à la base
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiresNewWithoutTransaction() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED dans un EJB dont le 
	 * TransactionAttribute est REQUIRES_NEW
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiredInRequiresNew() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.callMethodOfEJBTransacRequired(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW dans un EJB dont le
	 * TransactionAttribute est REQUIRED
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 */
	@Test
	public void testTransactionRequiresNewInRequired() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.callMethodOfEJBTransacRequiresNew(), 2);
	}
	
	/**
	 * Teste que l'EntityManager est bien injecté suite à une annotation 
	 * @throws EmptyPoolEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws AmbiguousEJBException 
	 * @PersistenceContext
	 */
	@Test
	public void testEntityManagerNotNull() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertNotNull(EJBContainer.getInstance().create(ILecture.class).getEntityManager());
	}
	
	/**
	 * Teste que les traitements sont bien effectués après la construction d'un EJB quand il
	 * y a l'annotation @PostConstruct
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws EmptyPoolEJBException 
	 */
	@Test
	public void testPostConstruct() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		assertEquals(EJBContainer.getInstance().create(ILecture.class).getValPostConstruct(), 0);
	}
	
	/**
	 * Teste que les traitements sont bien effectués avant la destruction d'un EJB quand il y
	 * a l'annotation @PreDestroy
	 * @throws AmbiguousEJBException 
	 * @throws NoImplementationEJBException 
	 * @throws EmptyPoolEJBException 
	 */
	@Test
	public void testPreDestroy() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException{
		InstanceManager.resetPools(1);
		
		EJBContainer.getInstance().create(ILecture.class).readDB();
				
		assertEquals(EJBContainer.getInstance().create(ILecture.class).getValPreDestroy(), 1);
		
		InstanceManager.resetPools(10);
	}
	
	/**
	 * Teste que l'exception FullPoolEJBException est bien lancée
	 * lorsque l'on essaie de créer plus d'EJB que la pool 
	 * n'en contient
	 * @throws Exception 
	 */
	@Test(expected = EmptyPoolEJBException.class)
	public void testEmptyPoolEJBException() throws Exception{
		InstanceManager.resetPools(1);
		
		try{
			EJBContainer.getInstance().create(ILecture.class).methodUsingAnotherEJB();
		} catch(Exception e){
			InstanceManager.resetPools(10);
			throw e;
		}
	}
	
	/**
	 * Teste que les EJBs imbriqués dans des EJBs sont bien injectés 
	 * en même temps que leur EJB parent
	 * @throws EmptyPoolEJBException
	 * @throws NoImplementationEJBException
	 * @throws AmbiguousEJBException
	 */
	@Test
	public void testEJBImbriques() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException {
		assertNotNull(EJBContainer.getInstance().create(IEJBImbriques.class).getEJBImbrique());
	}
	
	/**
	 * Teste que les EJBs imbriqués dans des EJBs
	 * sont bien des proxys
	 * @throws EmptyPoolEJBException
	 * @throws NoImplementationEJBException
	 * @throws AmbiguousEJBException
	 */
	@Test
	public void testEJBImbriquesIsProxy() throws EmptyPoolEJBException, NoImplementationEJBException, AmbiguousEJBException {
		assertTrue(Proxy.isProxyClass(EJBContainer.getInstance().create(IEJBImbriques.class).getEJBImbrique().getClass()));
	}
}
