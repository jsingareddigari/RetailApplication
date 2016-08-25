package com.target.services;

import java.util.Arrays;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.target.dao.ProductDao;
import com.target.exception.AppException;
import com.target.request.ProductUpdaterequest;
import com.target.response.ExternalItem;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;

/**
 * @author jyothiswaroopsingareddigari
 *
 */

@Service
public class ProductServiceImpl implements ProductSercive {
	@Autowired
	private ProductDao productDao;

	private static final Logger LOGGER = Logger
			.getLogger(ProductServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.services.ProductSercive#sendProductRequest(int)
	 */
	public ProductDTO sendProductRequest(int id) {
		LOGGER.info("Entering in send product request service");
		ProductDTO response = productDao.callDaoForProductSearch(id);
		LOGGER.info("Exiting in send product request service");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.services.ProductSercive#sendValuesforDifferentSource(int)
	 */
	public MultipleResponse sendValuesforDifferentSource(int id)
			throws Exception {
		LOGGER.info("Entering Multi database service");
		MultipleResponse response = productDao
				.callDaoForMultipleProductSearch(id);
		LOGGER.info("Exiting Multi database service");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.target.services.ProductSercive#addNoSqlUpdateRequest(com.target.request
	 * .ProductNoSqlrequest, int)
	 */
	public ProductUpdateResponse addNoSqlUpdateRequest(
			ProductUpdaterequest productDetails, int id) throws Exception {
		LOGGER.info("Entering Update service");
		ProductUpdateResponse response = productDao
				.callProcedureForNoSqlUpdate(productDetails, id);
		LOGGER.info("Exiting Update service");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.services.ProductSercive#getExternalProduct(int)
	 */
	@SuppressWarnings("unused")
	public ExternalItem getExternalProduct(int id) {
		LOGGER.info("Entering external product system");
		String url = "https://api.target.com/products/v3/" + id;
		url += "?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";

		LOGGER.info("api.target.com URL :" + url);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);
		ExternalItem item = new ExternalItem();

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);
		JSONObject responseobj = new JSONObject(response.getBody().toString());
		JSONObject rootobj = responseobj
				.getJSONObject("product_composite_response");
		JSONArray itemsData = rootobj.getJSONArray("items");
		JSONObject itemDetails = itemsData.getJSONObject(0);

		item.setItemDescription(itemDetails.getString("general_description"));
		item.setDpci(itemDetails.getString("dpci"));
		item.setDepartmentId(itemDetails.getInt("department_id"));
		item.setClassId(itemDetails.getInt("class_id"));
		item.setItemId(itemDetails.getInt("item_id"));
		LOGGER.info(item);
		LOGGER.info("Exitting external product system");
		if (response == null) {
			throw new AppException("The product with id " + id
					+ " is not found");
		} else {
			return item;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.target.services.ProductSercive#sendUpdateRequest(com.target.request.ProductUpdaterequest, int)
	 */
	public ProductUpdateResponse sendUpdateRequest(ProductUpdaterequest product, int id) {
		
		LOGGER.info("Entering Update service");
		ProductUpdateResponse response = productDao
				.callProcedureForUpdate(product, id);
		LOGGER.info("Exiting Update service");
		return response;
	}
}
