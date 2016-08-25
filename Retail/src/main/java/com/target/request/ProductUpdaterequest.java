package com.target.request;

public class ProductUpdaterequest {

    private CurrentPriceNoSql current_price;
    private String name;
	
	public CurrentPriceNoSql getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(CurrentPriceNoSql current_price) {
		this.current_price = current_price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
