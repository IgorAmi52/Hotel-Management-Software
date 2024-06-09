package com.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.exceptions.NoPricingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.models.Reservation;
import com.models.Room;
import com.models.Staff;
import com.models.enums.DataTypes;

public class ReportsService {
	
	private String fromDate="";
	private String toDate="";
	private int confirmedNumber = 0;
	private int cancelledNumber = 0;
	private int rejectedNumber = 0;
	private HashMap<String, Integer> cleaners = new HashMap<String, Integer>();
	private HashMap<String, double[]> rooms = new HashMap<String, double[]>();
	
	private void setData() throws IOException {
		
		this.confirmedNumber = 0;
		this.cancelledNumber = 0;
		this.rejectedNumber = 0;
		this.cleaners.clear();
		this.rooms.clear();
		
		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
		Set<String> dateRange = DateLabelFormatter.getDateRange(this.fromDate, this.toDate);
		
		for(String date: jsonObject.keySet()) {
			if(dateRange.isEmpty()) {
				break;
			}
			if(dateRange.contains(date)) {
				dateRange.remove(date);
				JsonObject dateObject = jsonObject.getAsJsonObject(date);
				this.confirmedNumber += dateObject.get("Confirmed").getAsInt();
				this.cancelledNumber += dateObject.get("Cancelled").getAsInt();
				this.rejectedNumber += dateObject.get("Rejected").getAsInt();
				
				JsonObject roomsObject = dateObject.getAsJsonObject("rooms");
				
				for(String room: roomsObject.keySet()) {
					double price = roomsObject.get(room).getAsDouble();
					if(rooms.containsKey(room)) {
						double[] values = rooms.get(room);
						values[0]++;
						values[1]+= price;
						rooms.put(room, values);
					}
					else {
						double[] values = {1,price};
						rooms.put(room, values);
					}
				}
				JsonObject cleanersObject = dateObject.getAsJsonObject("cleaners");
				
				for(String cleaner: cleanersObject.keySet()) {
					if(cleaners.containsKey(cleaner)) {
						Integer value = cleaners.get(cleaner) + cleanersObject.get(cleaner).getAsInt();
						cleaners.put(cleaner, value);
					}
					else {
						cleaners.put(cleaner, cleanersObject.get(cleaner).getAsInt());
					}
				}
			}
		}
	}
	
	public static Map<String, double[]> getYearlyRoomTypeStatistics(int year) throws IOException{
		
		Map<String, double[]> ret = new HashMap<String, double[]>();
		String[] roomTypes =  RoomService.getRoomTypes();
		for(String type: roomTypes) {
			double[] temp = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			ret.put(type, temp);
		}
		double[] temp = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		ret.put("total", temp);
		
		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
		
		for(String date: jsonObject.keySet()) {
			if(date.equals("lastDate")) {
				continue;
			}
			if(DateLabelFormatter.isDateInYear(year, date)) { //in this year
				
				JsonObject roomTypesObject = jsonObject.getAsJsonObject(date).getAsJsonObject("roomTypes");
				int month = DateLabelFormatter.getMonthAsInt(date);
				
				for(String type: roomTypesObject.keySet()) {
					double profit = roomTypesObject.get(type).getAsDouble();
					double[] typeMonths = ret.get(type);
					typeMonths[month-1]+=profit;
					ret.put(type, typeMonths); //maybe not needed
					
					double[] totalMonths = ret.get("total");
					totalMonths[month-1]+=profit;
				}
			}
		}
		return ret;
	}
	public static Map<String, Integer> getCleanersActivityInLastThirtyDays() throws IOException{
		Map<String, Integer> ret = new HashMap<String, Integer>();
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		String startDate = todaysDate;
		
		for(int i = 0; i < 30; i++) {
			startDate = DateLabelFormatter.previousDate(startDate);
		}
		
		Set<String> intervalSet = DateLabelFormatter.getDateRange(startDate, todaysDate);
		
		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
		
		for(String date: jsonObject.keySet()) {
			if(!intervalSet.contains(date)) {
				continue;
			}
			if(intervalSet.isEmpty()) {
				break;
			}
			intervalSet.remove(date);
			JsonObject cleanersObject = jsonObject.getAsJsonObject(date).getAsJsonObject("cleaners");
			
			for(String cleaner:cleanersObject.keySet()) {
				if(ret.containsKey(cleaner)) {
					Integer temp = ret.get(cleaner) + cleanersObject.get(cleaner).getAsInt();
					ret.put(cleaner, temp);
					continue;
				}
				Integer temp = cleanersObject.get(cleaner).getAsInt();
				ret.put(cleaner, temp);
			}
		}
		return ret;
	}
	public static Map<String, Integer> getReservationStatusesCreatedInLastThirtyDays() throws IOException{
		Map<String, Integer> ret = new HashMap<String, Integer>();
		
		ret.put("Pending", 0);
		ret.put("Confirmed", 0);
		ret.put("Rejected", 0);
		ret.put("Cancelled", 0);
	
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		String startDate = todaysDate;
		
		for(int i = 0; i < 30; i++) {
			startDate = DateLabelFormatter.previousDate(startDate);
		}
		
		Set<String> intervalSet = DateLabelFormatter.getDateRange(startDate, todaysDate);
		
		JsonObject jsonObject = DataAccessService.getData(DataTypes.RESERVATIONS);
		
		for(String status: jsonObject.keySet()) {
			JsonArray statusArray = jsonObject.getAsJsonArray(status);
			
			for(int i = 0; i < statusArray.size(); i++) {
				
				JsonObject reservationObject = statusArray.get(i).getAsJsonObject();
				String creationDate = reservationObject.get("creationDate").getAsString();
				
				if(intervalSet.contains(creationDate)) {
					int temp;
					String currentStatus = status;
					if(status.equals("Archive")) {
						currentStatus = "Confirmed";
					}
					temp = ret.get(currentStatus) + 1;

					ret.put(currentStatus,temp);
				}
			}
		}
		return ret;
	}
	
