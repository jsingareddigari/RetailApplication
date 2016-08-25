package com.target.service.junit.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.target.exception.AppException;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;
import com.target.services.ProductSercive;
import com.target.services.ProductServiceImpl;

/**
 * @author jyothiswaroopsingareddigari
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AppTest extends TestCase {
	
	@Autowired
	ProductSercive productService;
	private static final Logger LOGGER = Logger.getLogger(AppTest.class);
	private static String url="http://localhost:8082/Retail/ProductTest";
	/**
	 * Testcase for product search
	 */
	@Test
	public void test_GET_ProductSearch() {
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/Currency/1001");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			int id = 1001;
			String name = "swaroop";
			String type = null;
			double val = 0;
			ProductDTO p=productService.sendProductRequest(id);
			String s = null;
			Assert.assertEquals("Id product attribute retrieved correctly", id,
					p.getId());
			Assert.assertEquals("Name product attribute retrieved correctly", name,
					p.getName());
			Assert.assertEquals("Id product attribute retrieved correctly",
					"1500", p.getCurrent_price().get(0).getValue().toString());
			Assert.assertNotEquals(
					"Currency_code product attribute retrieved correctly", "INR", p
							.getCurrent_price().get(0).getCurrency_code());
			httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Failure case for Product search.
	 * 
	 */
	@Test
	public void test_GET_FailId_ProductSearch() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/Currency/100");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			String s = null;
			while ((output = br.readLine()) != null) {
				s = output;
			}
			assertEquals(
					true,
					s.contains("No product found for Id:100HTTP Status not found::404"));

			httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Improper URL
	 */
	@Test
	public void test_GET_InvalidUrl() {
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/Currency/123/100");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(404, response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Multiple DataSource
	 */
	@Test
	public void test_GET_MultipleDataSourceSearch() throws Exception{
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/SearchFromMultiple?id=1001");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			int id = 10002;
			String name = "TV";
			MultipleResponse p = productService.sendValuesforDifferentSource(id);
			Assert.assertEquals("Id product attribute retrieved correctly", id,
					p.getRelationalProductId());
			Assert.assertEquals("Name product attribute retrieved correctly", name,
					p.getRelationalProductName());
			Assert.assertNotNull(
					"Price product attribute retrieved correctly from nosql",
					p.getNosqlValue());


			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Failure Case for multiple data source
	 */
	@Test
	public void test_GET_FailId_MultipleDataSourceSearch() {
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/SearchFromMultiple?id=100");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");

			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			String s = null;
			while ((output = br.readLine()) != null) {
				s = output;
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * External DataSource Success
	 */
	@Test
	public void test_GET_ExternalSource() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/ExternalProduct?id=13860428");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			String s = null;
			while ((output = br.readLine()) != null) {
				s = output;
			}
			assertEquals(true, s.contains("34"));
			assertEquals(true, s.contains("436"));
			assertEquals(true, s.contains("BIG LEBOWSKI, THE Blu-ray"));

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * External dataSource Failure
	 */
	@Test
	public void test_GET_FailId_ExternalSource() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(
					url+"/ExternalProduct?id=1386042");
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("accept", "application/xml");
			HttpResponse response = httpClient.execute(getRequest);
			assertEquals(200, response.getStatusLine().getStatusCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			String s = null;
			while ((output = br.readLine()) != null) {
				s = output;
			}
			assertEquals(
					true,
					s.contains("No product found for Id:1386042HTTP Status not found::404"));

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * PUT method Test Case
	 */
	
	@Test
	public void test_PUT_service() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPut putRequest = new HttpPut(
					url+"/1002");

			StringEntity input = new StringEntity("{ \n" + "\n"
					+ "\"current_price\" : { \n" + "\n" + "\"value\" : 184, \n"
					+ "\n" + "\"currency_code\" : \"INR\" \n" + "\n" + "}, \n"
					+ "\n" + "\"name\" : \"Test\" \n" + "\n" + "}");
			ProductUpdateResponse queryRequest = new ProductUpdateResponse();
			// queryRequest.
			input.setContentType("application/json");
			putRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(putRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new AppException("HTTP Status not found::404");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				LOGGER.info(output+queryRequest);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	@Test
	public void test_PUT_Postgres_service() {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPut putRequest = new HttpPut(
					url+"/Update/1004");

			StringEntity input = new StringEntity("{ \n" + "\n"
					+ "\"current_price\" : { \n" + "\n" + "\"value\" : 184, \n"
					+ "\n" + "\"currency_code\" : \"INR\" \n" + "\n" + "}, \n"
					+ "\n" + "\"name\" : \"Test\" \n" + "\n" + "}");
			ProductUpdateResponse queryRequest = new ProductUpdateResponse();
			// queryRequest.
			input.setContentType("application/json");
			putRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(putRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new AppException("HTTP Status not found::404");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				LOGGER.info(output+queryRequest);
			}
			httpClient.getConnectionManager().shutdown();
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
