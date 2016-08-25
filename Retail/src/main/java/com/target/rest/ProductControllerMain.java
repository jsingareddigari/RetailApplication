package com.target.rest;

import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.target.exception.AppException;
import com.target.request.ProductUpdaterequest;
import com.target.response.ExternalItem;
import com.target.response.ProductUpdateResponse;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.services.ProductSercive;

/**
 * @author jyothiswaroopsingareddigari
 *
 */
@RestController
@RequestMapping("/ProductTest")
public class ProductControllerMain {

	/**
	 * 
	 */
	@Autowired
	private ProductSercive productService;

	private static final Logger LOGGER = Logger
			.getLogger(ProductControllerMain.class);

	/**
	 * Controller for Simple Http GET Product Price URL:Currency/{id}
	 * 
	 * @param id
	 * @return ProductDTO
	 */
	@RequestMapping(value = "Currency/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO ProductSearch(@PathVariable("id") int id) {
		LOGGER.info("Get the Products Input for ID:" + id);
		try {
			LOGGER.info("Inside Product Search Controller");
			ProductDTO response=productService.sendProductRequest(id);
			return response;
		} catch (Exception e) {
			throw new AppException("No product found for Id:" + id
					+ "HTTP Status not found::" + HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Connect to Relational and NoSQL(MongoDB) and get product Details.
	 * 
	 * @param id
	 * @return MultipleResponse
	 */
	@RequestMapping(value = "SearchFromMultiple", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public MultipleResponse getMultipleDataSource(
			@RequestParam(value = "id") int id) throws Exception {

		LOGGER.info("Multiple database product Input for ID :" + id);
		try {
			return productService.sendValuesforDifferentSource(id);
		} catch (Exception e) {
			
			throw new AppException("No product found for Id:" + id
					+ "HTTP Status not found::" + HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * PUT to NoSQL(MongoDB) and update Product Details.
	 * 
	 * @param id
	 *            , updateNoSqlDTO
	 * @return ProductUpdateResponse
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProductUpdateResponse UpdateNoSql(@PathVariable("id") int id,
			@RequestBody ProductUpdaterequest product) throws Exception {

		LOGGER.info("Update Nosql database for ID:" + id);

		try {
			return productService.addNoSqlUpdateRequest(product, id);
		} catch (Exception e) {
			throw new AppException("No product found for Id:" + id
					+ "HTTP Status not found::" + HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Get ExternalProduct Details.
	 * 
	 * @param id
	 * @return ExternalProduct
	 */
	@RequestMapping(value = "ExternalProduct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ExternalItem getExternalProduct(@RequestParam(value = "id") int id)
			throws Exception {

		LOGGER.info("Get details from external source for Id:" + id);
		try {
			return productService.getExternalProduct(id);
		} catch (Exception e) {
			throw new AppException("No product found for Id:" + id
					+ "HTTP Status not found::" + HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * @param id
	 * @param product
	 * @return ProductUpdateResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "Update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProductUpdateResponse sendupdateRequest(@PathVariable("id") int id,
			@RequestBody ProductUpdaterequest product) throws Exception {

		LOGGER.info("Update database for ID:" + id);

		try {
			return productService.sendUpdateRequest(product, id);
		} catch (Exception e) {
			throw new AppException("No product found for Id:" + id
					+ "HTTP Status not found::" + HttpStatus.NOT_FOUND);
		}
	}
}