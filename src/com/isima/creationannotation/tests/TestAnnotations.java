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

public class TestAnnotations {
	
	@EJB
	ILecture l;

	@Test
	public void testEJBContainerCreate() {
		assertNotNull(EJBContainer.create(ILecture.class));
	}

	@Test
	public void testEJBStateless() {
		ILecture lect = new Lecture();
		
		int i1 = lect.getInteger();
		
		lect.incInteger();
		int i2 = lect.getInteger();
		
		assertEquals(i1, i2);
	}

	@Test
	public void testManaged() {
		EJBContainer.manage(this);
		l.readDB();
	}

	@Test
	public void testNonManaged() {
		EJBContainer
			.create(ILecture.class)
			.readDB();
	}

	@Test
	public void isProxyReturned(){
		EJBContainer.manage(this);
		assertTrue(Proxy.isProxyClass(l.getClass()));
	}
	
	@Test(expected=NoImplementationEJBException.class)
	public void testInjectionEJBWithoutImplementation() {
		EJBContainer.create(IEJBWithoutImpl.class);
	}
	
	@Test(expected=AmbiguousEJBException.class)
	public void testInjectionEBJWithMultipleImplementation(){
		EJBContainer.create(IEJBWithMultImpl.class);
	}
	
	@Test
	public void testTransactionRequiredWithTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.useMethodWhichNeedsTransaction(), 1);
	}
	
	@Test
	public void testTransactionRequiredWithoutTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.execSQL(), 1);
	}
	
	@Test
	public void testTransactionRequiresNewWithTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.useMethodWhichNeedsTransaction(), 2);
	}
	
	@Test
	public void testTransactionRequiresNewWithoutTransaction(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.execSQL(), 1);
	}
	
	@Test
	public void testTransactionRequiredInRequiresNew(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequiresNew.class)
				.callMethodOfEJBTransacRequired(), 1);
	}
	
	@Test
	public void testTransactionRequiresNewInRequired(){
		assertEquals(EJBContainer.create(IEJBWithTransacRequired.class)
				.callMethodOfEJBTransacRequiresNew(), 2);
	}
}
