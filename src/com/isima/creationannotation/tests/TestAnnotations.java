package com.isima.creationannotation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.isima.creationannotation.annotations.EJB;
import com.isima.creationannotation.container.EJBContainer;
import com.isima.creationannotation.exceptions.AmbiguousEJBException;
import com.isima.creationannotation.exceptions.NoImplementationEJBException;
import com.isima.creationannotation.myejbs.IEJBWithoutImpl;
import com.isima.creationannotation.myejbs.IEJBWithMultImpl;
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
	public void injectionEJBWithoutImplementation() {
		EJBContainer.create(IEJBWithoutImpl.class);
	}
	
	@Test(expected=AmbiguousEJBException.class)
	public void injectionEBJWithMultipleImplementation(){
		EJBContainer.create(IEJBWithMultImpl.class);
	}
	
	@Test
	public void testTransactionRequired(){
		
	}
	
	@Test
	public void testTransactionRequiresNew(){
		
	}
	
	@Test
	public void testTransactionRequiredInRequiresNew(){
		
	}
	
	@Test
	public void testTransactionRequiresNewInRequired(){
		
	}
}
