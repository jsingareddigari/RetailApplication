package com.target.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.target.exception.AppException;
import com.target.request.ProductUpdaterequest;
import com.target.response.CurrentPrice;
import com.target.response.ProductUpdateResponse;
import com.target.response.MultipleResponse;
import com.target.response.ProductDTO;

/**
 * @author jyothiswaroopsingareddigari
 *
 */
@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ProductUpdateResponse productUpdateResponse;
	@Autowired
	private ProductDTO product;

	private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.dao.ProductDao#callDaoForProductSearch(int)
	 */
	@SuppressWarnings("rawtypes")
	public ProductDTO callDaoForProductSearch(int id) {
		try {
			LOGGER.info("Entering call dao product");
			String sql = "select p.id,p.name, pd.value, pd.currency_code from testproduct p, TESTPRODUCTderails pd where p.id="
					+ id+"and pd.id="+id;
			LOGGER.info(sql);
			List<CurrentPrice> priceInfo = new ArrayList<>();
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			LOGGER.info("Rows length:" + rows.size());
			if (rows.isEmpty()) {
				LOGGER.info("Throwing empty exception");
				throw new AppException("No Product found on Id:" + id);
			}
			for (Map row : rows) {
				CurrentPrice priceDetails = new CurrentPrice();
				priceDetails.setCurrency_code(((String) row
						.get("CURRENCY_CODE")));
				priceDetails.setValue((BigDecimal) row.get("VALUE"));
				priceInfo.add(priceDetails);

				product.setCurrent_price(priceInfo);
				product.setName(((String) row.get("NAME")));
			}
			product.setId(id);
			LOGGER.info("Exiting call dao product");
			return product;
		} catch (Exception e) {
			throw new AppException("Invalid Product call.." + e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.dao.ProductDao#callDaoForMultipleProductSearch(int)
	 */

	public MultipleResponse callDaoForMultipleProductSearch(int id)
			throws Exception {
		LOGGER.info("Entering call for multiple product");
		try {
			MongoOperations mongoOps = new MongoTemplate(new MongoClient(),
					"test");
			DBCursor cursor =  mongoOps.getCollection("testcollupdate").find(new BasicDBObject().append("id", id));
			System.out.println(cursor);
			MultipleResponse response = new MultipleResponse();
			while (cursor.hasNext()) {
				// System.out.println(cursor.next()); //This is to run around
				// the collection
				DBObject obj = cursor.next();
				Object value = obj.get("current_price");
				LOGGER.info("Current Price:" + value);
				callDaoForProductSearch(id);// call from relational

				response.setRelationalProductId(id);
				response.setRelationalProductName(product.getName());// Get the
																		// name
																		// of
																		// product
				response.setNosqlValue(value);
				LOGGER.info("Exiting");

			}
			return response;
		} catch (IllegalStateException e) {
			throw new AppException(
					"Invalid Product call from Different datasources..");

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.target.dao.ProductDao#callProcedureForNoSqlUpdate(com.target.request
	 * .ProductNoSqlrequest, int)
	 */

	public ProductUpdateResponse callProcedureForNoSqlUpdate(
			ProductUpdaterequest product, int id) throws Exception {
		LOGGER.info("Entering callProcedureForNoSqlUpdate");
		try {
			MongoOperations mongoOps = new MongoTemplate(new MongoClient(),
					"test");
			DBCollection collection = mongoOps.getCollection("testcollupdate");
			String name = product.getName();
			product.getCurrent_price();
			String code = product.getCurrent_price().getCurrency_code();
			double val = product.getCurrent_price().getValue().doubleValue();
			DBObject listItem = new BasicDBObject("current_price",
					new BasicDBObject("currency_code", code).append("value",
							val));

			collection.update(new BasicDBObject("id", id), new BasicDBObject(
					"$set", new BasicDBObject("id", id).append("name", name)
							.append("current_price", listItem)));

			LOGGER.info("Exiting callProcedureForNoSqlUpdate");
			productUpdateResponse.setStatusMessage("Updated Values are Name: "
					+ name + "current_price= " + val);
			return productUpdateResponse;
		} catch (IllegalStateException e) {
			throw new AppException("Invalid Call to datasources.." + e);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.dao.ProductDao#callProcedureForUpdate(com.target.request.
	 * ProductUpdaterequest, int)
	 */
	@Override
	public ProductUpdateResponse callProcedureForUpdate(
			ProductUpdaterequest product, int id) {
		String code = product.getCurrent_price().getCurrency_code();
		String updatesql = "update testPRODUCTderails set value="
				+ product.getCurrent_price().getValue() + " where id=" + id
				+ " and currency_code=" + "'" + code + "'";
		int rowCount = jdbcTemplate.update(updatesql);
		if (rowCount > 0) {
			productUpdateResponse.setStatusMessage("Price Updated for :"
					+ product.getCurrent_price().getValue() + "  for Id:  "
					+ id + "  and currency Code:  " + "'" + code + "'");
			return productUpdateResponse;
		} else
			throw new AppException("No rows Updated.");

	}

}
