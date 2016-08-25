package com.target.service.junit.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.json.JSONObject;
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
import com.target.response.ExternalItem;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;
import com.target.services.ProductSercive;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ProductControllerServiceTest {

	@Autowired
	ProductSercive productService;

	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductUpdaterequest productupdaterequest;


	/**
	 * Success Case for Product Search
	 * 
	 * @throws Exception
	 */
	@Test
	public void retrieveProduct_whenIdIsFound() throws Exception {

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
		ProductDTO p = productService.sendProductRequest(id);
		Assert.assertEquals("Id product attribute retrieved correctly", id,
				p.getId());
		Assert.assertEquals("Name product attribute retrieved correctly", name,
				p.getName());
		Assert.assertEquals("Id product attribute retrieved correctly",
				"1500", p.getCurrent_price().get(0).getValue().toString());
		Assert.assertNotEquals(
				"Currency_code product attribute retrieved correctly", "INR", p
						.getCurrent_price().get(0).getCurrency_code());

	}

	/**
	 * Failure Case for Product Search
	 */
	@Test(expected = AppException.class)
	public void product_Failed_request() {
		Integer id = 111;
		productService.sendProductRequest(id);

	}

	/**
	 * Success Case for Multiple datasource
	 * 
	 * @throws Exception
	 */
	@Test
	public void ProductfromDifferent_whenIdIsFound() throws Exception {

		int id = 1001;
		String name = "swaroop";
		MultipleResponse p = productService.sendValuesforDifferentSource(id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", id,
				p.getRelationalProductId());
		Assert.assertNotEquals("Name product attribute retrieved correctly", name,
				p.getRelationalProductName());
		Assert.assertNull(
				"Price product attribute retrieved correctly from nosql",
				p.getNosqlValue());

	}


	/**
	 * Test Case for External URL
	 * 
	 * @throws Exception
	 */

	@Test
	public void ExternalSource_whenIdIsFound() throws Exception {

		int id = 13860428;
		String dpci = "058-34-0436";
		int departmentId = 58;
		int classId = 34;
		int itemId = 436;
		String itemDescription = "BIG LEBOWSKI, THE Blu-ray";

		ExternalItem p = productService.getExternalProduct(id);
		Assert.assertEquals("dpci product attribute retrieved correctly", dpci,
				p.getDpci());
		Assert.assertEquals(
				"departmentId product attribute retrieved correctly",
				departmentId, p.getDepartmentId());
		Assert.assertEquals("classId product attribute retrieved correctly",
				classId, p.getClassId());
		Assert.assertEquals("itemId product attribute retrieved correctly",
				itemId, p.getItemId());
		Assert.assertEquals(
				"itemDescription product attribute retrieved correctly",
				itemDescription, p.getItemDescription());

	}


	/**
	 * @throws Exception
	 */
	@Test
	public void Nosql_whenIdIsFound() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		productupdaterequest.setName(name);
		productupdaterequest.setCurrent_price(new CurrentPriceNoSql());
		productupdaterequest.getCurrent_price().setCurrency_code(code);
		productupdaterequest.getCurrent_price().setValue(val);
		ProductUpdateResponse p = productService.addNoSqlUpdateRequest(productupdaterequest, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Hi",
				p.getStatusMessage());
		

	}
	
	/**
	 * @throws Exception
	 */
	@Test(expected=NullPointerException.class)
	public void postgres_whenIdIsFound() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		ProductUpdaterequest productupdaterequest=new ProductUpdaterequest();
		productupdaterequest.setName(name);
		ProductUpdateResponse p = productService.sendUpdateRequest(null, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Hi",
				p.getStatusMessage());
		

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void Nosql_whenIdIsFoundCorrect() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		productupdaterequest.setName(name);
		productupdaterequest.setCurrent_price(new CurrentPriceNoSql());
		productupdaterequest.getCurrent_price().setCurrency_code(code);
		productupdaterequest.getCurrent_price().setValue(val);
		ProductUpdateResponse p = productService.addNoSqlUpdateRequest(productupdaterequest, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Updated Values are Name: Testcurrent_price= 182.0",
				p.getStatusMessage());
		

	}
	
	@Test
	public void Postgres_whenIdIsNotCorrect() throws Exception {

		int id = 1002;
		String name = "test";
		String code="INR";
		BigDecimal val=new BigDecimal("182.0");
		productupdaterequest.setName(name);
		productupdaterequest.setCurrent_price(new CurrentPriceNoSql());
		productupdaterequest.getCurrent_price().setCurrency_code(code);
		productupdaterequest.getCurrent_price().setValue(val);
		ProductUpdateResponse p = productService.addNoSqlUpdateRequest(productupdaterequest, id);
		Assert.assertNotEquals("Id product attribute retrieved correctly", "Updated",
				p.getStatusMessage());
		

	}
}
