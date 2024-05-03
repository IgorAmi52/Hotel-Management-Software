package com.models;

import com.google.gson.JsonObject;

public class Pricing {
	
	private String fromDate;
	private String toDate;	
	private String type;
	private Double price;
	
	public Pricing(String type, Double price, String fromDate, String toDate) {
		this.type = type;
		this.price = price;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	public JsonObject getJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("fromDate", fromDate);
		jsonObject.addProperty("toDate", toDate);
		jsonObject.addProperty("price", price);
		
		return jsonObject;
	}
	public String getType() {
		return type;
	}
}
