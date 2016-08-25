package com.target.response;

public class MultipleResponse {
	private int relationalProductId;
	private String relationalProductName;
	private Object nosqlValue;
	public int getRelationalProductId() {
		return relationalProductId;
	}
	public void setRelationalProductId(int relationalProductId) {
		this.relationalProductId = relationalProductId;
	}
	public String getRelationalProductName() {
		return relationalProductName;
	}
	public void setRelationalProductName(String relationalProductName) {
		this.relationalProductName = relationalProductName;
	}
	public Object getNosqlValue() {
		return nosqlValue;
	}
	public void setNosqlValue(Object nosqlValue) {
		this.nosqlValue = nosqlValue;
	}
	
}
