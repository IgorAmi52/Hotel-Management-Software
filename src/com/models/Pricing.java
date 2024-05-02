package com.models;

import com.google.gson.JsonObject;
import com.models.enums.ServiceType;

public class Pricing {
	
	private String fromDate;
	private String toDate;	
	private ServiceType type;
	private Double price;
	
	public Pricing(ServiceType type, Double price, String fromDate, String toDate) {
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
	public ServiceType getType() {
		return type;
	}
}
