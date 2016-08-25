package com.target.service.junit.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.target.dao.ProductDao;
import com.target.exception.AppException;
import com.target.request.CurrentPriceNoSql;
import com.target.request.ProductUpdaterequest;
import com.target.response.CurrentPrice;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;

/**
 * @author jyothiswaroopsingareddigari
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ProductDaoTest {

	@Autowired
	ProductDao productDao;

	/**
	 * Search Product Dao
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveProduct_Dao() throws Exception {
		int id = 1001;
		String name = "swaroop";
		String type = null;
		double val = 0;
		List<CurrentPrice> current_price = new ArrayList<CurrentPrice>();
		for (int i = 0; i < current_price.size(); i++) {
			CurrentPrice c = new CurrentPrice();
			type = c.getCurrency_code();
			val = c.getValue().doubleValue();
		}
		ProductDTO p = productDao.callDaoForProductSearch(id);
		Assert.assertEquals("Id product attribute retrieved correctly", id,
				p.getId());
		Assert.assertEquals("Name product attribute retrieved correctly", name,
				p.getName());
		Assert.assertEquals("Id product attribute retrieved correctly",
				"1500", p.getCurrent_price().get(0).getValue().toString());
		Assert.assertEquals(
				"Currency_code product attribute retrieved correctly", "USD", p
						.getCurrent_price().get(0).getCurrency_code());

	}

	/**
	 * Search Failure in Dao
	 */
	@Test(expected = AppException.class)
	public void product_Failed_Dao() {
		Integer id = 111;
		productDao.callDaoForProductSearch(id);

	}

	/**
	 * Different Data Source Search Dao
	 * 
	 * @throws Exception
	 */
	@Test
	public void ProductfromDifferent_Dao() throws Exception {

		int id = 1001;
		String name = "swaroop";
		MultipleResponse p = productDao.callDaoForMultipleProductSearch(id);
	
		Assert.assertNotEquals("Name product attribute retrieved correctly", name,
				p.getRelationalProductName());

	}



	@Test(expected=NullPointerException.class)
	public void Nosql_whenIdIsFound() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		ProductUpdateResponse p = productDao.callProcedureForNoSqlUpdate(null, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Hi",
				p.getStatusMessage());
		

	}
	
	@Test(expected=NullPointerException.class)
	public void postgres_whenIdIsFound() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		ProductUpdateResponse p = productDao.callProcedureForUpdate(null, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Hi",
				p.getStatusMessage());
		

	}
	
	@Test
	public void Nosql_whenIdIsnotFound() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		ProductUpdaterequest productupdaterequest=new ProductUpdaterequest();
		productupdaterequest.setName(name);
		productupdaterequest.setCurrent_price(new CurrentPriceNoSql());
		productupdaterequest.getCurrent_price().setCurrency_code(code);
		productupdaterequest.getCurrent_price().setValue(val);
		ProductUpdateResponse p = productDao.callProcedureForNoSqlUpdate(productupdaterequest, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Hi",
				p.getStatusMessage());
		

	}
	
	@Test
	public void Postgres_whenIdIsnotFound() throws Exception {

		int id = 1004;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		ProductUpdaterequest productupdaterequest=new ProductUpdaterequest();
		productupdaterequest.setName(name);
		productupdaterequest.setCurrent_price(new CurrentPriceNoSql());
		productupdaterequest.getCurrent_price().setCurrency_code(code);
		productupdaterequest.getCurrent_price().setValue(val);
		ProductUpdateResponse p = productDao.callProcedureForUpdate(productupdaterequest, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Updated",
				p.getStatusMessage());
		

	}
	
	
}