	public int getConfirmedNumber(String fromDate, String toDate) throws IOException {
		if(!this.fromDate.equals(toDate) || !this.toDate.equals(toDate)){
			this.fromDate = fromDate;
			this.toDate = toDate;
			setData();
		}
		return confirmedNumber;
	}
	public int getCancelledNumber(String fromDate, String toDate) throws IOException {
		if(!this.fromDate.equals(toDate) || !this.toDate.equals(toDate)){
			this.fromDate = fromDate;
			this.toDate = toDate;
			setData();
		}
		return cancelledNumber;
	}
	public int getRejectedNumber(String fromDate, String toDate) throws IOException {
		if(!this.fromDate.equals(toDate) || !this.toDate.equals(toDate)){
			this.fromDate = fromDate;
			this.toDate = toDate;
			setData();
		}
		return rejectedNumber;
	}
	public HashMap<String, Integer> getCleanersActity(String fromDate, String toDate) throws IOException {
		if(!this.fromDate.equals(toDate) || !this.toDate.equals(toDate)){
			this.fromDate = fromDate;
			this.toDate = toDate;
			setData();
		}
		return cleaners;
	}
	public HashMap<String, double[]> getRoomsActivity(String fromDate, String toDate) throws IOException {
		if(!this.fromDate.equals(toDate) || !this.toDate.equals(toDate)){
			this.fromDate = fromDate;
			this.toDate = toDate;
			setData();
		}
		return rooms;
	}
	
	
	public static void reservationConfirmed(Reservation reservation) throws IOException {

		createMissingReports(reservation.getCheckOutDate());

		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
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
			
			double roomPrice = PricingService.getRoomPricingForDate(room, currentDate);
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
		DataAccessService.setData(DataTypes.REPORTS, jsonObject);
	}
	
	public static void reservationCancelled() throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		
		todaysJson.addProperty("Cancelled", todaysJson.get("Cancelled").getAsInt()+1);		
		jsonObject.add(todaysDate, todaysJson);
		 
		DataAccessService.setData(DataTypes.REPORTS, jsonObject);
	}
	
	public static void reservationRejected() throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
		JsonObject todaysJson = jsonObject.getAsJsonObject(todaysDate);
		todaysJson.addProperty("Rejected", todaysJson.get("Rejected").getAsInt()+1);
		
		jsonObject.add(todaysDate, todaysJson);
		 
		DataAccessService.setData(DataTypes.REPORTS, jsonObject);
	}
	
	public static void cleaningAssigned(Staff cleaner) throws IOException {
		
		String todaysDate = DateLabelFormatter.getTodaysDateStr();
		createMissingReports(todaysDate);

		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
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
		
		DataAccessService.setData(DataTypes.REPORTS, jsonObject);
	}
	
	public static void createMissingReports(String lastDate) throws IOException {
		
		JsonObject jsonObject = DataAccessService.getData(DataTypes.REPORTS);
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
        
       DataAccessService.setData(DataTypes.REPORTS, jsonObject);
	}
	
}
