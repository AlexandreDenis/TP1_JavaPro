package com.isima.creationannotation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
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
		EJBContainer.manage(this);
		l.readDB();
	}

	/**
	 * Vérifie que la création d'un EJB renvoie bien une instance et donc 
	 * une valeur non nulle
	 */
	@Test
	public void testNonManaged() {
		EJBContainer
			.create(ILecture.class)
			.readDB();
	}

	/**
	 * Vérifie que l'injection d'une classe injecte en fait un proxy
	 */
	@Test
	public void isProxyReturned(){
		EJBContainer.manage(this);
		assertTrue(Proxy.isProxyClass(l.getClass()));
	}
	
	/**
	 * Test l'injection d'un EJB qui n'a pas d'implémentation
	 */
	@Test(expected=NoImplementationEJBException.class)
	public void testInjectionEJBWithoutImplementation() {
		EJBContainer.create(IEJBWithoutImpl.class);
	}
	
	/**
	 * Test l'injection d'un EJB qui possède plusieurs implémentations
	 * possibles
	 */
	@Test(expected=AmbiguousEJBException.class)
	public void testInjectionEBJWithMultipleImplementation(){
		EJBContainer.create(IEJBWithMultImpl.class);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Transaction à la base
	 */
	@Test
	public void testTransactionRequiredWithTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.useMethodWhichNeedsTransaction(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED
	 * Pas de transaction à la base
	 */
	@Test
	public void testTransactionRequiredWithoutTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Transaction à la base
	 */
	@Test
	public void testTransactionRequiresNewWithTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.useMethodWhichNeedsTransaction(), 2);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW
	 * Pas de transaction à la base
	 */
	@Test
	public void testTransactionRequiresNewWithoutTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.execSQL(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRED dans un EJB dont le 
	 * TransactionAttribute est REQUIRES_NEW
	 */
	@Test
	public void testTransactionRequiredInRequiresNew(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.callMethodOfEJBTransacRequired(), 1);
	}
	
	/**
	 * TransactionAttribute REQUIRES_NEW dans un EJB dont le
	 * TransactionAttribute est REQUIRED
	 */
	@Test
	public void testTransactionRequiresNewInRequired(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.callMethodOfEJBTransacRequiresNew(), 2);
	}
}
