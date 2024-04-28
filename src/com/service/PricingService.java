package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PricingService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;
	
	public static String[][] getRoomPricing() throws IOException {
		
		reader = new FileReader(Holder.getProjectPath()+"/src/com/database/pricing.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject("rooms");
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
			String [] priceArr = new String[4];
			
			for(int i = 0; i< arrayOfPrices.size();i++) {
				JsonObject priceObject = arrayOfPrices.get(i).getAsJsonObject();
				
				String roomType = priceObject.get("type").getAsString();
				String price = priceObject.get("price").getAsString();
				String fromDate = priceObject.get("from").getAsString();
				String toDate = priceObject.get("to").getAsString();
				
				priceArr[0] = roomType;
				priceArr[1] = price;
				priceArr[2] = fromDate;
				priceArr[3] = toDate;
			}
			ret[itt] = priceArr;
			itt++;

		}
		return ret;
	}
}
