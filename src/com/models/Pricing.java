package com.models;

import com.google.gson.Gson;
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
		
		Gson gson = new Gson();
        String jsonString = gson.toJson(Pricing.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
		
		return jsonObject;
	}
	public String getType() {
		return type;
	}
}
