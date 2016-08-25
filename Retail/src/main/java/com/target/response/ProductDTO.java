package com.target.response;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
	private int id;
    List<CurrentPrice> current_price=new ArrayList<CurrentPrice>();
    private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<CurrentPrice> getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(List<CurrentPrice> current_price) {
		this.current_price = current_price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
