package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.Pricing;

public class PricingService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	public static String[][] getPricing(Boolean isRoom) throws IOException {
		
		reader = new FileReader("data/pricing.json");
		String entity = "additionals";
		
		if(isRoom) {
			entity = "rooms";
		}
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject(entity);
		reader.close();
		
		int arrLength = 0;
		for(String type: jsonObject.keySet()) {
			arrLength+= jsonObject.get(type).getAsJsonArray().size();
		}
		if(arrLength==0) {
			return new String[0][4];
		}
		String[][] ret = new String[arrLength][];
		int itt = 0;
		for(String type: jsonObject.keySet()) {
			
			JsonArray arrayOfPrices = jsonObject.get(type).getAsJsonArray();
			
			if(arrayOfPrices.size()==0) {
				continue;
			}
			for(int i = 0; i< arrayOfPrices.size();i++) {
				String [] priceArr = new String[4];
				JsonObject priceObject = arrayOfPrices.get(i).getAsJsonObject();
				
				String addType = type;
				String price = priceObject.get("price").getAsString();
				String fromDate = priceObject.get("fromDate").getAsString();
				String toDate = priceObject.get("toDate").getAsString();
				
				priceArr[0] = addType;
				priceArr[1] = price;
				priceArr[2] = fromDate;
				priceArr[3] = toDate;
				
				ret[itt] = priceArr;
				itt++;
			}
		}
		return ret;
	}
	public static void addPricing(Pricing pricing, Boolean isRoom) throws IOException {
		reader = new FileReader("data/pricing.json");
		
		String entity = "additionals";
		
		if(isRoom) {
			entity = "rooms";
		}
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

		String service = pricing.getType().getService();
		JsonArray arr = jsonObject.getAsJsonObject(entity).getAsJsonArray(service);

		arr.add(pricing.getJson());
		
		jsonObject.getAsJsonObject(entity).add(service, arr);
		
		FileWriter writer = new FileWriter("data/pricing.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}

}
