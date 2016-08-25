package com.target.service.junit.test;


import org.junit.Test;
import org.meanbean.test.BeanTester;
	public class ProductRequest {
	  @Test()
	  public void testGettersAndSetters() {
	    final BeanTester tester = new BeanTester();
	    tester.testBean(ProductRequest.class);
	  }
	}
