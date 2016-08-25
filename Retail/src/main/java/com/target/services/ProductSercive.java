package com.target.services;
import com.target.request.ProductUpdaterequest;

import com.target.response.ExternalItem;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;

/**
 * @author jyothiswaroopsingareddigari
 *
 */
public interface ProductSercive {

	/**
	 * Retrieves the info based on id 
	 * @param id
	 * @return ProductDTO
	 */
	public ProductDTO sendProductRequest(int id);
	/**
	 * Multiple Database connectivity
	 * @param id
	 * @return MultipleResponse
	 * @throws Exception
	 */
	public MultipleResponse sendValuesforDifferentSource(int id) throws Exception;
	/**
	 * Update the product in Nosql
	 * @param product
	 * @param id
	 * @return ProductUpdateResponse
	 * @throws Exception 
	 */
	public ProductUpdateResponse addNoSqlUpdateRequest(ProductUpdaterequest product,int id) throws Exception;
	/**
	 * Connect to external Item
	 * @param id
	 * @return ExternalItem
	 */
	public ExternalItem getExternalProduct(int id);
	/**
	 * @param product
	 * @param id
	 * @return ProductUpdateResponse
	 */
	public ProductUpdateResponse sendUpdateRequest(ProductUpdaterequest product,int id);

}
