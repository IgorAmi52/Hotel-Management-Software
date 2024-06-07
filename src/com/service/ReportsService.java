package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.models.Reservation;
import com.models.Room;
import com.models.Staff;

public class ReportsService {
	
	private static Gson gson = new Gson();
	private static FileReader reader;
	private static FileWriter writer;

	public static void reservationConfirmed(Reservation reservation, double roomPrice) throws IOException {

		createMissingReports(reservation.getCheckOutDate());

		reader = new FileReader("data/reports.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		todaysJson.addProperty("Confirmed", todaysJson.get("Confirmed").getAsInt()+1);
		
		jsonObject.add(todaysDate, todaysJson);
		
		Room room = reservation.getRoom();

		String checkInDate = reservation.getCheckInDate();
		String lastDate = DateLabelFormatter.previousDate(reservation.getCheckOutDate());
		String roomType = room.getType();
		String roomID = room.getID();
		
		Set<String> missingDatesSet = DateLabelFormatter.getDateRange(checkInDate, lastDate);
		
		for(String currentDate: missingDatesSet) {
			JsonObject currentJson = jsonObject.getAsJsonObject(currentDate);
			
			currentJson.getAsJsonObject("rooms").addProperty(roomID, roomPrice);
			
			if(currentJson.getAsJsonObject("roomTypes").has(roomType)) {
				double totalPrice = currentJson.getAsJsonObject("roomTypes").get(roomType).getAsDouble();
				currentJson.getAsJsonObject("roomTypes").addProperty(roomType, totalPrice + roomPrice);
			}
			else {
				currentJson.getAsJsonObject("roomTypes").addProperty(roomType,roomPrice);
			}
			jsonObject.add(currentDate, currentJson);
		}
		
        writer = new FileWriter("data/reports.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	public static void reservationCancelled() throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		reader = new FileReader("data/reports.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		todaysJson.addProperty("Cancelled", todaysJson.get("Cancelled").getAsInt()+1);
		
		jsonObject.add(todaysDate, todaysJson);
		 
		writer = new FileWriter("data/reports.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	public static void reservationRejected() throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		reader = new FileReader("data/reports.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		todaysJson.addProperty("Rejected", todaysJson.get("Rejected").getAsInt()+1);
		
		jsonObject.add(todaysDate, todaysJson);
		 
		writer = new FileWriter("data/reports.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
	}
	public static void cleaningAssigned(Staff cleaner) throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		reader = new FileReader("data/reports.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		JsonObject cleanersJson = todaysJson.getAsJsonObject("cleaners");
		
		String username =cleaner.getUserName();
		
		if(cleanersJson.has(username)) {
			int totalCleans = cleanersJson.get(username).getAsInt();
			cleanersJson.addProperty(username, totalCleans+1);
		}
		else {
			cleanersJson.addProperty(username, 1);
		}
		todaysJson.add("cleaners", cleanersJson);
		jsonObject.add(todaysDate, todaysJson);
		
        writer = new FileWriter("data/reports.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
        
	}
	public static void createMissingReports(String lastDate) throws IOException {
		
		reader = new FileReader("data/reports.json");
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
		reader.close();
		
		String lastDateCreated = jsonObject.get("lastDate").getAsString();

		
		if(!DateLabelFormatter.isFirstDateGreater(lastDate, lastDateCreated)) {
			return;
		}
		Set<String> missingDatesSet = DateLabelFormatter.getDateRange(lastDateCreated, lastDate);
		
        JsonObject dateJson = new JsonObject();
        dateJson.add("cleaners", new JsonObject());
        dateJson.add("rooms", new JsonObject());
        dateJson.add("roomTypes", new JsonObject());
        dateJson.addProperty("Confirmed", 0);
        dateJson.addProperty("Rejected", 0);
        dateJson.addProperty("Cancelled", 0);
        
        for(String currentDate : missingDatesSet) {
        	jsonObject.add(currentDate, dateJson);
        }
        jsonObject.addProperty("lastDate", lastDate);
        
        writer = new FileWriter("data/reports.json");
        writer.write(gson.toJson(jsonObject));
        writer.close();
 
	}
}
