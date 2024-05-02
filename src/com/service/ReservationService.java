package com.service;

import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.Person;
import com.models.Reservation;

public class ReservationService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	
	public static void requestReservation(Reservation reservation) throws IOException {
		// a lot of checking and validation
		reader = new FileReader("data/reservations.json");
		
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();

 		jsonObject.get(reservation.getStatus()).getAsJsonArray().add(reservation.getJson());
		writer = new FileWriter("data/reservations.json");
		writer.write(new Gson().toJson(jsonObject));
		writer.close();
		
	}
	public static String[][] getReservationsGuest(Person user) throws IOException{
		
		reader = new FileReader("data/reservations.json");
	
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class).getAsJsonObject();
		reader.close();
		
		int count = 0;
		
		for(String status: jsonObject.keySet()) {
			for(JsonElement res: jsonObject.getAsJsonArray(status)) {
				if(((JsonObject)res).get("guestID").getAsString().equals(user.getUserName())) { // if reservation from our guest
					count++;
				}
			}
		}
		if(count==0) {
			return new String[0][7];
		}
		String[][] ret = new String[count][];
		int i = 0;
		for(String status: jsonObject.keySet()) {
			for(JsonElement res: jsonObject.getAsJsonArray(status)) {
				if(((JsonObject)res).get("guestID").getAsString().equals(user.getUserName())) { 
					
					JsonObject resObj = (JsonObject)res;
					
					String roomType = resObj.get("roomType").getAsString();
					String checkInDate = resObj.get("checkInDate").getAsString();
				    String checkOutDate = resObj.get("checkOutDate").getAsString();
			        String comment = resObj.get("comment").getAsString();
			        String price = resObj.get("price").getAsString();
			        String addServices = String.join(", ", gson.fromJson(resObj.get("addServices"), String[].class));
			        
			        
			        String[] resArr = {roomType,checkInDate,checkOutDate,addServices,status,price,comment};
			        ret[i] = resArr;
			        i++;
				}
			}
		}
		return ret;
	}
}
