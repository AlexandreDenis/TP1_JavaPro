package com.isima.creationannotation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.FullPoolEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;
import com.isima.creationannotation.myejbs.IEJBWithMultImpl;
import com.isima.creationannotation.myejbs.IEJBWithTransacRequired;
import com.isima.creationannotation.myejbs.IEJBWithTransacRequiresNew;
import com.isima.creationannotation.myejbs.IEJBWithoutImpl;
import com.isima.creationannotation.myejbs.ILecture;
import com.isima.creationannotation.myejbs.Lecture;

/**
 * Classe de test du TP
 * @author alexandre.denis
 *
 */
public class TestAnnotations {
	
	@EJB
	ILecture l;
	
	/**
	 * Test que l'aspect sans état de l'EJB Stateless est bien respecté
	 */
	@Test
	public void testEJBStateless() {
		ILecture lect = new Lecture();
		
		int i1 = lect.getInteger();
		
		lect.incInteger();
		int i2 = lect.getInteger();
		
		assertEquals(i1, i2);
	}

	/**
	 * Vérifie que la méthode manage() de l'EJBContainer injecte bien des instances 
	 * aux EJBs de la classe de test
	 */
	@Test
	public void testManaged() {
		EJBContainer.getInstance().manage(this);
		l.readDB();
	}

	/**
	 * Vérifie que la création d'un EJB renvoie bien une instance et donc 
	 * une valeur non nulle
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testNonManaged() throws FullPoolEJBException {
		EJBContainer
			.getInstance()
			.create(ILecture.class)
			.readDB();
	}

	/**
	 * Vérifie que l'injection d'une classe injecte en fait un proxy
	 */
	@Test
	public void isProxyReturned(){
		EJBContainer.getInstance().manage(this);
		assertTrue(Proxy.isProxyClass(l.getClass()));
	}
	
	/**
	 * Test l'injection d'un EJB qui n'a pas d'implémentation
	 * @throws FullPoolEJBException 
	 */
	@Test(expected=NoImplementationEJBException.class)
	public void testInjectionEJBWithoutImplementation() throws FullPoolEJBException {
		EJBContainer.getInstance().create(IEJBWithoutImpl.class);
	}
	
	/**
	 * Test l'injection d'un EJB qui possède plusieurs implémentations
	 * possibles
	 * @throws FullPoolEJBException 
	 */
	@Test(expected=AmbiguousEJBException.class)
	public void testInjectionEBJWithMultipleImplementation() throws FullPoolEJBException{
		EJBContainer.getInstance().create(IEJBWithMultImpl.class);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Transaction à la base
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiredWithTransaction() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.useMethodWhichNeedsTransaction(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Pas de transaction à la base
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiredWithoutTransaction() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Transaction à la base
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiresNewWithTransaction() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.useMethodWhichNeedsTransaction(), 2);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Pas de transaction à la base
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiresNewWithoutTransaction() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED dans un EJB dont le 
	 * TransactionAttribute est REQUIRES_NEW
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiredInRequiresNew() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequiresNew.class)
				.callMethodOfEJBTransacRequired(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW dans un EJB dont le
	 * TransactionAttribute est REQUIRED
	 * @throws FullPoolEJBException 
	 */
	@Test
	public void testTransactionRequiresNewInRequired() throws FullPoolEJBException{
		assertEquals(EJBContainer.getInstance().create(IEJBWithTransacRequired.class)
				.callMethodOfEJBTransacRequiresNew(), 2);
	}
	
	/**
	 * Test que l'EntityManager est bien injecté suite à une annotation 
	 * @throws FullPoolEJBException 
	 * @PersistenceContext
	 */
	@Test
	public void testEntityManagerNotNull() throws FullPoolEJBException{
		assertNotNull(EJBContainer.getInstance().create(ILecture.class).getEntityManager());
	}
	
	/**
	 * Test que les traitements sont bien effectués après la construction d'un EJB quand il
	 * y a l'annotation @PostConstruct
	 */
	@Test
	public void testPostConstruct(){
		
	}
	
	/**
	 * Test que les traitements sont bien effectués avant la destruction d'un EJB quand il y
	 * a l'annotation @PreDestroy
	 */
	@Test
	public void testPreDestroy(){
		
	}
	
	/**
	 * Test que l'exception FullPoolEJBException est bien lancée
	 * lorsque l'on essaie de créer plus d'EJB que la pool 
	 * n'en contient
	 * @throws FullPoolEJBException 
	 */
	@Test(expected = FullPoolEJBException.class)
	public void testFullPoolEJBException() throws FullPoolEJBException{
		
	}
}
