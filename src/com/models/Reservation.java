package com.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.enums.ReservationStatus;

public class Reservation {
	
	private String checkInDate;
	private String checkOutDate;
	private String roomType;
	private  Room room;
	private  Guest guest;
	private String[] addServices;
	private double price;
	private String comment = "";
	private ReservationStatus status = ReservationStatus.PENDING;
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, String[] addServices, Guest guest) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.addServices = addServices;
		this.guest = guest;
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
		Gson gson = new Gson();
        String jsonString = gson.toJson(Reservation.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);	
		return jsonObject;
	}
}
