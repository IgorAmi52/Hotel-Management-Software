package com.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.enums.ReservationStatus;
import com.models.enums.RoomType;

public class Reservation {
	
	private String checkInDate;
	private String checkOutDate;
	private RoomType roomType;
	private  String roomID;
	private  String guestID;
	private String[] addServices;
	private double price;
	private String comment = "";
	private ReservationStatus status = ReservationStatus.PENDING;
	
	public Reservation(String checkInDate, String checkOutDate, RoomType roomType, String[] addServices, String guestID) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.addServices = addServices;
		this.guestID = guestID;
		this.price = calculatePrice();
	}
	private double calculatePrice() {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getStatus() {
		return status.getStatus();
	}
	public JsonObject getJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("checkInDate", checkInDate);
		jsonObject.addProperty("checkOutDate", checkOutDate);
		jsonObject.addProperty("price", price);
		jsonObject.addProperty("roomID", roomID);
		jsonObject.addProperty("guestID", guestID);
		jsonObject.addProperty("comment", comment);
		jsonObject.addProperty("roomType", roomType.getType());
		JsonArray serviceArray = new JsonArray();
		for(String service : addServices) {
			serviceArray.add(service);
		}
		jsonObject.add("addServices", serviceArray);
		
		return jsonObject;
	}
}
