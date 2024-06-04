package com.models;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import com.exceptions.NoPricingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.models.enums.ReservationStatus;
import com.service.PricingService;

public class Reservation {
	
	private String checkInDate;
	private String checkOutDate;
	private String roomType;
	private  Room room;
	private  Guest guest;
	private String[] addServices;
	private double price = 0.0;
	private String comment = "";
	private ReservationStatus status = ReservationStatus.PENDING;
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, String[] addServices, Guest guest) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomType = roomType;
		this.addServices = addServices;
		this.guest = guest;	
	}
	public void addPricing(double price) {
		this.price+=price;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getStatus() {
		return status.getStatus();
	}
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	public void cancelReservation() {
		this.status = ReservationStatus.CANCELED;
	}
	public String getComment() {
		return this.comment;
	}
	public Double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
	public JsonObject getJson() {
		Gson gson = new Gson();
        String jsonString = gson.toJson(Reservation.this);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);	
		return jsonObject;
	}
	public String getRoomType() {
		return this.roomType;
	}
	public Room getRoom() {
		return this.room;
	}
	public Guest getGuest() {
		return this.guest;
	}
	public String getCheckInDate() {
		return this.checkInDate;
	}
	public String getCheckOutDate() {
		return this.checkOutDate;
	}
	public String[] getAddServices() {
		return this.addServices;
	}
	public void addAddServices(String[] addServices) {
		String[] arr = new String[this.addServices.length+addServices.length];
        int index = 0;
        for (String element : this.addServices) {
            arr[index++] = element;
        }
        for (String element : addServices) {
            arr[index++] = element;
        }
        this.addServices = arr;
	}
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Reservation that = (Reservation) o;
	        return Double.compare(that.price, price) == 0 &&
	                Objects.equals(checkInDate, that.checkInDate) &&
	                Objects.equals(checkOutDate, that.checkOutDate) &&
	                Objects.equals(roomType, that.roomType) &&
	                Objects.equals(room, that.room) &&
	                Objects.equals(guest, that.guest) &&
	                Arrays.equals(addServices, that.addServices) &&
	                Objects.equals(comment, that.comment) &&
	                status == that.status;
	    }

	    @Override
	    public int hashCode() {
	        int result = Objects.hash(checkInDate, checkOutDate, roomType, room, guest, price, comment, status);
	        result = 31 * result + Arrays.hashCode(addServices);
	        return result;
	    }

}
