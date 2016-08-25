package com.target.dao;

import com.target.request.ProductUpdaterequest;

import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;
import com.target.response.ProductUpdateResponse;


/**
 * @author jyothiswaroopsingareddigari
 *
 */
public interface ProductDao {

	/**
	 * Retrieves values from DataBase based on id
	 * @param id
	 * @return ProductDTO
	 */
	public ProductDTO callDaoForProductSearch(int id);

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MultipleResponse callDaoForMultipleProductSearch(int id) throws Exception;

	/**
	 * @param product
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ProductUpdateResponse callProcedureForNoSqlUpdate(ProductUpdaterequest product,int id) throws Exception;
	/**
	 * @param product
	 * @param id
	 * @return ProductUpdateResponse
	 */
	public ProductUpdateResponse callProcedureForUpdate(ProductUpdaterequest product, int id);
}
