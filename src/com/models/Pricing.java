package com.models;

import java.util.Objects;

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
	public String getFromDate() {
		return this.fromDate;
	}
	public String getToDate() {
		return this.toDate;
	}
	public Double getPrice() {
		return this.price;
	}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pricing pricing = (Pricing) o;
        return Objects.equals(fromDate, pricing.fromDate) &&
               Objects.equals(toDate, pricing.toDate) &&
               Objects.equals(type, pricing.type) &&
               Objects.equals(price, pricing.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate, type, price);
    }
}
