package com.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.Pricing;
import com.models.enums.RoomType;

public class PricingService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	public static String[][] getPricing(Boolean isRoom) throws IOException {
		
		reader = new FileReader("data/pricing.json");
	
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		int arrLength = 0;
		
		for(String type: jsonObject.keySet()) {
			if(isRoom) {
				if(Arrays.asList(RoomService.getRoomTypesArr()).contains(type)) {
					arrLength+= jsonObject.getAsJsonArray(type).size();
				}
			}
			else {
				if(!Arrays.asList(RoomService.getRoomTypesArr()).contains(type)) {
					arrLength+= jsonObject.getAsJsonArray(type).size();}				
				}		
		}
		
		if(arrLength==0) { //if no items still
			return new String[0][4];
		}
		String[][] ret = new String[arrLength][];
		int itt = 0;
		for(String type: jsonObject.keySet()) {
			if(isRoom) {
				if(Arrays.asList(RoomService.getRoomTypesArr()).contains(type)) {
					JsonArray roomsArr = jsonObject.getAsJsonArray(type); //Collecting Rooms and object is of type room, or collecting addServices and object not of type room
					for(int i = 0; i < roomsArr.size(); i++) {
						
						String [] priceArr = new String[4];
						JsonObject priceObject = roomsArr.get(i).getAsJsonObject();
			
						String price = priceObject.get("price").getAsString();
						String fromDate = priceObject.get("fromDate").getAsString();
						String toDate = priceObject.get("toDate").getAsString();
						priceArr[0] = type;
						priceArr[1] = price;
						priceArr[2] = fromDate;
						priceArr[3] = toDate;
						
						ret[itt] = priceArr;
						itt++;
					}
				}

			}
			else {
				if(!Arrays.asList(RoomService.getRoomTypesArr()).contains(type)) {
					JsonArray roomsArr = jsonObject.getAsJsonArray(type); //Collecting Rooms and object is of type room, or collecting addServices and object not of type room
					for(int i = 0; i < roomsArr.size(); i++) {
						
						String [] priceArr = new String[4];
						JsonObject priceObject = roomsArr.get(i).getAsJsonObject();
			
						String price = priceObject.get("price").getAsString();
						String fromDate = priceObject.get("fromDate").getAsString();
						String toDate = priceObject.get("toDate").getAsString();
						priceArr[0] = type;
						priceArr[1] = price;
						priceArr[2] = fromDate;
						priceArr[3] = toDate;
						
						ret[itt] = priceArr;
						itt++;
					}
				}
			}		
		}
		return ret;
	}
	public static void addPricing(Pricing pricing) throws IOException {

		reader = new FileReader("data/pricing.json");

		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

		JsonArray arr = jsonObject.getAsJsonArray(pricing.getType());
		
		if(arr==null) {
			arr = new JsonArray();
			jsonObject.add(pricing.getType(), arr);
		}
		
		arr.add(pricing.getJson());
		
		jsonObject.add(pricing.getType(), arr);

		FileWriter writer = new FileWriter("data/pricing.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}

}
